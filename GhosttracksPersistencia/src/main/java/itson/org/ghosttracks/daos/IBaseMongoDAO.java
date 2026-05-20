package itson.org.ghosttracks.daos;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author emyla
 */
public interface IBaseMongoDAO {
    
    public abstract MongoDatabase obtenerBaseDatos(MongoClient cliente);
    
    public abstract MongoCollection obtenerColeccion(MongoDatabase baseDatos);
}
