package itson.org.ghosttracks.daos;

import itson.org.ghosttracks.entidades.Administrador;
import itson.org.ghosttracks.entidades.Cliente;
import itson.org.ghosttracks.entidades.Producto;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import java.util.List;

/**
 *
 * @author emyla
 */
public interface IPersistencia {
    
    // Métodos de los productos
    public Producto registrarProducto(Producto producto) throws PersistenciaException;
    
    public Producto consultarProductoPorId(String idProducto) throws PersistenciaException;
    
    public List<Producto> buscarProductos(String filtro) throws PersistenciaException;
    
    public Producto modificarProducto(Producto producto) throws PersistenciaException;
    
    public Producto eliminarProducto(String idProducto) throws PersistenciaException;
    
    // Métodos de usuario 
    public Administrador autenticarAdmin(String idUsuario, String contrasenia) throws PersistenciaException;

    // Métodos de Clientes 
    public Cliente obtenerClientePorId(String idCliente) throws PersistenciaException;

    public Cliente autenticarCliente(String correo, String contrasena) throws PersistenciaException;

    public List<String> buscarIdsClientesPorNombre(String nombreCliente) throws PersistenciaException;
}