package controller;

import data.DataProductos;
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
        clientes = new ArrayList<>();
        trabajadores = new ArrayList<>();
        admins = new ArrayList<>();
        catalogo = new ArrayList<>();
        catalogo.addAll(DataProductos.getProductosMock());//TODO REVISAR
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

    //Metodo que agrega un producto al carro de un cliente que le pasamos
    public boolean addProductoCarrito(Cliente cliente, int idProducto) {
        Producto producto = buscaProductoById(idProducto);
        if (producto == null) return false;
        cliente.getCarro().add(new Producto(producto));
        return true;
    }

    //Metodo que busca un producto por su id
    public Producto buscaProductoById(int id) {
        for (Producto p : catalogo) {
            if (p.getId() == id) return p;
        }
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

    //Metodo que busca a un cliente por su ID
    public Cliente buscaClienteById(int idCliente) {
        for (Cliente c : clientes) {
            if (c.getId() == idCliente) return c;
        }
        return null;
    }

    //Metodo que busca a un cliente por su ID (metodo inventado por Ahmed)
    public Object buscaClienteByEmail(String emailIntro) {
        for (Cliente c : clientes) {
            if (c.getEmail().equalsIgnoreCase(emailIntro)) return c;
        }
        return null;
    }

    //Metodo que agrega un cliente al sistema (ArrayList) (metodo inventado por Ahmed)
    public boolean agregaClienteSistema(Cliente c) {
        return clientes.add(c);
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

    /*Metodo que nos devuelve el total de pedidos sin asignar recorre todos los trabajadores recabando los pedidos asignados
    luego recorre los clientes pillando los pedidos y luego de la lista de pedidosClientes le quita los pedidos ya asignados a trabajadores*/
    public int numPedidosSinTrabajador() {
        ArrayList<Pedido> pedidosAsignados = new ArrayList<>();
        ArrayList<Pedido> pedidosSinAsignar = new ArrayList<>();
        for (Trabajador t : trabajadores) {
            pedidosAsignados.addAll(t.getPedidosAsignados());
        }
        for (Cliente c : clientes) {
            pedidosSinAsignar.addAll(c.getPedidos());
        }
        pedidosSinAsignar.removeAll(pedidosAsignados);
        return pedidosSinAsignar.size();
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

    //Metodo que genera el id del trabajador
    private int generaIdTrabajador() {
        int idTrabajador;
        do {
            idTrabajador = (int) ((Math.random() * 90000) + 10000);
        } while (buscaTrabajadorByID(idTrabajador) != null);
        idTrabajador = Integer.parseInt(("1" + idTrabajador));
        return idTrabajador;
    }

    //TODO metodo creado por Ahmed
    //Metodo que devuelve el total de pedidos pendientes de entrega a un cliente (se hace mirando el estado)
    public int getTotalPedidosPendientesEntregaCliente(Cliente cliente) {
        int cont = 0;
        for (Pedido p : cliente.getPedidos()) {
            if (p.getEstado() == 2) cont++;
        }
        return cont;
    }

    //TODO metodo creado por Ahmed
    /*//Metodo que devuelve la posicion de un trabajador en el array
    public int getPosicionTrabajadorArray(Trabajador t) {
        return trabajadores.indexOf(t);
    }*/
}
