package itson.org.ghosttracks.daos;

import itson.org.ghosttracks.entidades.Administrador;
import itson.org.ghosttracks.exceptions.PersistenciaException;

/**
 *
 * @author emyla
 */
public interface IAdministradoresDAO {

    public abstract Administrador autenticar(String correo, String contrasenia) throws PersistenciaException;

}
