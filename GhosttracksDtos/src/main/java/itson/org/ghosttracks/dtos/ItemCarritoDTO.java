package itson.org.ghosttracks.dtos;

/**
 *
 * @author emy
 */
public class ItemCarritoDTO {
    
    private ProductoDTO productoSeleccionado;
    private Integer cantidad;
    private Double subtotal;

    public ItemCarritoDTO() {
    }

    
    public ItemCarritoDTO(ProductoDTO productoSeleccionado, Integer cantidad, Double subtotal) {
        this.productoSeleccionado = productoSeleccionado;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public ProductoDTO getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(ProductoDTO productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
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
