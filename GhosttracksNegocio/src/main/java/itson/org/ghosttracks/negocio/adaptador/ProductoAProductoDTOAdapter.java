
package itson.org.ghosttracks.negocio.adaptador;

import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.entidades.Producto;

/**
 *
 * @author oliro
 */
public class ProductoAProductoDTOAdapter {
    
    public static ProductoDTO adapt(Producto producto) {
        
        ProductoDTO productoDTO = new ProductoDTO( 
                producto.getIdProducto(), 
                producto.getNombre(), 
                producto.getImgProducto(), 
                producto.getTipo(), 
                producto.getArtista(), 
                producto.getGenero(), 
                producto.getSetlist(), 
                producto.getPrecio(), 
                producto.getStock(), 
                producto.getEstado()
        );
        
        return productoDTO;
        
    }
    
}
