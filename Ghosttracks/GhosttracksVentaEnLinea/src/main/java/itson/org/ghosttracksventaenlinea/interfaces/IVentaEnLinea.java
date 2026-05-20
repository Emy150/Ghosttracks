package itson.org.ghosttracksventaenlinea.interfaces;

import itson.org.ghosttracks.dtos.CarritoDTO;
import itson.org.ghosttracks.dtos.ClienteDTO;
import itson.org.ghosttracks.dtos.NuevoPedidoDTO;
import itson.org.ghosttracks.dtos.PaqueteDTO;
import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import itson.org.ghosttracksventaenlinea.excepciones.VentaEnLineaException;
import java.util.List;

/**
 *
 * @author nafbr
 */
public interface IVentaEnLinea {

    String obtenerNombreCliente(String idCliente) throws VentaEnLineaException;
    
    List<ProductoDTO> obtenerCatalogo() throws VentaEnLineaException;

    ProductoDTO consultarDetalleProducto(String id) throws VentaEnLineaException;

    ClienteDTO consultarPerfilCliente(String idCliente) throws VentaEnLineaException;
    
    CarritoDTO agregarAlCarrito(CarritoDTO carritoActual, ProductoDTO producto, Integer cantidad) throws VentaEnLineaException;
    
    PedidoDTO confirmarCompra(NuevoPedidoDTO pedido) throws VentaEnLineaException;
    
    PedidoDTO actualizarEstadoPedido(String idPedido, EstadoPedidoDTO nuevoEstado) throws VentaEnLineaException;
    
    CarritoDTO eliminarDelCarrito(CarritoDTO carrito, String idProducto) throws VentaEnLineaException;
    
    List<PedidoDTO> obtenerTodosLosPedidos() throws VentaEnLineaException;
    
    PedidoDTO obtenerPedidoPorID(String idPedido) throws VentaEnLineaException;
    
    ClienteDTO iniciarSesion(String correo, String contrasena) throws VentaEnLineaException;
    
    List<PedidoDTO> consultarPedidosFiltrados(String nombreCliente, EstadoPedidoDTO estado) throws VentaEnLineaException;
    
    PedidoDTO despacharPedidoCliente(String idPedido, Double peso, Double largo, Double ancho, Double alto) throws VentaEnLineaException;

}
