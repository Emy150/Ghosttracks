package itson.org.ghosttracks.bos;

import itson.org.ghosttracks.daos.IPersistencia;
import itson.org.ghosttracks.dtos.AdministradorDTO;
import itson.org.ghosttracks.dtos.NuevoProductoDTO;
import itson.org.ghosttracks.dtos.ProductoActualizadoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.entidades.Producto;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import itson.org.ghosttracks.fachada.PersistenciaFachada;
import itson.org.ghosttracks.mappers.ProductoMapper;
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
    
    private final IPersistencia persistencia; // Conector a la Fachada de datos
    private final ProductoMapper mapper; // El traductor de formatos
    private final AdministradoresBO administradoresBO; // Inyección de otro BO para validar permisos

    public ProductosBO() {
        // En lugar de hacer un "new", usamos el patrón Singleton de la Fachada de persistencia
        this.persistencia = PersistenciaFachada.getInstancia();
        this.mapper = new ProductoMapper();
        this.administradoresBO = new AdministradoresBO();
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
            Producto productoEntidad = mapper.fromNuevoDTO(nuevoDto);
            
            // Le pedimos a la fachada que lo registre
            Producto registrado = persistencia.registrarProducto(productoEntidad);
            
            // Devolvemos el producto registrado convertido a DTO para la vista
            LOGGER.fine("BO: Producto registrado con éxito mediante la fachada :D");
            return mapper.toDTO(registrado);
            
        } catch (NegocioException e) {
            throw e; // Si es un error nuestro, lo lanzamos tal cual
        } catch (Exception e) {
            e.printStackTrace(); 
            throw new NegocioException("No se pudo completar el registro del producto");
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
            Producto producto = persistencia.consultarProductoPorId(actualizadoDto.getIdProducto());
            ProductoMapper.updateEntity(producto, actualizadoDto);
            
            Producto modificado = persistencia.modificarProducto(producto); // Mandamos a la fachada a que haga el cambio en los datos
            
            LOGGER.fine("BO: Producto modificado correctamente yey!"); // Regresamos el resultado como DTO para que la interfaz se actualice
            return mapper.toDTO(modificado);
            
        } catch (NegocioException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.severe("BO: Falló la modificación del producto");
            e.printStackTrace();
            throw new NegocioException("Error al actualizar el producto", e);
        }
    }

    @Override
    public ProductoDTO eliminarProducto(String idProducto, String correoAdmin, String contrasenia) throws NegocioException {

        try {

            // Delegamos la validación de seguridad al BO de Administradores 
            AdministradorDTO admin = administradoresBO.validarAutorizacion(correoAdmin, contrasenia);

            // Si el método de arriba nos dice que no existe o la contra está mal... cuello
            if (admin == null) {
                throw new NegocioException("No autorizado");
            }
            
            // Si pasó el filtro, procedemos a decirle a la persistencia que lo borre de las tablas
            Producto eliminado = persistencia.eliminarProducto(idProducto);

            return mapper.toDTO(eliminado);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al eliminar producto", e);
        }
    }
    
    @Override
    public List<ProductoDTO> consultarCatalogoCompleto() throws NegocioException {
        try {
            // Traemos la lista de entidades crudas desde las tablas
            List<Producto> productosEntidad = persistencia.consultarInventario();
            
            // Creamos una lista vacía para llenarla de DTOs aptos para la vista
            List<ProductoDTO> productosDTO = new ArrayList<>();

            // Ciclo que traduce uno por uno de Entidad -> DTO
            for (Producto p : productosEntidad) {
                productosDTO.add(ProductoMapper.toDTO(p));
            }
            LOGGER.fine("BO: Catálogo completo cargado correctamente");
            return productosDTO;

        } catch (Exception e) {
            LOGGER.severe("BO: Error al consultar catálogo completo: " + e.getMessage());
            e.printStackTrace();
            throw new NegocioException("Error al consultar catálogo completo", e);
        }
    }

    @Override
    public List<ProductoDTO> buscarProductos(String filtro) throws NegocioException {
        try {
            // Le pedimos a la persistencia que ejecute la query de búsqueda con base al texto enviado
            List<Producto> productosEntidad =
                    persistencia.buscarProductos(filtro, null, null);

            List<ProductoDTO> productosDtos = new ArrayList<>();

            for (Producto p : productosEntidad) {
                productosDtos.add(ProductoMapper.toDTO(p));
            }

            return productosDtos;

        } catch (Exception e) {
            throw new NegocioException("Error al consultar inventario", e);
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