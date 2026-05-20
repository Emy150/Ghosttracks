/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.org.ghosttracks.daos;

import itson.org.ghosttracks.entidades.Pedido;
import itson.org.ghosttracks.enums.EstadoPedido;
import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import java.util.List;

/**
 *
 * @author nafbr
 */
public interface IPedidosDAO {
    public Pedido guardarPedido(Pedido pedido) throws PersistenciaException;
    
    public Pedido actualizarEstado(Long idPedido, EstadoPedido nuevoEstado) throws PersistenciaException;
    
    public List<Pedido> consultarTodos() throws PersistenciaException;
    
    public Pedido consultarPorId(Long idPedido) throws PersistenciaException;
    
    public Pedido actualizarPedido(Pedido pedidoActualizado) throws PersistenciaException;
    
    List<Pedido> buscarPedidosFiltrados(List<Long> idsClientes, EstadoPedido estado) throws PersistenciaException;
    
}
