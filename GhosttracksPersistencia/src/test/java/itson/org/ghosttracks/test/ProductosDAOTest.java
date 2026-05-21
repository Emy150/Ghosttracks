package itson.org.ghosttracks.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.bson.types.ObjectId;

import itson.org.ghosttracks.persistencia.ProductosDAO;
import itson.org.ghosttracks.entidades.Producto;
import itson.org.ghosttracks.enums.EstadoProducto;
import itson.org.ghosttracks.enums.TipoProducto;
import itson.org.ghosttracks.exceptions.PersistenciaException;

import java.util.List;

/**
 * Pruebas de integración para ProductosDAO (Conexión 100% real no fake a MongoDB).
 * @author emyla
 */
public class ProductosDAOTest {
    
    private ProductosDAO dao;

    @BeforeEach
    public void init() {
        // Inicializamos el DAO real antes de cada prueba
        this.dao = new ProductosDAO();
    }

    public ProductosDAOTest() {
    }

    //TESTS DE REGISTRO

    @Test
    public void testRegistrarNuevoProductoHappyPath() {
        // Setup: Creamos una entidad Producto válida (sin ID, porque Mongo se lo pondrá)
        Producto nuevoProducto = new Producto();
        nuevoProducto.setTitulo("Random Access Memories");
        // nuevoProducto.setImagenProducto("ram.jpg");
        nuevoProducto.setTipo(TipoProducto.VINILO);
        nuevoProducto.setArtista("Daft Punk");
        nuevoProducto.setPrecio(550.00);
        nuevoProducto.setStockInicial(10);
        nuevoProducto.setEstado(EstadoProducto.DISPONIBLE);
        
        String idGenerado = null;

        try {
            // Ejecución
            Producto productoGuardado = assertDoesNotThrow(() -> {
                return dao.registratNuevoProducto(nuevoProducto);
            });
            
            // Verificación
            assertNotNull(productoGuardado, "El producto devuelto no debe ser nulo");
            assertNotNull(productoGuardado.getIdProducto(), "MongoDB debió generar y asignar un ID");
            assertEquals("Random Access Memories", productoGuardado.getTitulo(), "El título debe coincidir");
            
            // Guardamos el ID para borrarlo en el finally
            idGenerado = productoGuardado.getIdProducto();
            System.out.println("Prueba de registro exitosa. ID generado: " + idGenerado);

        } finally {
            // TEAR DOWN: Borramos el producto de la base de datos real para no dejar basura
            if (idGenerado != null) {
                try {
                    dao.eliminarProducto(idGenerado);
                } catch (PersistenciaException ex) {
                    System.err.println("No se pudo limpiar el producto de prueba: " + ex.getMessage());
                }
            }
        }
    }

    @Test
    public void testRegistrarProductoNulo() {
        // Setup
        Producto productoNulo = null;
        
        // Ejecución y Verificación: Como el DAO manda directo a Mongo, saltará la excepción general de persistencia
        PersistenciaException ex = assertThrows(PersistenciaException.class, () -> {
            dao.registratNuevoProducto(productoNulo);
        });
        
        assertTrue(ex.getMessage().contains("Error al registrar"));
    }

    @Test
    public void testRegistrarProductoTituloVacio() {
        // Setup: Producto con título vacío
        Producto productoInvalido = new Producto();
        productoInvalido.setTitulo(""); 
        
        String idGenerado = null;
        try {
            // Ejecución
            Producto productoGuardado = assertDoesNotThrow(() -> {
                return dao.registratNuevoProducto(productoInvalido);
            });
            
            assertNotNull(productoGuardado);
            idGenerado = productoGuardado.getIdProducto();
        } finally {
            // Limpieza
            if (idGenerado != null) {
                try {
                    dao.eliminarProducto(idGenerado);
                } catch (PersistenciaException e) {
                    System.err.println("Error al limpiar: " + e.getMessage());
                }
            }
        }
    }

    // TESTS DE CONSULTA

    @Test
    public void testConsultarCatalogoNoFalla() {
        // Ejecución: Simplemente verificamos que pueda traer la lista de la BD sin explotar
        assertDoesNotThrow(() -> {
            List<Producto> listaProductos = dao.consultarCatalogo();
            
            // Verificación
            assertNotNull(listaProductos, "La lista no debería ser nula, aunque esté vacía");
            System.out.println("Catálogo consultado correctamente. Productos actuales: " + listaProductos.size());
        });
    }

    @Test
    public void testConsultarProductoPorIdHappyPath() throws PersistenciaException {
        // Setup: Primero insertamos uno para estar seguros de que existe
        Producto productoPrueba = new Producto();
        productoPrueba.setTitulo("Abbey Road - Test");
        Producto insertado = dao.registratNuevoProducto(productoPrueba);
        String idBuscado = insertado.getIdProducto();
        
        try {
            // Ejecución 
            Producto encontrado = assertDoesNotThrow(() -> {
                return dao.consultarProductoPorId(idBuscado);
            });
            
            // Verificación
            assertNotNull(encontrado);
            assertEquals(idBuscado, encontrado.getIdProducto());
            assertEquals("Abbey Road - Test", encontrado.getTitulo());
            
        } finally {
            // Limpieza
            dao.eliminarProducto(idBuscado);
        }
    }

    @Test
    public void testConsultarPorIdNoExiste() {
        // Setup: Generamos un ObjectId válido de Mongo pero falso (que no existe en BD)
        String idFake = new ObjectId().toHexString(); 
        
        // Ejecución y Verificación: .first() en Mongo regresa NULL si no encuentra nada, no lanza excepción.
        assertDoesNotThrow(() -> {
            Producto encontrado = dao.consultarProductoPorId(idFake);
            assertNull(encontrado, "Debería regresar null porque el ID no existe en la base de datos");
        });
    }

    @Test
    public void testConsultarPorIdFormatoInvalido() {
        // Setup: Un ID que no es un Hexadecimal de 24 caracteres
        String idInvalido = "id-100-%-fake";
        
        // Ejecución y Verificación: 
        PersistenciaException ex = assertThrows(PersistenciaException.class, () -> {
            dao.consultarProductoPorId(idInvalido);
        });
        
        assertTrue(ex.getMessage().contains("formato válido"));
    }
}