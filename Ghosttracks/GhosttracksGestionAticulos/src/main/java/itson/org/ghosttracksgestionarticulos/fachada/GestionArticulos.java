package itson.org.ghosttracksgestionarticulos.fachada;


import itson.org.ghosttracks.bos.ProductosBO;
import itson.org.ghosttracks.bos.UsuariosBO;
import itson.org.ghosttracks.dtos.AdministradorDTO;
import itson.org.ghosttracks.dtos.NuevoProductoDTO;
import itson.org.ghosttracks.dtos.ProductoActualizadoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.negocio.interfaces.IProductosBO;
import itson.org.ghosttracks.negocio.interfaces.IUsuariosBO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import itson.org.ghosttracksgestionarticulos.interfaces.IGestionArticulos;
import java.util.List;
import java.util.logging.Logger;

/**
 * Esta clase es el mero jefe. 
 * Se encarga de recibir todo lo de la pantalla, filtrar las cosas técnicas
 * y luego repartir la chamba a los BOs correspondientes.
 * * @author emyla
 */
public class GestionArticulos implements IGestionArticulos {

    private static final Logger LOGGER = Logger.getLogger(GestionArticulos.class.getName());
    
    private final IProductosBO productosBO;
    private final IUsuariosBO usuariosBO;

    public GestionArticulos() {
        // Inicializamos el BO de productos para tenerlo listo para la acción
        this.productosBO = new ProductosBO();
        this.usuariosBO = new UsuariosBO();
    }

    // PRODUCTOS
    
    @Override
    public ProductoDTO registrarProducto(NuevoProductoDTO nuevoDto) throws NegocioException {
        LOGGER.info("Subsistema: Iniciando el proceso de registro de un producto nuevo");

        // Primero checamos que no nos manden algo vacío desde la interfaz
        if (nuevoDto == null) {
            throw new NegocioException("Error: No llegó ninguna información del producto :c");
        }
        
        // Validación de imagen, q tan pesada está
        if (nuevoDto.getImagen() == null || nuevoDto.getImagen().length == 0) {
            throw new NegocioException("¡Ojo! Es obligatorio subir una imagen para el catálogo");
        }
        
        // No queremos saturar la base de datos con fotos gigantes (máximo 2MB)
        if (nuevoDto.getImagen().length > 2 * 1024 * 1024) {
            LOGGER.warning("Subsistema: Intento de registro con imagen muy pesada");
            throw new NegocioException("La imagen está muy grande, intenta con una de menos de 2MB");
        }

        // Si todo está nice, le pasamos la bola al BO para que valide el negocio (precios, stock, etc.)
        return productosBO.registrarProducto(nuevoDto);
    }

    @Override
    public List<ProductoDTO> consultarInventario(String filtro) throws NegocioException {
        LOGGER.info("Subsistema Fachada: Solicitando inventario completo al BO.");
        return productosBO.buscarProductos(filtro);
    }

    @Override
    public ProductoDTO modificarProducto(ProductoActualizadoDTO actualizadoDto) throws NegocioException {
        // Delegamos la actualización al BO
        LOGGER.info("Subsistema: Solicitando modificación de producto");
        return productosBO.modificarProducto(actualizadoDto);
    }

    @Override
    public ProductoDTO eliminarProducto(String idProducto) throws NegocioException {
        // Mandamos a borrar el producto usando su ID
        LOGGER.info("Subsistema: Solicitando eliminación del producto ID: " + idProducto);
        return productosBO.eliminarProducto(idProducto);
    }
    
    // USUARIOS
    
    @Override
    public AdministradorDTO autenticarAdmin(String correo, String contrasenia) throws NegocioException {
        LOGGER.info("Subsistema: Solicitando autenticación de administrador desde la pantalla");
        // El jefe no se mete en broncas de contraseñas, le avienta la chamba al BO de usuarios
        return usuariosBO.autenticarAdmin(correo, contrasenia);
    }
}