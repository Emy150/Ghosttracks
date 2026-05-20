package itson.org.ghosttracks.persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import itson.org.ghosttracks.conexion.ManejadorConexiones;
import static itson.org.ghosttracks.conexion.ManejadorConexiones.obtenerCodecs;
import itson.org.ghosttracks.daos.IBaseMongoDAO;
import itson.org.ghosttracks.daos.IPedidosDAO;
import itson.org.ghosttracks.entidades.Pedido;
import itson.org.ghosttracks.enums.EstadoPedido;
import itson.org.ghosttracks.exceptions.PersistenciaException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * DAO para la gestión de la colección "pedidos" en MongoDB.
 * @author nafbr
 */
public class PedidosDAO implements IPedidosDAO, IBaseMongoDAO {

    private static final Logger LOGGER = Logger.getLogger(PedidosDAO.class.getName());
    private static final String COLECCION = "pedidos";

    @Override
    public MongoDatabase obtenerBaseDatos(MongoClient cliente) {
        return cliente.getDatabase(ManejadorConexiones.BASE_DATOS).withCodecRegistry(obtenerCodecs());
    }

    @Override
    public MongoCollection<Pedido> obtenerColeccion(MongoDatabase baseDatos) {
        return baseDatos.getCollection(COLECCION, Pedido.class);
    }

    @Override
    public Pedido guardarPedido(Pedido pedido) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Pedido> coleccion = this.obtenerColeccion(baseDatos);

            coleccion.insertOne(pedido);
            LOGGER.log(Level.INFO, "ENTIDAD PERSISTIDA: Pedido guardado exitosamente");
            
            return pedido;
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al intentar persistir el pedido en MongoDB", e);
            throw new PersistenciaException("Error al guardar el pedido en BD: " + e.getMessage(), e);
        }
    }

    @Override
    public Pedido actualizarEstado(String idPedido, EstadoPedido nuevoEstado) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Pedido> coleccion = this.obtenerColeccion(baseDatos);

            ObjectId idObj = new ObjectId(idPedido);
            
            var resultado = coleccion.updateOne(
                Filters.eq("_id", idObj),
                Updates.set("estado", nuevoEstado)
            );

            if (resultado.getMatchedCount() == 0) {
                LOGGER.log(Level.WARNING, "No se pudo actualizar: Pedido ID {0} no encontrado.", idPedido);
                throw new PersistenciaException("No se encontró ningún pedido con el ID: " + idPedido);
            }

            LOGGER.log(Level.INFO, "Estado actualizado correctamente para el pedido {0}", idPedido);
            
            return consultarPorId(idPedido);
            
        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("El ID del pedido proporcionado no tiene un formato válido para MongoDB.", e);
        } catch (Exception e) {
            throw new PersistenciaException("Error inesperado al actualizar el estado del pedido.", e);
        }
    }

    @Override
    public List<Pedido> consultarTodos() throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Pedido> coleccion = this.obtenerColeccion(baseDatos);

            return coleccion.find().into(new ArrayList<>());
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al recuperar la lista de pedidos desde MongoDB", e);
            throw new PersistenciaException("Error al consultar los pedidos: " + e.getMessage(), e);
        }
    }

    @Override
    public Pedido consultarPorId(String idPedido) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Pedido> coleccion = this.obtenerColeccion(baseDatos);

            ObjectId idObj = new ObjectId(idPedido);
            Pedido pedidoEncontrado = coleccion.find(Filters.eq("_id", idObj)).first();

            if (pedidoEncontrado == null) {
                LOGGER.log(Level.WARNING, "Consulta fallida: Pedido {0} no existe.", idPedido);
                throw new PersistenciaException("Pedido no encontrado: " + idPedido);
            }
            return pedidoEncontrado;
            
        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("El ID del pedido proporcionado no tiene un formato válido para MongoDB.", e);
        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar el pedido por ID.", e);
        }
    }

    @Override
    public Pedido actualizarPedido(Pedido pedidoActualizado) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Pedido> coleccion = this.obtenerColeccion(baseDatos);

            if (pedidoActualizado.getIdPedido() == null || pedidoActualizado.getIdPedido().trim().isEmpty()) {
                throw new PersistenciaException("El pedido debe tener un ID para poder actualizarse.");
            }

            ObjectId idObj = new ObjectId(pedidoActualizado.getIdPedido());
            
            var resultado = coleccion.replaceOne(Filters.eq("_id", idObj), pedidoActualizado);

            if (resultado.getMatchedCount() == 0) {
                LOGGER.log(Level.WARNING, "No se pudo actualizar: Pedido ID {0} no encontrado.", pedidoActualizado.getIdPedido());
                throw new PersistenciaException("No se encontró ningún pedido con el ID: " + pedidoActualizado.getIdPedido());
            }

            return pedidoActualizado;
            
        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("El ID del pedido a actualizar no tiene un formato válido.", e);
        } catch (Exception e) {
            throw new PersistenciaException("Error inesperado al intentar reemplazar el pedido.", e);
        }
    }

    @Override
    public List<Pedido> buscarPedidosFiltrados(List<String> idsClientes, EstadoPedido estado) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Pedido> coleccion = this.obtenerColeccion(baseDatos);

            List<Bson> filtros = new ArrayList<>();

            if (idsClientes != null && !idsClientes.isEmpty()) {
                List<ObjectId> objectIdsClientes = new ArrayList<>();
                for (String id : idsClientes) {
                    objectIdsClientes.add(new ObjectId(id));
                }
                filtros.add(Filters.in("idCliente", objectIdsClientes)); 
            }

            if (estado != null) {
                filtros.add(Filters.eq("estado", estado));
            }

            Bson filtroFinal = filtros.isEmpty() ? new Document() : Filters.and(filtros);

            return coleccion.find(filtroFinal).into(new ArrayList<>());
            
        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("Alguno de los IDs de cliente proporcionados no tiene un formato válido.", e);
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar pedidos filtrados en la base de datos.", e);
        }
    }
}