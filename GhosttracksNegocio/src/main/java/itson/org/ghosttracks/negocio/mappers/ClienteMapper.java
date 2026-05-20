/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.negocio.mappers;

import itson.org.ghosttracks.dtos.ClienteDTO;
import itson.org.ghosttracks.dtos.DireccionClienteDTO;
import itson.org.ghosttracks.entidades.Cliente;
import itson.org.ghosttracks.entidades.Direccion;

/**
 *
 * @author nafbr
 */
public class ClienteMapper {

    private ClienteMapper() {}
 
    public static ClienteDTO toDTO(Cliente entidad) {
        if (entidad == null) return null;
 
        ClienteDTO dto = new ClienteDTO();
        dto.setIdUsuario(entidad.getIdUsuario());
        dto.setNombres(entidad.getNombres());
        dto.setApellidoPaterno(entidad.getApellidoPaterno());
        dto.setApellidoMaterno(entidad.getApellidoMaterno());
        dto.setCorreo(entidad.getCorreo());
        dto.setContraseña(entidad.getContraseña());
        dto.setTelefono(entidad.getTelefono());
        dto.setDireccion(toDireccionDTO(entidad.getDireccion()));
        return dto;
    }
 
    public static Cliente toEntidad(ClienteDTO dto) {
        if (dto == null) return null;
 
        Cliente entidad = new Cliente();
        entidad.setIdUsuario(dto.getIdUsuario());
        entidad.setNombres(dto.getNombres());
        entidad.setApellidoPaterno(dto.getApellidoPaterno());
        entidad.setApellidoMaterno(dto.getApellidoMaterno());
        entidad.setCorreo(dto.getCorreo());
        entidad.setContraseña(dto.getContraseña());
        entidad.setTelefono(dto.getTelefono());
        return entidad;
    }
 
    public static String nombreCompleto(Cliente entidad) {
        if (entidad == null) return "";
        return (entidad.getNombres() + " "
                + entidad.getApellidoPaterno() + " "
                + entidad.getApellidoMaterno()).trim();
    }
    
    //Método auxiliar
      private static DireccionClienteDTO toDireccionDTO(Direccion dir) {
        if (dir == null) return null;
 
        DireccionClienteDTO dto = new DireccionClienteDTO();
        dto.setCalle(dir.getCalle());
        dto.setNumero(dir.getNumero());
        dto.setCodigoPostal(dir.getCodigoPostal());
        return dto;
    }
}
