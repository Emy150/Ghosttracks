/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.entidades;

import itson.org.ghosttracks.enums.EstadoOrden;
import itson.org.ghosttracks.enums.TipoOrden;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author nafbr
 */
public class Orden {
    private Long idOrden;
    private String folio;
    private TipoOrden tipoOrden;
    private String comentarios;
    private Double total;
    private LocalDate fechaEntregaEst;
    private LocalDateTime fechaSolicitud;
    private EstadoOrden estadoOrden;
    private List<ProductoOrden> productosOrden;
    private Byte[] imagenRecepcion;
    
    //Relaciones
    private Long idProovedor;
    private Long idSucursal;
}
