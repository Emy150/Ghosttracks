package itson.org.ghosttracks.persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import itson.org.ghosttracks.conexion.ManejadorConexiones;
import static itson.org.ghosttracks.conexion.ManejadorConexiones.obtenerCodecs;
import itson.org.ghosttracks.daos.IBaseMongoDAO;
import itson.org.ghosttracks.daos.IUsuariosDAO;
import itson.org.ghosttracks.entidades.Usuario;
import itson.org.ghosttracks.exceptions.PersistenciaException;

import java.util.logging.Logger;
import org.bson.types.ObjectId;

/**
 * DAO para manejar Usuarios.
 * @author emyla
 */
public class UsuariosDAO implements IUsuariosDAO, IBaseMongoDAO {

    private static final Logger LOGGER = Logger.getLogger(UsuariosDAO.class.getName());
    private static final String COLECCION = "usuarios";

    @Override
    public MongoDatabase obtenerBaseDatos(MongoClient cliente) {
        return cliente.getDatabase(ManejadorConexiones.BASE_DATOS).withCodecRegistry(obtenerCodecs());
    }

    @Override
    public MongoCollection<Usuario> obtenerColeccion(MongoDatabase baseDatos) {
        return baseDatos.getCollection(COLECCION, Usuario.class);
    }

    @Override
    public Usuario obtenerUsuarioPorId(String idUsuario) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Usuario> coleccion = this.obtenerColeccion(baseDatos);

            ObjectId idObj = new ObjectId(idUsuario);
            Usuario usuarioEncontrado = coleccion.find(Filters.eq("_id", idObj)).first();

            if (usuarioEncontrado == null) {
                throw new PersistenciaException("Usuario no encontrado con el ID: " + idUsuario);
            }
            return usuarioEncontrado;
            
        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("El formato del ID no es válido para MongoDB.", e);
        } catch (Exception e) {
            throw new PersistenciaException("Error inesperado al buscar el usuario", e);
        }
    }

}