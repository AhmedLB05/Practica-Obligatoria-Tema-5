package controller;

import models.*;

import java.util.ArrayList;

public class Controlador {

    //Atributos
    private ArrayList<Cliente> clientes;
    private ArrayList<Trabajador> trabajadores;
    private ArrayList<Admin> admins;
    private ArrayList<Producto> catalogo;

    //Constructor
    public Controlador() {
        this.clientes = clientes;
        this.trabajadores = trabajadores;
        this.admins = admins;
        this.catalogo = catalogo;
    }

    //Getters y Setters
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public ArrayList<Trabajador> getTrabajadores() {
        return trabajadores;
    }

    public void setTrabajadores(ArrayList<Trabajador> trabajadores) {
        this.trabajadores = trabajadores;
    }

    public ArrayList<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(ArrayList<Admin> admins) {
        this.admins = admins;
    }

    public ArrayList<Producto> getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(ArrayList<Producto> catalogo) {
        this.catalogo = catalogo;
    }

    //Otros metodos

    public Object login(String email, String clave) {
        return null;
    }

    public boolean addProductoCarrito(Cliente cliente, int idProducto) {
        return false;
    }

    public Producto buscaProductoById(int id) {
        return null;
    }

    public boolean confirmaPedidoCliente(int id) {
        return false;
    }

    public Trabajador buscaTrabajadorCandidatoParaAsignar() {
        return null;
    }

    public boolean hayEmpateTrabajadoresCandidatos(Trabajador candidato) {
        return false;
    }

    public Cliente buscaClienteById(int idCliente) {
        return null;
    }

    public ArrayList<Producto> buscaProductosByMarca(String marca) {
        return new ArrayList<>();
    }

    public ArrayList<Producto> buscaProductosByModelo(String modelo) {
        return new ArrayList<>();
    }

    public ArrayList<Producto> buscaProductosByDescripcion(String descripcion) {
        return new ArrayList<>();
    }

    public ArrayList<Producto> buscaProductosByTermino(String termino) {
        return new ArrayList<>();
    }

    public ArrayList<Producto> buscaProductosByPrecio(float precioMin, float precioMax) {
        return new ArrayList<>();
    }

    public boolean editarProducto(Producto p) {
        return false;
    }

    public ArrayList<Pedido> getTodosPedidos() {
        return new ArrayList<>();
    }

    public int numPedidosTotales() {
        return 0;
    }

    public Pedido buscaPedidoById(int idPedido) {
        return null;
    }

    public boolean cambiaEstadoPedido(int idPedido, int nuevoEstado) {
        return false;
    }

    public boolean nuevoTrabajador(String email, String clave, String nombre, int movil) {
        return false;
    }

    public Trabajador buscaTrabajadorAsignadoAPedido(int idPedido) {
        return null;
    }

    public ArrayList<Pedido> pedidosSinTrabajador(int id) {
        return new ArrayList<>();
    }

    public int numPedidosSinTrabajador() {
        return 0;
    }

    public boolean asignaPedido(int idPedido, int idTrabajador) {
        return false;
    }

    public Trabajador buscaTrabajadorByID(int idTrabajador) {
        return null;
    }

    public Pedido buscaPedidoAsignadoTrabajador(int idTrabajador, int idPedido) {
        return null;
    }

    /*public ArrayList<PedidoClienteDataClass> getPedidosAsignadosTrabajador(int idTrabajador) {
        return new ArrayList<>();
    }

    public ArrayList<PedidoClienteDataClass> getPedidosCompletadosTrabajador(int idTrabajador) {
        return new ArrayList<>();
    }

    public ArrayList<PedidoClienteDataClass> getPedidosAsignadosYCompletados(int idTrabajador) {
        return new ArrayList<>();
    }*/

    private int generaIdCliente() {
        return 0;
    }

    private int generaIdProducto() {
        return 0;
    }

    private int generaIdPedido() {
        return 0;
    }

    private int generaIdAdmin() {
        return 0;
    }

    private int generaIdTrabajador() {
        return 0;
    }

}
