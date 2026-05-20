package itson.org.ghosttracks.daos;

import itson.org.ghosttracks.entidades.Administrador;
import itson.org.ghosttracks.entidades.Cliente;
import itson.org.ghosttracks.entidades.Usuario;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import java.util.List;

/**
 * Interface para el acceso a datos de Usuarios (Administradores y Clientes).
 * @author emyla
 */
public interface IUsuariosDAO {
    
    //  Métodos Genéricos 
    public Usuario obtenerUsuarioPorId(String idUsuario) throws PersistenciaException;
    
    //Métodos de Administrador 
    public Administrador autenticarAdmin(String correo, String contrasenia) throws PersistenciaException;
    
    public Administrador obtenerAdminPorIdEmpleado(Long idEmpleado) throws PersistenciaException;
    
    // Métodos de Cliente 
    public Cliente autenticarCliente(String correo, String contrasenia) throws PersistenciaException;
    
    public Cliente obtenerClientePorId(String idCliente) throws PersistenciaException;
    
    public List<Cliente> buscarClientesPorNombre(String nombreBusqueda) throws PersistenciaException;
    
}