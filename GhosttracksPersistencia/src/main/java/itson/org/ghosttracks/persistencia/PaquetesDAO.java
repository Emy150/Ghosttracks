package itson.org.ghosttracks.persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import itson.org.ghosttracks.conexion.ManejadorConexiones;
import static itson.org.ghosttracks.conexion.ManejadorConexiones.obtenerCodecs;
import itson.org.ghosttracks.daos.IBaseMongoDAO;
import itson.org.ghosttracks.daos.IPaquetesDAO;
import itson.org.ghosttracks.entidades.Paquete;
import itson.org.ghosttracks.exceptions.PersistenciaException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.types.ObjectId;

/**
 * DAO para manejar las operaciones de la colección "paquetes" en MongoDB.
 * @author emyla
 */
public class PaquetesDAO implements IPaquetesDAO, IBaseMongoDAO {

    private static final Logger LOGGER = Logger.getLogger(PaquetesDAO.class.getName());
    private static final String COLECCION = "paquetes";

    @Override
    public MongoDatabase obtenerBaseDatos(MongoClient cliente) {
        return cliente.getDatabase(ManejadorConexiones.BASE_DATOS).withCodecRegistry(obtenerCodecs());
    }

    @Override
    public MongoCollection<Paquete> obtenerColeccion(MongoDatabase baseDatos) {
        return baseDatos.getCollection(COLECCION, Paquete.class);
    }

    @Override
    public Paquete agregarPaquete(Paquete paquete) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Paquete> coleccion = this.obtenerColeccion(baseDatos);

            coleccion.insertOne(paquete);
            LOGGER.log(Level.INFO, "Paquete guardado exitosamente con guía {0}", paquete.getNumeroGuia());
            
            return paquete;
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al guardar el paquete en MongoDB", e);
            throw new PersistenciaException("Error al guardar el paquete: " + e.getMessage(), e);
        }
    }

    @Override
    public Paquete buscarPorId(String idPaquete) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Paquete> coleccion = this.obtenerColeccion(baseDatos);

            ObjectId idObj = new ObjectId(idPaquete);
            Paquete paqueteEncontrado = coleccion.find(Filters.eq("_id", idObj)).first();

            if (paqueteEncontrado == null) {
                throw new PersistenciaException("No se encontró ningún paquete con el ID: " + idPaquete);
            }
            return paqueteEncontrado;
            
        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("El formato del ID de paquete no es válido para MongoDB.", e);
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar el paquete por ID en la base de datos.", e);
        }
    }

    @Override
    public Paquete buscarPorGuia(String numeroGuia) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Paquete> coleccion = this.obtenerColeccion(baseDatos);

            Paquete paqueteEncontrado = coleccion.find(Filters.eq("numeroGuia", numeroGuia)).first();

            if (paqueteEncontrado == null) {
                throw new PersistenciaException("No se encontró ningún paquete con la guía: " + numeroGuia);
            }
            return paqueteEncontrado;
            
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar el paquete por número de guía.", e);
        }
    }

    @Override
    public Paquete actualizarPaquete(Paquete paquete) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Paquete> coleccion = this.obtenerColeccion(baseDatos);

            if (paquete.getIdPaquete() == null || paquete.getIdPaquete().trim().isEmpty()) {
                throw new PersistenciaException("El paquete debe tener un ID para poder actualizarse.");
            }

            ObjectId idObj = new ObjectId(paquete.getIdPaquete());
            
            var resultado = coleccion.replaceOne(Filters.eq("_id", idObj), paquete);

            if (resultado.getMatchedCount() == 0) {
                throw new PersistenciaException("Error al actualizar: Paquete no encontrado en la base de datos.");
            }
            
            LOGGER.log(Level.INFO, "Paquete con ID {0} actualizado correctamente.", paquete.getIdPaquete());
            return paquete;
            
        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("El formato del ID del paquete a actualizar no es válido.", e);
        } catch (Exception e) {
            throw new PersistenciaException("Error inesperado al intentar actualizar el paquete.", e);
        }
    }

    @Override
    public List<Paquete> obtenerTodos() throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Paquete> coleccion = this.obtenerColeccion(baseDatos);

            return coleccion.find().into(new ArrayList<>());
            
        } catch (Exception e) {
            throw new PersistenciaException("Error al obtener la lista de todos los paquetes.", e);
        }
    }
}