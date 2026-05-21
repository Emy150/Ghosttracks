package itson.org.ghosttracks.bos;

import itson.org.ghosttracks.daos.IPersistencia;
import itson.org.ghosttracks.dtos.AdministradorDTO;
import itson.org.ghosttracks.entidades.Administrador;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import itson.org.ghosttracks.fachada.PersistenciaFachada;
import itson.org.ghosttracks.mappers.AdministradorMapper;
import itson.org.ghosttracks.negocio.interfaces.IAdministradoresBO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;

import java.util.logging.Logger;

public class AdministradoresBO implements IAdministradoresBO {

    private static final Logger LOGGER = Logger.getLogger(AdministradoresBO.class.getName());

    private final IPersistencia persistencia;
    private final AdministradorMapper mapper;

    public AdministradoresBO() {
        this.persistencia = PersistenciaFachada.getInstancia();
        this.mapper = new AdministradorMapper();
    }

    @Override
    public AdministradorDTO validarAutorizacion(String correo, String contrasenia)
            throws NegocioException {

        try {
            if (correo == null || correo.trim().isEmpty()) {
                throw new NegocioException("Correo requerido");
            }

            if (contrasenia == null || contrasenia.trim().isEmpty()) {
                throw new NegocioException("Contraseña requerida");
            }

            Administrador admin =
                    persistencia.autenticarAdmin(correo, contrasenia);

            if (admin == null) {
                throw new NegocioException("Credenciales inválidas");
            }

            return mapper.toDTO(admin);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al validar autorización del administrador", e);
        }
    }
}