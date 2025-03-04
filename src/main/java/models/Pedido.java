package models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Pedido {

    //Atributos
    private int id;
    private LocalDate fechaPedido;
    private LocalDate fechaEntregaEstimada;
    private LocalDate deliveryDate;
    private int estado;
    private String comentario;
    private ArrayList<Producto> productos;

    //Constructor
    public Pedido(int id, LocalDate fechaPedido, LocalDate fechaEntregaEstimada, LocalDate deliveryDate, int estado, String comentario, ArrayList<Producto> productos) {
        this.id = id;
        this.fechaPedido = fechaPedido;
        this.fechaEntregaEstimada = fechaEntregaEstimada;
        this.deliveryDate = deliveryDate;
        this.estado = estado;
        this.comentario = comentario;
        this.productos = productos;
    }

    //Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public LocalDate getFechaEntregaEstimada() {
        return fechaEntregaEstimada;
    }

    public void setFechaEntregaEstimada(LocalDate fechaEntregaEstimada) {
        this.fechaEntregaEstimada = fechaEntregaEstimada;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    //Otros metodos

    //Metodo que cambia el estado del pedido
    public boolean cambiaEstado(int nuevoEstado) {
        if (!(nuevoEstado >= 1 && nuevoEstado <= 4)) return false;
        setEstado(nuevoEstado);
        return true;
    }

    //Metodo para cambiar la fecha de entrega deliveryDate
    public boolean cambiaFechaEntrega(LocalDate nuevaFecha) {
        if (nuevaFecha.isBefore(fechaPedido) || nuevaFecha.isEqual(fechaPedido)) return false;
        setDeliveryDate(nuevaFecha);
        return true;
    }

    //Metodo que calcula el total del pedido SIN IVA
    public float calculaTotalPedidoSinIVA() {
        float precioTotalPedido = 0;
        for (Producto p : productos) {
            precioTotalPedido += p.getPrecio();
        }
        return precioTotalPedido;
    }

    //Metodo que calcula el precio del IVA del pedido
    public float calculaIVAPedido(int IVA) {
        float precioIVAPedido = 0;
        for (Producto p : productos) {
            precioIVAPedido += p.getPrecio() * (IVA / 100f);
        }
        return precioIVAPedido;
    }

    //Metodo que calcula el total del pedido
    public float calculaTotalPedidoConIVA(int IVA) {
        return calculaTotalPedidoSinIVA() + calculaIVAPedido(IVA);
    }

    //Metodo que devuelve el número de articulos en un pedido
    public int numArticulos() {
        if (productos == null) return 0;
        return productos.size();
    }

    //Metodo que busca un producto en el pedido por ID
    public Producto buscaProducto(int idProducto) {
        for (Producto p : productos) {
            if (p.getId() == idProducto) return p;
        }
        return null;
    }

    //Metodo para agregar un producto al pedido
    public void addProducto(Producto p) {
        productos.add(p);
    }
}
