/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.mocks;

import itson.org.ghosttracks.daos.IAdministradoresDAO;
import itson.org.ghosttracks.entidades.Administrador;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emyla
 */
public class AdministradoresMockDAO implements IAdministradoresDAO {
    
    private List<Administrador> administradoresDB;

    public AdministradoresMockDAO() {
        this.administradoresDB = new ArrayList<>();
        
        Administrador admin = new Administrador();
        admin.setIdUsuario(2L);
        admin.setIdEmpleado(1001L); 
        admin.setNombres("Nafi");
        admin.setApellidoPaterno("Master");
        admin.setApellidoMaterno("Ghost");
        admin.setCorreo("admin@ghosttracks.com");
        admin.setContraseña("adminSeguro123");
        
        administradoresDB.add(admin);
    }

    @Override
    public Administrador buscarPorId(Long idUsuario) throws PersistenciaException {
        for (Administrador admin : administradoresDB) {
            if (admin.getIdUsuario().equals(idUsuario)) {
                return admin;
            }
        }
        throw new PersistenciaException("Administrador no encontrado con el ID de usuario: " + idUsuario);
    }
    
    @Override
    public Administrador buscarPorIdEmpleado(Long idEmpleado) throws PersistenciaException {
        for (Administrador admin : administradoresDB) {
            if (admin.getIdEmpleado().equals(idEmpleado)) {
                return admin;
            }
        }
        throw new PersistenciaException("Administrador no encontrado con el número de empleado: " + idEmpleado);
    }
    
}
