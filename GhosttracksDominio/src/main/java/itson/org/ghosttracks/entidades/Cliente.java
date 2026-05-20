/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.entidades;

/**
 *
 * @author nafbr
 */
public class Cliente extends Usuario{
    private String telefono;
    private Direccion direccion;

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Cliente() {
    }

     public Cliente(
            String telefono,
            Direccion direccion,
            Long idUsuario,
            String nombres,
            String apellidoPaterno,
            String apellidoMaterno,
            String correo,
            String contraseña
    ) {
        super(nombres, apellidoPaterno, apellidoMaterno, correo, contraseña);
        this.telefono = telefono;
        this.direccion = direccion;
    }
}
