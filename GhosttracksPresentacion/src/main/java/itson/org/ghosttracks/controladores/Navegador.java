
package itson.org.ghosttracks.controladores;

import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.presentacion.VentanaPrincipal;
import itson.org.ghosttracks.presentacion.administrador.PanelConfirmarEmpaquetado;
import itson.org.ghosttracks.presentacion.administrador.PanelDatosPaquete;
import itson.org.ghosttracks.presentacion.administrador.PantallaVentasProcesarAdmin;
import itson.org.ghosttracks.presentacion.administrador.PantallaVentas;
import itson.org.ghosttracks.presentacion.cliente.PantallaCarrito;
import itson.org.ghosttracks.presentacion.cliente.PantallaFormularioContacto;
import itson.org.ghosttracks.presentacion.cliente.PantallaFormularioEntrega;
import itson.org.ghosttracks.presentacion.cliente.PantallaInicioCliente;
import itson.org.ghosttracks.presentacion.cliente.PantallaSeleccionMetodoDePago;
import itson.org.ghosttracks.presentacion.cliente.PantallaVistaProducto;
import itson.org.ghosttracks.presentacion.panelLogin;
import itson.org.ghosttracks.utilerias.PanelHeader;
import itson.org.ghosttracks.utilerias.pnlMenuLateral;
import itson.org.ghosttracks.utilerias.pnlMenuLateralAdmin;
import itson.org.ghosttracks.utilerias.pnlResumenPedidoConfirmado;
import javax.swing.JPanel;

/**
 *
 * @author oliro
 */
public class Navegador {
    private final VentanaPrincipal ventana;
    private final ControlVentaEnLinea ctrlVentaLinea;
    private ControladorVentasAdmin ctrlVentasAdmin;
 
    public Navegador(VentanaPrincipal ventana) {
        this.ctrlVentaLinea = new ControlVentaEnLinea(this);
        this.ventana = ventana;
    }
    
    //Cosas de las sesión
    public void irLogin() {
        ventana.limpiarMenuYHeader();
        ControlLogin ctrl = new ControlLogin(this);
        panelLogin vista = new panelLogin(ctrl);
        ventana.cambiarPantalla(vista);
    }
 
    public void cerrarSesion() {
        ctrlVentasAdmin = null;
        irLogin();
    }
    
    //Paneles del cliente
     public void iniciarSesionClienteExitoso() {
        ventana.fijarMenuLateral(new pnlMenuLateral(this));
        ventana.fijarHeader(new PanelHeader(this));
    }
 
    public void irInicioCliente() {
        PantallaInicioCliente vista = new PantallaInicioCliente(ctrlVentaLinea);
        ventana.cambiarPantalla(vista);
    }
 
    public void irVistaProducto(ProductoDTO producto) {
        PantallaVistaProducto vista = new PantallaVistaProducto(ctrlVentaLinea, producto);
        ventana.cambiarPantalla(vista);
    }
 
    public void irCarrito() {
        PantallaCarrito vista = new PantallaCarrito(ctrlVentaLinea);
        ventana.cambiarPantalla(vista);
    }
 
    public void irFormularioContacto() {
        PantallaFormularioContacto vista = new PantallaFormularioContacto(ctrlVentaLinea);
        ventana.cambiarPantalla(vista);
    }
 
    public void irFormularioEntrega() {
        PantallaFormularioEntrega vista = new PantallaFormularioEntrega(ctrlVentaLinea);
        ventana.cambiarPantalla(vista);
    }
 
    public void irSeleccionMetodoPago() {
        PantallaSeleccionMetodoDePago vista = new PantallaSeleccionMetodoDePago(ctrlVentaLinea);
        ventana.cambiarPantalla(vista);
    }
 
    public void irPedidoConfirmado(PedidoDTO pedidoConfirmado) {
        pnlResumenPedidoConfirmado vista = new pnlResumenPedidoConfirmado(ctrlVentaLinea);
        vista.cargarDatosPedido(pedidoConfirmado);
        ventana.cambiarPantalla(vista);
    }
    
    //Paneles de admin
     public void iniciarSesionAdminExitoso() {
        ctrlVentasAdmin = new ControladorVentasAdmin(this);
        ventana.fijarMenuLateral(new pnlMenuLateralAdmin(this));
        ventana.fijarHeader(new PanelHeader(this));
    }
 
    public void irVentasAdmin() {
        PantallaVentas vista = new PantallaVentas(ctrlVentasAdmin);
        ventana.cambiarPantalla(vista);
    }
 
    public void irPantallaConfirmarEmpaque(PedidoDTO pedidoSeleccionado) {
        PantallaVentasProcesarAdmin vistaBase = new PantallaVentasProcesarAdmin(ctrlVentasAdmin, pedidoSeleccionado);
        JPanel panelEmpaque = new PanelConfirmarEmpaquetado(ctrlVentasAdmin, pedidoSeleccionado);
        vistaBase.cambiarPanelAccion(panelEmpaque);
        ventana.cambiarPantalla(vistaBase);
    }
 
    public void irPantallaConfirmarEnvio(PedidoDTO pedidoSeleccionado) {
        PantallaVentasProcesarAdmin vistaBase = new PantallaVentasProcesarAdmin(ctrlVentasAdmin, pedidoSeleccionado);
        JPanel panelEnvio = new PanelDatosPaquete(ctrlVentasAdmin, pedidoSeleccionado);
        vistaBase.cambiarPanelAccion(panelEnvio);
        ventana.cambiarPantalla(vistaBase);
    }
    
    //Utilidades
    public void mostrarMensaje(String mensaje, boolean esError) {
        ventana.mostrarMensaje(mensaje, esError);
    }
}
