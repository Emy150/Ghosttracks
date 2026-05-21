package itson.org.ghosttracks.controladores;

import itson.org.ghosttracks.dtos.CarritoDTO;
import itson.org.ghosttracks.dtos.ClienteDTO;
import itson.org.ghosttracks.dtos.ContactoDTO;
import itson.org.ghosttracks.dtos.DatosPagoDTO;
import itson.org.ghosttracks.dtos.DireccionEntregaDTO;
import itson.org.ghosttracks.dtos.NuevoPedidoDTO;
import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.dtos.PedidoDTOBuilder;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.dtos.SucursalDTO;
import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import itson.org.ghosttracks.enums.TipoProducto;
import itson.org.ghosttracks.presentacion.cliente.PantallaCarrito;
import itson.org.ghosttracks.presentacion.cliente.PantallaInicioCliente;
import itson.org.ghosttracks.utilerias.pnlResumenPedido;
import itson.org.ghosttracksventaenlinea.excepciones.VentaEnLineaException;
import itson.org.ghosttracksventaenlinea.fachada.VentaEnLineaFachada;
import itson.org.ghosttracksventaenlinea.interfaces.IVentaEnLinea;
import itson.org.infraestructura.ServicioArchivos;
import itson.org.interfaces.IServicioArchivos;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 * Controlador para gestionar el flujo de la venta en línea y catálogo de clientes.
 * @author oliro, emyla
 */
public class ControlVentaEnLinea {
    
    private final Navegador navegador;
    private final IVentaEnLinea ventaFachada = new VentaEnLineaFachada();
    private final IServicioArchivos servicioArchivos = new ServicioArchivos();
 
    private CarritoDTO carrito;
    private PedidoDTOBuilder pedidoBuilder;
 
    public ControlVentaEnLinea(Navegador nav) {
        this.navegador = nav;
        this.carrito = new CarritoDTO();
        this.pedidoBuilder = new PedidoDTOBuilder();
    }
    
    // NAVEGACIÓN
    public void irAInicio() {
        navegador.irInicioCliente();
    }
 
    public void irAFormularioContacto() {
        navegador.irFormularioContacto();
    }
 
    public void irAFormularioEntrega() {
        navegador.irFormularioEntrega();
    }
 
    public void irASeleccionPago() {
        navegador.irSeleccionMetodoPago();
    }
 
    public void irADetalleProducto(ProductoDTO productoSeleccionado) {
        navegador.irVistaProducto(productoSeleccionado);
    }
    
    // VISTAS
    public void llenarTablaCarrito(PantallaCarrito vista) {
        vista.llenarTabla(carrito);
    }
 
    public void llenarCatalogo(PantallaInicioCliente vista) {
        try {
            List<ProductoDTO> productos = ventaFachada.obtenerCatalogo();
            vista.cargarCatalogo(productos);
        } catch (VentaEnLineaException ex) {
            navegador.mostrarMensaje("Error al llenar el catálogo de productos.", true);
        }
    }
 
    /**
     * Obtiene el catálogo completo de productos disponibles.
     * Útil para cuando necesitamos la lista de datos puro.
     */
    public List<ProductoDTO> obtenerCatalogoCompleto() {
        try {
            return ventaFachada.obtenerCatalogo();
        } catch (VentaEnLineaException ex) {
            navegador.mostrarMensaje("No ha sido posible cargar el catálogo.", true);
            return new ArrayList<>();
        }
    }
 
    public void obtenerCatalogoPorTipo(PantallaInicioCliente vista, TipoProducto tipo) {
        try {
            List<ProductoDTO> productosFiltrados = ventaFachada.obtenerCatalogo().stream()
                    .filter(p -> p.getTipo() != null && p.getTipo().equalsIgnoreCase(tipo.name()))
                    .toList();
            vista.cargarCatalogo(productosFiltrados);
        } catch (VentaEnLineaException ex) {
            navegador.mostrarMensaje("Error al filtrar el catálogo de productos.", true);
        }
    }
 
    public void obtenerCatalogoPorGenero(PantallaInicioCliente vista, String genero) {
        try {
            List<ProductoDTO> productosFiltrados = ventaFachada.obtenerCatalogo().stream()
                    .filter(p -> p.getGenero() != null && p.getGenero().equalsIgnoreCase(genero))
                    .toList();
            vista.cargarCatalogo(productosFiltrados);
        } catch (VentaEnLineaException ex) {
            navegador.mostrarMensaje("Error al filtrar el catálogo por género.", true);
        }
    }
 
    public void llenarResumenPedido(pnlResumenPedido vistaResumen) {
        try {
            vistaResumen.cargarResumen(this.carrito);
        } catch (Exception ex) {
            navegador.mostrarMensaje("Error al cargar el resumen del pedido.", true);
        }
    }
    
    public ImageIcon obtenerIconoProducto(ProductoDTO producto, int ancho, int alto) {
        byte[] bytes = (producto != null) ? producto.getImg() : null;
        return servicioArchivos.crearIconoDesdeBytes(bytes, ancho, alto);
    }
    
    // COMPRA
    public void guardarDatosEntrega(DireccionEntregaDTO dto) {
        pedidoBuilder.setDireccionEntrega(dto);
    }
 
    public void guardarDatosContacto(ContactoDTO dto) {
        pedidoBuilder.setContacto(dto);
    }
 
    public void guardarMetodoPago(DatosPagoDTO dto) {
        pedidoBuilder.setDatosPago(dto);
    }
 
    public void procesarPedido() {
        if (!SesionUsuario.getInstancia().haySesionActiva()) {
            navegador.mostrarMensaje("Por favor, inicia sesión para terminar tu compra.", true);
            return;
        }
 
        try {
            ClienteDTO clienteLogueado = SesionUsuario.getInstancia().getCliente();
 
            SucursalDTO sucursal = new SucursalDTO();
            sucursal.setNombre("Obreyork 1");
 
            NuevoPedidoDTO nuevoPedido = this.pedidoBuilder
                    .setCliente(clienteLogueado)
                    .setSucursal(sucursal)
                    .setCarrito(this.carrito)
                    .setEstado(EstadoPedidoDTO.PENDIENTE)
                    .build();
 
            PedidoDTO pedidoGenerado = ventaFachada.confirmarCompra(nuevoPedido);
 
            navegador.mostrarMensaje("¡Compra realizada con éxito! Pedido #" + pedidoGenerado.getIdPedido(), false);
 
            this.carrito = new CarritoDTO();
            this.pedidoBuilder.reset();
 
            navegador.irPedidoConfirmado(pedidoGenerado);
 
        } catch (VentaEnLineaException ex) {
            navegador.mostrarMensaje("No pudimos procesar tu compra: " + ex.getMessage(), true);
        }
    }
    
    // CARRITO
    public void agregarProductoCarrito(ProductoDTO producto, Integer cantidad) {
        try {
            this.carrito = ventaFachada.agregarAlCarrito(this.carrito, producto, cantidad);
        } catch (VentaEnLineaException ex) {
            navegador.mostrarMensaje("No ha sido posible agregar el producto al carrito.", true);
        }
    }
 
    public void eliminarProductoCarrito(String idProducto) {
        try {
            this.carrito = ventaFachada.eliminarDelCarrito(this.carrito, idProducto);
        } catch (VentaEnLineaException ex) {
            navegador.mostrarMensaje("Error al eliminar el producto del carrito.", true);
        }
    }
    
    // UTILIDAD
    public void mostrarMensaje(String mensaje, boolean esError) {
        navegador.mostrarMensaje(mensaje, esError);
    }
}