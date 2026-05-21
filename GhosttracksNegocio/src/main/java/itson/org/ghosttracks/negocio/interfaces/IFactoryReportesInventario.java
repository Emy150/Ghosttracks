/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.negocio.interfaces;

import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.dtos.ReporteInventarioDTO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.util.List;

/**
 *
 * @author emyla
 */
public interface IFactoryReportesInventario {
    
    public ReporteInventarioDTO crearReporte(
            List<ProductoDTO> productos, 
            int totalArticulos, 
            double valorTotal, 
            int stockCritico, 
            String autor) throws NegocioException;
}
