package itson.org.ghosttracks.conexion;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import itson.org.ghosttracks.entidades.Administrador;
import itson.org.ghosttracks.entidades.Cliente;
import itson.org.ghosttracks.entidades.Usuario;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 *
 * @author emy
 */
public class ManejadorConexiones {
    
    public final static String CONEXION = "mongodb://localhost:27017";
    
    public final static String BASE_DATOS = "Ghost_Tracks";
    
    public static MongoClient crearConexion(){
        MongoClient cliente = MongoClients.create(CONEXION);
        return cliente;
    }
    
    public static CodecRegistry obtenerCodecs() {
        PojoCodecProvider proveedorPOJO = PojoCodecProvider.builder()
                .register(Usuario.class, Administrador.class, Cliente.class)
                .automatic(true)
                .build();

        return fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(proveedorPOJO)
        );
    }
}

