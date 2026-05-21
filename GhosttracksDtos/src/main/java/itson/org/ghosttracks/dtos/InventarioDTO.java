package itson.org.ghosttracks.dtos;

import java.util.List;

/**
 *
 * @author emyla
 */
public class InventarioDTO {
    
    private List<ProductoDTO> productos;
    private Integer totalResultados;

    public InventarioDTO() {
        
    }

    public List<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }

    public Integer getTotalResultados() {
        return totalResultados;
    }

    public void setTotalResultados(Integer totalResultados) {
        this.totalResultados = totalResultados;
    }
}
