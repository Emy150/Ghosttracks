/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.negocio.interfaces;

import itson.org.ghosttracks.dtos.PaqueteDTO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;

/**
 *
 * @author emyla
 */
public interface IProveedorEnvios {
    
    // Ahora devuelve un DTO con toda la info
    public PaqueteDTO generarGuiaPaquete(Long idPedido, Double pesoKg) throws NegocioException;
}
