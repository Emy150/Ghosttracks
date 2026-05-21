package itson.org.ghosttracks.entidades;

/**
 *
 * @author emyla
 */
public class Imagen {
    
    private String idImagen;
    private byte[] datos;
    
    public Imagen(){
        
    }
    
    public Imagen(String idImagen, byte[] bytes){
        this.idImagen = idImagen;
        this.datos = bytes;
    }

    public String getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(String idImagen) {
        this.idImagen = idImagen;
    }

    public byte[] getDatos() {
        return datos;
    }

    public void setDatos(byte[] datos) {
        this.datos = datos;
    }
}

