/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.entidades;

/**
 *
 * @author cinca
 */
public class Sucursal {
    
    private Long idSucursal;
    
    private Direccion direccion;
    private String telefono;
    private String nombre;
    
    public Sucursal() {
        
    }

    public Sucursal(Direccion direccion, String telefono, String nombre) {
        this.direccion = direccion;
        this.telefono = telefono;
        this.nombre = nombre;
    }

    public Sucursal(Long idSucursal, Direccion direccion, String telefono, String nombre) {
        this.idSucursal = idSucursal;
        this.direccion = direccion;
        this.telefono = telefono;
        this.nombre = nombre;
    }

    public Long getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Long idSucursal) {
        this.idSucursal = idSucursal;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
