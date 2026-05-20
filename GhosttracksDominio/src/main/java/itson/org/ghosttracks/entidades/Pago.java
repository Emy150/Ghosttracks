
package itson.org.ghosttracks.entidades;

import itson.org.ghosttracks.enums.TipoPago;
import java.time.LocalDateTime;

/**
 *
 * @author oliro
 */
public class Pago {

    private TipoPago tipo;
    private Double monto;
    private LocalDateTime fechaPago;
    
    public Pago(TipoPago tipo, Double monto, LocalDateTime fechaPago) {
        this.tipo = tipo;
        this.monto = monto;
        this.fechaPago = fechaPago;
    }

    public TipoPago getTipo() {
        return tipo;
    }

    public void setTipo(TipoPago tipo) {
        this.tipo = tipo;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }
    
    
    
}
