package itson.org.ghosttracks.entidades;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

/**
 * @author nafbr
 */
@BsonDiscriminator(value = "CLIENTE")
public class Cliente extends Usuario {
    
    private String telefono;
    private Direccion direccion; 

    public Cliente() {
        super();
    }

    public Cliente(
            String telefono,
            Direccion direccion,
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

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
    
    public String getIdCliente() {
        return super.getIdUsuario(); 
    }
    
    public void setIdCliente(String idCliente) {
        super.setIdUsuario(idCliente);
    }
}