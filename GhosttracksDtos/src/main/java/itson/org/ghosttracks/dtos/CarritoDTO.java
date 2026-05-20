package itson.org.ghosttracks.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emyla
 */
public class CarritoDTO {
    
    private List<ItemCarritoDTO> productos = new ArrayList<>();
    private Double subtotal;
    private Double impuestos;
    private Double total;
    
    public CarritoDTO() {
        this.productos = new ArrayList<>();
    }

    public CarritoDTO(List<ItemCarritoDTO> productos, Double subtotal, Double impuestos, Double total) {
        this.productos = (productos != null) ? productos : new ArrayList<>();
        this.subtotal = subtotal;
        this.impuestos = impuestos;
        this.total = total;
    }

    public List<ItemCarritoDTO> getProductos() {
        if (productos == null) {
            productos = new ArrayList<>();
        }
        return productos;
    }

    public void setProductos(List<ItemCarritoDTO> productos) {
        this.productos = (productos != null) ? productos : new ArrayList<>();
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}