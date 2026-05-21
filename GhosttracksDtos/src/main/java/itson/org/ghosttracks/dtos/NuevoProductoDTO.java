package itson.org.ghosttracks.dtos;

import java.io.File;
import java.time.LocalDateTime;

/**
 *
 * @author emyla
 */
public class NuevoProductoDTO {
    
    private String titulo;
    private String artista;
    private Double precio;
    private String sku;
    private Integer stockInicial;
    private String tipo;
    private String genero;
    private File imagen;
    private LocalDateTime fechaRegistro;

    public NuevoProductoDTO() {
        
    }

    public NuevoProductoDTO(String titulo, String artista, Double precio, String sku, Integer stockInicial, String tipo, String genero, File imagen, LocalDateTime fechaRegistro) {
        this.titulo = titulo;
        this.artista = artista;
        this.precio = precio;
        this.sku = sku;
        this.stockInicial = stockInicial;
        this.tipo = tipo;
        this.genero = genero;
        this.imagen = imagen;
        this.fechaRegistro = fechaRegistro;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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

    public void setIdGenero(String genero) {
        this.genero = genero;
    }

    public File getImagen() {
        return imagen;
    }

    public void setImagen(File imagen) {
        this.imagen = imagen;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
}
