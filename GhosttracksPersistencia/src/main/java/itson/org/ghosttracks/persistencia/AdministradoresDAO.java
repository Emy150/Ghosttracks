package itson.org.ghosttracks.persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import itson.org.ghosttracks.conexion.ManejadorConexiones;
import itson.org.ghosttracks.daos.IAdministradoresDAO;
import itson.org.ghosttracks.daos.IBaseMongoDAO;
import itson.org.ghosttracks.entidades.Administrador;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import org.bson.Document; // IMPORTANTE: Agregar esta importación
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Data Access Object para la entidad Administrador utilizando MongoDB.
 * @author emyla
 */
public class AdministradoresDAO implements IAdministradoresDAO, IBaseMongoDAO {

    private static final Logger LOGGER = Logger.getLogger(AdministradoresDAO.class.getName());
    private static final String COLECCION = "usuarios";
    
    @Override
    public MongoDatabase obtenerBaseDatos(MongoClient cliente) {
        // Usamos la base de datos limpia para la consulta nativa de seguridad
        return cliente.getDatabase(ManejadorConexiones.BASE_DATOS);
    }

    @Override
    public MongoCollection<Document> obtenerColeccion(MongoDatabase baseDatos) {
        // Cambiamos temporalmente a Document para saltarnos restricciones estrictas del Codec
        return baseDatos.getCollection(COLECCION, Document.class);
    }
    
    @Override
    public Administrador autenticar(String correo, String contrasenia) throws PersistenciaException {

        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Document> coleccion = this.obtenerColeccion(baseDatos);

            // Patrón Regex para ignorar mayúsculas/minúsculas y espacios
            Pattern regexCorreo = Pattern.compile("^" + Pattern.quote(correo.trim()) + "$", Pattern.CASE_INSENSITIVE);

            // Mensaje de rastreo para ver qué está llegando desde la interfaz de usuario
            LOGGER.info("Buscando en la colección '" + COLECCION + "' el correo: " + correo.trim());

            // Realizamos la búsqueda nativa en formato de Documento BSON
            Document docEncontrado = coleccion.find(
                Filters.and(
                    Filters.regex("correo", regexCorreo),
                    Filters.eq("_t", "ADMINISTRADOR")
                )
            ).first();

            // Si no se encuentra, intentamos buscar SOLO por correo (por si el campo _t tiene discrepancias)
            if (docEncontrado == null) {
                LOGGER.warning("No se encontró con '_t':'ADMINISTRADOR'. Intentando búsqueda flexibilizada solo por correo...");
                docEncontrado = coleccion.find(Filters.regex("correo", regexCorreo)).first();
            }

            if (docEncontrado == null) {
                LOGGER.warning("Intento de autenticación fallido: El correo '" + correo + "' no existe en la colección '" + COLECCION + "'.");
                return null;
            }

            // Extraemos los valores crudos del documento BSON
            String contraseniaBD = docEncontrado.getString("contrasenia");

            // Comparar contraseña de forma exacta
            if (contraseniaBD == null || !contraseniaBD.equals(contrasenia)) {
                LOGGER.warning("Intento de autenticación fallido: Contraseña incorrecta para el usuario " + correo);
                return null;
            }

            // Si todo coincide, construimos manualmente el objeto Administrador para no romper tu lógica de negocio
            Administrador admin = new Administrador();
            admin.setCorreo(docEncontrado.getString("correo"));
            admin.setContrasenia(contraseniaBD);
            admin.setNombre(docEncontrado.getString("nombre"));
            admin.setApellidoPaterno(docEncontrado.getString("apellidoPaterno"));
            admin.setApellidoMaterno(docEncontrado.getString("apellidoMaterno"));
            
            LOGGER.info("¡Administrador '" + admin.getNombre() + "' autenticado con éxito!");
            return admin;

        } catch (Exception e) {
            LOGGER.severe("Error crítico en el proceso de autenticación: " + e.getMessage());
            throw new PersistenciaException("Error al autenticar administrador.", e);
        }
    }
}