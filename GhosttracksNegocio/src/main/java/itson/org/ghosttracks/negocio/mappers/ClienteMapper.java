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
        dto.setNombre(entidad.getNombre());
        dto.setApellidoPaterno(entidad.getApellidoPaterno());
        dto.setApellidoMaterno(entidad.getApellidoMaterno());
        dto.setCorreo(entidad.getCorreo());
        dto.setContrasenia(entidad.getContrasenia());
        dto.setTelefono(entidad.getTelefono());
        dto.setDireccion(toDireccionDTO(entidad.getDireccion()));
        return dto;
    }
 
    public static Cliente toEntidad(ClienteDTO dto) {
        if (dto == null) return null;
 
        Cliente entidad = new Cliente();
        entidad.setIdUsuario(dto.getIdUsuario());
        entidad.setNombre(dto.getNombre());
        entidad.setApellidoPaterno(dto.getApellidoPaterno());
        entidad.setApellidoMaterno(dto.getApellidoMaterno());
        entidad.setCorreo(dto.getCorreo());
        entidad.setContrasenia(dto.getContrasenia());
        entidad.setTelefono(dto.getTelefono());
        entidad.setDireccion(toDireccionEntidad(dto.getDireccion())); 
        return entidad;
    }
 
    public static String nombreCompleto(Cliente entidad) {
        if (entidad == null) return "";
        return (entidad.getNombre() + " "
                + entidad.getApellidoPaterno() + " "
                + entidad.getApellidoMaterno()).trim();
    }
    
    private static DireccionClienteDTO toDireccionDTO(Direccion dir) {
        if (dir == null) return null;
 
        DireccionClienteDTO dto = new DireccionClienteDTO();
        dto.setCalle(dir.getCalle());
        dto.setNumero(dir.getNumero());
        dto.setCodigoPostal(dir.getCodigoPostal());
        return dto;
    }

    private static Direccion toDireccionEntidad(DireccionClienteDTO dto) {
        if (dto == null) return null;

        Direccion dir = new Direccion();
        dir.setCalle(dto.getCalle());
        dir.setNumero(dto.getNumero());
        dir.setCodigoPostal(dto.getCodigoPostal());
        return dir;
    }
}