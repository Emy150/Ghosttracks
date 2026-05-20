/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
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
    public Pedido actualizarEstado(Long idPedido, EstadoPedido nuevoEstado) throws NegocioException;
    public List<Pedido> consultarTodos() throws NegocioException;
    public PedidoDTO generarPedido(NuevoPedidoDTO pedidoDto) throws NegocioException;
    public Pedido obtenerPedidoPorId(Long idPedido) throws NegocioException;
    public Pedido despacharPedido(Long idPedido, Double peso, Double largo, Double ancho, Double alto) throws NegocioException;
    public List<Pedido> buscarPedidosFiltrados(List<Long> idsClientes, EstadoPedido estado) throws NegocioException;
}
