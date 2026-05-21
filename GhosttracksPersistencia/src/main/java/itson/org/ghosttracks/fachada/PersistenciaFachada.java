package itson.org.ghosttracks.fachada;

import itson.org.ghosttracks.daos.IClientesDAO;
import itson.org.ghosttracks.daos.IPersistencia;
import itson.org.ghosttracks.daos.IProductosDAO;
import itson.org.ghosttracks.daos.IAdministradoresDAO;

import itson.org.ghosttracks.entidades.Administrador;
import itson.org.ghosttracks.entidades.Cliente;
import itson.org.ghosttracks.entidades.Producto;
import itson.org.ghosttracks.enums.EstadoProducto;
import itson.org.ghosttracks.enums.TipoProducto;

import itson.org.ghosttracks.exceptions.PersistenciaException;

import itson.org.ghosttracks.persistencia.ClientesDAO;
import itson.org.ghosttracks.persistencia.ProductosDAO;
import itson.org.ghosttracks.persistencia.AdministradoresDAO;

import java.util.List;
import java.util.logging.Logger;

/**
 * Clase Fachada de Persistencia. Básicamente es el intermediario chilo
 * que conecta todo lo de la base de datos con el resto del sistema.
 * Es como el cadenero de GhostTracks osea literal le pides las cosas a ella
 * y ella va y manda a los DAOs correspondientes para q hagan la chamba lol.
 * * @author emyla 
 */
public class PersistenciaFachada implements IPersistencia {

    private static final Logger LOGGER = Logger.getLogger(PersistenciaFachada.class.getName());
    private static PersistenciaFachada instancia; // La única e inigualable instancia de la fachada (Smn, patrón Singleton, elegancia pura)
    private final IProductosDAO productosDAO; // Interfaz para los productos (las rolitas)
    private final IAdministradoresDAO administradoresDAO; // Para los jefes, los meros meros dueños del circo
    private final IClientesDAO clientesDAO; // Para los mortales q entran a GhostTracks a comprar thingies

    // Constructor PRIVADO. Osea que nadie de afuera puede andar haciendo un "new PersistenciaFachada()" porque se le arma.
    // Aquí inicializamos todos los DAOs de golpe cada q se crea la santa instancia.
    private PersistenciaFachada() {
        this.productosDAO = new ProductosDAO();
        this.administradoresDAO = new AdministradoresDAO();
        this.clientesDAO = new ClientesDAO();
    }

    // El todopoderoso método para obtener la instancia. Si no existe la crea, y si ya existe, pues te da la que ya tiene. No andamos gastando memoria a lo loco :D
    public static PersistenciaFachada getInstancia() {
        if (instancia == null) { // "Si instancia es equivalente a nulo, entonces..."
            instancia = new PersistenciaFachada(); // Creamos la primera y única instancia 
            LOGGER.fine("Fachada: instancia creada"); // Le avisamos al chismoso del LOGGER que ya jaló
        }
        return instancia; // Devolvemos la instancia bien feliz
    }

    // Métodos de los productos

    // Recibe el objeto producto completito, si algo sale mal avienta una excepción bien fea
    @Override
    public Producto registrarProducto(Producto producto) throws PersistenciaException {
        return productosDAO.registratNuevoProducto(producto); // Le pasa la bolita al DAO de productos para que haga la magia
    }

    @Override
    public Producto consultarProductoPorId(String idProducto) throws PersistenciaException {
        return productosDAO.consultarProductoPorId(idProducto);
    }

    // Este wey es exigente, recibe un filtro en texto, el tipo de producto y el estado 
    @Override
    public List<Producto> buscarProductos(String filtro, TipoProducto tipo, EstadoProducto estado) throws PersistenciaException {
        return productosDAO.buscarProductos(filtro, tipo, estado); // El DAO hace un desmadre en la BD para buscar lo que le pediste
    }
    
    // No recibe parámetros wuuuu, te avienta la lista con todo el catálogo
    @Override
    public List<Producto> consultarInventario() throws PersistenciaException {
        return productosDAO.consultarCatalogo(); // Llama al catálogo completito como niño grande
    }

    @Override
    public Producto modificarProducto(Producto producto) throws PersistenciaException {
        return productosDAO.modificarProducto(producto); // modificamos el producto (en realidad lo cambiamos lol
    }

    
    @Override
    public Producto eliminarProducto(String idProducto) throws PersistenciaException { // cuello pasandole el id
        return productosDAO.eliminarProducto(idProducto); // BOOOOOM desaparecido
    }
    
    // Métodos del admin
    @Override
    public Administrador autenticarAdmin(String correo, String contrasenia) throws PersistenciaException {
        LOGGER.fine("Fachada: autenticando admin"); // El LOGGER de chismoso anotando que alguien se quiere loguear
        return administradoresDAO.autenticar(correo, contrasenia); // Mandamos los datos al DAO para validar si si pasa
    }

    // Metodos del cliente

    @Override
    public Cliente obtenerClientePorId(String idCliente) throws PersistenciaException {
        return clientesDAO.obtenerClientePorId(idCliente);
    }

    @Override
    public Cliente autenticarCliente(String correo, String contrasena) throws PersistenciaException {
        return clientesDAO.autenticarCliente(correo, contrasena);
    }

    @Override
    public List<Cliente> buscarClientesPorNombre(String nombreCliente) throws PersistenciaException {
        return clientesDAO.buscarClientesPorNombre(nombreCliente); 
    }
}