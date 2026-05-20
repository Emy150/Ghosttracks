/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.sistemaskydropxapi.servicios;

import itson.org.sistemaskydropxapi.core.SkydropxCliente;
import itson.org.sistemaskydropxapi.objetos.SkydropxPaquete;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Logger;

/**
 *
 * @author emyla
 */
public class SkydropxServicio {
    
    // Instanciamos el Logger oficial para esta clase
    private static final Logger LOGGER = Logger.getLogger(SkydropxServicio.class.getName());

    // Método para cotizar el envío
    public Double cotizarEnvio(Double pesoKg) {
        // VALIDACIÓN: El peso no puede ser nulo, cero o negativo
        if (pesoKg == null || pesoKg <= 0) {
            LOGGER.severe("Intento de cotización fallido. Peso inválido: {0}");
            throw new IllegalArgumentException("El peso debe ser mayor a 0 para cotizar el envío.");
        }
        
        return 50.0 + (pesoKg * 10.0);
    }

    // Crea, genera guía y guarda el paquete
    public SkydropxPaquete crearEnvio(Long idReferenciaExterna, Double pesoKg) {
        try {
            if (idReferenciaExterna == null) {
                throw new IllegalArgumentException("El ID de referencia externa es obligatorio para crear el envío.");
            }

            String numeroGuia = "SKY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            Double costo = cotizarEnvio(pesoKg); 
            LocalDateTime fechaActual = LocalDateTime.now();
            LocalDateTime entregaEstimada = fechaActual.plusDays(8); 
            
            SkydropxPaquete nuevoPaquete = new SkydropxPaquete(
                    numeroGuia, 
                    idReferenciaExterna, 
                    "ENVIADO", 
                    fechaActual, 
                    entregaEstimada,  
                    costo
            );
            
            // El Servicio usa al Cliente para guardar en la BD
            SkydropxCliente.getInstancia().getBaseDatos().guardar(nuevoPaquete);
            return nuevoPaquete;

        } catch (IllegalArgumentException e) {
           LOGGER.severe("[Skydropx API] Datos inválidos al crear envío: {0}");
            throw e; 
        } catch (Exception e) {
            LOGGER.severe("[Skydropx API] Error crítico interno al generar la guía.");
            throw new RuntimeException("Error interno en el servidor de Skydropx al procesar el envío.", e);
        }
    }

    // Aun no se utiliza pero lo guardaremos por si las dudas
//    // Método para consultar un paquete
//    public SkydropxPaquete consultarPaquete(String numeroGuia) {
//        if (numeroGuia == null || numeroGuia.trim().isEmpty()) {
//            LOGGER.severe("Intento de consulta bloqueado: número de guía vacío.");
//            throw new IllegalArgumentException("Debe proporcionar un número de guía válido para la consulta.");
//        }
//
//        SkydropxPaquete paquete = SkydropxCliente.getInstancia().getBaseDatos().buscar(numeroGuia);
//        return paquete;
//    }
}
