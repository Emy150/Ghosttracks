package itson.org.ghosttracksgestionarticulos.fachada;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import itson.org.ghosttracks.bos.AdministradoresBO;
import itson.org.ghosttracks.bos.ProductosBO;
import itson.org.ghosttracks.bos.ReportesBO;
import itson.org.ghosttracks.dtos.*;
import itson.org.ghosttracks.negocio.interfaces.IAdministradoresBO;
import itson.org.ghosttracks.negocio.interfaces.IProductosBO;
import itson.org.ghosttracks.negocio.interfaces.IReportesBO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import itson.org.ghosttracksgestionarticulos.interfaces.IGestionArticulos;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

public class GestionArticulos implements IGestionArticulos {

    private static final Logger LOGGER = Logger.getLogger(GestionArticulos.class.getName());

    private final IProductosBO productosBO;
    private final IAdministradoresBO administradorBO;
    private final IReportesBO reportesBO;

    public GestionArticulos() {
        this.productosBO = new ProductosBO();
        this.administradorBO = new AdministradoresBO();
        this.reportesBO = new ReportesBO();
    }

    // MÉTODOS PRODUCTOS

    @Override
    public ProductoDTO registrarProducto(NuevoProductoDTO dto) throws NegocioException {
        return productosBO.registrarProducto(dto);
    }

    @Override
    public ProductoDTO modificarProducto(ProductoActualizadoDTO dto) throws NegocioException {
        return productosBO.modificarProducto(dto);
    }

    @Override
    public ProductoDTO eliminarProducto(String idProducto, String correo, String contrasenia) throws NegocioException {
        LOGGER.info("Fachada: Iniciando proceso de eliminación segura para el producto: " + idProducto);
        
        AdministradorDTO adminValidado = administradorBO.validarAutorizacion(correo, contrasenia);
        
        if (adminValidado == null) {
            throw new NegocioException("Credenciales de administrador inválidas. Operación cancelada.");
        }
        
        LOGGER.info("Fachada: Administrador '" + adminValidado.getCorreo() + "' autorizado. Eliminando...");

        return productosBO.eliminarProducto(idProducto, correo, contrasenia); 
    }

    @Override
    public List<ProductoDTO> consultarInventario(String filtro) throws NegocioException {
        return productosBO.buscarProductos(filtro);
    }
    
    @Override
    public List<ProductoDTO> consultarInventarioCompleto() throws NegocioException {
        return productosBO.consultarCatalogoCompleto();
    }

    // SKU (delegado o lógica simple)
    @Override
    public String generarSkuProducto(String inicialesCategoria) throws NegocioException {

        if (inicialesCategoria == null || inicialesCategoria.trim().isEmpty()) {
            throw new NegocioException("Iniciales inválidas");
        }

        return inicialesCategoria.toUpperCase()
                + "-" + System.currentTimeMillis();
    }
    
    @Override
    public List<GeneroDTO> consultarGeneros() throws NegocioException {
        return List.of(
            new GeneroDTO("6a0ee8b538730412a044152e", "Rock"),
            new GeneroDTO("6a0ee8b538730412a044152f", "Alternativo"),
            new GeneroDTO("6a0ee8b538730412a0441530", "Rap"),
            new GeneroDTO("6a0ee8b538730412a0441531", "Rock en Español"),
            new GeneroDTO("6a0ee8b538730412a0441532", "Hip hop")
        );
    }

    // MÉTODOS ADMIN

    @Override
    public AdministradorDTO autenticarAdmin(String correo, String contrasenia) throws NegocioException {
        return administradorBO.validarAutorizacion(correo, contrasenia);
    }

    @Override
    public AdministradorDTO validarAutorizacionAdmin(String correo, String clave) throws NegocioException {

        AdministradorDTO admin = administradorBO.validarAutorizacion(correo, clave);

        if (admin == null) {
            throw new NegocioException("Clave de autorización incorrecta");
        }

        return admin;
    }
    
    
    // REPORTES
    @Override
    public ReporteInventarioDTO generarReporteInventario(LocalDateTime inicio, LocalDateTime fin, String correo, String clave) throws NegocioException {
 
        List<ProductoDTO> listaCruda = productosBO.consultarCatalogoCompleto();
        
        InventarioDTO inventario = new InventarioDTO();
        inventario.setProductos(listaCruda);
        inventario.setTotalResultados(listaCruda != null ? listaCruda.size() : 0);
        
        List<ProductoDTO> productosFiltrados = new ArrayList<>();
        double valorTotal = 0.0;
        int stockCritico = 0;

        if (inventario != null && inventario.getProductos() != null) {
            for (ProductoDTO p : inventario.getProductos()) {
                if (inicio != null && fin != null) {
                    if (p.getFechaRegistro() == null || 
                        p.getFechaRegistro().isBefore(inicio) || 
                        p.getFechaRegistro().isAfter(fin)) {
                        continue;
                    }
                }
                
                productosFiltrados.add(p);
                int stock = (p.getStockInicial() != null) ? p.getStockInicial() : 0;
                double precio = (p.getPrecio() != null) ? p.getPrecio() : 0.0;
                
                valorTotal += (precio * stock);
                if (stock <= 5) {
                    stockCritico++;
                }
            }
        }

        int totalArticulos = productosFiltrados.size();

        return reportesBO.generarReporte(
            productosFiltrados, 
            totalArticulos, 
            valorTotal, 
            stockCritico, 
            correo, 
            clave
        );
    }
        
    @Override
    public byte[] exportarReportePDF(ReporteInventarioDTO reporte) throws NegocioException {
        if (reporte == null) {
            throw new NegocioException("No hay datos disponibles para exportar el reporte.");
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document documento = new Document(PageSize.A4, 36, 36, 36, 36);
            PdfWriter.getInstance(documento, baos);
            documento.open();

            // CONTEXTO DE COLORES FIJOS USANDO BASECOLOR DE ITEXT
            com.itextpdf.text.BaseColor grisOscuro = new com.itextpdf.text.BaseColor(64, 64, 64);
            com.itextpdf.text.BaseColor grisMedio = new com.itextpdf.text.BaseColor(120, 120, 120);
            com.itextpdf.text.BaseColor grisClaro = new com.itextpdf.text.BaseColor(90, 90, 90);

            // CONFIGURACIÓN DE FUENTES CORREGIDA
            Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, grisOscuro);
            Font fuenteSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 10, com.itextpdf.text.BaseColor.GRAY);
            Font fuenteSeccion = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13, new com.itextpdf.text.BaseColor(40, 40, 40));
            Font fuenteTablaHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, com.itextpdf.text.BaseColor.WHITE);
            Font fuenteTablaContenido = FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.BaseColor.BLACK);

            // 1. ENCABEZADO PRINCIPAL
            Paragraph titulo = new Paragraph("Reporte de Inventario - Ghost Tracks", fuenteTitulo);
            titulo.setSpacingAfter(4);
            documento.add(titulo);

            // METADATOS
            documento.add(new Paragraph(
                "Generado por: " + reporte.getAutor(),
                fuenteSubtitulo
            ));

            documento.add(new Paragraph("Departamento: Gerencia", fuenteSubtitulo));
            String fechaActual = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd 'de' MMMM, yyyy"));
            documento.add(new Paragraph("Fecha de emisión: " + fechaActual, fuenteSubtitulo));
            String rango;

            if (reporte.getFechaInicio() != null && reporte.getFechaFin() != null) {

                rango = reporte.getFechaInicio().toLocalDate() +
                         " - " +
                         reporte.getFechaFin().toLocalDate();

            } else {

                rango = "Catálogo completo";
            }

            documento.add(new Paragraph(
                "Rango de reporte: " + rango,
                fuenteSubtitulo
            ));
            
            documento.add(new Paragraph("\n")); // Espaciador

            // 2. RESUMEN GERENCIAL
            Paragraph secResumen = new Paragraph("Resumen Gerencial", fuenteSeccion);
            secResumen.setSpacingAfter(8);
            documento.add(secResumen);

            PdfPTable tablaResumen = new PdfPTable(3);
            tablaResumen.setWidthPercentage(100);
            tablaResumen.setWidths(new float[]{1f, 1f, 1f});

            // Headers del resumen
            String[] headersResumen = {"Total de Artículos", "Valor Total Estimado", "Alertas de Stock (<5)"};
            for (String h : headersResumen) {
                PdfPCell cell = new PdfPCell(new Phrase(h, fuenteTablaHeader));
                cell.setBackgroundColor(grisClaro);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(6);
                tablaResumen.addCell(cell);
            }

            // Datos del resumen
            tablaResumen.addCell(crearCeldaCentrada(String.valueOf(reporte.getTotalArticulos()), fuenteTablaContenido));
            tablaResumen.addCell(crearCeldaCentrada(String.format("$%,.2f", reporte.getValorTotalInventario()), fuenteTablaContenido));
            tablaResumen.addCell(crearCeldaCentrada(String.valueOf(reporte.getArticulosStockCritico()), fuenteTablaContenido));
            documento.add(tablaResumen);

            documento.add(new Paragraph("\n")); // Espaciador

            // 3. DETALLE DE INVENTARIO (TABLA PRINCIPAL)
            Paragraph secDetalle = new Paragraph("Detalle de Inventario", fuenteSeccion);
            secDetalle.setSpacingAfter(8);
            documento.add(secDetalle);

            PdfPTable tablaProductos = new PdfPTable(6);
            tablaProductos.setWidthPercentage(100);
            tablaProductos.setWidths(new float[]{2.5f, 2f, 1.5f, 1.2f, 1f, 1.2f}); 

            String[] headersProd = {"Título", "Artista", "Tipo", "Precio", "Stock", "Estado"};
            for (String h : headersProd) {
                PdfPCell cell = new PdfPCell(new Phrase(h, fuenteTablaHeader));
                cell.setBackgroundColor(grisMedio);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(6);
                tablaProductos.addCell(cell);
            }

            // Filas dinámicas extraídas del DTO
            if (reporte.getProductos() != null) {
                for (ProductoDTO p : reporte.getProductos()) {
                    tablaProductos.addCell(new PdfPCell(new Phrase(p.getTitulo(), fuenteTablaContenido)));
                    tablaProductos.addCell(new PdfPCell(new Phrase(p.getArtista(), fuenteTablaContenido)));
                    tablaProductos.addCell(crearCeldaCentrada(p.getTipo(), fuenteTablaContenido));
                    tablaProductos.addCell(crearCeldaCentrada(String.format("$%.2f", p.getPrecio()), fuenteTablaContenido));
                    tablaProductos.addCell(crearCeldaCentrada(String.valueOf(p.getStockInicial()), fuenteTablaContenido));
                    
                    // Estado con color condicional explícito de iText
                    String estadoStr = (p.getStockInicial() != null && p.getStockInicial() > 0) ? "Disponible" : "Agotado";
                    com.itextpdf.text.BaseColor colorEstado = estadoStr.equals("Disponible") 
                            ? new com.itextpdf.text.BaseColor(0, 130, 0) 
                            : com.itextpdf.text.BaseColor.RED;
                            
                    Font fuenteEstado = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, colorEstado);
                    tablaProductos.addCell(crearCeldaCentrada(estadoStr, fuenteEstado));
                }
            }

            documento.add(tablaProductos);
            documento.close();

            return baos.toByteArray();

        } catch (Exception e) {
            throw new NegocioException("Error crítico construyendo el PDF: " + e.getMessage());
        }
    }
    
    private PdfPCell crearCeldaCentrada(String texto, Font fuente) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celda.setPadding(5);
        return celda;
    }
}