package itson.org.ghosttracksgestionarticulos.interfaces;

import itson.org.ghosttracks.dtos.AdministradorDTO;
import java.util.List;

import itson.org.ghosttracks.dtos.NuevoProductoDTO;
import itson.org.ghosttracks.dtos.ProductoActualizadoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;

/**
 *
 * @author emyla
 */
public interface IGestionArticulos {
    
    // Productos
    ProductoDTO registrarProducto(NuevoProductoDTO nuevoDto) throws NegocioException;
    
    ProductoDTO modificarProducto(ProductoActualizadoDTO actualizadoDto) throws NegocioException;
    
    ProductoDTO eliminarProducto(String idProducto) throws NegocioException;
    
    List<ProductoDTO> consultarCatalogo(String filtro) throws NegocioException;
    
    // Usuarios
    AdministradorDTO autenticarAdmin(String correo, String contrasenia) throws NegocioException;
}
