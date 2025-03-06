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

    //Metodo para el login que devuelve el tipo de objeto
    public Object login(String email, String clave) {
        for (Cliente c : clientes) {
            if (c.login(email, clave)) return c;
        }
        for (Trabajador t : trabajadores) {
            if (t.login(email, clave)) return t;
        }
        for (Admin a : admins) {
            if (a.login(email, clave)) return a;
        }
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
        for (Cliente c : clientes) {
            if (c.getId() == idCliente) return c;
        }
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

    //Metodo que busca el trabajador por ID
    public Trabajador buscaTrabajadorByID(int idTrabajador) {
        for (Trabajador t : trabajadores) {
            if (t.getId() == idTrabajador) return t;
        }
        return null;
    }

    //Metodo que busca un pedido en los pedidos asignados de un trabajador
    public Pedido buscaPedidoAsignadoTrabajador(int idTrabajador, int idPedido) {
        Trabajador t = buscaTrabajadorByID(idTrabajador);
        if (t == null || t.getPedidosAsignados() == null) return null;
        for (Pedido p : t.getPedidosAsignados()) {
            if (p.getId() == idPedido) return p;
        }
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
        int idCliente;
        do {
            idCliente = (int) ((Math.random() * 90000) + 10000);
        } while (buscaClienteById(idCliente) != null);
        idCliente = Integer.parseInt(("2" + idCliente));
        return idCliente;
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
        int idTrabajador;
        do {
            idTrabajador = (int) ((Math.random() * 90000) + 10000);
        } while (buscaTrabajadorByID(idTrabajador) != null);
        idTrabajador = Integer.parseInt(("1" + idTrabajador));
        return idTrabajador;
    }

}
