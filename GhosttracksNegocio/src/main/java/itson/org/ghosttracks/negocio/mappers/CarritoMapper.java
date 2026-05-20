/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.negocio.mappers;

import itson.org.ghosttracks.dtos.CarritoDTO;
import itson.org.ghosttracks.dtos.ItemCarritoDTO;
import itson.org.ghosttracks.entidades.Carrito;
import itson.org.ghosttracks.entidades.ItemCarrito;
import itson.org.ghosttracks.entidades.Producto;
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
    
    //Métodos auxiliares
    private static ItemCarritoDTO toItemDTO(ItemCarrito entidad) {
        if (entidad == null) return null;
 
        ItemCarritoDTO dto = new ItemCarritoDTO();
        dto.setCantidad(entidad.getCantidad());
        dto.setSubtotal(entidad.getSubtotal());
        dto.setProductoSeleccionado(ProductoMapper.toDTO(entidad.getProducto()));
        return dto;
    }
 
    private static ItemCarrito toItemEntidad(ItemCarritoDTO dto) {
        if (dto == null) return null;
 
        ItemCarrito entidad = new ItemCarrito();
        entidad.setCantidad(dto.getCantidad());
        entidad.setSubtotal(dto.getSubtotal());
 
        if (dto.getProductoSeleccionado() != null) {
            Producto productoEntidad = ProductoMapper.toEntidad(dto.getProductoSeleccionado());
            entidad.setProducto(productoEntidad);
        }
        return entidad;
    }
}
