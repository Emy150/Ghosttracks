package itson.org.ghosttracksgestionarticulos.interfaces;

import itson.org.ghosttracks.dtos.AdministradorDTO;
import itson.org.ghosttracks.dtos.GeneroDTO;
import java.util.List;

import itson.org.ghosttracks.dtos.NuevoProductoDTO;
import itson.org.ghosttracks.dtos.ProductoActualizadoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.dtos.ReporteInventarioDTO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.time.LocalDateTime;

/**
 *
 * @author emyla
 */
public interface IGestionArticulos {
    
    // Productos
    ProductoDTO registrarProducto(NuevoProductoDTO nuevoDto) throws NegocioException;
    
    ProductoDTO modificarProducto(ProductoActualizadoDTO actualizadoDto) throws NegocioException;
    
    public ProductoDTO eliminarProducto(String idProducto, String correo, String contrasenia) throws NegocioException;
    
    List<ProductoDTO> consultarInventario(String filtro) throws NegocioException;
    
    List<ProductoDTO> consultarInventarioCompleto() throws NegocioException;
    
    public String generarSkuProducto(String inicialesCategoria) throws NegocioException;
    
    public List<GeneroDTO> consultarGeneros() throws NegocioException;
    
    // Usuarios
    AdministradorDTO autenticarAdmin(String correo, String contrasenia) throws NegocioException;

    AdministradorDTO validarAutorizacionAdmin(String correo, String clave) throws NegocioException;
    
    // Reportes
    public ReporteInventarioDTO generarReporteInventario(LocalDateTime inicio, LocalDateTime fin, String correo, String clave) throws NegocioException;
    
    byte[] exportarReportePDF(ReporteInventarioDTO reporte) throws NegocioException;
    
}
