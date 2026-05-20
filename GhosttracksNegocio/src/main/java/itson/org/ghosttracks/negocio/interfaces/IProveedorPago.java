package itson.org.ghosttracks.negocio.interfaces;

import itson.org.ghosttracks.enums.TipoPago;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;

public interface IProveedorPago {
    
boolean cobrar(String tarjeta, Double monto, TipoPago tipo) throws NegocioException;


}