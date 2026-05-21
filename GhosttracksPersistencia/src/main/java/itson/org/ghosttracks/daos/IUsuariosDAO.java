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
    
}