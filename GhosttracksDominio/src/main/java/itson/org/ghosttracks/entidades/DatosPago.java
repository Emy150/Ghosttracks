package itson.org.ghosttracks.entidades;

import java.time.LocalDate;
import java.util.Objects;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 * @author emyla
 */
public class DatosPago {
    
    @BsonId
    @BsonProperty("_id")
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idTarjeta;
    
    private String titularTarjeta;
    private String numeroTrjeta; 
    private LocalDate fechaExpiracion;
    private String cvv;

    public DatosPago() {
    }

    public DatosPago(String titularTarjeta, String numeroTrjeta, LocalDate fechaExpiracion, String cvv) {
        this.titularTarjeta = titularTarjeta;
        this.numeroTrjeta = numeroTrjeta;
        this.fechaExpiracion = fechaExpiracion;
        this.cvv = cvv;
    }

    public DatosPago(String idTarjeta, String titularTarjeta, String numeroTrjeta, LocalDate fechaExpiracion, String cvv) {
        this.idTarjeta = idTarjeta;
        this.titularTarjeta = titularTarjeta;
        this.numeroTrjeta = numeroTrjeta;
        this.fechaExpiracion = fechaExpiracion;
        this.cvv = cvv;
    }

    public String getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(String idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getTitularTarjeta() {
        return titularTarjeta;
    }

    public void setTitularTarjeta(String titularTarjeta) {
        this.titularTarjeta = titularTarjeta;
    }

    public String getNumeroTrjeta() {
        return numeroTrjeta;
    }

    public void setNumeroTrjeta(String numeroTrjeta) {
        this.numeroTrjeta = numeroTrjeta;
    }

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.idTarjeta);
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
        final DatosPago other = (DatosPago) obj;
        return Objects.equals(this.idTarjeta, other.idTarjeta);
    }

    @Override
    public String toString() {
        return "DatosPago{" + "idTarjeta=" + idTarjeta + 
                ", titularTarjeta=" + titularTarjeta + 
                ", numeroTrjeta=" + numeroTrjeta + 
                ", fechaExpiracion=" + fechaExpiracion + 
                ", cvv=" + cvv + '}';
    }

}