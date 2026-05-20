package itson.org.ghosttracks.negocio.objetosNegocio;

import itson.org.ghosttracks.daos.IPedidosDAO;
import itson.org.ghosttracks.dtos.DatosPagoDTO;
import itson.org.ghosttracks.dtos.NuevoPedidoDTO;
import itson.org.ghosttracks.dtos.PaqueteDTO;
import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.entidades.Pago;
import itson.org.ghosttracks.entidades.Paquete;
import itson.org.ghosttracks.entidades.Pedido;
import itson.org.ghosttracks.enums.EstadoPaquete;
import itson.org.ghosttracks.enums.EstadoPedido;
import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import itson.org.ghosttracks.enums.TipoPago;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import itson.org.ghosttracks.mocks.PedidosMockDAO;
import itson.org.ghosttracks.negocio.adaptador.SkydropxAdapter;
import itson.org.ghosttracks.negocio.fabricas.ProveedorPagoFactory;
import itson.org.ghosttracks.negocio.interfaces.IPaquetesBO;
import itson.org.ghosttracks.negocio.interfaces.IPedidosBO;
import itson.org.ghosttracks.negocio.interfaces.IProveedorEnvios;
import itson.org.ghosttracks.negocio.interfaces.IProveedorPago;
import itson.org.ghosttracks.negocio.mappers.CarritoMapper;
import itson.org.ghosttracks.negocio.mappers.ClienteMapper;
import itson.org.ghosttracks.negocio.mappers.PedidoMapper;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PedidosBO implements IPedidosBO {

    private final IPedidosDAO pedidosDAO;
    private final IPaquetesBO paquetesBO;
    private final IProveedorEnvios proveedorEnvios;
    private static final Logger LOGGER = Logger.getLogger(PedidosBO.class.getName());
 
    public PedidosBO() {
        this.pedidosDAO = new PedidosMockDAO();
        this.paquetesBO = new PaquetesBO();
        this.proveedorEnvios = new SkydropxAdapter();
    }
 
    @Override
    public Pedido guardarPedido(Pedido pedido) throws NegocioException {
        try {
            return pedidosDAO.guardarPedido(pedido);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al guardar el pedido en BD", e);
        }
    }
 
    @Override
    public Pedido actualizarEstado(Long idPedido, EstadoPedido nuevoEstado) throws NegocioException {
        try {
            return pedidosDAO.actualizarEstado(idPedido, nuevoEstado);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al actualizar el pedido en BD", e);
        }
    }
 
    @Override
    public List<Pedido> consultarTodos() throws NegocioException {
        try {
            return pedidosDAO.consultarTodos();
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al consultar pedidos en BD", e);
        }
    }
 
    @Override
    public PedidoDTO generarPedido(NuevoPedidoDTO nuevoPedido) throws NegocioException {
        validarPedido(nuevoPedido);
        procesarPago(nuevoPedido.getDatosPago(), nuevoPedido.getCarrito().getTotal());
 
        try {
            Pedido entidad = construirEntidadPedido(nuevoPedido);
            Pedido guardado = pedidosDAO.guardarPedido(entidad);
 
            LOGGER.log(Level.INFO, "Pedido y Pago registrados con éxito. ID: {0}", guardado.getIdPedido());
 
            PedidoDTO respuesta = new PedidoDTO();
            respuesta.setIdPedido(guardado.getIdPedido());
            respuesta.setEstado(EstadoPedidoDTO.PAGADO);
            return respuesta;
 
        } catch (PersistenciaException e) {
            LOGGER.log(Level.SEVERE, "ERROR CRÍTICO: Cobro realizado pero falló la persistencia.", e);
            throw new NegocioException("El pago se realizó, pero hubo un error al guardar el pedido. Contacte a soporte.", e);
        }
    }
 
    @Override
    public Pedido obtenerPedidoPorId(Long idPedido) throws NegocioException {
        if (idPedido == null) {
            throw new NegocioException("El id del pedido no es válido.");
        }
        try {
            return pedidosDAO.consultarPorId(idPedido);
        } catch (PersistenciaException e) {
            LOGGER.log(Level.WARNING, "ERROR: No se logró consultar el pedido por el ID.", e);
            throw new NegocioException("No se logró consultar el pedido por el ID", e);
        }
    }
 
    @Override
    public Pedido despacharPedido(Long idPedido, Double peso, Double largo, Double ancho, Double alto) throws NegocioException {
        try {
            Pedido pedido = pedidosDAO.consultarPorId(idPedido);
 
            if (pedido.getEstado() == EstadoPedido.ENVIADO) {
                throw new NegocioException("Este pedido ya ha sido procesado y enviado anteriormente.");
            }
 
            Paquete nuevoPaquete = construirPaquete(idPedido, peso, largo, ancho, alto);
            nuevoPaquete.setPedido(pedido);
            pedido.setPaquete(nuevoPaquete);
            pedido.setEstado(EstadoPedido.ENVIADO);
 
            paquetesBO.registrarEmpaque(nuevoPaquete);
            return pedidosDAO.actualizarPedido(pedido);
 
        } catch (PersistenciaException e) {
            LOGGER.log(Level.SEVERE, "Error al intentar despachar el pedido.", e);
            throw new NegocioException("Ocurrió un error en la base de datos al despachar el pedido.", e);
        } catch (NegocioException e) {
            throw e;
        } catch (Exception e) {
            throw new NegocioException("Error al comunicarse con el proveedor de envíos: " + e.getMessage(), e);
        }
    }
 
    @Override
    public List<Pedido> buscarPedidosFiltrados(List<Long> idsClientes, EstadoPedido estado) throws NegocioException {
        try {
            return pedidosDAO.buscarPedidosFiltrados(idsClientes, estado);
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible la consulta de los pedidos especificados", e);
        }
    }
 
   //Métodos auxiliares
 
    private void validarPedido(NuevoPedidoDTO nuevoPedido) throws NegocioException {
        if (nuevoPedido == null
                || nuevoPedido.getCarrito() == null
                || nuevoPedido.getCarrito().getProductos().isEmpty()) {
            throw new NegocioException("No se puede procesar un pedido vacío.");
        }
    }
 
    private void procesarPago(DatosPagoDTO datosPago, Double total) throws NegocioException {
        if (datosPago == null) return;
 
        IProveedorPago estrategia = ProveedorPagoFactory.obtenerEstrategia(datosPago.getMetodoPago());
        estrategia.cobrar(datosPago.getNumeroTarjeta(), total, datosPago.getMetodoPago());
    }
 
    private Pedido construirEntidadPedido(NuevoPedidoDTO nuevoPedido) {
        Pedido entidad = new Pedido();
        entidad.setEstado(EstadoPedido.PAGADO);
        entidad.setFechaPedido(LocalDateTime.now());
 
        if (nuevoPedido.getDatosPago() != null) {
            entidad.setPago(new Pago(
                    TipoPago.TARJETA,
                    nuevoPedido.getCarrito().getTotal(),
                    LocalDateTime.now()
            ));
        }
 
        entidad.setCliente(ClienteMapper.toEntidad(nuevoPedido.getCliente()));
        entidad.setCarrito(CarritoMapper.toEntidad(nuevoPedido.getCarrito()));
        entidad.setDireccionEntrega(PedidoMapper.toDireccionEntrega(nuevoPedido.getDireccionEntrega()));
        entidad.setContacto(PedidoMapper.toContacto(nuevoPedido.getContacto()));
        entidad.setSucursal(PedidoMapper.toSucursal(nuevoPedido.getSucursal()));
 
        return entidad;
    }
 
    private Paquete construirPaquete(Long idPedido, Double peso, Double largo, Double ancho, Double alto) throws Exception {
        PaqueteDTO infoGuia = proveedorEnvios.generarGuiaPaquete(idPedido, peso);
 
        Paquete paquete = new Paquete();
        paquete.setNumeroGuia(infoGuia.getNumeroGuia());
        paquete.setFechaEntregaEstimada(infoGuia.getFechaEntregaEstimada());
        paquete.setEstado(EstadoPaquete.ENVIADO);
        paquete.setFechaEnvio(LocalDateTime.now());
        paquete.setPesoKg(peso);
        paquete.setLargoCm(largo);
        paquete.setAnchoCm(ancho);
        paquete.setAltoCm(alto);
        return paquete;
    }
}
