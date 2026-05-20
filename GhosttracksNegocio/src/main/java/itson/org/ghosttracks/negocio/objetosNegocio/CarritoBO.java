package itson.org.ghosttracks.negocio.objetosNegocio;

import itson.org.ghosttracks.dtos.CarritoDTO;
import itson.org.ghosttracks.dtos.ItemCarritoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.negocio.interfaces.ICarritoBO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.util.ArrayList;

/**
 *
 * @author nafbr
 */
public class CarritoBO implements ICarritoBO {

    private static final double TASA_IVA = 0.16;
    private static final int CANTIDAD_MAXIMA = 100;

    @Override
    public CarritoDTO agregarProducto(CarritoDTO carrito, ProductoDTO producto, Integer cantidad) throws NegocioException {
        if (carrito == null) {
            carrito = new CarritoDTO();
        }
        
        if (carrito.getProductos() == null) {
            carrito.setProductos(new ArrayList<>());
        }

        if (producto == null) {
            throw new NegocioException("El producto no puede ser nulo.");
        }
        if (cantidad == null || cantidad <= 0 || cantidad > CANTIDAD_MAXIMA) {
            throw new NegocioException("Cantidad inválida. Debe ser entre 1 y " + CANTIDAD_MAXIMA + ".");
        }
        if (producto.getStockInicial() == null || cantidad > producto.getStockInicial()) {
            throw new NegocioException("No hay suficiente stock para el producto: " + producto.getTitulo());
        }

        boolean existe = false;
        for (ItemCarritoDTO item : carrito.getProductos()) {
            if (item.getProductoSeleccionado() != null && 
                item.getProductoSeleccionado().getIdProducto().equals(producto.getIdProducto())) {
                
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

        recalcularTotales(carrito);
        return carrito;
    }

    @Override
    public CarritoDTO eliminarProducto(CarritoDTO carrito, Long idProducto) throws NegocioException {
        if (carrito == null || carrito.getProductos() == null || carrito.getProductos().isEmpty()) {
            throw new NegocioException("No hay productos en el carrito para eliminar.");
        }
        
        carrito.getProductos().removeIf(item -> 
                item.getProductoSeleccionado() != null && 
                item.getProductoSeleccionado().getIdProducto().equals(idProducto)
        );
        
        recalcularTotales(carrito);
        return carrito;
    }

    // Métodos auxiliares
    private void recalcularTotales(CarritoDTO carrito) {
        if (carrito == null) return;
        
        if (carrito.getProductos() == null) {
            carrito.setProductos(new ArrayList<>());
        }

        double subtotal = 0.0;
        for (ItemCarritoDTO item : carrito.getProductos()) {
            if (item != null && item.getSubtotal() != null) {
                subtotal += item.getSubtotal();
            }
        }
        double impuestos = subtotal * TASA_IVA;
        carrito.setSubtotal(subtotal);
        carrito.setImpuestos(impuestos);
        carrito.setTotal(subtotal + impuestos);
    }
}