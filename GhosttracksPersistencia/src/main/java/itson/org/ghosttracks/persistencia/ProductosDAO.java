package itson.org.ghosttracks.persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import itson.org.ghosttracks.conexion.ManejadorConexiones;
import static itson.org.ghosttracks.conexion.ManejadorConexiones.obtenerCodecs;
import itson.org.ghosttracks.daos.IBaseMongoDAO;
import itson.org.ghosttracks.daos.IProductosDAO;
import itson.org.ghosttracks.entidades.Producto;
import itson.org.ghosttracks.enums.EstadoProducto;
import itson.org.ghosttracks.enums.TipoProducto;
import itson.org.ghosttracks.exceptions.PersistenciaException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.types.ObjectId;

/**
 * @author emyla
 */
public class ProductosDAO implements IProductosDAO, IBaseMongoDAO {

    private static final String COLECCION = "productos";
    
    @Override
    public MongoDatabase obtenerBaseDatos(MongoClient cliente) {
        return cliente.getDatabase(ManejadorConexiones.BASE_DATOS).withCodecRegistry(obtenerCodecs());
    }

    @Override
    public MongoCollection<Producto> obtenerColeccion(MongoDatabase baseDatos) {
        return baseDatos.getCollection(COLECCION, Producto.class);
    }
    

    @Override
    public Producto registratNuevoProducto(Producto producto) throws PersistenciaException {
        if (producto == null){
            throw new PersistenciaException("Error, el producto a agregar está vacío.");
        }
        if (producto.getTitulo() == null || producto.getTitulo().trim().isEmpty()) {
            throw new PersistenciaException("El producto debe tener un título válido.");
        }

        try(MongoClient cliente = ManejadorConexiones.crearConexion()){
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Producto> coleccion = this.obtenerColeccion(baseDatos);

            InsertOneResult resultadoInsersion = coleccion.insertOne(producto);
            
            if(!resultadoInsersion.wasAcknowledged()){ 
                throw new PersistenciaException("Error al registrar el producto en MongoDB.");
            }
            
            producto.setIdProducto(resultadoInsersion.getInsertedId().asObjectId().getValue().toHexString());
            
            return producto;
        } catch (Exception e) {
            throw new PersistenciaException("Error inesperado al intentar agregar el producto.", e);
        }
    }

    @Override
    public List<Producto> consultarCatalogo() throws PersistenciaException {
        try(MongoClient cliente = ManejadorConexiones.crearConexion()){
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Producto> coleccion = this.obtenerColeccion(baseDatos);
            
            return coleccion.find().into(new ArrayList<>());
        } catch (Exception e) {
            throw new PersistenciaException("Ocurrió un error al consultar el catálogo de productos.", e);
        }
    }

    @Override
    public Producto consultarProductoPorId(String idProducto) throws PersistenciaException {
        if (idProducto == null || idProducto.trim().isEmpty()) {
            throw new PersistenciaException("El ID del producto a buscar no puede ser nulo o vacío");
        }
        
        if (!ObjectId.isValid(idProducto)) {
            throw new PersistenciaException("El ID del producto no tiene un formato válido para MongoDB.");
        }

        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Producto> coleccion = this.obtenerColeccion(baseDatos);
            
            ObjectId idObj = new ObjectId(idProducto);
            Producto encontrado = coleccion.find(Filters.eq("_id", idObj)).first();
            
            if (encontrado == null) {
                throw new PersistenciaException("No se encontró el producto con el ID: " + idProducto);
            }
            
            return encontrado;
            
        } catch (PersistenciaException pe) {
            throw pe;
            
        } catch (Exception e) {
            throw new PersistenciaException("Error inesperado al intentar buscar el producto.", e);
        }
    }

    @Override
    public Producto modificarProducto(Producto producto) throws PersistenciaException {
        try(MongoClient cliente = ManejadorConexiones.crearConexion()){
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Producto> coleccion = this.obtenerColeccion(baseDatos);
            
            if (producto == null || producto.getIdProducto() == null){
                throw new PersistenciaException("Error, el producto o su ID están vacíos.");
            }
            
            ObjectId idObj = new ObjectId(producto.getIdProducto());
            UpdateResult resultado = coleccion.replaceOne(Filters.eq("_id", idObj), producto);
            
            if(resultado.getMatchedCount() == 0){
                throw new PersistenciaException("No se encontró el producto a modificar.");
            }
            
            return producto;
        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("El ID del producto no tiene un formato válido.", e);
        }
    }

    @Override
    public Producto eliminarProducto(String idProducto) throws PersistenciaException {
        try(MongoClient cliente = ManejadorConexiones.crearConexion()){
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Producto> coleccion = this.obtenerColeccion(baseDatos);
            
            ObjectId idObj = new ObjectId(idProducto);
            Producto eliminado = coleccion.findOneAndDelete(Filters.eq("_id", idObj));
            
            if (eliminado == null) {
                throw new PersistenciaException("No se encontró el producto a eliminar.");
            }
            
            return eliminado;
        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("El ID del producto no tiene un formato válido.", e);
        }
    }

    @Override
    public List<Producto> buscarProductos(String filtro) throws PersistenciaException {
        try(MongoClient cliente = ManejadorConexiones.crearConexion()){
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Producto> coleccion = this.obtenerColeccion(baseDatos);
            
            Pattern patron = Pattern.compile(filtro, Pattern.CASE_INSENSITIVE);
            return coleccion.find(
                    Filters.or(
                            Filters.regex("titulo", patron),
                            Filters.regex("artista", patron)
                    )
            ).into(new ArrayList<>());
        }
    }

    @Override
    public List<Producto> buscarProductoPorTipo(TipoProducto tipo) throws PersistenciaException {
        try(MongoClient cliente = ManejadorConexiones.crearConexion()){
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Producto> coleccion = this.obtenerColeccion(baseDatos);
            
            return coleccion.find(Filters.eq("tipo", tipo)).into(new ArrayList<>());
        }
    }

    @Override
    public List<Producto> buscarPorEstado(EstadoProducto estado) throws PersistenciaException {
        try(MongoClient cliente = ManejadorConexiones.crearConexion()){
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Producto> coleccion = this.obtenerColeccion(baseDatos);
            
            return coleccion.find(Filters.eq("estado", estado)).into(new ArrayList<>());
        }
    }

    @Override
    public List<Producto> consultarStockCritico() throws PersistenciaException {
        try(MongoClient cliente = ManejadorConexiones.crearConexion()){
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Producto> coleccion = this.obtenerColeccion(baseDatos);
            
            return coleccion.find(Filters.lt("stockInicial", 5)).into(new ArrayList<>());
        }
    }

    @Override
    public List<Producto> buscarPorGenero(String idGenero) throws PersistenciaException {
        try(MongoClient cliente = ManejadorConexiones.crearConexion()){
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Producto> coleccion = this.obtenerColeccion(baseDatos);
            
            ObjectId idObj = new ObjectId(idGenero);
            return coleccion.find(Filters.eq("idGenero", idObj)).into(new ArrayList<>());
            
        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("El ID del género no tiene un formato válido.", e);
        }
    }

}