package itson.org.utilidades;

import itson.org.ghosttracks.entidades.Imagen;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.bson.Document;

/**
 * @author emyla
 */
public class ConversorImagenes {
    
    public static Imagen fromFile(File file) {
        if (file == null) return null;

        try {
            byte[] data = Files.readAllBytes(file.toPath());

            Document docTemporal = new Document("bytes", data);
            byte[] bytesFormateados = docTemporal.get("bytes", byte[].class);

            Imagen img = new Imagen();
            img.setIdImagen(java.util.UUID.randomUUID().toString());
            img.setDatos(bytesFormateados); // Se va ultra compatible para el POJO

            return img;

        } catch (IOException e) {
            throw new RuntimeException("Error al convertir archivo a imagen", e);
        }
    }
    
    public static Imagen fromBytes(byte[] data) {
        if (data == null) return null;

        Imagen img = new Imagen();
        img.setIdImagen(java.util.UUID.randomUUID().toString());
        img.setDatos(data);

        return img;
    }
}