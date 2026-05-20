package itson.org.ghosttracks.negocio.interfaces;

import itson.org.ghosttracks.dtos.AdministradorDTO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;

/**
 *
 * @author emyla
 */
public interface IUsuariosBO {
    
    public abstract AdministradorDTO autenticarAdmin(String idUsuario, String contrasenia) throws NegocioException;
    
}
