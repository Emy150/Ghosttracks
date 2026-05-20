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
    
    public Paquete generarAsignarGuia(String idPaquete) throws NegocioException;
            
    public abstract Paquete consultarPaquetePorId(String idPaquete) throws NegocioException;
    
    public abstract List<Paquete> consultarTodos() throws NegocioException;
    
}
