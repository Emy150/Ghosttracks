package itson.org.ghosttracks.utilidades;

import java.util.UUID;

/**
 *
 * @author emy
 */
public class GeneradorFolios {

    private GeneradorFolios() {
        
    }

    /**
     * Genera un folio único corto para los pedidos.
     * @return 
     */
    public static String generarFolioPedido() {
        String codigoCorto = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "PED-" + codigoCorto; 
    }

    public static String generarSkuProducto(String inicialesCategoria) {
        String codigoCorto = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return inicialesCategoria + "-" + codigoCorto; 
    }
}