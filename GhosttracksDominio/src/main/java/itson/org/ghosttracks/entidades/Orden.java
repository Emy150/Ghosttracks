package itson.org.ghosttracks.entidades;

import itson.org.ghosttracks.enums.EstadoOrden;
import itson.org.ghosttracks.enums.TipoOrden;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 * @author nafbr
 */
public class Orden {
    
    @BsonId
    @BsonProperty("_id")
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idOrden;
    
    private String folio;
    private TipoOrden tipoOrden;
    private String comentarios;
    private Double total;
    private LocalDate fechaEntregaEst;
    private LocalDateTime fechaSolicitud;
    private EstadoOrden estadoOrden;
    private List<ProductoOrden> productosOrden;
    private Byte[] imagenRecepcion;
    
    //Relaciones
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idProovedor;
    
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idSucursal;
    
    public Orden() {
        
    }
    
    public Orden(String idOrden, String folio, TipoOrden tipoOrden, String comentarios, Double total, LocalDate fechaEntregaEst, LocalDateTime fechaSolicitud, EstadoOrden estadoOrden, List<ProductoOrden> productosOrden, Byte[] imagenRecepcion, String idProovedor, String idSucursal) {
        this.idOrden = idOrden;
        this.folio = folio;
        this.tipoOrden = tipoOrden;
        this.comentarios = comentarios;
        this.total = total;
        this.fechaEntregaEst = fechaEntregaEst;
        this.fechaSolicitud = fechaSolicitud;
        this.estadoOrden = estadoOrden;
        this.productosOrden = productosOrden;
        this.imagenRecepcion = imagenRecepcion;
        this.idProovedor = idProovedor;
        this.idSucursal = idSucursal;
    }

    public String getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(String idOrden) {
        this.idOrden = idOrden;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public TipoOrden getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(TipoOrden tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDate getFechaEntregaEst() {
        return fechaEntregaEst;
    }

    public void setFechaEntregaEst(LocalDate fechaEntregaEst) {
        this.fechaEntregaEst = fechaEntregaEst;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public EstadoOrden getEstadoOrden() {
        return estadoOrden;
    }

    public void setEstadoOrden(EstadoOrden estadoOrden) {
        this.estadoOrden = estadoOrden;
    }

    public List<ProductoOrden> getProductosOrden() {
        return productosOrden;
    }

    public void setProductosOrden(List<ProductoOrden> productosOrden) {
        this.productosOrden = productosOrden;
    }

    public Byte[] getImagenRecepcion() {
        return imagenRecepcion;
    }

    public void setImagenRecepcion(Byte[] imagenRecepcion) {
        this.imagenRecepcion = imagenRecepcion;
    }

    public String getIdProovedor() {
        return idProovedor;
    }

    public void setIdProovedor(String idProovedor) {
        this.idProovedor = idProovedor;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.idOrden);
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
        final Orden other = (Orden) obj;
        return Objects.equals(this.idOrden, other.idOrden);
    }

    @Override
    public String toString() {
        return "Orden{" + "idOrden=" + idOrden + ", folio=" + folio + ", tipoOrden=" + tipoOrden + ", comentarios=" + comentarios + ", total=" + total + ", fechaEntregaEst=" + fechaEntregaEst + ", fechaSolicitud=" + fechaSolicitud + ", estadoOrden=" + estadoOrden + ", productosOrden=" + productosOrden + ", imagenRecepcion=" + imagenRecepcion + ", idProovedor=" + idProovedor + ", idSucursal=" + idSucursal + '}';
    }
    
}