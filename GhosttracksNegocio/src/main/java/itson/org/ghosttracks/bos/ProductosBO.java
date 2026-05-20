package itson.org.ghosttracks.bos;

import itson.org.ghosttracks.daos.IPersistencia;
import itson.org.ghosttracks.dtos.NuevoProductoDTO;
import itson.org.ghosttracks.dtos.ProductoActualizadoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.entidades.Producto;
import itson.org.ghosttracks.fachada.PersistenciaFachada;
import itson.org.ghosttracks.negocio.adaptador.ProductoAdapter;
import itson.org.ghosttracks.negocio.interfaces.IProductosBO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author emyla
 */
public class ProductosBO implements IProductosBO {
    
    private static final Logger LOGGER = Logger.getLogger(ProductosBO.class.getName());
    
    private final IPersistencia persistencia; 
    private final ProductoAdapter adapter;

    public ProductosBO() {
        this.persistencia = PersistenciaFachada.getInstancia();
        this.adapter = new ProductoAdapter();
    }
    
    @Override
    public ProductoDTO registrarProducto(NuevoProductoDTO nuevoDto) throws NegocioException {
        try {
            // Validaciones rápidas de negocio antes de ir a la Base de datos
            if (nuevoDto.getPrecio() <= 0) {
                throw new NegocioException("El precio del producto debe ser mayor a 0");
            }
            if (nuevoDto.getStockInicial() <= 0) {
                throw new NegocioException("El stock inicial debe ser mayor a 0");
            }

            // Convertimos el DTO de la pantalla a una Entidad que la Persistencia entienda
            Producto productoEntidad = adapter.adaptNuevoProductoDTOToProducto(nuevoDto);
            
            // Le pedimos a la fachada que lo registre
            Producto registrado = persistencia.registrarProducto(productoEntidad);
            
            // Devolvemos el producto registrado convertido a DTO para la vista
            LOGGER.fine("BO: Producto registrado con éxito mediante la fachada :D");
            return adapter.adaptProductoToProductoDTO(registrado);
            
        } catch (NegocioException e) {
            throw e; // Si es un error nuestro, lo lanzamos tal cual
        } catch (Exception e) {
            LOGGER.severe("BO: Error al intentar registrar el producto :c");
            throw new NegocioException("No se pudo completar el registro del producto", e);
        }
    }

    @Override
    public ProductoDTO modificarProducto(ProductoActualizadoDTO actualizadoDto) throws NegocioException {
        try {
            // Validaciones de negocio
            if (actualizadoDto.getPrecio() <= 0) {
                throw new NegocioException("El precio del producto debe ser mayor a 0");
            }

            // Primero adaptamos el DTO de actualización a nuestra entidad Producto
            Producto productoAModificar = adapter.adaptProductoActualizadoDTOToProducto(actualizadoDto);
            
            // Mandamos a la fachada a que haga el cambio en los datos
            Producto modificado = persistencia.modificarProducto(productoAModificar);
            
            // Regresamos el resultado como DTO para que la interfaz se actualice
            LOGGER.fine("BO: Producto modificado correctamente yey!");
            return adapter.adaptProductoToProductoDTO(modificado);
            
        } catch (NegocioException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.severe("BO: Falló la modificación del producto");
            throw new NegocioException("Error al actualizar el producto", e);
        }
    }

    @Override
    public ProductoDTO eliminarProducto(String idProducto) throws NegocioException {
        try {
            // Mandamos el ID directo a la fachada para que lo borre
            Producto eliminado = persistencia.eliminarProducto(idProducto);
            
            LOGGER.fine("BO: Se eliminó el producto con ID: " + idProducto);
            return adapter.adaptProductoToProductoDTO(eliminado);
            
        } catch (Exception e) {
            LOGGER.severe("BO: No se pudo eliminar el producto con el id proporcionado");
            throw new NegocioException("Error al eliminar el producto", e);
        }
    }

    @Override
    public List<ProductoDTO> buscarProductos(String filtro) throws NegocioException {
        try {
            // Obtenemos la lista de entidades desde la fachada
            List<Producto> productosEntidad = persistencia.buscarProductos(filtro);
            List<ProductoDTO> productosDtos = new ArrayList<>();
            
            // Traducimos cada entidad de la lista a DTO
            for (Producto p : productosEntidad) {
                productosDtos.add(adapter.adaptProductoToProductoDTO(p));
            }
            
            LOGGER.fine("BO: Búsqueda completada con " + productosDtos.size() + " resultados");
            return productosDtos;
            
        } catch (Exception e) {
            LOGGER.severe("BO: Error al realizar la búsqueda con el filtro: " + filtro);
            throw new NegocioException("Error en la consulta de productos", e);
        }
    }

    @Override
    public boolean validarStockMinimo(ProductoDTO productoDto) throws NegocioException {
        // Validación porq no podemos tener inventario negativo o vacío
        if (productoDto == null) {
            throw new NegocioException("No se puede validar un producto nulo");
        }
        
        boolean esValido = productoDto.getStockInicial() > 0;
        if (!esValido) {
            LOGGER.warning("BO: El stock no cumple con el mínimo requerido");
        }
        
        return esValido;
    }

    @Override
    public boolean validarPrecioMinimo(ProductoDTO productoDto) throws NegocioException {
        // El precio siempre debe ser un valor positivo
        if (productoDto == null) {
            throw new NegocioException("Producto nulo en validación de precio");
        }
        
        boolean esValido = productoDto.getPrecio() > 0;
        if (!esValido) {
            LOGGER.warning("BO: El precio asignado no es válido");
        }
        
        return esValido;
    }
}