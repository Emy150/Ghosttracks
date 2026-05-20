
package itson.org.ghosttracksventaenlinea.fachada;

import itson.org.ghosttracks.dtos.CarritoDTO;
import itson.org.ghosttracks.dtos.ClienteDTO;
import itson.org.ghosttracks.dtos.ContactoDTO;
import itson.org.ghosttracks.dtos.DatosPagoDTO;
import itson.org.ghosttracks.dtos.DireccionClienteDTO;
import itson.org.ghosttracks.dtos.DireccionEntregaDTO;
import itson.org.ghosttracks.dtos.DireccionSucursalDTO;
import itson.org.ghosttracks.dtos.ItemCarritoDTO;
import itson.org.ghosttracks.dtos.NuevoPedidoDTO;
import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.dtos.SucursalDTO;
import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import itson.org.ghosttracks.enums.EstadoProducto;
import itson.org.ghosttracks.enums.TipoProducto;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author oliro
 */
public class VentaEnLineaFachadaTest {
    
    private final VentaEnLineaFachada CU = new VentaEnLineaFachada();
    
    
    public VentaEnLineaFachadaTest() {
    }
    
    @Test
    public void generarPedido() {
        
        NuevoPedidoDTO nuevoPedido = crearPedidoValido();
        PedidoDTO pedido = Assertions.assertDoesNotThrow(() -> {
            return CU.confirmarCompra(nuevoPedido);
        });
        
    }
    
    private NuevoPedidoDTO crearPedidoValido() {
        
        ClienteDTO cliente = new ClienteDTO();
        cliente.setIdUsuario(1L);
        cliente.setNombres("Emy");
        cliente.setApellidoPaterno("Ghost");
        cliente.setApellidoMaterno("Tracks");
        cliente.setCorreo("emy@ghosttracks.com");
        cliente.setContraseña("secreta123");
        cliente.setTelefono("6441234567");
        
        DireccionClienteDTO dir = new DireccionClienteDTO();
        dir.setCalle("Nainari");
        dir.setNumero("123");
        dir.setCodigoPostal("85000");
        cliente.setDireccion(dir);
        
        ContactoDTO contacto = new ContactoDTO(
                "Emy", 
                "Emy@gmail.com", 
                "1234567890"
        );
        
        DireccionEntregaDTO direccionEntrega = new DireccionEntregaDTO(
                "Miguel aleman", 
                "100", 
                "Centro", 
                "85000", 
                "Obregon", 
                "Sonora"
        );
        
        DireccionSucursalDTO direccionSucursal = new DireccionSucursalDTO(
                "Nainari", 
                "200", 
                "Olivos", 
                "85100", 
                "Obregon", 
                "Sonora"
        );
        
        SucursalDTO sucursal = new SucursalDTO(
                direccionSucursal, 
                "0099887766", 
                "Obregon 1"
        );
        
        DatosPagoDTO datosPago = new DatosPagoDTO(
                "Emy Lara", 
                "1122334455667788", 
                "12/28", 
                "123"
        );
        
        ProductoDTO productoSeleccionado = new ProductoDTO(
                1L,                       
                "Abbey Road",             
                "AbbeyRoad.png",         
                TipoProducto.VINILO,       
                "The Beatles",            
                "Rock",                   
                Arrays.asList("Come Together", "Something", "Maxwell's Silver Hammer", "Oh! Darling", "Octopus's Garden", "I Want You", "Here Comes the Sun"), // setlist
                650.00,                  
                15,                       
                EstadoProducto.DISPONIBLE 
        );
        
        List<ItemCarritoDTO> productos = new LinkedList();
        productos.add(new ItemCarritoDTO(
                productoSeleccionado, 
                1, 
                650.00
        ));
        
        CarritoDTO carrito = new CarritoDTO(
                productos, 
                650.00, 
                104.00, 
                754.00
        );
        
        NuevoPedidoDTO nuevoPedido = new NuevoPedidoDTO(
                cliente, 
                contacto, 
                direccionEntrega, 
                sucursal, 
                null, 
                carrito, 
                EstadoPedidoDTO.PAGADO
        );
        
        return nuevoPedido;
        
    }
    
}
