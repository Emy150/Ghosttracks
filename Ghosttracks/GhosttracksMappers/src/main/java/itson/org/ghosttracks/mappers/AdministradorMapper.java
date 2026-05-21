package itson.org.ghosttracks.mappers;

import itson.org.ghosttracks.dtos.AdministradorDTO;
import itson.org.ghosttracks.entidades.Administrador;

public class AdministradorMapper {

    
    public static AdministradorDTO toDTO(Administrador admin) {

        if (admin == null) return null;

        return new AdministradorDTO(
                admin.getIdUsuario(),
                admin.getNombre(),
                admin.getApellidoPaterno(),
                admin.getApellidoMaterno(),
                admin.getCorreo()
        );
    }

    
    public static Administrador fromDTO(AdministradorDTO dto) {

        if (dto == null) return null;

        return new Administrador(
                dto.getIdUsuario(),
                dto.getNombre(),
                dto.getApellidoPaterno(),
                dto.getApellidoMaterno(),
                dto.getCorreo(),
                null 
        );
    }
}