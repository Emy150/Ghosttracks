package itson.org.ghosttracks.controladores;

import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import itson.org.ghosttracks.presentacion.administrador.PantallaGestionArticulos;
import itson.org.ghosttracks.utilerias.DialogosConfirmacion;
import itson.org.ghosttracksgestionarticulos.fachada.GestionArticulos;
import itson.org.ghosttracksgestionarticulos.interfaces.IGestionArticulos;
import java.awt.Window;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 *
 * @author emyla
 */
public class ControladorGestionArticulos {
    
    private final Navegador navegador;
    private final IGestionArticulos gestionFachada; 
    
    public ControladorGestionArticulos(Navegador nav) {
        this.navegador = nav;
        this.gestionFachada = new GestionArticulos(); 
    }
    
    public void llenarTablaInventario(PantallaGestionArticulos vista) {
        try {
            List<ProductoDTO> productos = gestionFachada.consultarInventario("");
            vista.cargarCatalogo(productos);
        } catch (NegocioException ex) {
            navegador.mostrarMensaje("Error al cargar el inventario: " + ex.getMessage(), true);
        }
    }
    
    public void solicitarEliminacion(PantallaGestionArticulos vista, ProductoDTO producto) {

        if (!SesionUsuario.getInstancia().haySesionActiva()) {
            navegador.mostrarMensaje("Error: No hay ninguna sesión activa para autorizar esta acción.", true);
            return;
        }

        Window ventanaPadre = SwingUtilities.getWindowAncestor(vista);

        boolean seguroEliminar = DialogosConfirmacion.solicitarConfirmacionEliminar(ventanaPadre, producto);

        if (!seguroEliminar) {
            return; 
        }

        String passwordIngresada = DialogosConfirmacion.solicitarContraseniaAdmin(ventanaPadre);

        if (passwordIngresada == null || passwordIngresada.trim().isEmpty()) {
            return;
        }

        try {
            String idUsuarioLogueado = SesionUsuario.getInstancia().getCliente().getIdCliente(); 

            Object adminDTO = gestionFachada.validarContraseniaAdmin(idUsuarioLogueado, passwordIngresada);

            if (adminDTO != null) {
                this.eliminarArticuloDeBaseDatos(producto.getIdProducto());

                this.llenarTablaInventario(vista);

            } else {
                navegador.mostrarMensaje("Contraseña incorrecta. Autorización denegada.", true);
            }

        } catch (NegocioException ex) {
            navegador.mostrarMensaje("Error al validar la autorización: " + ex.getMessage(), true);
        }
    }
    
    private void eliminarArticuloDeBaseDatos(String idProducto) {
        try {
            gestionFachada.eliminarProducto(idProducto); 
            navegador.mostrarMensaje("El artículo ha sido eliminado correctamente.", false);
        } catch (NegocioException ex) {
            navegador.mostrarMensaje("Error al intentar eliminar el artículo: " + ex.getMessage(), true);
        }
    }
}