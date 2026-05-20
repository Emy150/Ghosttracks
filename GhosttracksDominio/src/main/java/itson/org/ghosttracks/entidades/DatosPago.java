package itson.org.ghosttracks.entidades;

import java.time.LocalDate;

/**
 *
 * @author emyla
 */
public class DatosPago {
    
    private Long idTarjeta;
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

    public DatosPago(Long idTarjeta, String titularTarjeta, String numeroTrjeta, LocalDate fechaExpiracion, String cvv) {
        this.idTarjeta = idTarjeta;
        this.titularTarjeta = titularTarjeta;
        this.numeroTrjeta = numeroTrjeta;
        this.fechaExpiracion = fechaExpiracion;
        this.cvv = cvv;
    }

    public Long getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(Long idTarjeta) {
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
    
}
