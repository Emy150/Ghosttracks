/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.negocio.mappers;

import itson.org.ghosttracks.dtos.PaqueteDTO;
import itson.org.ghosttracks.entidades.Paquete;

/**
 *
 * @author nafbr
 */
public class PaqueteMapper {

    private PaqueteMapper() {}
 
    public static PaqueteDTO toDTO(Paquete entidad) {
        if (entidad == null) return null;
 
        PaqueteDTO dto = new PaqueteDTO();
        dto.setIdPaquete(entidad.getIdPaquete());
        dto.setNumeroGuia(entidad.getNumeroGuia());
        dto.setIdEnvio(entidad.getIdEnvio());
        dto.setEstado(entidad.getEstado());
        dto.setFechaEnvio(entidad.getFechaEnvio());
        dto.setFechaEntregaEstimada(entidad.getFechaEntregaEstimada());
        dto.setFechaEntregaFinal(entidad.getFechaEntregaFinal());
        dto.setUbicacionActual(entidad.getUbicacionActual());
        return dto;
    }
 
    public static Paquete toEntidad(PaqueteDTO dto) {
        if (dto == null) return null;
 
        Paquete entidad = new Paquete();
        entidad.setIdPaquete(dto.getIdPaquete());
        entidad.setNumeroGuia(dto.getNumeroGuia());
        entidad.setIdEnvio(dto.getIdEnvio());
        entidad.setEstado(dto.getEstado());
        entidad.setFechaEnvio(dto.getFechaEnvio());
        entidad.setFechaEntregaEstimada(dto.getFechaEntregaEstimada());
        entidad.setFechaEntregaFinal(dto.getFechaEntregaFinal());
        entidad.setUbicacionActual(dto.getUbicacionActual());
        return entidad;
    }
    
}
