package itson.org.ghosttracksnegocio.factory;

import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.dtos.ReporteInventarioDTO;
import itson.org.ghosttracks.negocio.interfaces.IFactoryReportesInventario;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author emyla
 */
public class FactoryReportesInventario implements IFactoryReportesInventario{
    
    @Override
    public ReporteInventarioDTO crearReporte(
            List<ProductoDTO> productos, 
            int totalArticulos, 
            double valorTotal, 
            int stockCritico, 
            String autor) throws NegocioException{
        
        ReporteInventarioDTO dto = new ReporteInventarioDTO();
        dto.setProductos(productos);
        dto.setTotalArticulos(totalArticulos);
        dto.setValorTotalInventario(valorTotal);
        dto.setArticulosStockCritico(stockCritico);
        dto.setFechaGeneracion(LocalDateTime.now());
        dto.setAutor(autor != null ? autor : "Sistema Automático");
        
        return dto;
    }
}
