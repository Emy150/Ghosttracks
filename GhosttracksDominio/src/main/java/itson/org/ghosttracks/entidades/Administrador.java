package itson.org.ghosttracks.entidades;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

/**
 *
 * @author cinca
 */
@BsonDiscriminator(value = "ADMIN")
public class Administrador extends Usuario{

    public Administrador() {
        
    }

    public Administrador(String idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String correo, String contrasenia) {
        super(idUsuario, nombre, apellidoPaterno, apellidoMaterno, correo, contrasenia);
    }
}
