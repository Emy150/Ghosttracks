package itson.org.ghosttracksventaenlinea.fachada;

import itson.org.ghosttracks.dtos.CarritoDTO;
import itson.org.ghosttracks.dtos.ClienteDTO;
import itson.org.ghosttracks.dtos.ItemCarritoDTO;
import itson.org.ghosttracks.dtos.NuevoPedidoDTO;
import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.entidades.Pedido;
import itson.org.ghosttracks.entidades.Producto;
import itson.org.ghosttracks.enums.EstadoPedido;
import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import itson.org.ghosttracks.negocio.interfaces.IClientesBO;
import itson.org.ghosttracks.negocio.interfaces.IPaquetesBO;
import itson.org.ghosttracks.negocio.interfaces.IPedidosBO;
import itson.org.ghosttracks.negocio.interfaces.IProductosBO;
import itson.org.ghosttracks.negocio.mappers.ClienteMapper;
import itson.org.ghosttracks.negocio.mappers.PedidoMapper;
import itson.org.ghosttracks.negocio.mappers.ProductoMapper;
import itson.org.ghosttracks.negocio.objetosNegocio.ClientesBO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import itson.org.ghosttracks.negocio.objetosNegocio.PaquetesBO;
import itson.org.ghosttracks.negocio.objetosNegocio.PedidosBO;
import itson.org.ghosttracks.negocio.objetosNegocio.ProductosBO;
import itson.org.ghosttracksventaenlinea.excepciones.CodigoErrorVenta;
import itson.org.ghosttracksventaenlinea.excepciones.VentaEnLineaException;
import itson.org.ghosttracksventaenlinea.interfaces.IVentaEnLinea;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nafbr
 */
public class VentaEnLineaFachada implements IVentaEnLinea {

private final IPedidosBO pedidosBO;
    private final IProductosBO productosBO;
    private final IClientesBO clientesBO;
    private final IPaquetesBO paquetesBO;
 
    public VentaEnLineaFachada() {
        this.productosBO = new ProductosBO();
        this.clientesBO = new ClientesBO();
        this.pedidosBO = new PedidosBO();
        this.paquetesBO = new PaquetesBO();
    }
 
    @Override
    public List<ProductoDTO> obtenerCatalogo() throws VentaEnLineaException {
        try {
            List<Producto> productosEntidad = productosBO.obtenerTodos();
            List<ProductoDTO> disponibles = new ArrayList<>();
 
            for (Producto p : productosEntidad) {
                if (p.getStock() != null && p.getStock() > 0) {
                    disponibles.add(ProductoMapper.toDTO(p));
                }
            }
            return disponibles;
        } catch (NegocioException ex) {
            throw new VentaEnLineaException(CodigoErrorVenta.ERROR_PERSISTENCIA, "Error al cargar el catálogo", ex);
        }
    }
 
    @Override
    public ProductoDTO consultarDetalleProducto(Long id) throws VentaEnLineaException {
        if (id == null || id <= 0) {
            throw new VentaEnLineaException(CodigoErrorVenta.DATOS_INVALIDOS, "ID de producto inválido");
        }
        try {
            return ProductoMapper.toDTO(productosBO.obtenerProductoPorId(id));
        } catch (NegocioException ex) {
            throw new VentaEnLineaException(CodigoErrorVenta.PRODUCTO_NO_ENCONTRADO, "No se encontró el producto", ex);
        }
    }
 
    @Override
    public ClienteDTO consultarPerfilCliente(Long idCliente) throws VentaEnLineaException {
        try {
            return ClienteMapper.toDTO(clientesBO.obtenerClientePorId(idCliente));
        } catch (NegocioException ex) {
            throw new VentaEnLineaException(CodigoErrorVenta.CLIENTE_NO_ENCONTRADO, "Error al obtener perfil", ex);
        }
    }
 
    @Override
    public ClienteDTO iniciarSesion(String correo, String contrasena) throws VentaEnLineaException {
        try {
            return ClienteMapper.toDTO(clientesBO.iniciarSesion(correo, contrasena));
        } catch (NegocioException ex) {
            throw new VentaEnLineaException(CodigoErrorVenta.DATOS_INVALIDOS, "Credenciales incorrectas", ex);
        }
    }
 
    @Override
    public String obtenerNombreCliente(Long idCliente) throws VentaEnLineaException {
        try {
            return ClienteMapper.nombreCompleto(clientesBO.obtenerClientePorId(idCliente));
        } catch (NegocioException ex) {
            throw new VentaEnLineaException(CodigoErrorVenta.ERROR_PERSISTENCIA, "No fue posible consultar el nombre del cliente", ex);
        }
    }
 
    @Override
    public CarritoDTO agregarAlCarrito(CarritoDTO carrito, ProductoDTO producto, Integer cantidad) throws VentaEnLineaException {
        if (carrito == null) {
            carrito = new CarritoDTO();
        }
        if (producto == null) {
            throw new VentaEnLineaException(CodigoErrorVenta.DATOS_INVALIDOS, "Producto inválido.");
        }
        if (cantidad == null || cantidad <= 0 || cantidad > 100) {
            throw new VentaEnLineaException(CodigoErrorVenta.DATOS_INVALIDOS, "Cantidad inválida.");
        }
        if (producto.getStock() == null || cantidad > producto.getStock()) {
            throw new VentaEnLineaException(CodigoErrorVenta.STOCK_INSUFICIENTE, "Stock insuficiente para: " + producto.getNombre());
        }
 
        boolean existe = false;
        for (ItemCarritoDTO item : carrito.getProductos()) {
            if (item.getProductoSeleccionado().getIdProducto().equals(producto.getIdProducto())) {
                item.setCantidad(item.getCantidad() + cantidad);
                item.setSubtotal(item.getCantidad() * producto.getPrecio());
                existe = true;
                break;
            }
        }
 
        if (!existe) {
            ItemCarritoDTO nuevoItem = new ItemCarritoDTO();
            nuevoItem.setProductoSeleccionado(producto);
            nuevoItem.setCantidad(cantidad);
            nuevoItem.setSubtotal(cantidad * producto.getPrecio());
            carrito.getProductos().add(nuevoItem);
        }
 
        recalcularTotalesCarrito(carrito);
        return carrito;
    }
 
    @Override
    public CarritoDTO eliminarDelCarrito(CarritoDTO carrito, Long idProducto) throws VentaEnLineaException {
        if (carrito == null || carrito.getProductos().isEmpty()) {
            throw new VentaEnLineaException(CodigoErrorVenta.CARRITO_VACIO, "No hay productos para eliminar.");
        }
        carrito.getProductos().removeIf(item -> item.getProductoSeleccionado().getIdProducto().equals(idProducto));
        recalcularTotalesCarrito(carrito);
        return carrito;
    }
 
    @Override
    public PedidoDTO confirmarCompra(NuevoPedidoDTO nuevoPedido) throws VentaEnLineaException {
        if (nuevoPedido == null || nuevoPedido.getCarrito() == null || nuevoPedido.getCarrito().getProductos().isEmpty()) {
            throw new VentaEnLineaException(CodigoErrorVenta.PEDIDO_INVALIDO, "No se puede procesar un pedido vacío.");
        }
        try {
            return pedidosBO.generarPedido(nuevoPedido);
        } catch (NegocioException e) {
            throw new VentaEnLineaException(CodigoErrorVenta.ERROR_PERSISTENCIA, e.getMessage(), e);
        }
    }
 
    @Override
    public PedidoDTO actualizarEstadoPedido(Long idPedido, EstadoPedidoDTO nuevoEstadoDTO) throws VentaEnLineaException {
        if (idPedido == null || idPedido <= 0 || nuevoEstadoDTO == null) {
            throw new VentaEnLineaException(CodigoErrorVenta.DATOS_INVALIDOS, "Datos de actualización inválidos.");
        }
        try {
            EstadoPedido estadoDominio = PedidoMapper.estadoAEntidad(nuevoEstadoDTO);
            Pedido pedidoActualizado = pedidosBO.actualizarEstado(idPedido, estadoDominio);
            return PedidoMapper.toDTO(pedidoActualizado);
        } catch (NegocioException e) {
            throw new VentaEnLineaException(CodigoErrorVenta.ERROR_PERSISTENCIA, "No se pudo actualizar el estado", e);
        }
    }
 
    @Override
    public PedidoDTO obtenerPedidoPorID(Long idPedido) throws VentaEnLineaException {
        if (idPedido == null || idPedido <= 0) {
            throw new VentaEnLineaException(CodigoErrorVenta.DATOS_INVALIDOS, "ID de pedido inválido.");
        }
        try {
            return PedidoMapper.toDTO(pedidosBO.obtenerPedidoPorId(idPedido));
        } catch (NegocioException ex) {
            throw new VentaEnLineaException(CodigoErrorVenta.ERROR_PERSISTENCIA, "No se encontró el pedido", ex);
        }
    }
 
    @Override
    public List<PedidoDTO> obtenerTodosLosPedidos() throws VentaEnLineaException {
        try {
            List<Pedido> pedidosEntidad = pedidosBO.consultarTodos();
            List<PedidoDTO> listaDtos = new ArrayList<>();
            for (Pedido p : pedidosEntidad) {
                listaDtos.add(PedidoMapper.toDTO(p));
            }
            return listaDtos;
        } catch (NegocioException e) {
            throw new VentaEnLineaException(CodigoErrorVenta.ERROR_PERSISTENCIA, "Error al obtener pedidos", e);
        }
    }
 
    @Override
    public PedidoDTO despacharPedidoCliente(Long idPedido, Double peso, Double largo, Double ancho, Double alto) throws VentaEnLineaException {
        if (idPedido == null || idPedido <= 0) {
            throw new VentaEnLineaException(CodigoErrorVenta.DATOS_INVALIDOS, "ID de pedido inválido para despacho.");
        }
        try {
            return PedidoMapper.toDTO(pedidosBO.despacharPedido(idPedido, peso, largo, ancho, alto));
        } catch (NegocioException ex) {
            throw new VentaEnLineaException(CodigoErrorVenta.ERROR_PERSISTENCIA, "Error al generar el empaque y guía del pedido.", ex);
        }
    }
 
    @Override
    public List<PedidoDTO> consultarPedidosFiltrados(String nombreCliente, EstadoPedidoDTO estadoDTO) throws VentaEnLineaException {
        try {
            List<Long> idsClientes = null;
            if (nombreCliente != null && !nombreCliente.trim().isEmpty()) {
                idsClientes = clientesBO.buscarIdsPorNombre(nombreCliente);
                if (idsClientes.isEmpty()) {
                    return new ArrayList<>();
                }
            }
 
            EstadoPedido estadoEntidad = PedidoMapper.estadoAEntidad(estadoDTO);
            List<Pedido> pedidosFiltrados = pedidosBO.buscarPedidosFiltrados(idsClientes, estadoEntidad);
 
            List<PedidoDTO> dtos = new ArrayList<>();
            for (Pedido p : pedidosFiltrados) {
                dtos.add(PedidoMapper.toDTO(p));
            }
            return dtos;
        } catch (NegocioException ex) {
            throw new VentaEnLineaException(CodigoErrorVenta.ERROR_PERSISTENCIA, "Error al filtrar pedidos", ex);
        }
    }
 
    //Método auxiliar
 
    private void recalcularTotalesCarrito(CarritoDTO carrito) {
        double subtotal = 0.0;
        for (ItemCarritoDTO item : carrito.getProductos()) {
            if (item.getSubtotal() != null) {
                subtotal += item.getSubtotal();
            }
        }
        carrito.setSubtotal(subtotal);
        double impuestos = subtotal * 0.16;
        carrito.setImpuestos(impuestos);
        carrito.setTotal(subtotal + impuestos);
    }
}
