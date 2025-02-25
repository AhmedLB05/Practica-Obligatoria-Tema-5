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
    public boolean cambiaEstado(int nuevoEstado) {
        return false;
    }

    public boolean cambiaFechaEntrega(LocalDate nuevaFecha) {
        return false;
    }

    public float calculaTotalPedidoSinIVA() {
        return 0.0f;
    }

    public float calculaIVAPedido(int IVA) {
        return 0.0f;
    }

    public float calculaTotalPedidoConIVA(int IVA) {
        return 0.0f;
    }

    public int numArticulos() {
        return 0;
    }

    public Producto buscaProducto(int idProducto) {
        return null;
    }

    public void addProducto(Producto p) {

    }
}
