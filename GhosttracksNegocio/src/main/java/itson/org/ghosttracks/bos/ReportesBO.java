package itson.org.ghosttracks.bos;

import itson.org.ghosttracks.dtos.AdministradorDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.dtos.ReporteInventarioDTO;
import itson.org.ghosttracks.negocio.interfaces.IFactoryReportesInventario;
import itson.org.ghosttracks.negocio.interfaces.IReportesBO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import itson.org.ghosttracksnegocio.factory.FactoryReportesInventario;
import java.util.List;

/**
 * Componente enfocado en reglas de negocio críticas, auditoría y seguridad de reportes.
 * Básicamente el que dice quién puede ver los números de la empresa.
 */
public class ReportesBO implements IReportesBO {

    private IFactoryReportesInventario factoryReportes; // Usamos la interfaz buscando desacoplamiento 
    private final AdministradoresBO administradoresBO;

    /**
     * Constructor: Aquí inicializamos las herramientas de auditoría.
     */
    public ReportesBO() {
        // Instanciamos la fábrica encargada de construir los reportes
        this.factoryReportes = new FactoryReportesInventario(); 
        // Reutilizamos el BO de admins.
        this.administradoresBO = new AdministradoresBO();
    }

    /**
     * GENERAR REPORTE
     * Recibe la lista de cosas a evaluar, hace cálculos de riesgo y escupe el DTO final armado.
     * @return 
     */
    @Override
    public ReporteInventarioDTO generarReporte(
            List<ProductoDTO> productos, 
            int totalArticulos, 
            double valorTotal, 
            int stockCritico, 
            String correoAdmin, 
            String contrasenia) throws NegocioException {
        
        // VALIDAR A NUESTRO USUARIO
        // Antes de enseñarte los secretos del negocio, ocupo saber si de verdad eres jefe.
        AdministradorDTO admin = administradoresBO.validarAutorizacion(correoAdmin, contrasenia);

        // Si el wey no existe o se equivocó de contraseña... lol, denegado de una.
        if (admin == null) {
            throw new NegocioException("No autorizado: Credenciales de administrador incorrectas.");
        }

        // Error de captura? no pasa nada, aquí resolvemos
        if (totalArticulos > 0 && valorTotal <= 0.0) {
            throw new NegocioException("Inconsistencia crítica: Existen artículos registrados pero el valor total del inventario es $0.00.");
        }
        
        // 
        // Empezamos a armar la firma de auditoría con el nombre del responsable
        String firmaAutorizacion = "Generado por: " + admin.getNombre() + " (" + correoAdmin + ")";
        

        // LLAMAMOS A NUESTRO FACTORY
        // El BO ya hizo lo suyo, OSEA, validó seguridad
        // Ahora, en lugar de ponerse a hacer un "new ReporteInventarioDTO" kilométrico aquí adentro,
        // le avienta los datos limpios a la Fábrica para q ella los ensamble y nos regrese el juguete terminado.
        return factoryReportes.crearReporte(
            productos, 
            totalArticulos, 
            valorTotal, 
            stockCritico, 
            firmaAutorizacion
        );
    }
}