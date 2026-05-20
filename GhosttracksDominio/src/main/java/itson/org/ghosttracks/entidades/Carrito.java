
package itson.org.ghosttracks.entidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oliro
 */
public class Carrito {

    private List<ItemCarrito> items = new ArrayList<>();
    private Double subtotal = 0.0;
    private Double impuestos = 0.0;
    private Double total = 0.0;
    private static final double TASA_IMPUESTO = 0.16;
    
    public Carrito() {
    }

    public void calcularTotalGeneral() {
        this.subtotal = 0.0;
        if (items != null) {
            for (ItemCarrito item : items) {
                item.calcularSubtotal();
                this.subtotal += item.getSubtotal();
            }
        }
        
        this.impuestos = this.subtotal * TASA_IMPUESTO;
        this.total = this.subtotal + this.impuestos;
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    public void setItems(List<ItemCarrito> items) {
        this.items = items;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(Double impuestos) {
        this.impuestos = impuestos;
    }
    
}
