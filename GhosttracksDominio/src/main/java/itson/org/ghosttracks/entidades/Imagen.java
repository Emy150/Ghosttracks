package itson.org.ghosttracks.entidades;

/**
 *
 * @author emyla
 */
public class Imagen {
    
    private String idImagen;
    private byte[] bytes;
    
    public Imagen(){
        
    }
    
    public Imagen(String idImagen, byte[] bytes){
        this.idImagen = idImagen;
        this.bytes = bytes;
    }

    public String getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(String idImagen) {
        this.idImagen = idImagen;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}

