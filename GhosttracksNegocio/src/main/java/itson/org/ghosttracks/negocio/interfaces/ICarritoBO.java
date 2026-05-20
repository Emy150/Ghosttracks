/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.org.ghosttracks.negocio.interfaces;

import itson.org.ghosttracks.dtos.CarritoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;

/**
 *
 * @author nafbr
 */
public interface ICarritoBO {
    
    CarritoDTO agregarProducto(CarritoDTO carritoActual, ProductoDTO producto, Integer cantidad) throws NegocioException;
    
    CarritoDTO eliminarProducto(CarritoDTO carritoActual, Long idProducto) throws NegocioException;
}
