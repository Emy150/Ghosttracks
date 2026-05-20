package itson.org.ghosttracks.dtos;

import itson.org.ghosttracks.enums.TipoPago;
import java.time.LocalDateTime;

/**
 *
 * @author emyla
 */
public class DatosPagoDTO {
    
    private Long idTarjeta;
    private String titularTarjeta;
    private String numeroTarjeta;
    private String fechaExpiracion;
    private String cvv;
    private TipoPago metodoPago;

    public DatosPagoDTO() {
    }

    public DatosPagoDTO(
            String titularTarjeta, 
            String numeroTrajeta, 
            String fechaExpiracion, 
            String cvv
    ) {
        this.titularTarjeta = titularTarjeta;
        this.numeroTarjeta = numeroTrajeta;
        this.fechaExpiracion = fechaExpiracion;
        this.cvv = cvv;
    }

    public DatosPagoDTO(Long idTarjeta, String titularTarjeta, String numeroTarjeta, String fechaExpiracion, String cvv) {
        this.idTarjeta = idTarjeta;
        this.titularTarjeta = titularTarjeta;
        this.numeroTarjeta = numeroTarjeta;
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

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTrajeta) {
        this.numeroTarjeta = numeroTrajeta;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public TipoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(TipoPago metodoPago) {
        this.metodoPago = metodoPago;
    }
    
       
}
