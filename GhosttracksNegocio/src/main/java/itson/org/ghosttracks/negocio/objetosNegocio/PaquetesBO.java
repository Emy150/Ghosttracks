/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.negocio.objetosNegocio;

import itson.org.ghosttracks.daos.IPaquetesDAO;
import itson.org.ghosttracks.dtos.PaqueteDTO;
import itson.org.ghosttracks.entidades.Paquete;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import itson.org.ghosttracks.mocks.PaquetesMockDAO;
import itson.org.ghosttracks.negocio.adaptador.SkydropxAdapter;
import itson.org.ghosttracks.negocio.interfaces.IPaquetesBO;
import itson.org.ghosttracks.negocio.interfaces.IProveedorEnvios;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author emyla
 */
public class PaquetesBO implements IPaquetesBO{
    
    private final IPaquetesDAO paquetesDAO;
    private final IProveedorEnvios proveedorEnvios;
    
    public PaquetesBO() {
        this.paquetesDAO = new PaquetesMockDAO();
        this.proveedorEnvios = new SkydropxAdapter();
    }

    @Override
    public Paquete registrarEmpaque(Paquete paquete) throws NegocioException {
        try {
            paquete.setFechaEnvio(LocalDateTime.now());
            return paquetesDAO.agregarPaquete(paquete);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo registrar el empaque del pedido.", ex);
        }
    }

    @Override
    public Paquete generarAsignarGuia(Long idPaquete) throws NegocioException {
        try {
            
            // 1. Buscamos el paquete que queremos actualizar
            Paquete paqueteDB = paquetesDAO.buscarPorId(idPaquete);
            
            Long idPedidoRef = paqueteDB.getPedido().getIdPedido();
            Double pesoKg = paqueteDB.getPesoKg();

            PaqueteDTO infoSkydropx = proveedorEnvios.generarGuiaPaquete(idPedidoRef, pesoKg);
            
            paqueteDB.setNumeroGuia(infoSkydropx.getNumeroGuia());
            paqueteDB.setFechaEntregaEstimada(infoSkydropx.getFechaEntregaEstimada());
            
            return paquetesDAO.actualizarPaquete(paqueteDB);
            
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error de base de datos al buscar/actualizar el paquete.", ex);
        } catch (Exception ex) {
            throw new NegocioException("No se pudo generar la guía con el proveedor: " + ex.getMessage(), ex);
        }
    }

    @Override
    public Paquete consultarPaquetePorId(Long idPaquete) throws NegocioException {
        try {
            return paquetesDAO.buscarPorId(idPaquete);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se encontró el paquete solicitado.", ex);
        }
    }

    @Override
    public List<Paquete> consultarTodos() throws NegocioException {
        try {
            return paquetesDAO.obtenerTodos();
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al cargar la lista de paquetes.", ex);
        }
    }
    
}
