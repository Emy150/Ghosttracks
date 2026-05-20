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
import itson.org.ghosttracks.entidades.Administrador;
import itson.org.ghosttracks.entidades.Cliente;
import itson.org.ghosttracks.exceptions.PersistenciaException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.bson.types.ObjectId;

/**
 * DAO unificado para manejar Usuarios (Clientes y Administradores) en MongoDB.
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

    // ADMINISTRADORES

    @Override
    public Administrador autenticarAdmin(String correo, String contrasenia) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Usuario> coleccion = this.obtenerColeccion(baseDatos);

            Usuario adminEncontrado = coleccion.find(
                Filters.and(
                    Filters.eq("correo", correo),
                    Filters.eq("contrasenia", contrasenia),
                    Filters.eq("tipoUsuario", "ADMIN")
                )
            ).first();

            if (adminEncontrado != null) {
                LOGGER.fine("DAO: ¡Administrador autenticado exitosamente!");
                return (Administrador) adminEncontrado; 
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new PersistenciaException("Error en el proceso de autenticación de administrador", e);
        }
    }

    @Override
    public Administrador obtenerAdminPorIdEmpleado(Long idEmpleado) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Usuario> coleccion = this.obtenerColeccion(baseDatos);

            Usuario adminEncontrado = coleccion.find(
                Filters.and(
                    Filters.eq("idEmpleado", idEmpleado),
                    Filters.eq("tipoUsuario", "ADMIN")
                )
            ).first();

            if (adminEncontrado == null) {
                throw new PersistenciaException("Administrador no encontrado con el número de empleado: " + idEmpleado);
            }
            return (Administrador) adminEncontrado;
            
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar el administrador por número de empleado.", e);
        }
    }

    // CLIENTES

    @Override
    public Cliente autenticarCliente(String correo, String contrasenia) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Usuario> coleccion = this.obtenerColeccion(baseDatos);

            Usuario clienteEncontrado = coleccion.find(
                Filters.and(
                    Filters.eq("correo", correo),
                    Filters.eq("contrasenia", contrasenia), 
                    Filters.eq("tipoUsuario", "CLIENTE")
                )
            ).first();

            if (clienteEncontrado != null) {
                LOGGER.fine("DAO: ¡Cliente autenticado exitosamente!");
                return (Cliente) clienteEncontrado;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new PersistenciaException("Error en el proceso de autenticación del cliente", e);
        }
    }

    @Override
    public Cliente obtenerClientePorId(String idCliente) throws PersistenciaException {
        try {
            Usuario usuario = this.obtenerUsuarioPorId(idCliente);
            
            if (usuario instanceof Cliente) {
                return (Cliente) usuario;
            } else {
                throw new PersistenciaException("El ID proporcionado existe, pero no pertenece a un Cliente.");
            }
        } catch (PersistenciaException e) {
            throw e;
        }
    }

    @Override
    public List<Cliente> buscarClientesPorNombre(String nombreBusqueda) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Usuario> coleccion = this.obtenerColeccion(baseDatos);

            if (nombreBusqueda == null || nombreBusqueda.trim().isEmpty()) {
                return new ArrayList<>();
            }

            Pattern patron = Pattern.compile(nombreBusqueda.trim(), Pattern.CASE_INSENSITIVE);

            List<Usuario> usuariosEncontrados = coleccion.find(
                Filters.and(
                    Filters.eq("tipoUsuario", "CLIENTE"),
                    Filters.or(
                        Filters.regex("nombre", patron),
                        Filters.regex("apellidoPaterno", patron),
                        Filters.regex("apellidoMaterno", patron)
                    )
                )
            ).into(new ArrayList<>());

            List<Cliente> clientes = new ArrayList<>();
            for (Usuario u : usuariosEncontrados) {
                clientes.add((Cliente) u);
            }
            return clientes;
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar clientes por nombre en la base de datos.", e);
        }
    }
}