package itson.org.ghosttracks.negocio.adaptador;

import itson.org.ghosttracks.dtos.PaqueteDTO;
import itson.org.ghosttracks.enums.EstadoPaquete;
import itson.org.ghosttracks.negocio.interfaces.IProveedorEnvios;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import itson.org.sistemaskydropxapi.servicios.SkydropxServicio;
import itson.org.sistemaskydropxapi.objetos.SkydropxPaquete;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Adaptador que conecta Ghosttracks con la API de Skydropx.
 */
public class SkydropxAdapter implements IProveedorEnvios {

    private final SkydropxServicio skydropxAPI; // La API real
    private static final Logger LOGGER = Logger.getLogger(SkydropxAdapter.class.getName());

    public SkydropxAdapter() {
        // Instanciamos el servicio real de tu módulo SistemaSkydropxAPI
        this.skydropxAPI = new SkydropxServicio();
    }

    @Override
    public PaqueteDTO generarGuiaPaquete(Long idPedido, Double pesoKg) throws NegocioException {
        try {
            LOGGER.log(Level.INFO, "Solicitando guía a Skydropx para el pedido: {0}", idPedido);
            
            // Skydropx hace su trabajo (Crea su propio paquete interno)
            SkydropxPaquete paqueteSkydropx = skydropxAPI.crearEnvio(idPedido, pesoKg);
            
            // Mapeamos la respuesta a tu concepto de PAQUETE (DTO)
            PaqueteDTO paqueteDTO = new PaqueteDTO();
            paqueteDTO.setNumeroGuia(paqueteSkydropx.getNumeroGuia());
            paqueteDTO.setEstado(EstadoPaquete.ENVIADO);
            paqueteDTO.setFechaEnvio(paqueteSkydropx.getFechaEnvio());
            paqueteDTO.setFechaEntregaEstimada(paqueteSkydropx.getFechaEntregaEstimada());
            
            return paqueteDTO; 
            
        } catch (Exception e) {
            LOGGER.severe("Error en Skydropx al generar número de guía");
            throw new NegocioException("Error del proveedor de paquetería: " + e.getMessage());
        }
    }
}