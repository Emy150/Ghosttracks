package itson.org.ghosttracks.negocio.interfaces;

import itson.org.ghosttracks.entidades.Cliente;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.util.List;

public interface IClientesBO {
    
    public abstract Cliente obtenerClientePorId(String idCliente) throws NegocioException;
    
    public abstract Cliente iniciarSesion(String correo, String contrasena) throws NegocioException;
    
    public abstract List<String> buscarIdsPorNombre(String nombreCliente) throws NegocioException;
    
}