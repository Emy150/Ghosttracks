/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.daos;

import itson.org.ghosttracks.entidades.Paquete;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import java.util.List;

/**
 *
 * @author emyla
 */
public interface IPaquetesDAO {
    
    public abstract Paquete agregarPaquete(Paquete paquete) throws PersistenciaException;
    
    public abstract Paquete buscarPorId(Long idPaquete) throws PersistenciaException;
    
    public Paquete buscarPorGuia(String numeroGuia) throws PersistenciaException;
    
    public abstract Paquete actualizarPaquete(Paquete paquete) throws PersistenciaException;
    
    public abstract List<Paquete> obtenerTodos() throws PersistenciaException;
    
}
