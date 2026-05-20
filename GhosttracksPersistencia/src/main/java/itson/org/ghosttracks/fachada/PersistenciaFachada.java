package itson.org.ghosttracks.fachada;

import itson.org.ghosttracks.daos.IClientesDAO;
import itson.org.ghosttracks.daos.IPersistencia;
import itson.org.ghosttracks.daos.IProductosDAO;
import itson.org.ghosttracks.daos.IUsuariosDAO;
import itson.org.ghosttracks.entidades.Administrador;
import itson.org.ghosttracks.entidades.Cliente; 
import itson.org.ghosttracks.entidades.Producto;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import itson.org.ghosttracks.persistencia.ClientesDAO; 
import itson.org.ghosttracks.persistencia.ProductosDAO;
import itson.org.ghosttracks.persistencia.UsuariosDAO;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author emyla
 */
public class PersistenciaFachada implements IPersistencia {
    
    private static final Logger LOGGER = Logger.getLogger(PersistenciaFachada.class.getName());

    private static PersistenciaFachada instancia;
    
    private final IProductosDAO productosDAO;
    private final IUsuariosDAO usuariosDAO;
    private final IClientesDAO clientesDAO; 
    
    private PersistenciaFachada() {
        this.productosDAO = new ProductosDAO();
        this.usuariosDAO = new UsuariosDAO();
        this.clientesDAO = new ClientesDAO(); 
    }
    
    public static PersistenciaFachada getInstancia() {
        if (instancia == null) {
            instancia = new PersistenciaFachada();
            LOGGER.fine("Fachada: ¡Creando la única instancia del Singleton! B)");
        }
        return instancia;
    }

    // MÉTODOS DE PRODUCTOS
    @Override
    public Producto registrarProducto(Producto producto) throws PersistenciaException {
        LOGGER.fine("Fachada: Mandando registrar producto al DAO :P");
        return productosDAO.registratNuevoProducto(producto);
    }

    @Override
    public Producto consultarProductoPorId(String idProducto) throws PersistenciaException {
        LOGGER.fine("Fachada: Buscando producto con id: " + idProducto);
        return productosDAO.consultarProductoPorId(idProducto);
    }

    @Override
    public List<Producto> buscarProductos(String filtro) throws PersistenciaException {
        return productosDAO.buscarProductos(filtro);
    }

    @Override
    public Producto modificarProducto(Producto producto) throws PersistenciaException {
        LOGGER.fine("Fachada: Actualizando datos en el DAO");
        return productosDAO.modificarProducto(producto);
    }

    @Override
    public Producto eliminarProducto(String idProducto) throws PersistenciaException {
        LOGGER.fine("Fachada: Avisando al DAO que borre el producto con id " + idProducto);
        return productosDAO.eliminarProducto(idProducto);
    }
    
    // MÉTODOS DE USUARIOS (ADMIN)
    @Override
    public Administrador autenticarAdmin(String idUsuario, String contrasenia) throws PersistenciaException {
        LOGGER.fine("Fachada: Redirigiendo solicitud de autenticación de admin al DAO");
        return usuariosDAO.autenticarAdmin(idUsuario, contrasenia);
    }

    // MÉTODOS DE CLIENTES 
    @Override
    public Cliente obtenerClientePorId(String idCliente) throws PersistenciaException {
        LOGGER.fine("Fachada: Solicitando al ClientesDAO buscar por ID: " + idCliente);
        return clientesDAO.obtenerClientePorId(idCliente);
    }

    @Override
    public Cliente autenticarCliente(String correo, String contrasena) throws PersistenciaException {
        LOGGER.fine("Fachada: Solicitando al ClientesDAO autenticar cliente: " + correo);
        return clientesDAO.autenticarCliente(correo, contrasena);
    }

    @Override
    public List<String> buscarIdsClientesPorNombre(String nombreCliente) throws PersistenciaException {
        LOGGER.fine("Fachada: Solicitando al ClientesDAO filtrar IDs por nombre: " + nombreCliente);
        return clientesDAO.buscarIdsClientesPorNombre(nombreCliente);
    }
}