package itson.org.sistemaskydropxapi.objetos;

import java.time.LocalDateTime;

/**
 *
 * @author emyla
 */
public class SkydropxPaquete {
    private String numeroGuia;
    private String idReferenciaExterna; 
    private String estado;
    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaEntregaEstimada;
    private Double costoEnvio;

    public SkydropxPaquete() {
        
    }    
    
    public SkydropxPaquete(String numeroGuia, String idReferenciaExterna, String estado, LocalDateTime fechaEnvio, LocalDateTime fechaEntrega, Double costoEnvio) {
        this.numeroGuia = numeroGuia;
        this.idReferenciaExterna = idReferenciaExterna;
        this.estado = estado;
        this.fechaEnvio = fechaEnvio;
        this.fechaEntregaEstimada = fechaEntrega;
        this.costoEnvio = costoEnvio;
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public String getIdReferenciaExterna() {
        return idReferenciaExterna;
    }

    public void setIdReferenciaExterna(String idReferenciaExterna) {
        this.idReferenciaExterna = idReferenciaExterna;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public LocalDateTime getFechaEntregaEstimada() {
        return fechaEntregaEstimada;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntregaEstimada = fechaEntrega;
    }

    public Double getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(Double costoEnvio) {
        this.costoEnvio = costoEnvio;
    }
    
    
}
