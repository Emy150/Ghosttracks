
package itson.org.ghosttracks.dtos;

import itson.org.ghosttracks.enums.TipoPago;
import java.time.LocalDateTime;

/**
 *
 * @author oliro
 */
public class PagoDTO {

    private TipoPago tipo;
    private Double monto;
    private LocalDateTime fechaPago;

    public PagoDTO(TipoPago tipo, Double monto, LocalDateTime fechaPago) {
        this.tipo = tipo;
        this.monto = monto;
        this.fechaPago = fechaPago;
    }

    public TipoPago getTipo() {
        return tipo;
    }

    public Double getMonto() {
        return monto;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }
    
}
