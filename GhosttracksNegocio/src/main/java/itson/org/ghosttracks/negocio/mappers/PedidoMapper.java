/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.negocio.mappers;

import itson.org.ghosttracks.dtos.ContactoDTO;
import itson.org.ghosttracks.dtos.DireccionEntregaDTO;
import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.dtos.SucursalDTO;
import itson.org.ghosttracks.entidades.Contacto;
import itson.org.ghosttracks.entidades.Direccion;
import itson.org.ghosttracks.entidades.Pedido;
import itson.org.ghosttracks.entidades.Sucursal;
import itson.org.ghosttracks.enums.EstadoPedido;
import itson.org.ghosttracks.enums.EstadoPedidoDTO;

/**
 * Pedido Mapper.
 * Usan el nombre mapper ya que no queremos que colisionen con los adapters
 * @author nafbr
 */
public class PedidoMapper {
 
    public static PedidoDTO toDTO(Pedido entidad) {
        if (entidad == null) return null;
 
        PedidoDTO dto = new PedidoDTO();
        dto.setIdPedido(entidad.getIdPedido());
 
        if (entidad.getEstado() != null) {
            dto.setEstado(EstadoPedidoDTO.valueOf(entidad.getEstado().name()));
        }
 
        dto.setCliente(ClienteMapper.toDTO(entidad.getCliente()));
        dto.setCarrito(CarritoMapper.toDTO(entidad.getCarrito()));
        dto.setPaquete(PaqueteMapper.toDTO(entidad.getPaquete()));
        dto.setDireccionEntrega(toDireccionEntregaDTO(entidad.getDireccionEntrega()));
        dto.setContacto(toContactoDTO(entidad.getContacto()));
        dto.setSucursal(toSucursalDTO(entidad.getSucursal()));
 
        return dto;
    }
 
    private static DireccionEntregaDTO toDireccionEntregaDTO(Direccion dir) {
        if (dir == null) return null;
 
        DireccionEntregaDTO dto = new DireccionEntregaDTO();
        dto.setCalle(dir.getCalle());
        dto.setNumero(dir.getNumero());
        dto.setCiudad(dir.getCiudad());
        dto.setCodigoPostal(dir.getCodigoPostal());
        return dto;
    }
 
    private static ContactoDTO toContactoDTO(Contacto contacto) {
        if (contacto == null) return null;
 
        ContactoDTO dto = new ContactoDTO();
        dto.setNombre(contacto.getNombre());
        dto.setTelefono(contacto.getTelefono());
        return dto;
    }
 
    private static SucursalDTO toSucursalDTO(Sucursal sucursal) {
        if (sucursal == null) return null;
 
        SucursalDTO dto = new SucursalDTO();
        dto.setNombre(sucursal.getNombre());
        return dto;
    }
 
    public static EstadoPedido estadoAEntidad(EstadoPedidoDTO estadoDTO) {
        if (estadoDTO == null) return null;
        return EstadoPedido.valueOf(estadoDTO.name());
    }
 
    public static Direccion toDireccionEntrega(DireccionEntregaDTO dto) {
        if (dto == null) return null;
 
        Direccion entidad = new Direccion();
        entidad.setCalle(dto.getCalle());
        entidad.setColonia(dto.getColonia());
        entidad.setNumero(dto.getNumero());
        entidad.setCiudad(dto.getCiudad());
        entidad.setCodigoPostal(dto.getCodigoPostal());
        return entidad;
    }
 
    public static Contacto toContacto(ContactoDTO dto) {
        if (dto == null) return null;
 
        Contacto entidad = new Contacto();
        entidad.setNombre(dto.getNombre());
        entidad.setTelefono(dto.getTelefono());
        entidad.setCorreo(dto.getCorreo());
        return entidad;
    }
 
    public static Sucursal toSucursal(SucursalDTO dto) {
        if (dto == null) return null;
 
        Sucursal entidad = new Sucursal();
        entidad.setNombre(dto.getNombre());
        return entidad;
    }
}
