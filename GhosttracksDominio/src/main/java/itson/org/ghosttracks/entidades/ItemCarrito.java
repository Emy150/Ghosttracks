package itson.org.ghosttracks.entidades;

import org.bson.codecs.pojo.annotations.BsonProperty;

/**
 *
 * @author oliro
 */
public class ItemCarrito {
    
    @BsonProperty("idProducto")
    private String idProducto;
    
    private Integer cantidad;
    private Double subtotal = 0.0;
    
    public ItemCarrito() {
    }

    public ItemCarrito(String idProducto, Integer cantidad, Double precioProducto) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.calcularSubtotal(precioProducto);
    }

    public void calcularSubtotal(Double precioProducto) {
        if (precioProducto != null && this.cantidad != null) {
            this.subtotal = precioProducto * this.cantidad;
        }
    }


    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
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