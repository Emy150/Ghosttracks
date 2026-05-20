package itson.org.ghosttracks.negocio.mappers;

import itson.org.ghosttracks.dtos.CarritoDTO;
import itson.org.ghosttracks.dtos.ItemCarritoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO; // Asegúrate de importar esto
import itson.org.ghosttracks.entidades.Carrito;
import itson.org.ghosttracks.entidades.ItemCarrito;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nafbr
 */
public class CarritoMapper {

    private CarritoMapper() {
    }

    public static CarritoDTO toDTO(Carrito entidad) {
        if (entidad == null) {
            return null;
        }

        CarritoDTO dto = new CarritoDTO();
        dto.setTotal(entidad.getTotal());
        dto.setSubtotal(entidad.getSubtotal());
        dto.setImpuestos(entidad.getImpuestos());

        if (entidad.getItems() != null) {
            for (ItemCarrito itemEntidad : entidad.getItems()) {
                dto.getProductos().add(toItemDTO(itemEntidad));
            }
        }
        return dto;
    }

    public static Carrito toEntidad(CarritoDTO dto) {
        if (dto == null) {
            return null;
        }

        Carrito entidad = new Carrito();
        entidad.setTotal(dto.getTotal());
        entidad.setSubtotal(dto.getSubtotal());
        entidad.setImpuestos(dto.getImpuestos());

        List<ItemCarrito> items = new ArrayList<>();
        if (dto.getProductos() != null) {
            for (ItemCarritoDTO itemDTO : dto.getProductos()) {
                items.add(toItemEntidad(itemDTO));
            }
        }
        entidad.setItems(items);
        return entidad;
    }
    
    // Métodos auxiliares
    private static ItemCarritoDTO toItemDTO(ItemCarrito entidad) {
        if (entidad == null) return null;
 
        ItemCarritoDTO dto = new ItemCarritoDTO();
        dto.setCantidad(entidad.getCantidad());
        dto.setSubtotal(entidad.getSubtotal());
        
        if (entidad.getIdProducto() != null) {
            ProductoDTO productoCascaron = new ProductoDTO();
            productoCascaron.setIdProducto(entidad.getIdProducto()); 
            dto.setProductoSeleccionado(productoCascaron);
        }
        return dto;
    }
 
    private static ItemCarrito toItemEntidad(ItemCarritoDTO dto) {
        if (dto == null) return null;
 
        ItemCarrito entidad = new ItemCarrito();
        entidad.setCantidad(dto.getCantidad());
        entidad.setSubtotal(dto.getSubtotal());
 
        if (dto.getProductoSeleccionado() != null) {
            entidad.setIdProducto(dto.getProductoSeleccionado().getIdProducto()); 
        }
        
        return entidad;
    }
}