package itson.org.ghosttracks.persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import itson.org.ghosttracks.conexion.ManejadorConexiones;
import static itson.org.ghosttracks.conexion.ManejadorConexiones.obtenerCodecs;
import itson.org.ghosttracks.daos.IBaseMongoDAO;
import itson.org.ghosttracks.daos.IClientesDAO;
import itson.org.ghosttracks.entidades.Cliente;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * DAO para la gestión de la colección "clientes" en MongoDB.
 * @author emyla
 */
public class ClientesDAO implements IClientesDAO, IBaseMongoDAO {

    private static final String COLECCION = "usuarios";

    @Override
    public MongoDatabase obtenerBaseDatos(MongoClient cliente) {
        return cliente.getDatabase(ManejadorConexiones.BASE_DATOS).withCodecRegistry(obtenerCodecs());
    }

    @Override
    public MongoCollection<Cliente> obtenerColeccion(MongoDatabase baseDatos) {
        return baseDatos.getCollection(COLECCION, Cliente.class);
    }

    @Override
    public Cliente obtenerClientePorId(String idCliente) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Cliente> coleccion = this.obtenerColeccion(baseDatos);

            ObjectId idObj = new ObjectId(idCliente);
            Cliente clienteEncontrado = coleccion.find(Filters.eq("_id", idObj)).first();

            if (clienteEncontrado == null) {
                throw new PersistenciaException("Cliente no encontrado con el ID: " + idCliente);
            }
            
            return clienteEncontrado;
            
        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("El ID del cliente proporcionado no tiene un formato válido para MongoDB.", e);
        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar el cliente por ID.", e);
        }
    }

    @Override
    public Cliente autenticarCliente(String correo, String contrasena) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Cliente> coleccion = this.obtenerColeccion(baseDatos);

            return coleccion.find(
                Filters.and(
                    Filters.eq("correo", correo),
                    Filters.eq("contrasenia", contrasena)
                )
            ).first();
            
        } catch (Exception e) {
            throw new PersistenciaException("Error al conectar con la base de datos para validar credenciales.", e);
        }
    }

    @Override
    public List<Cliente> buscarClientesPorNombre(String nombreCliente) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Cliente> coleccion = this.obtenerColeccion(baseDatos);

            if (nombreCliente == null || nombreCliente.trim().isEmpty()) {
                return new ArrayList<>();
            }

            Bson filtroNombre = Filters.or(
                    Filters.regex("nombre", nombreCliente, "i"),
                    Filters.regex("apellidoPaterno", nombreCliente, "i"),
                    Filters.regex("apellidoMaterno", nombreCliente, "i")
            );

            return coleccion.find(filtroNombre)
                    .into(new ArrayList<>());

        } catch (Exception e) {

            throw new PersistenciaException("Error al buscar clientes por nombre en la base de datos.", e);
        }
    }
}