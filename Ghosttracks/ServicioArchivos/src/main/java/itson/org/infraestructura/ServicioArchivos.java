package itson.org.infraestructura;

import itson.org.interfaces.IServicioArchivos;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Servicio encargado de la manipulación binaria de archivos del sistema
 * (Imágenes, Reportes, etc.) para el ecosistema de Ghost Tracks.
 * * @author emyla
 */
public class ServicioArchivos implements IServicioArchivos{

    /**
     * Convierte cualquier archivo físico del disco duro a un arreglo de bytes.
     * Ideal para procesar imágenes del JFileChooser antes de persistirlas.
     * * @param archivo El archivo seleccionado por el usuario.
     * @return Arreglo de bytes listo para asignarse a un DTO / Campo BLOB.
     * @throws IOException Si ocurre un error de lectura en el disco.
     */
    @Override
    public byte[] convertirArchivoABytes(File archivo) throws IOException {
        if (archivo == null || !archivo.exists()) {
            throw new IllegalArgumentException("El archivo proporcionado no es válido o no existe.");
        }

        try (InputStream is = new FileInputStream(archivo);
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            byte[] datos = new byte[4096]; // Buffer temporal de lectura
            int bytesLeidos;

            while ((bytesLeidos = is.read(datos, 0, datos.length)) != -1) {
                buffer.write(datos, 0, bytesLeidos);
            }

            return buffer.toByteArray();
        }
    }
    
    @Override
    public ImageIcon crearIconoDesdeBytes(byte[] bytes, int ancho, int alto) {
        String rutaDefault = "/imgCatalogo/default.png";

        if (bytes != null && bytes.length > 0) {
            try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
                Image imagenBff = ImageIO.read(bais);
                if (imagenBff != null) {
                    Image imagenEscalada = imagenBff.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                    return new ImageIcon(imagenEscalada);
                }
            } catch (Exception e) {
                System.out.println("Error en ServicioArchivos al procesar bytes: " + e.getMessage());
            }
        }

        try {
            java.net.URL urlDefault = getClass().getResource(rutaDefault);
            if (urlDefault != null) {
                return new ImageIcon(urlDefault);
            }
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen default.png: " + e.getMessage());
        }

        return new ImageIcon(); 
    }
}