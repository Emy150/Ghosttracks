package itson.org.ghosttracks.bos;

import itson.org.ghosttracks.daos.IPersistencia; 
import itson.org.ghosttracks.entidades.Cliente;
import itson.org.ghosttracks.fachada.PersistenciaFachada;
import itson.org.ghosttracks.negocio.interfaces.IClientesBO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Componente de Negocio para la gestión de Clientes.
 * @author emyla
 */
public class ClientesBO implements IClientesBO {

    private static final Logger LOGGER = Logger.getLogger(ClientesBO.class.getName());
    private final IPersistencia persistencia;

    public ClientesBO() {
        this.persistencia = PersistenciaFachada.getInstancia();
    }

    @Override
    public Cliente obtenerClientePorId(String idCliente) throws NegocioException {
        if (idCliente == null || idCliente.trim().isEmpty()) {
            LOGGER.warning("BO: Intento de buscar cliente con ID vacío u omitido.");
            throw new NegocioException("El ID del cliente no es válido.");
        }
        try {
            Cliente cliente = persistencia.obtenerClientePorId(idCliente);
            if (cliente == null) {
                throw new NegocioException("No se encontró ningún cliente con el ID especificado.");
            }
            return cliente;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "BO: Error al consultar el cliente por ID: " + idCliente, e);
            throw new NegocioException("Error en la base de datos al buscar al cliente.", e);
        }
    }

    @Override
    public Cliente iniciarSesion(String correo, String contrasena) throws NegocioException {
        if (correo == null || correo.trim().isEmpty()) {
            throw new NegocioException("El correo electrónico es requerido.");
        }
        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new NegocioException("La contraseña es requerida.");
        }

        try {
            Cliente cliente = persistencia.autenticarCliente(correo, contrasena);
            
            if (cliente == null) {
                LOGGER.info("BO: Intento de inicio de sesión fallido para el correo: " + correo);
                throw new NegocioException("Credenciales incorrectas o el cliente no existe.");
            }

            LOGGER.fine("BO: Cliente autenticado con éxito: " + correo);
            return cliente;
        } catch (NegocioException e) {
            throw e; 
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "BO: Error inesperado durante el inicio de sesión de: " + correo, e);
            throw new NegocioException("Error interno al intentar iniciar sesión.", e);
        }
    }

    @Override
    public List<String> buscarIdsPorNombre(String nombreCliente) throws NegocioException {
        if (nombreCliente == null || nombreCliente.trim().isEmpty()) {
            throw new NegocioException("El filtro de nombre no puede estar vacío.");
        }
        try {
            List<String> ids =  persistencia.buscarIdsClientesPorNombre(nombreCliente);
            LOGGER.fine("BO: Se encontraron " + ids.size() + " IDs de clientes que coinciden con: " + nombreCliente);
            return ids;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "BO: Falló la consulta de filtrado de IDs por nombre: " + nombreCliente, e);
            throw new NegocioException("Error al buscar filtros de clientes.", e);
        }
    }
}