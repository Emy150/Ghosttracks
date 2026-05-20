package itson.org.ghosttracks.dtos;

import java.time.LocalDateTime;

/**
 *
 * @author emyla
 */
public class NuevoProductoDTO {
    
    private String titulo;
    private String artista;
    private Double precio;
    private Integer stockInicial;
    private String tipo;
    private String genero;
    private byte[] Imagen;
    private LocalDateTime fechaRegistro;

    public NuevoProductoDTO() {
        
    }

    public NuevoProductoDTO(String titulo, String artista, Double precio, Integer stockInicial, String tipo, String genero, byte[] Imagen, LocalDateTime fechaRegistro) {
        this.titulo = titulo;
        this.artista = artista;
        this.precio = precio;
        this.stockInicial = stockInicial;
        this.tipo = tipo;
        this.genero = genero;
        this.Imagen = Imagen;
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

    public byte[] getImagen() {
        return Imagen;
    }

    public void setImagen(byte[] Imagen) {
        this.Imagen = Imagen;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
