
package itson.org.ghosttracks.entidades;

import itson.org.ghosttracks.enums.TipoPago;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author oliro
 */
public class PagoTarjeta extends Pago {

    private String titularTarjeta;
    private String ultimosCuatroDigitos; 
    private LocalDate fechaExpiracion;
    
    public PagoTarjeta(Double monto, LocalDateTime fechaPago, String titularTarjeta, String ultimosCuatroDigitos, LocalDate fechaExpiracion) {
        super(TipoPago.TARJETA, monto, fechaPago);
        this.titularTarjeta = titularTarjeta;
        this.ultimosCuatroDigitos = ultimosCuatroDigitos;
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getTitularTarjeta() {
        return titularTarjeta;
    }

    public void setTitularTarjeta(String titularTarjeta) {
        this.titularTarjeta = titularTarjeta;
    }

    public String getUltimosCuatroDigitos() {
        return ultimosCuatroDigitos;
    }

    public void setUltimosCuatroDigitos(String ultimosCuatroDigitos) {
        this.ultimosCuatroDigitos = ultimosCuatroDigitos;
    }

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }
    
}
