package itson.org.ghosttracks.negocio.mappers;

import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.entidades.Producto;
import itson.org.ghosttracks.entidades.Imagen; 
import itson.org.ghosttracks.enums.EstadoProducto;
import itson.org.ghosttracks.enums.TipoProducto;

/**
 *
 * @author nafbr
 */
public class ProductoMapper {

    private ProductoMapper() {
    }

    public static ProductoDTO toDTO(Producto entidad) {
        if (entidad == null) {
            return null;
        }

        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(entidad.getIdProducto());
        dto.setTitulo(entidad.getTitulo());
        dto.setArtista(entidad.getArtista());
        dto.setGenero(entidad.getIdGenero());
        dto.setPrecio(entidad.getPrecio());
        dto.setStockInicial(entidad.getStockInicial());

        if (entidad.getTipo() != null) {
            dto.setTipo(entidad.getTipo().name());
        }

        if (entidad.getEstado() != null) {
            dto.setEstado(entidad.getEstado().name());
        }

        if (entidad.getImgProducto() != null) {
            dto.setImg(entidad.getImgProducto().getBytes()); 
        }

        return dto;
    }

    public static Producto toEntidad(ProductoDTO dto) {
        if (dto == null) {
            return null;
        }

        Producto entidad = new Producto();
        entidad.setIdProducto(dto.getIdProducto());
        entidad.setTitulo(dto.getTitulo());
        entidad.setPrecio(dto.getPrecio());
        entidad.setStockInicial(dto.getStockInicial());
        entidad.setArtista(dto.getArtista());
        entidad.setIdGenero(dto.getGenero());

        if (dto.getTipo() != null && !dto.getTipo().isEmpty()) {
            entidad.setTipo(TipoProducto.valueOf(dto.getTipo()));
        }

        if (dto.getEstado() != null && !dto.getEstado().isEmpty()) {
            entidad.setEstado(EstadoProducto.valueOf(dto.getEstado()));
        }

        if (dto.getImg() != null) {
            Imagen imagen = new Imagen();
            imagen.setBytes(dto.getImg()); 
            entidad.setImgProducto(imagen);
        }

        return entidad;
    }
}