package itson.org.ghosttracks.dtos;

/**
 *
 * @author emyla, nafbr
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
            String idUsuario, 
            String nombre,  
            String apellidoPaterno,
            String apellidoMaterno,
            String correo,
            String contrasenia 
    ) {
        super(idUsuario, nombre, apellidoPaterno, apellidoMaterno, correo, contrasenia);
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
    
    public String getIdCliente() {
        return super.getIdUsuario();
    }
    
    public void setIdCliente(String idCliente) {
        super.setIdUsuario(idCliente); 
    }
}