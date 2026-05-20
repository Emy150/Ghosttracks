
package itson.org.ghosttracks.dtos;

import itson.org.ghosttracks.enums.EstadoPedidoDTO;

/**
 *
 * @author oliro
 */
public class NuevoPedidoDTO {

    private ClienteDTO cliente;
    private ContactoDTO contacto;
    private DireccionEntregaDTO direccionEntrega;
    private SucursalDTO sucursal;
    private DatosPagoDTO datosPago;
    private CarritoDTO carrito;
    private EstadoPedidoDTO estado;

    public NuevoPedidoDTO() {
    }

    public NuevoPedidoDTO(ClienteDTO cliente, ContactoDTO contacto, DireccionEntregaDTO direccionEntrega, SucursalDTO sucursal, DatosPagoDTO datosPago, CarritoDTO carrito, EstadoPedidoDTO estado) {
        this.cliente = cliente;
        this.contacto = contacto;
        this.direccionEntrega = direccionEntrega;
        this.sucursal = sucursal;
        this.datosPago = datosPago;
        this.carrito = carrito;
        this.estado = estado;
    }
    
    public ClienteDTO getCliente() {
        return cliente;
    }

    public ContactoDTO getContacto() {
        return contacto;
    }

    public DireccionEntregaDTO getDireccionEntrega() {
        return direccionEntrega;
    }

    public SucursalDTO getSucursal() {
        return sucursal;
    }

    public DatosPagoDTO getDatosPago() {
        return datosPago;
    }

    public CarritoDTO getCarrito() {
        return carrito;
    }

    public EstadoPedidoDTO getEstado() {
        return estado;
    }
    
}
