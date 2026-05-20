package itson.org.ghosttracks.negocio.interfaces;

import itson.org.ghosttracks.dtos.NuevoPedidoDTO;
import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.entidades.Pedido;
import itson.org.ghosttracks.enums.EstadoPedido;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.util.List;

/**
 *
 * @author nafbr
 */
public interface IPedidosBO {
    public Pedido guardarPedido(Pedido pedido) throws NegocioException;
    
    public Pedido actualizarEstado(String idPedido, EstadoPedido nuevoEstado) throws NegocioException;
    
    public List<Pedido> consultarTodos() throws NegocioException;
    
    public PedidoDTO generarPedido(NuevoPedidoDTO pedidoDto) throws NegocioException;
    
    public Pedido obtenerPedidoPorId(String idPedido) throws NegocioException;
    
    public Pedido despacharPedido(String idPedido, Double peso, Double largo, Double ancho, Double alto) throws NegocioException;
    
    public List<Pedido> buscarPedidosFiltrados(List<String> idsClientes, EstadoPedido estado) throws NegocioException;
}
