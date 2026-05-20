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
    
    public abstract Paquete buscarPorId(String idPaquete) throws PersistenciaException;
    
    public Paquete buscarPorGuia(String numeroGuia) throws PersistenciaException;
    
    public abstract Paquete actualizarPaquete(Paquete paquete) throws PersistenciaException;
    
    public abstract List<Paquete> obtenerTodos() throws PersistenciaException;
    
}
