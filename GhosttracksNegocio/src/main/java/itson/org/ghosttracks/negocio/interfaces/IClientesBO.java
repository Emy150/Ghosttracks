/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.org.ghosttracks.negocio.interfaces;

import itson.org.ghosttracks.entidades.Cliente;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.util.List;

/**
 *
 * @author nafbr
 */
public interface IClientesBO {
    
    Cliente obtenerClientePorId(Long idCliente) throws NegocioException;
    
    public abstract Cliente iniciarSesion(String correo, String contrasena) throws NegocioException;
    
    List<Long> buscarIdsPorNombre(String nombreCliente) throws NegocioException;
}
