 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.entidades;
/**
 *
 * @author cinca
 */
public class Administrador extends Usuario{
    
    private Long idEmpleado;
    
    public Administrador() {
        
    }

    public Administrador(Long idEmpleado, Long idUsuario, String nombres, String apellidoPaterno, String apellidoMaterno, String correo, String contraseña) {
        super(idUsuario, nombres, apellidoPaterno, apellidoMaterno, correo, contraseña);
        this.idEmpleado = idEmpleado;
    }

    public Administrador(Long idEmpleado, String nombres, String apellidoPaterno, String apellidoMaterno, String correo, String contraseña) {
        super(nombres, apellidoPaterno, apellidoMaterno, correo, contraseña);
        this.idEmpleado = idEmpleado;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    
}
