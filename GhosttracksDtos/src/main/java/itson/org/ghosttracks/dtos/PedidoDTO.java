
package itson.org.ghosttracks.dtos;

import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import java.time.LocalDateTime;

/**
 *
 * @author oliro
 */
public class PedidoDTO {

    private Long idPedido;
    private String folio;
    private ClienteDTO cliente;
    private CarritoDTO carrito;
    private ContactoDTO contacto;
    private DireccionEntregaDTO direccionEntrega;
    private SucursalDTO sucursal;
    private EstadoPedidoDTO estado;
    private LocalDateTime fechaPedido;
    private Double costoEnvio;
    private PaqueteDTO paquete;
    private PagoDTO pago;

    public PedidoDTO() {
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public CarritoDTO getCarrito() {
        return carrito;
    }

    public void setCarrito(CarritoDTO carrito) {
        this.carrito = carrito;
    }

    public ContactoDTO getContacto() {
        return contacto;
    }

    public void setContacto(ContactoDTO contacto) {
        this.contacto = contacto;
    }

    public DireccionEntregaDTO getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(DireccionEntregaDTO direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public SucursalDTO getSucursal() {
        return sucursal;
    }

    public void setSucursal(SucursalDTO sucursal) {
        this.sucursal = sucursal;
    }

    public EstadoPedidoDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedidoDTO estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Double getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(Double costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public PaqueteDTO getPaquete() {
        return paquete;
    }

    public void setPaquete(PaqueteDTO paquete) {
        this.paquete = paquete;
    }

    public PagoDTO getPago() {
        return pago;
    }

    public void setPago(PagoDTO pago) {
        this.pago = pago;
    }
    
    
    
}
