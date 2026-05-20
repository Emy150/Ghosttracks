package itson.org.ghosttracks.negocio.adaptador;

import itson.org.ghosttracks.dtos.AdministradorDTO;
import itson.org.ghosttracks.entidades.Administrador;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.util.logging.Logger;

/**
 *
 * @author emyla
 */
public class UsuarioAdapter {
    
    private static final Logger LOGGER = Logger.getLogger(UsuarioAdapter.class.getName());

    /**
     * Convierte una entidad Administrador (que viene de BD) a un AdministradorDTO (para la pantalla).
     * @param admin
     * @return 
     */
    public AdministradorDTO adaptAdministradorToDTO(Administrador admin) throws NegocioException {
        
        if (admin == null) {
            LOGGER.severe("ERROR! El administrador recibido es nulo :o");
            throw new NegocioException("No fue posible adaptar, la entidad Administrador es nula.");
        }

        try {
            AdministradorDTO dto = new AdministradorDTO();

            dto.setNombre(admin.getNombre());
            dto.setApellidoPaterno(admin.getApellidoPaterno());
            dto.setApellidoMaterno(admin.getApellidoMaterno());
            dto.setCorreo(admin.getCorreo());
            
            LOGGER.fine("Conversión de Administrador a DTO realizada con éxito :D");
            return dto;

        } catch (Exception e) {
            LOGGER.severe("ERROR al armar el AdministradorDTO!");
            throw new NegocioException("Ocurrió un error al adaptar de Administrador a DTO", e);
        }
    }

    /**
     * Convierte un AdministradorDTO (que viene de la pantalla) a una entidad Administrador (para la BD).
     */
    public Administrador adaptDTOToAdministrador(AdministradorDTO dto) throws NegocioException {
        
        if (dto == null) {
            LOGGER.severe("ERROR! El AdministradorDTO llegó nulo D:");
            throw new NegocioException("No fue posible adaptar, el DTO es nulo.");
        }

        try {
            Administrador admin = new Administrador();

            admin.setNombre(dto.getNombre());
            admin.setApellidoPaterno(dto.getApellidoPaterno());
            admin.setApellidoMaterno(dto.getApellidoMaterno());
            admin.setCorreo(dto.getCorreo());
            admin.setContrasenia(dto.getContrasenia());
            
            LOGGER.fine("Conversión de DTO a Entidad Administrador lista yey!");
            return admin;

        } catch (Exception e) {
            LOGGER.severe("ERROR al armar la entidad Administrador!");
            throw new NegocioException("Ocurrió un error al adaptar de DTO a Administrador", e);
        }
    }
}
