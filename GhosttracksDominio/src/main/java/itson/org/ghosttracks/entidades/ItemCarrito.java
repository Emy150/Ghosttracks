
package itson.org.ghosttracks.entidades;

/**
 *
 * @author oliro
 */
public class ItemCarrito {
    
    private Producto producto;
    private Integer cantidad;
    private Double subtotal = 0.0;
    
    public ItemCarrito() {
    }

    public ItemCarrito(Producto producto, Integer cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public void calcularSubtotal() {
        if (this.producto != null && this.producto.getPrecio() != null && this.cantidad != null) {
            this.subtotal = this.producto.getPrecio() * this.cantidad;
        }
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
    
}
