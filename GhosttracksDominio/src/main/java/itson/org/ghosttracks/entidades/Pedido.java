package itson.org.ghosttracks.entidades;

import itson.org.ghosttracks.enums.EstadoPedido;
import java.time.LocalDateTime;
import java.util.Objects;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 * @author nafbr
 */
public class Pedido {
    
    @BsonId
    @BsonProperty("_id")
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idPedido;
    
    private String folio;

    // Referencia a cliente
    @BsonProperty("idCliente")
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idCliente; 
    
    private Carrito carrito;
    private Contacto contacto; 
    private Direccion direccionEntrega; 
    private Sucursal sucursal;
    private EstadoPedido estado;
    private LocalDateTime fechaPedido;
    private Double costoEnvio;
    private Paquete paquete;
    private Pago pago; 

    public Pedido() {
        
    }

    public Pedido(String idPedido, String folio, String idCliente, Carrito carrito, Contacto contacto, Direccion direccionEntrega, Sucursal sucursal, EstadoPedido estado, LocalDateTime fechaPedido, Double costoEnvio, Paquete paquete, Pago pago) {
        this.idPedido = idPedido;
        this.folio = folio;
        this.idCliente = idCliente;
        this.carrito = carrito;
        this.contacto = contacto;
        this.direccionEntrega = direccionEntrega;
        this.sucursal = sucursal;
        this.estado = estado;
        this.fechaPedido = fechaPedido;
        this.costoEnvio = costoEnvio;
        this.paquete = paquete;
        this.pago = pago;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    public Direccion getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(Direccion direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Double getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(Double costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public Paquete getPaquete() {
        return paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.idPedido);
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
        final Pedido other = (Pedido) obj;
        return Objects.equals(this.idPedido, other.idPedido);
    }

    @Override
    public String toString() {
        return "Pedido{" + "idPedido=" + idPedido + 
                ", folio=" + folio + 
                ", idCliente=" + idCliente + // Actualizado aquí también
                ", carrito=" + carrito + 
                ", contacto=" + contacto + 
                ", direccionEntrega=" + direccionEntrega + 
                ", sucursal=" + sucursal + 
                ", estado=" + estado + 
                ", fechaPedido=" + fechaPedido + 
                ", costoEnvio=" + costoEnvio + 
                ", paquete=" + paquete + 
                ", pago=" + pago + '}';
    } 
}