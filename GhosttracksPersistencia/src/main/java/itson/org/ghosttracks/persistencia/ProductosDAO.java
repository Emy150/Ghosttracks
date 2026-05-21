package itson.org.ghosttracks.persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import itson.org.ghosttracks.conexion.ManejadorConexiones;
import static itson.org.ghosttracks.conexion.ManejadorConexiones.obtenerCodecs;
import itson.org.ghosttracks.daos.IBaseMongoDAO;
import itson.org.ghosttracks.daos.IProductosDAO;
import itson.org.ghosttracks.entidades.Producto;
import itson.org.ghosttracks.enums.EstadoProducto;
import itson.org.ghosttracks.enums.TipoProducto;
import itson.org.ghosttracks.exceptions.PersistenciaException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Implementación del DAO para la gestión de Productos utilizando MongoDB 
 * Básicamente aquí es donde nos peleamos con la base de datos de Mongo
 * para meter, sacar y borrar las rolitas o mercancía de GhostTracks.
 * @author emyla (:3)
 */
public class ProductosDAO implements IProductosDAO, IBaseMongoDAO {

    private static final Logger LOGGER = Logger.getLogger(ProductosDAO.class.getName());
    private static final String COLECCION = "productos"; // Nombre de la colección en Mongo (nuestra tablita de toda la vida pues)

    // Método para conectar con la base de datos de Mongo y meterle los Codecs chilos para que entienda nuestros objetos
    @Override
    public MongoDatabase obtenerBaseDatos(MongoClient cliente) {
        return cliente.getDatabase(ManejadorConexiones.BASE_DATOS).withCodecRegistry(obtenerCodecs());
    }

    // Aquí nomás le decimos a Mongo "e we, pasame la colección de productos pero mapeada como la clase Producto"
    @Override
    public MongoCollection<Producto> obtenerColeccion(MongoDatabase baseDatos) {
        return baseDatos.getCollection(COLECCION, Producto.class);
    }

    @Override
    public Producto registratNuevoProducto(Producto producto) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Producto> coleccion = this.obtenerColeccion(baseDatos);

            coleccion.insertOne(producto); // Agregamos el producto recibido a la base yipiii
            LOGGER.info("Producto registrado, Stock Keeping Unit (o folio): " + producto.getSku()); 
            return producto; // Lo regresamos ya guardado
        } catch (Exception e) {
            LOGGER.severe("Error al registrar nuevo producto en MongoDB: " + e.getMessage());
            throw new PersistenciaException("Error al registrar el producto: " + e.getMessage(), e);
        }
    }

    @Override
    public Producto modificarProducto(Producto producto) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Producto> coleccion = this.obtenerColeccion(baseDatos);

            // Validamos que el producto no sea nulo con el id
            if (producto.getIdProducto() == null || producto.getIdProducto().trim().isEmpty()) {
                throw new PersistenciaException("El producto debe tener un ID para poder modificarse.");
            }
            
            ObjectId idObj = new ObjectId(producto.getIdProducto()); // Pasamos el id de String a ObjectId para q mongo lo digiera sin hacer gestos
            var resultado = coleccion.replaceOne( // reemplazamos un documento...
                    Filters.eq("_id", idObj), // busca el documento que tenga id que coincida con el que recibimos en nuestro objeto Producto
                    producto // le indicamos q inserte este Producto modificado
            );

            if (resultado.getMatchedCount() == 0) { // Le preguntamos si el total de documentos que se encontraron con el filtro es 0...
                // Osea, si no hubo coincidencias y el ID no existía en la BD... dayum
                LOGGER.warning("No se pudo modificar: Producto ID " + producto.getIdProducto() + " no encontrado.");
                throw new PersistenciaException("No se encontró ningún producto con el ID: " + producto.getIdProducto());
            }

            LOGGER.info("PRODUCTO MODIFICADO: ID " + producto.getIdProducto()); // En caso de q si se haya modificado, lanzamos logger de info
            return producto; // Regresamos el producto modificado como buena practica :D
        } catch (IllegalArgumentException e) { 
            throw new PersistenciaException("El ID del producto a modificar no tiene un formato válido.", e);
        } catch (Exception e) {
            throw new PersistenciaException("Error inesperado al intentar modificar el producto.", e);
        }
    }

    @Override
    public Producto eliminarProducto(String idProducto) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Producto> coleccion = this.obtenerColeccion(baseDatos);

            ObjectId idObj = new ObjectId(idProducto);
            // Primero consultamos el producto para tener su info guardada en memoria antes de matarlo, para poder retornarlo al final
            Producto productoEliminar = consultarProductoPorId(idProducto);

            var resultado = coleccion.deleteOne(
                    Filters.eq("_id", idObj) // lo mismo q modificar, buscamos un doc q cumpla con la condición...
                    // el id de ese documento es igual al q recibimos? si lo es, se elimina. BOOOOOM desaparecido
            );

            if (resultado.getDeletedCount() == 0) { // Si borró 0 cosas es que algo salio mal wey
                throw new PersistenciaException("No se pudo eliminar el producto con ID: " + idProducto);
            }

            LOGGER.info("PRODUCTO ELIMINADO: ID " + idProducto);
            return productoEliminar; // Devolvemos el fantasma del producto que borramos 
        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("El ID proporcionado para eliminar no es válido.", e);
        } catch (Exception e) {
            throw new PersistenciaException("Error al eliminar el producto de la base de datos.", e);
        }
    }

    @Override
    public Producto consultarProductoPorId(String idProducto) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Producto> coleccion = this.obtenerColeccion(baseDatos);

            ObjectId idObj = new ObjectId(idProducto); // Lo convertimos a ObjectId de Mongo
            
            // Busca el primero que coincida con el ID y lo regresa relas
            return coleccion.find(Filters.eq("_id", idObj)).first();
        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("El ID del producto no tiene un formato válido para MongoDB.", e);
        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar el producto por ID.", e);
        }
    }

    
    @Override // Trae todo de golpe ordenado por fecha
    public List<Producto> consultarCatalogo() throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Producto> coleccion = this.obtenerColeccion(baseDatos);

            return coleccion.find() // find porq la consulta está sencilla
                .sort(Sorts.descending("fechaRegistro")) // Los ordena de los más nuevecitos a los más viejos
                .into(new ArrayList<>()); // Lo metemos todo dentro de un ArrayList de Java
            
        } catch (Exception e) {
            LOGGER.severe("Error al consultar el catálogo completo: " + e.getMessage());
            throw new PersistenciaException("Error al consultar el catálogo completo: " + e.getMessage(), e);
        }
    }

    // BUSCAR PRODUCTOS CON FILTROS DINÁMICOS: El método más perrón porq el usuario puede mandar filtros o dejar cosas vacías...no alcancé a implementarlo :(
    @Override
    public List<Producto> buscarProductos(String filtro, TipoProducto tipo, EstadoProducto estado) throws PersistenciaException {
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Producto> coleccion = this.obtenerColeccion(baseDatos);

            // Lista mágica donde iremos metiendo las condiciones que sí existan
            List<Bson> filtros = new ArrayList<>();
            
            // Filtro de Texto: Si el usuario escribió algo en la barra de búsqueda...
            if (filtro != null && !filtro.trim().isEmpty()) {
                // Buscamos con una expresión regular (regex) para que encuentre coincidencias
                // aunque escriban en mayúsculas o minúsculas (por eso la "i" de insensitive)
                filtros.add(
                    Filters.or(
                        Filters.regex("titulo", filtro, "i"),  // ¿Coincide con el título del álbum/producto?
                        Filters.regex("artista", filtro, "i"), // ¿Coincide con el artista chilo?
                        Filters.regex("sku", filtro, "i")      // ¿O con el código SKU?
                    )
                );
            }

            // Filtro por tipo: Vinilo, cassette, cd
            if (tipo != null) {
                filtros.add(Filters.eq("tipo", tipo)); // Lo agregamos a la lista de "requisitos"
            }
            
            // Filtro por estado: Si quiere ver solo los que están disponibles, agotados
            if (estado != null) {
                filtros.add(Filters.eq("estado", estado));
            }

            // CONSTRUCCIÓN DEL FILTRO FINAL
            Bson filtroFinal;

            // Si la lista de filtros quedó vacía (osea que el usuario no seleccionó ni escribió nada)...
            if (filtros.isEmpty()) {
                filtroFinal = new Document(); // Un documento vacío significa "jarvis traeme TODO pero en fa papito"
            } else {
                filtroFinal = Filters.and(filtros); // Si sí hay filtros, obligamos a que se cumplan TODOS juntos 
            }

            // Ejecutamos la consulta pasándole nuestro filtro dinámico casero
            return coleccion.find(filtroFinal)
                    .into(new ArrayList<>());

        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("El ID de género no tiene un formato válido.", e);
        } catch (Exception e) {
            LOGGER.severe("Error al buscar productos: " + e.getMessage());
            throw new PersistenciaException("Error al buscar productos.", e);
        }
    }

    
    @Override
    public List<Producto> consultarStockCritico() throws PersistenciaException { // Para ver qué productos ya se van a acabar en GhostTracks y ocupamos surtir
        try (MongoClient cliente = ManejadorConexiones.crearConexion()) {
            MongoDatabase baseDatos = this.obtenerBaseDatos(cliente);
            MongoCollection<Producto> coleccion = this.obtenerColeccion(baseDatos);

            // Filtramos únicamente los productos que estén en peligro, osea, con STOCK_BAJO u AGOTADO
            Bson filtroCritico = Filters.or(
                    Filters.eq("estado", EstadoProducto.STOCK_BAJO),
                    Filters.eq("estado", EstadoProducto.AGOTADO)
            );

            // Los buscamos y los ordenamos de menor a mayor según su stockInicial para ver cuál urge más
            return coleccion.find(filtroCritico)
                    .sort(Sorts.ascending("stockInicial"))
                    .into(new ArrayList<>());

        } catch (Exception e) {
            LOGGER.severe("Error al consultar productos con stock crítico: "+ e.getMessage());
            throw new PersistenciaException("Error al consultar productos con stock crítico.", e);
        }
    }
}