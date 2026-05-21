package itson.org.ghosttracks.dtos;

/**
 *
 * @author emyla
 */
public class AdministradorDTO extends UsuarioDTO {

    public AdministradorDTO() {
        super();
    }

    public AdministradorDTO(
            String idUsuario,
            String nombre,
            String apellidoPaterno,
            String apellidoMaterno,
            String correo
    ) {
        super(idUsuario, nombre, apellidoPaterno, apellidoMaterno, correo, null);
    }
}