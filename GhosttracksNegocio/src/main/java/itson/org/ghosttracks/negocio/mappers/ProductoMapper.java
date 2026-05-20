/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.negocio.mappers;

import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.entidades.Producto;

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
        dto.setNombre(entidad.getNombre());
        dto.setImgProducto(entidad.getImgProducto());
        dto.setTipoProducto(entidad.getTipo());
        dto.setArtista(entidad.getArtista());
        dto.setGenero(entidad.getGenero());
        dto.setSetlist(entidad.getSetlist());
        dto.setPrecio(entidad.getPrecio());
        dto.setStock(entidad.getStock());
        dto.setEstado(entidad.getEstado());
        return dto;
    }

    public static Producto toEntidad(ProductoDTO dto) {
        if (dto == null) {
            return null;
        }

        Producto entidad = new Producto();
        entidad.setIdProducto(dto.getIdProducto());
        entidad.setNombre(dto.getNombre());
        entidad.setImgProducto(dto.getImgProducto());
        entidad.setTipo(dto.getTipoProducto());
        entidad.setPrecio(dto.getPrecio());
        entidad.setStock(dto.getStock());
        return entidad;
    }

}
