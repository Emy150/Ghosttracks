
package itson.org.interfaces;

import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;

/**
 *
 * @author emyla
 */
public interface IServicioArchivos {
    
    public byte[] convertirArchivoABytes(File archivo) throws IOException;
    
    ImageIcon crearIconoDesdeBytes(byte[] bytes, int ancho, int alto);
}
