package itson.org.ghosttracks.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.entidades.Producto;
import itson.org.ghosttracks.enums.EstadoProducto;
import itson.org.ghosttracks.enums.TipoProducto;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import itson.org.ghosttracks.mocks.ProductosMockDAO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Pruebas unitarias para ProductosMockDAO.
 * @author emyla
 */
public class ProductosMockDAOTest {
    
//    private ProductosMockDAO dao;
//
//    @BeforeEach
//    public void init() {
//        // Setup general
//        this.dao = new ProductosMockDAO();
//    }
//
//    public ProductosMockDAOTest() {
//    }
//
//    @Test
//    public void testObtenerTodosHappyPath() {
//        // Ejecución
//        assertDoesNotThrow(() -> {
//            List<Producto> listaProductos = dao.obtenerTodos();
//            
//            // Verificación
//            assertNotNull(listaProductos);
//            assertEquals(3, listaProductos.size()); 
//        });
//    }
//
//    @Test
//    public void testBuscarPorIdHappyPath() {
//        // Setup
//        Long idProductoBuscado = 1L; 
//        
//        // Ejecución 
//        assertDoesNotThrow(() -> {
//            Producto producto = dao.buscarPorId(idProductoBuscado);
//            
//            // Verificación
//            assertNotNull(producto);
//            assertEquals(idProductoBuscado, producto.getIdProducto());
//            assertEquals("Abbey Road", producto.getNombre());
//        });
//    }
//
//    @Test
//    public void testBuscarPorIdNoExiste() {
//        // Setup
//        Long idProductoFake = 99L; 
//        
//        // Ejecución y Verificación
//        assertThrows(PersistenciaException.class, () -> {
//            dao.buscarPorId(idProductoFake);
//        });
//    }
//
//    @Test
//    public void testBuscarPorIdNulo() {
//        // Setup
//        Long idNulo = null;
//        
//        // Ejecución y Verificación
//        assertThrows(PersistenciaException.class, () -> {
//            dao.buscarPorId(idNulo);
//        });
//    }
//
//    @Test
//    public void testAgregarHappyPath() {
//        // Setup
//        ProductoDTO nuevoProductoDTO = new ProductoDTO(
//                null, 
//                "Random Access Memories", 
//                "ram.jpg", 
//                TipoProducto.VINILO, 
//                "Daft Punk", 
//                "Electronic", 
//                new ArrayList<>(Arrays.asList("Give Life Back to Music", "Get Lucky")), 
//                550.00, 
//                10, 
//                EstadoProducto.DISPONIBLE
//        );
//        
//        // Ejecución
//        assertDoesNotThrow(() -> {
//            Producto productoGuardado = dao.agregar(nuevoProductoDTO);
//            
//            // Verificación
//            assertNotNull(productoGuardado.getIdProducto());
//            assertEquals(4L, productoGuardado.getIdProducto()); 
//            assertEquals(nuevoProductoDTO.getNombre(), productoGuardado.getNombre());
//            
//            // Verificamos que se añadió a la lista general
//            assertEquals(4, dao.obtenerTodos().size());
//        });
//    }
//
//    @Test
//    public void testAgregarProductoNulo() {
//        // Setup
//        ProductoDTO productoNulo = null; // El input ahora es DTO
//        
//        // Ejecución y Verificación
//        assertThrows(PersistenciaException.class, () -> {
//            dao.agregar(productoNulo);
//        });
//    }
//
//    @Test
//    public void testAgregarProductoNombreVacio() {
//        // Setup
//        // Creamos un DTO inválido para probar la validación de negocio
//        ProductoDTO productoInvalidoDTO = new ProductoDTO(
//                null, 
//                "", // Nombre vacío intencionalmente
//                "img.jpg", 
//                TipoProducto.CD, 
//                "Artista", 
//                "Pop", 
//                new ArrayList<>(), 
//                100.00, 
//                5, 
//                EstadoProducto.DISPONIBLE
//        );
//        
//        // Ejecución y Verificación
//        assertThrows(PersistenciaException.class, () -> {
//            dao.agregar(productoInvalidoDTO);
//        });
//    }
}