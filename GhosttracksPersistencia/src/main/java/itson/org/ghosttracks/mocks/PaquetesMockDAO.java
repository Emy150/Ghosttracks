/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.mocks;

import itson.org.ghosttracks.daos.IPaquetesDAO;
import itson.org.ghosttracks.entidades.Paquete;
import itson.org.ghosttracks.enums.EstadoPaquete;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emyla
 */
public class PaquetesMockDAO implements IPaquetesDAO{
    
    private static List<Paquete> paquetesDB = new ArrayList<>();
    private static Long generadorIds = 1L;
    private static final Logger LOGGER = Logger.getLogger(PaquetesMockDAO.class.getName());
    private static boolean datosPrecargados = false;

    public PaquetesMockDAO() {
        if (!datosPrecargados) {
            precargarPaquetes();
            datosPrecargados = true;
        }
    }

    private void precargarPaquetes() {
        Paquete p1 = new Paquete();
        p1.setIdPaquete(generadorIds++);
        p1.setNumeroGuia("SKY-12345");
        p1.setEstado(EstadoPaquete.ENVIADO);
        p1.setFechaEnvio(LocalDateTime.now());
        p1.setPesoKg(2.0);
        p1.setLargoCm(40.0);
        p1.setAnchoCm(32.0);
        p1.setAltoCm(6.0);
        
        paquetesDB.add(p1);
    }

    @Override
    public Paquete agregarPaquete(Paquete paquete) throws PersistenciaException {
        try {
            paquete.setIdPaquete(generadorIds++);
            paquetesDB.add(paquete);
            LOGGER.log(Level.INFO, "Paquete guardado exitosamente con ID {0} y guía {1}", 
                    new Object[]{paquete.getIdPaquete(), paquete.getNumeroGuia()});
            return paquete;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al guardar el paquete", e);
            throw new PersistenciaException("Error al guardar el paquete: " + e.getMessage());
        }
    }

    @Override
    public Paquete buscarPorId(Long idPaquete) throws PersistenciaException {
        return paquetesDB.stream()
            .filter(p -> p.getIdPaquete().equals(idPaquete))
            .findFirst()
            .orElseThrow(() -> new PersistenciaException("No se encontró ningún paquete con el ID: " + idPaquete));
    }

    @Override
    public Paquete buscarPorGuia(String numeroGuia) throws PersistenciaException {
        return paquetesDB.stream()
            .filter(p -> p.getNumeroGuia() != null && p.getNumeroGuia().equals(numeroGuia))
            .findFirst()
            .orElseThrow(() -> new PersistenciaException("No se encontró ningún paquete con la guía: " + numeroGuia));
    }

    @Override
    public Paquete actualizarPaquete(Paquete paqueteActualizado) throws PersistenciaException {
        for (int i = 0; i < paquetesDB.size(); i++) {
            if (paquetesDB.get(i).getIdPaquete().equals(paqueteActualizado.getIdPaquete())) {
                paquetesDB.set(i, paqueteActualizado);
                return paqueteActualizado;
            }
        }
        throw new PersistenciaException("Error al actualizar: Paquete no encontrado.");
    }

    @Override
    public List<Paquete> obtenerTodos() throws PersistenciaException {
        return new ArrayList<>(paquetesDB);
    }
}