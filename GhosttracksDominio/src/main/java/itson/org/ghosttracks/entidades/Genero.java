package itson.org.ghosttracks.entidades;

import java.util.Objects;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 *
 * @author emyla
 */
public class Genero {

    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idGenero;
    
    private String nombre;

    public Genero() {
    }

    public Genero(String idGenero, String nombre) {
        this.idGenero = idGenero;
        this.nombre = nombre;
    }

    public String getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(String idGenero) {
        this.idGenero = idGenero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idGenero);
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
        final Genero other = (Genero) obj;
        return Objects.equals(this.idGenero, other.idGenero);
    }

    @Override
    public String toString() {
        return "Genero{" + "idGenero=" + idGenero + ", nombre=" + nombre + '}';
    }
}
