package itson.org.ghosttracks.bos;

import itson.org.ghosttracks.daos.IPersistencia;
import itson.org.ghosttracks.dtos.AdministradorDTO;
import itson.org.ghosttracks.entidades.Administrador;
import itson.org.ghosttracks.fachada.PersistenciaFachada;
import itson.org.ghosttracks.negocio.adaptador.UsuarioAdapter;
import itson.org.ghosttracks.negocio.interfaces.IUsuariosBO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.util.logging.Logger;

/**
 *
 * @author emyla
 */
public class UsuariosBO implements IUsuariosBO{

    private static final Logger LOGGER = Logger.getLogger(UsuariosBO.class.getName());
    
    private final IPersistencia persistencia;
    private final UsuarioAdapter adapter;

    public UsuariosBO() {
        this.persistencia = PersistenciaFachada.getInstancia();
        this.adapter = new UsuarioAdapter(); 
    }
    
    public UsuariosBO(IPersistencia persistencia, UsuarioAdapter adapter) {
        this.persistencia = persistencia;
        this.adapter = adapter;
    }

    @Override
    public AdministradorDTO autenticarAdmin(String idUsuario, String contrasenia) throws NegocioException {
        
        if (idUsuario == null || idUsuario.trim().isEmpty()) {
            LOGGER.warning("BO: Intento de login con ID de usuario vacío");
            throw new NegocioException("El ID de usuario no puede estar vacío");
        }
        if (contrasenia == null || contrasenia.trim().isEmpty()) {
            LOGGER.warning("BO: Intento de login con contraseña vacía");
            throw new NegocioException("La contraseña no puede estar vacía");
        }

        try {
            Administrador adminEntidad = persistencia.autenticarAdmin(idUsuario, contrasenia);
            
            if (adminEntidad == null) {
                LOGGER.info("BO: Credenciales incorrectas para el usuario: " + idUsuario);
                throw new NegocioException("Credenciales incorrectas o usuario no encontrado");
            }

            LOGGER.fine("BO: Administrador autenticado con éxito :D");
            return adapter.adaptAdministradorToDTO(adminEntidad);

        } catch (NegocioException e) {
            throw e; 
        } catch (Exception e) {
            LOGGER.severe("BO: Ocurrió un error inesperado al intentar autenticar al admin");
            throw new NegocioException("Error interno al intentar iniciar sesión", e);
        }
    }
}
