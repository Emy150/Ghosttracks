package itson.org.ghosttracks.negocio.interfaces;

import itson.org.ghosttracks.dtos.NuevoProductoDTO;
import itson.org.ghosttracks.dtos.ProductoActualizadoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.util.List;

/**
 *
 * @author nafbr
 */
public interface IProductosBO {
    public abstract ProductoDTO registrarProducto(NuevoProductoDTO nuevoDto) throws NegocioException;
    
    public abstract ProductoDTO modificarProducto(ProductoActualizadoDTO actualizadoDto) throws NegocioException;
    
    public abstract ProductoDTO eliminarProducto(String idProducto, String correoAdmin, String contrasenia) throws NegocioException;
            
    public List<ProductoDTO> consultarCatalogoCompleto() throws NegocioException;
    
    public abstract List<ProductoDTO> buscarProductos(String filtro) throws NegocioException;
    
    public abstract boolean validarStockMinimo(ProductoDTO productoDto) throws NegocioException;
    
    public abstract boolean validarPrecioMinimo(ProductoDTO productoDto) throws NegocioException;
}
