package itson.org.ghosttracks.dtos;

import java.time.LocalDateTime;

/**
 * Producto DTO q funciona como respuesta
 * @author emyla
 */
public class ProductoDTO {

    private String idProducto;
    private String titulo;
    private String sku;
    private String artista;
    private Double precio;
    private Integer stockInicial;
    private String tipo;
    private String genero;
    private String estado;
    private LocalDateTime fechaRegistro;
    private byte[] img;

    public ProductoDTO() {
        
    }

    public ProductoDTO(String idProducto, String titulo, String sku, String artista, Double precio, Integer stockInicial, String tipo, String genero, String estado, LocalDateTime fechaRegistro, byte[] img) {
        this.idProducto = idProducto;
        this.titulo = titulo;
        this.sku = sku;
        this.artista = artista;
        this.precio = precio;
        this.stockInicial = stockInicial;
        this.tipo = tipo;
        this.genero = genero;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.img = img;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
        
}
