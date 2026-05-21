package itson.org.ghosttracks.daos;

import itson.org.ghosttracks.entidades.Cliente;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import java.util.List;

/**
 * Interfaz para las operaciones de persistencia de Clientes.
 * @author emyla
 */
public interface IClientesDAO {
    
    Cliente obtenerClientePorId(String idCliente) throws PersistenciaException;
    
    Cliente autenticarCliente(String correo, String contrasena) throws PersistenciaException;
    
    List<Cliente> buscarClientesPorNombre(String nombreCliente) throws PersistenciaException ;
}