package itson.org.ghosttracks.daos;

import itson.org.ghosttracks.entidades.Pedido;
import itson.org.ghosttracks.enums.EstadoPedido;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import java.util.List;

/**
 *
 * @author nafbr
 */
public interface IPedidosDAO {
    
    public Pedido guardarPedido(Pedido pedido) throws PersistenciaException;
    
    public Pedido actualizarEstado(String idPedido, EstadoPedido nuevoEstado) throws PersistenciaException;
    
    public List<Pedido> consultarTodos() throws PersistenciaException;
    
    public Pedido consultarPorId(String idPedido) throws PersistenciaException;
    
    public Pedido actualizarPedido(Pedido pedidoActualizado) throws PersistenciaException;
    
    public List<Pedido> buscarPedidosFiltrados(List<String> idsClientes, EstadoPedido estado) throws PersistenciaException;
    
}