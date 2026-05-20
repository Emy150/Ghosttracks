package itson.org.ghosttracks.daos;

import itson.org.ghosttracks.entidades.Producto;
import itson.org.ghosttracks.enums.EstadoProducto;
import itson.org.ghosttracks.enums.TipoProducto;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import java.util.List;

/**
 *
 * @author emyla
 */
public interface IProductosDAO {
    
    public abstract Producto registratNuevoProducto(Producto producto) throws PersistenciaException;
    
    public abstract Producto modificarProducto(Producto producto) throws PersistenciaException;
    
    public abstract Producto eliminarProducto(String idProducto) throws PersistenciaException;
    
    public abstract Producto consultarProductoPorId(String idProducto) throws PersistenciaException;
    
    public abstract List<Producto> consultarCatalogo() throws PersistenciaException;
    
    public abstract List<Producto> buscarProductos(String filtro) throws PersistenciaException;
    
    public abstract List<Producto> buscarProductoPorTipo(TipoProducto tipo) throws PersistenciaException;
    
    public abstract List<Producto> buscarPorEstado(EstadoProducto estado) throws PersistenciaException;
    
    public abstract List<Producto> buscarPorGenero(String idGenero) throws PersistenciaException;
    
    public abstract List<Producto> consultarStockCritico() throws PersistenciaException;
    
}
