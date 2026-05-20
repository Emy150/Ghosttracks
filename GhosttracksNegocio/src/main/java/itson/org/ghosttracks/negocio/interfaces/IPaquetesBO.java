/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.negocio.interfaces;

import itson.org.ghosttracks.entidades.Paquete;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.util.List;

/**
 *
 * @author emyla
 */
public interface IPaquetesBO {
    
    public abstract Paquete registrarEmpaque(Paquete paquete) throws NegocioException;
    
    public Paquete generarAsignarGuia(Long idPaquete) throws NegocioException;
            
    public abstract Paquete consultarPaquetePorId(Long idPaquete) throws NegocioException;
    
    public abstract List<Paquete> consultarTodos() throws NegocioException;
    
    
}
