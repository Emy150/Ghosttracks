/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package itson.org.ghosttracks.mappers;

import itson.org.ghosttracks.dtos.NuevoProductoDTO;
import itson.org.ghosttracks.dtos.ProductoActualizadoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.entidades.Producto;
import itson.org.ghosttracks.enums.TipoProducto;
import itson.org.utilidades.ConversorImagenes;

/**
 *
 * @author emyla
 */
public class ProductoMapper {

    public static ProductoDTO toDTO(Producto p) {

        if (p == null) return null;

        byte[] imagen = (p.getImgProducto() != null)
                ? p.getImgProducto().getDatos()
                : null;

        Integer stock = (p.getStockInicial() != null)
                ? p.getStockInicial()
                : 0;

        return new ProductoDTO(
                p.getIdProducto(),
                p.getTitulo(),
                p.getSku(),
                p.getArtista(),
                p.getPrecio(),
                stock,
                String.valueOf(p.getTipo()),
                String.valueOf(p.getIdGenero()),
                String.valueOf(p.getEstado()),
                p.getFechaRegistro(),
                imagen
        );
    }

    public static Producto fromNuevoDTO(NuevoProductoDTO dto) {

        if (dto == null) return null;

        Producto p = new Producto();

        p.setIdProducto(null);
        p.setTitulo(dto.getTitulo());
        p.setSku(dto.getSku());
        p.setArtista(dto.getArtista());
        p.setPrecio(dto.getPrecio());
        p.setStockInicial(dto.getStockInicial());

        if (dto.getTipo() != null) {
            p.setTipo(TipoProducto.valueOf(dto.getTipo().trim().toUpperCase()));
        }

        p.setIdGenero(dto.getGenero());

        if (dto.getImagen() != null) {
            p.setImgProducto(
                ConversorImagenes.fromFile(dto.getImagen())
            );
        }

        p.setFechaRegistro(java.time.LocalDateTime.now());

        return p;
    }
    
    public static void updateEntity(Producto p, ProductoActualizadoDTO dto) {

        if (p == null || dto == null) return;

        p.setTitulo(dto.getTitulo());
        p.setArtista(dto.getArtista());
        p.setPrecio(dto.getPrecio());
        p.setStockInicial(dto.getStockInicial());
        p.setTipo(TipoProducto.valueOf(dto.getTipo()));
        p.setIdGenero(dto.getGenero());

        if (dto.getImagen() != null) {
            p.setImgProducto(
                ConversorImagenes.fromFile(dto.getImagen())
            );
        }
    }
}
