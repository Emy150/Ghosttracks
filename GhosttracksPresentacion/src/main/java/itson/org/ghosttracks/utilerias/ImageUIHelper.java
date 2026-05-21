package itson.org.ghosttracks.utilerias;

import itson.org.infraestructura.ServicioArchivos;
import itson.org.interfaces.IServicioArchivos;
import javax.swing.ImageIcon;

public class ImageUIHelper {
    private static final IServicioArchivos servicio = new ServicioArchivos();

    public static ImageIcon conseguirIcono(byte[] bytes, int ancho, int alto) {
        return servicio.crearIconoDesdeBytes(bytes, ancho, alto);
    }
}