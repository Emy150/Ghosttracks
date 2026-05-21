package itson.org.ghosttracks.controladores;

import itson.org.ghosttracks.dtos.GeneroDTO;
import itson.org.ghosttracks.dtos.NuevoProductoDTO;
import itson.org.ghosttracks.dtos.ProductoActualizadoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.dtos.ReporteInventarioDTO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import itson.org.ghosttracks.presentacion.administrador.PantallaEditarProducto;
import itson.org.ghosttracks.presentacion.administrador.PantallaGestionArticulos;
import itson.org.ghosttracks.presentacion.administrador.PantallaReporteInventario;
import itson.org.ghosttracks.utilerias.DialogosConfirmacion;
import itson.org.ghosttracksgestionarticulos.fachada.GestionArticulos;
import itson.org.ghosttracksgestionarticulos.interfaces.IGestionArticulos;
import java.awt.Window;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 * Controlador para la gestión de artículos (o inventario pueh).
 * Coordina las interacciones entre las pantallas de la interfaz y la capa de negocio.
 * * @author emyla
 */
public class ControladorGestionArticulos {
    
    private final Navegador navegador;
    private final IGestionArticulos gestionFachada; 
    private String correoAdminLogueado;
    private String claveAdminLogueado;
    private List<ProductoDTO> listaProductosCached = new ArrayList<>();
    
    public ControladorGestionArticulos(Navegador nav) {
        this.navegador = nav;
        this.gestionFachada = new GestionArticulos(); 
    }
    
    public void llenarTablaInventario(PantallaGestionArticulos vista) {
        try {
            this.listaProductosCached = gestionFachada.consultarInventarioCompleto(); 
            vista.cargarCatalogo(this.listaProductosCached);
        } catch (NegocioException ex) {
            navegador.mostrarMensaje("Error al cargar el inventario: " + ex.getMessage(), true);
        }
    }
    
    
    public void procesarRegistroProducto(String titulo, String artista, String tipo, String genero, 
                                         Double precio, Integer stock, File archivoImagen) {
        try {
            String iniciales = (tipo != null && tipo.length() >= 3) ? tipo.substring(0, 3) : "PROD";
            
            NuevoProductoDTO nuevoDto = new NuevoProductoDTO();
            nuevoDto.setTitulo(titulo);
            nuevoDto.setSku(gestionFachada.generarSkuProducto(iniciales));
            nuevoDto.setArtista(artista);
            nuevoDto.setTipo(tipo);
            nuevoDto.setIdGenero(genero);
            nuevoDto.setPrecio(precio);
            nuevoDto.setStockInicial(stock);
            nuevoDto.setImagen(archivoImagen); 
            nuevoDto.setFechaRegistro(LocalDateTime.now());

            ProductoDTO productoRegistrado = gestionFachada.registrarProducto(nuevoDto);

            System.out.println("¡Producto registrado con ID: " + productoRegistrado.getSku());
            navegador.mostrarMensaje("¡Artículo guardado con éxito!", false);
            navegador.abrirGestionArticulos();

        } catch (NegocioException ex) {
            System.err.println("Error de negocio: " + ex.getMessage());
            navegador.mostrarMensaje("No se pudo guardar: " + ex.getMessage(), true);
        }
    }
    
    
    // Recibe la vista de la pantalla y el DTO del producto que quieren mandar a volar.
    public void solicitarEliminacion(PantallaGestionArticulos vista, ProductoDTO producto) {
        // Buscamos la ventana padre (el Jframe o JDialog principal) para que los cuadritos de diálogo salgan centrados y bien bonitos arriba de la app
        Window ventanaPadre = SwingUtilities.getWindowAncestor(vista);

        // validacion donde le preguntamos al usuario si de verdad está seguro de borrarlo o si picó el botón por accidente lol
        boolean seguroEliminar = DialogosConfirmacion.solicitarConfirmacionEliminar(ventanaPadre, producto);
        if (!seguroEliminar) return; // Si dice que no, nos salimos en corto y aquí no pasó nada :D

        // Segunda validación
        String[] credenciales = DialogosConfirmacion.solicitarCredencialesAdmin(ventanaPadre);
        
        // Si el arreglo viene nulo significa que el usuario le picó al botón de "Cancelar" en el diálogo de las credenciales
        if (credenciales == null) {
            return; // Nos salimos sin hacer barullo
        }

        // Si no canceló, sacamos el correo y la clave del arreglo (en la pos 0 y 1)
        String correo = credenciales[0];
        String clave = credenciales[1];

        // Validamos que el usuario inteligente no haya dejado los campos vacíos o con puros espacios
        if (correo.isBlank() || clave.isBlank()) {
            // Si los dejó vacíos, le aventamos un mensaje de advertencia
            navegador.mostrarMensaje("Debe proporcionar tanto el correo como la contraseña.", true);
            return; // Pa fuera
        }

        // Si pasamos todos los filtros de arriba, ahora sí viene lo chido, dijo el Luisito
        try {
            // Llamamos a la fachada de negocio para que intente borrar el producto. 
            // Ella se va a encargar de checar si el correo y la clave son correctos y si el ID del producto existe.
            gestionFachada.eliminarProducto(producto.getIdProducto(), correo, clave);

            // Si todo salió bien y no explotó nada, actualizamos la tablita de la pantalla para que ya no aparezca el fantasma del producto borrado
            llenarTablaInventario(vista);
            
            // Le avisamos al usuario con un mensaje feliz de éxito (el "false" es para que salga con icono bonito de info, no de error)
            navegador.mostrarMensaje("Artículo eliminado con éxito.", false);

        } catch (NegocioException ex) {
            // Si la capa de negocio dice "¡Ey, ese admin no existe!" o "Contraseña incorrecta", atrapamos el error aquí
            // Y le mostramos al usuario qué salió mal en una cajita de alerta.
            navegador.mostrarMensaje("No autorizado o error: " + ex.getMessage(), true);
        }
    }
    
    // Abre pantalla para editar y le carga los datos del producto
    public void solicitarEdicion(ProductoDTO producto) {
        PantallaEditarProducto pnlEdicion = navegador.abrirPantallaEditarProducto(); 

        if (pnlEdicion != null) {
            pnlEdicion.cargarDatosFormulario(producto, this);
        }
    }

    // Procesa los cambios del formulario y actualiza en la base de datos
    public void procesarEdicionProducto(String idProducto, String titulo, String artista, String tipo, String genero, 
                                        Double precio, Integer stock, File archivoImagen) {
        try {
            ProductoActualizadoDTO dtoActualizado = new ProductoActualizadoDTO();
            dtoActualizado.setIdProducto(idProducto); 
            dtoActualizado.setTitulo(titulo);
            dtoActualizado.setArtista(artista);
            dtoActualizado.setTipo(tipo != null ? tipo.trim().toUpperCase() : null); // Formato limpio para Mongo
            dtoActualizado.setGenero(genero); 
            dtoActualizado.setPrecio(precio);
            dtoActualizado.setStockInicial(stock);
            dtoActualizado.setImagen(archivoImagen);

            gestionFachada.modificarProducto(dtoActualizado); 

            navegador.mostrarMensaje("¡Artículo actualizado con éxito!", false);
            navegador.abrirGestionArticulos();

        } catch (NegocioException ex) {
            navegador.mostrarMensaje("No se pudieron guardar los cambios: " + ex.getMessage(), true);
        }
    }
    
    // Trae la lista de géneros musicales de la BD para los combobox
    public List<GeneroDTO> obtenerListaGeneros() {
        try {
            return gestionFachada.consultarGeneros();
        } catch (NegocioException ex) {
            navegador.mostrarMensaje("Error al cargar géneros: " + ex.getMessage(), true);
            return new ArrayList<>(); // Lista vacía para que no explote la vista
        }
    }
    
    public void filtrarInventarioDinamico(PantallaGestionArticulos vista, String busqueda, String tipo, String estado) {
        final String query = (busqueda != null) ? busqueda.trim().toLowerCase() : "";
        
        List<ProductoDTO> filtrados = this.listaProductosCached.stream()
            .filter(p -> {
                if (!query.isEmpty()) {
                    String titulo = (p.getTitulo() != null) ? p.getTitulo().toLowerCase() : "";
                    String artista = (p.getArtista() != null) ? p.getArtista().toLowerCase() : "";
                    String sku = (p.getSku() != null) ? p.getSku().toLowerCase() : "";
                    
                    if (!titulo.contains(query) && !artista.contains(query) && !sku.contains(query)) {
                        return false;
                    }
                }
                
                if (tipo != null && !tipo.equals("Seleccionar tipo")) {
                    if (p.getTipo() == null || !p.getTipo().equalsIgnoreCase(tipo)) {
                        return false;
                    }
                }
                
                if (estado != null && !estado.equals("Seleccionar estado")) {
                    int stock = (p.getStockInicial() != null) ? p.getStockInicial() : 0; 
                    if (estado.equalsIgnoreCase("Disponible") && stock <= 0) {
                        return false;
                    }
                    if (estado.equalsIgnoreCase("Agotado") && stock > 0) {
                        return false;
                    }
                }
                
                return true;
            })
            .toList();
        
        // Refresca el panel de catálogo con la sublista resultante
        vista.cargarCatalogo(filtrados);
    }
    
    // REPORTES
    
    // Pide credenciales de admin y carga el reporte completo en pantalla
    public void cargarReporteInicial(PantallaReporteInventario vista) {
        Window ventanaPadre = SwingUtilities.getWindowAncestor(vista);
        
        String[] credenciales = DialogosConfirmacion.solicitarCredencialesAdmin(ventanaPadre);
        if (credenciales == null) {
            navegador.mostrarMensaje("Operación cancelada. Se requiere autorización de administrador.", true);
            regresarAlCatalogo();
            return;
        }

        String correo = credenciales[0];
        String clave = credenciales[1];

        if (correo.isBlank() || clave.isBlank()) {
            navegador.mostrarMensaje("Debe proporcionar tanto el correo como la contraseña.", true);
            regresarAlCatalogo();
            return;
        }

        try {
            ReporteInventarioDTO reporte = gestionFachada.generarReporteInventario(null, null, correo, clave);
            
            // Guarda credenciales exitosas en memoria para reusarlas al filtrar
            this.correoAdminLogueado = correo;
            this.claveAdminLogueado = clave;
            
            vista.mostrarReporteEnPantalla(reporte);
        } catch (NegocioException ex) {
            navegador.mostrarMensaje("No autorizado o error: " + ex.getMessage(), true);
            regresarAlCatalogo();
        }
    }
    
    public void procesarFiltroReporte(PantallaReporteInventario vista) {
        // Control preventivo obligatorio: Si no hay credenciales en memoria, no podemos consultar
        if (correoAdminLogueado == null || claveAdminLogueado == null) {
            navegador.mostrarMensaje("Sesión de reporte inválida. Re-autenticando...", true);
            regresarAlCatalogo();
            return;
        }

        LocalDateTime inicio = vista.getFechaInicio();
        LocalDateTime fin = vista.getFechaFin();

        // VALIDACIÓN DE RANGO CORRECTO
        if (inicio != null && fin != null && inicio.isAfter(fin)) {
            navegador.mostrarMensaje("La fecha 'Desde' no puede ser posterior a 'Hasta'.", true);
            return;
        }

        try {
            /* * Si las fechas son nulas (el usuario limpió los filtros), mandamos null, null.
             * La fachada entenderá que queremos el catálogo completo. Reutilizamos las 
             * credenciales guardadas transparentemente en memoria. ¡Cero ventanas molestas!
             */
            ReporteInventarioDTO reporteFiltrado = gestionFachada.generarReporteInventario(
                inicio, 
                fin, 
                this.correoAdminLogueado, 
                this.claveAdminLogueado
            );
            
            vista.mostrarReporteEnPantalla(reporteFiltrado);
            
        } catch (NegocioException ex) {
            navegador.mostrarMensaje("Error al filtrar reporte: " + ex.getMessage(), true);
        }
    }
    
    public void exportarReportePDF(ReporteInventarioDTO reporte) {
        if (reporte == null || reporte.getProductos() == null || reporte.getProductos().isEmpty()) {
            navegador.mostrarMensaje("No hay datos en el reporte actual para exportar.", true);
            return;
        }

        javax.swing.JFileChooser selectorArchivo = new javax.swing.JFileChooser();
        selectorArchivo.setDialogTitle("Guardar Reporte de Inventario");
        selectorArchivo.setSelectedFile(new File("Reporte_Inventario_GhostTracks.pdf"));
        
        selectorArchivo.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Documentos PDF (*.pdf)", "pdf"));

        int seleccion = selectorArchivo.showSaveDialog(null);

        if (seleccion == javax.swing.JFileChooser.APPROVE_OPTION) {
            File archivoDestino = selectorArchivo.getSelectedFile();

            String ruta = archivoDestino.getAbsolutePath();
            if (!ruta.toLowerCase().endsWith(".pdf")) {
                archivoDestino = new File(ruta + ".pdf");
            }

            try {
                byte[] bytesPdf = gestionFachada.exportarReportePDF(reporte);

                try (java.io.FileOutputStream fos = new java.io.FileOutputStream(archivoDestino)) {
                    fos.write(bytesPdf);
                    fos.flush();
                }

                navegador.mostrarMensaje("¡Reporte PDF exportado con éxito!", false);

            } catch (NegocioException ex) {
                navegador.mostrarMensaje("Error de negocio al generar el PDF: " + ex.getMessage(), true);
            } catch (java.io.IOException ex) {
                navegador.mostrarMensaje("Error de entrada/salida al escribir el archivo: " + ex.getMessage(), true);
            }
        }
    }
    
    // PANTALLAS
    
    public void abrirReporteInventario(){
        navegador.abrirPantallaReporteInventario();
    }
    
    public void regresarAlCatalogo() {
        this.correoAdminLogueado = null;
        this.claveAdminLogueado = null;

        navegador.abrirGestionArticulos();
    }
    
    
    public void abrirRegistroProducto() {
        navegador.abrirPantallaAgregarProducto();
    }
}