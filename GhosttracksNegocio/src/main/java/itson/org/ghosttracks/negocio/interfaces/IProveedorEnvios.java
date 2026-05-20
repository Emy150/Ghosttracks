package itson.org.ghosttracks.negocio.interfaces;

import itson.org.ghosttracks.dtos.PaqueteDTO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;

/**
 *
 * @author emyla
 */
public interface IProveedorEnvios {
    
    public PaqueteDTO generarGuiaPaquete(String idPedido, Double pesoKg) throws NegocioException;
}
