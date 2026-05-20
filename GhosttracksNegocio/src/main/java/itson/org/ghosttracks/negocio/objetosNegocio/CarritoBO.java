/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.negocio.objetosNegocio;

import itson.org.ghosttracks.dtos.CarritoDTO;
import itson.org.ghosttracks.dtos.ItemCarritoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.negocio.interfaces.ICarritoBO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;

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
        if (producto == null) {
            throw new NegocioException("El producto no puede ser nulo.");
        }
        if (cantidad == null || cantidad <= 0 || cantidad > CANTIDAD_MAXIMA) {
            throw new NegocioException("Cantidad inválida. Debe ser entre 1 y " + CANTIDAD_MAXIMA + ".");
        }
        if (producto.getStock() == null || cantidad > producto.getStock()) {
            throw new NegocioException("No hay suficiente stock para el producto: " + producto.getNombre());
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

        recalcularTotales(carrito);
        return carrito;
    }

    @Override
    public CarritoDTO eliminarProducto(CarritoDTO carrito, Long idProducto) throws NegocioException {
        if (carrito == null || carrito.getProductos().isEmpty()) {
            throw new NegocioException("No hay productos en el carrito para eliminar.");
        }
        carrito.getProductos().removeIf(item
                -> item.getProductoSeleccionado().getIdProducto().equals(idProducto)
        );
        recalcularTotales(carrito);
        return carrito;
    }

    //Métodos auxiliares
    private void recalcularTotales(CarritoDTO carrito) {
        double subtotal = 0.0;
        for (ItemCarritoDTO item : carrito.getProductos()) {
            if (item.getSubtotal() != null) {
                subtotal += item.getSubtotal();
            }
        }
        double impuestos = subtotal * TASA_IVA;
        carrito.setSubtotal(subtotal);
        carrito.setImpuestos(impuestos);
        carrito.setTotal(subtotal + impuestos);
    }
}
