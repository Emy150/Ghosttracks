package itson.org.ghosttracks.dtos;

/**
 *
 * @author emyla
 */
public class ClienteDTO extends UsuarioDTO {

    private String telefono;
    private DireccionClienteDTO direccion;

    public ClienteDTO() {
        super();
    }

    public ClienteDTO(
            String telefono,
            DireccionClienteDTO direccion,
            Long idUsuario,
            String nombres,
            String apellidoPaterno,
            String apellidoMaterno,
            String correo,
            String contraseña
    ) {
        super(idUsuario, nombres, apellidoPaterno, apellidoMaterno, correo, contraseña);
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public DireccionClienteDTO getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionClienteDTO direccion) {
        this.direccion = direccion;
    }

}
