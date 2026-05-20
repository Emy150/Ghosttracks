package itson.org.ghosttracks.entidades; 

import itson.org.ghosttracks.enums.EstadoProducto;
import itson.org.ghosttracks.enums.TipoProducto;
import java.time.LocalDateTime;
import java.util.Objects;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 * Entidad de Dominio
 * @author Emy
 */
 public class Producto {

    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idProducto;
    
    private String sku;
    
    private String titulo;
    private String artista;
    private TipoProducto tipo;
    
    // Referencia a Generos
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idGenero; 
    
    private Double precio;
    private Integer stockInicial;
    private EstadoProducto estado;
    private Imagen imgProducto;
    private LocalDateTime fechaRegistro;

    public Producto() {
        
    }

    public Producto(
            String idProducto, 
            String sku,
            String titulo, 
            String artista, 
            TipoProducto tipo, 
            String idGenero, 
            Double precio, 
            Integer stockInicial, 
            EstadoProducto estado, 
            Imagen imgProducto, 
            LocalDateTime fechaRegistro
    ){
        this.idProducto = idProducto;
        this.sku = sku;
        this.titulo = titulo;
        this.artista = artista;
        this.tipo = tipo;
        this.idGenero = idGenero;
        this.precio = precio;
        this.stockInicial = stockInicial;
        this.estado = estado;
        this.imgProducto = imgProducto;
        this.fechaRegistro = fechaRegistro;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public TipoProducto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }

    public String getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(String idGenero) {
        this.idGenero = idGenero;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStockInicial() {
        return stockInicial;
    }

    public void setStockInicial(Integer stockInicial) {
        this.stockInicial = stockInicial;
    }

    public EstadoProducto getEstado() {
        return estado;
    }

    public void setEstado(EstadoProducto estado) {
        this.estado = estado;
    }

    public Imagen getImgProducto() {
        return imgProducto;
    }

    public void setImgProducto(Imagen imgProducto) {
        this.imgProducto = imgProducto;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.idProducto);
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
        final Producto other = (Producto) obj;
        return Objects.equals(this.idProducto, other.idProducto);
    }

    @Override
    public String toString() {
        return "Producto{" + "idProducto=" + idProducto + ", sku=" + sku + ", titulo=" + titulo + ", artista=" + artista + ", tipo=" + tipo + ", idGenero=" + idGenero + ", precio=" + precio + ", stockInicial=" + stockInicial + ", estado=" + estado + ", imgProducto=" + imgProducto + ", fechaRegistro=" + fechaRegistro + '}';
    }
}