package itson.org.ghosttracks.negocio.adaptador;


import itson.org.ghosttracks.dtos.NuevoProductoDTO;
import itson.org.ghosttracks.dtos.ProductoActualizadoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.entidades.Imagen;
import itson.org.ghosttracks.entidades.Producto;
import itson.org.ghosttracks.enums.EstadoProducto;
import itson.org.ghosttracks.enums.TipoProducto;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * Esta clase es nuestro traductor oficial entre lo que ve el usuario (DTOs) 
 * @author emyla
 */
public class ProductoAdapter {
    
    private static final Logger LOGGER = Logger.getLogger(ProductoAdapter.class.getName());
    
    public ProductoDTO adaptProductoToProductoDTO(Producto producto) throws NegocioException{
        
        // Primero lo primero, checar q no nos manden nada vacío
        if(producto == null){
            LOGGER.severe("ERROR! El producto llegó nulo D:");
            throw new NegocioException("No fue posible adaptar de Producto a ProductoDTO");
        }
        
        try{
            ProductoDTO productoDTO = new ProductoDTO();
            productoDTO.setIdProducto(producto.getIdProducto());
            
            // Le pasamos los datos básicos
            productoDTO.setTitulo(producto.getTitulo());
            productoDTO.setArtista(producto.getArtista());
            productoDTO.setPrecio(producto.getPrecio());
            productoDTO.setStockInicial(producto.getStockInicial());
            
            if (producto.getImgProducto() != null) {
                productoDTO.setImg(producto.getImgProducto().getBytes());
            }
            
            productoDTO.setGenero(producto.getIdGenero());
            
            productoDTO.setTipo(producto.getTipo().name());  
            productoDTO.setEstado(producto.getEstado().name());
            
            LOGGER.fine("Yipiii, si se realizó la conversión a DTO");
            return productoDTO; // Retornamos el DTO ya armadito
        } catch(Exception e){
            LOGGER.severe("ERROR al armar el DTO!");
            throw new NegocioException("No fue posible adaptar de Producto a ProductoDTO", e);
        }          
    }
    
    public Producto adaptProductoDTOToProducto(ProductoDTO productoDTO) throws NegocioException{
        
        // Validamos que si haya contenido en el ProductoDTO q recibimos
        if(productoDTO == null){
            LOGGER.severe("ERROR! No se puede continuar, el DTO es nulo :c");
            throw new NegocioException("El ProductoDTO es nulo");
        }
        
        // Si todo bien, intentamos la conversión inversa
        try{
            Producto producto = new Producto();
            
            producto.setIdProducto(productoDTO.getIdProducto()); 
            producto.setTitulo(productoDTO.getTitulo());
            producto.setArtista(productoDTO.getArtista());
            producto.setPrecio(productoDTO.getPrecio());
            producto.setStockInicial(productoDTO.getStockInicial());
            
            // Pasamos el ID del género directo al producto
            producto.setIdGenero(productoDTO.getGenero());
            
            producto.setEstado(EstadoProducto.DISPONIBLE);
            producto.setTipo(TipoProducto.valueOf(productoDTO.getTipo().toUpperCase()));
            
            // Armamos el objeto Imagen
            if (productoDTO.getImg() != null) {
                Imagen img = new Imagen();
                img.setBytes(productoDTO.getImg());
                producto.setImgProducto(img);
            }
            
            LOGGER.fine("Conversión realizada con éxito :D");
            return producto;
            
        } catch(Exception e){
            LOGGER.severe("ERROR! Falló la conversión a Entidad");
            throw new NegocioException("No fue posible adaptar de ProductoDTO a Producto", e);
        }
    }
        
    public Producto adaptNuevoProductoDTOToProducto(NuevoProductoDTO nuevoDTO) throws NegocioException{
        
        // Checamos q el nuevo DTO traiga info
        if(nuevoDTO == null){
            LOGGER.severe("ERROR! El nuevo DTO está vacío :o");
            throw new NegocioException("Nuevo producto DTO está vacío");
        }
        
        try{
            Producto producto = new Producto();
            
            // En un producto nuevo, el ID siempre es null para que Mongo lo genere
            producto.setIdProducto(null);
            producto.setTitulo(nuevoDTO.getTitulo());
            producto.setArtista(nuevoDTO.getArtista());
            producto.setPrecio(nuevoDTO.getPrecio());
            producto.setStockInicial(nuevoDTO.getStockInicial());
            
            // Pasamos el ID de referencia del género
            producto.setIdGenero(nuevoDTO.getGenero());
            
            // Default a disponible porq pues... es nuevo lol
            producto.setEstado(EstadoProducto.DISPONIBLE); 
            producto.setTipo(TipoProducto.valueOf(nuevoDTO.getTipo().toUpperCase()));
            
            // Preparamos la imagen
            if (nuevoDTO.getImagen() != null) {
                Imagen img = new Imagen();
                img.setBytes(nuevoDTO.getImagen());
                producto.setImgProducto(img);
            }
            
            // Le ponemos la fecha del DTO o la de ahorita si viene nula
            producto.setFechaRegistro(nuevoDTO.getFechaRegistro() != null ? nuevoDTO.getFechaRegistro() : LocalDateTime.now());
            
            LOGGER.fine("Conversión de Nuevo Producto DTO a Producto al cien! yey");
            return producto;
        } catch(Exception e){
            LOGGER.severe("ERROR!");
            throw new NegocioException("No se pudo convertir de Nuevo dto a Producto", e);
        }
    }
    
    public Producto adaptProductoActualizadoDTOToProducto(ProductoActualizadoDTO actualizadoDTO) throws NegocioException {
    
        // Validamos que el DTO no venga nulo porq si no explota
        if (actualizadoDTO == null) {
            LOGGER.severe("ERROR! El DTO de producto actualizado es nulo :/");
            throw new NegocioException("El ProductoActualizadoDTO no puede ser nulo");
        }

        try {
            Producto producto = new Producto();

            // Pasamos todos los datos 
            producto.setIdProducto(actualizadoDTO.getIdProducto());
            producto.setTitulo(actualizadoDTO.getTitulo());
            producto.setArtista(actualizadoDTO.getArtista());
            producto.setPrecio(actualizadoDTO.getPrecio());
            producto.setStockInicial(actualizadoDTO.getStockInicial());

            producto.setIdGenero(actualizadoDTO.getGenero());

            producto.setTipo(TipoProducto.valueOf(actualizadoDTO.getTipo().toUpperCase()));
            producto.setEstado(EstadoProducto.DISPONIBLE); 

            if (actualizadoDTO.getImagen() != null) {
                Imagen img = new Imagen();
                img.setBytes(actualizadoDTO.getImagen());
                producto.setImgProducto(img);
            }

            LOGGER.fine("Se adaptó el Producto Actualizado con éxito :P");
            return producto;

        } catch (IllegalArgumentException e) {
            LOGGER.severe("Ups, error con el Enum! Checa bien el tipo de producto");
            throw new NegocioException("Error en el formato de tipo", e);
        } catch (Exception e) {
            LOGGER.severe("Error inesperado en la conversión!");
            throw new NegocioException("No fue posible adaptar el producto actualizado", e);
        }
    }
    
}