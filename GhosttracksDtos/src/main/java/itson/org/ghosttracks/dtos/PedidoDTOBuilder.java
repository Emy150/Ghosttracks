
package itson.org.ghosttracks.dtos;

import itson.org.ghosttracks.enums.EstadoPedidoDTO;

/**
 *
 * @author oliro
 */
public class PedidoDTOBuilder {

    private ClienteDTO cliente;
    private ContactoDTO contacto;
    private DireccionEntregaDTO direccionEntrega;
    private SucursalDTO sucursal;
    private DatosPagoDTO datosPago;
    private CarritoDTO carrito;
    private EstadoPedidoDTO estado;
    
    public NuevoPedidoDTO build() {
        
        // TODO validaciones
        
        return new NuevoPedidoDTO(cliente, contacto, direccionEntrega, sucursal, datosPago, carrito, estado);
            
    }
    
    public void reset() {
        this.cliente = null;
        this.contacto = null;
        this.direccionEntrega = null;
        this.sucursal = null;
        this.datosPago = null;
        this.carrito = null;
        this.estado = null;
    }
    
    public PedidoDTOBuilder setCliente(ClienteDTO cliente){
        this.cliente = cliente;
        return this;
    }
    
    public PedidoDTOBuilder setCarrito(CarritoDTO carrito) {
        this.carrito = carrito;
        return this;
    }
    
    public PedidoDTOBuilder setContacto(ContactoDTO contacto) {
        this.contacto = contacto;
        return this;
    }
    
    public PedidoDTOBuilder setDireccionEntrega(DireccionEntregaDTO direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
        return this;
    }
    
    public PedidoDTOBuilder setSucursal(SucursalDTO sucursal) {
        this.sucursal = sucursal;
        return this;
    }
    
    public PedidoDTOBuilder setDatosPago(DatosPagoDTO datosPago) {
        this.datosPago = datosPago;
        return this;
    }
    
    public PedidoDTOBuilder setEstado(EstadoPedidoDTO estado) {
        this.estado = estado;
        return this;
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
