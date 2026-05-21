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
 * Clase para aser la autenticacion de los usuarios
 * @author emyla
 */
public class UsuariosBO implements IUsuariosBO {

    private static final Logger LOGGER = Logger.getLogger(UsuariosBO.class.getName());
    
    private final IPersistencia persistencia;
    private final UsuarioAdapter adapter; // el q adapta la entidad a DTO

    /**
     * Constructor normal q usa la vista cuando corre el programa
     */
    public UsuariosBO() {
        // agarramos la estancia del singleton de la fachada
        this.persistencia = PersistenciaFachada.getInstancia();
        this.adapter = new UsuarioAdapter(); 
    }
    
    public UsuariosBO(IPersistencia persistencia, UsuarioAdapter adapter) {
        this.persistencia = persistencia;
        this.adapter = adapter;
    }

    /**
     * METODO PARA ENTRAR AL SISTEMA (LOGIN)
     * Revisa q los datos no esten vacios y valida contra la base de datos
     */
    @Override
    public AdministradorDTO autenticarAdmin(String idUsuario, String contrasenia) throws NegocioException {
        
        // si el usuario es nulo o dejo la cajita en blanco... lo paramos de una
        if (idUsuario == null || idUsuario.trim().isEmpty()) {
            LOGGER.warning("BO: Intento de login con ID de usuario vacio");
            throw new NegocioException("El ID de usuario no puede estar vacio");
        }
        // lo mismo para la contra, no podemos dejar q mande espacios en blanco q flojera
        if (contrasenia == null || contrasenia.trim().isEmpty()) {
            LOGGER.warning("BO: Intento de login con contrasenia vacia");
            throw new NegocioException("La contrasenia no puede estar vacia");
        }

        try {
            // le decimos al dao/fachada q valla a buscar si el admin existe con esa contra
            Administrador adminEntidad = persistencia.autenticarAdmin(idUsuario, contrasenia);
            
            // si la bd nos regresa un null... es porq no coinciden los datos, tons aquí cachamos el error
            if (adminEntidad == null) {
                LOGGER.info("BO: Credenciales incorrectas para el usuario: " + idUsuario);
                throw new NegocioException("Credenciales incorrectas o usuario no encontrado");
            }

            LOGGER.fine("BO: Administrador autenticado con exito :D");
            
            // usamos un adaptador, cambiamos la entidad pesada de la bd a un DTO mas limpio para la pantalla
            return adapter.adaptAdministradorToDTO(adminEntidad);

        } catch (NegocioException e) {
            throw e; 
        } catch (Exception e) {
            LOGGER.severe("BO: Ocurrio un error inesperado al intentar autenticar al admin");
            throw new NegocioException("Error interno al intentar iniciar sesion", e);
        }
    }
}