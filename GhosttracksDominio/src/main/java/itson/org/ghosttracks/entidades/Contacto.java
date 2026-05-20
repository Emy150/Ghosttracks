package itson.org.ghosttracks.entidades;

import java.util.Objects;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 * Entidad de Dominio para la colección de contactos
 * @author emy
 */
public class Contacto {

    @BsonId
    @BsonProperty("_id")
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idContacto;

    private String nombre;
    private String correo;
    private String telefono;

    public Contacto() {
    }

    public Contacto(String idContacto, String nombre, String correo, String telefono) {
        this.idContacto = idContacto;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    public String getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(String idContacto) {
        this.idContacto = idContacto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idContacto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Contacto other = (Contacto) obj;
        return Objects.equals(this.idContacto, other.idContacto);
    }

    @Override
    public String toString() {
        return "Contacto{" + 
               "idContacto='" + idContacto + '\'' + 
               ", nombre='" + nombre + '\'' + 
               ", correo='" + correo + '\'' + 
               ", telefono='" + telefono + '\'' + 
               '}';
    }
}