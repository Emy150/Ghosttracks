package itson.org.ghosttracks.dtos;

/**
 *
 * @author emyla
 */
public class AdministradorDTO extends UsuarioDTO{
    
    private String numeroEmpleado;

    public AdministradorDTO() {
        super();
    }

    public AdministradorDTO(
            String numeroEmpleado, 
            Long idUsuario, 
            String nombres, 
            String apellidoPaterno, 
            String apellidoMaterno, 
            String correo, 
            String contraseña
    ) {
        super(idUsuario, nombres, apellidoPaterno, apellidoMaterno, correo, contraseña);
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }
    
    
}
