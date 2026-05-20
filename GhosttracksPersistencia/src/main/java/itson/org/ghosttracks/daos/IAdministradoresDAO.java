/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.daos;

import itson.org.ghosttracks.entidades.Administrador;
import itson.org.ghosttracks.exceptions.PersistenciaException;

/**
 *
 * @author emyla
 */
public interface IAdministradoresDAO {
    
    public abstract Administrador buscarPorId(Long idUsuario) throws PersistenciaException;
    
    public abstract Administrador buscarPorIdEmpleado(Long idEmpleado) throws PersistenciaException;
}
