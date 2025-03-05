package models;

import java.util.ArrayList;

public class Trabajador {

    //Atributos
    private int id;
    private String nombre;
    private String pass;
    private String email;
    private int movil;
    private ArrayList<Pedido> pedidosAsignados;

    //Constructor
    public Trabajador(int id, String nombre, String pass, String email, int movil, ArrayList<Pedido> pedidosAsignados) {
        this.id = id;
        this.nombre = nombre;
        this.pass = pass;
        this.email = email;
        this.movil = movil;
        this.pedidosAsignados = pedidosAsignados;
    }

    //Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMovil() {
        return movil;
    }

    public void setMovil(int movil) {
        this.movil = movil;
    }

    public ArrayList<Pedido> getPedidosAsignados() {
        return pedidosAsignados;
    }

    public void setPedidosAsignados(ArrayList<Pedido> pedidosAsignados) {
        this.pedidosAsignados = pedidosAsignados;
    }

    //Otros metodos

    //Metodo para el login del Cliente
    public boolean login(String email, String pass) {
        if (email == null || pass == null) return false;
        return (email.equalsIgnoreCase(this.email) && pass.equals(this.pass));
    }

    //Metodo que busca en los pedidos asignados pendientes un pedido por ID
    public Pedido buscaPedidoAsignadoPendiente(int idPedido) {
        ArrayList<Pedido> pedidosPendientes = getPedidosPendientes();
        for (Pedido p : pedidosPendientes) {
            if (p.getId() == idPedido) return p;
        }
        return null;
    }

    //Metodo que busca en los pedidos asignados completados un pedido por ID
    public Pedido buscaPedidoAsignadoCompletado(int idPedido) {
        ArrayList<Pedido> pedidosCompletados = getPedidosCompletados();
        for (Pedido p : pedidosCompletados) {
            if (p.getId() == idPedido) return p;
        }
        return null;
    }

    //Metodo que agrega un pedido a los pedidosAsignados
    public boolean asignaPedido(Pedido p) {
        if (p == null || pedidosAsignados.contains(p) || pedidosAsignados == null) return false;
        pedidosAsignados.add(p);
        return true;
    }

    //Metodo que agrega los pedidos EN PREPARACION a un Array (según el estado = 1 En preparacion) y lo devuelve
    public ArrayList<Pedido> getPedidosPendientes() {
        ArrayList<Pedido> pedidosPendientes = new ArrayList<>();
        for (Pedido p : pedidosAsignados) {
            if (p.getEstado() == 1) pedidosPendientes.add(p);
        }
        return pedidosPendientes;
    }

    //Metodo que agrega los pedidos ENTREGADOS a un Array (segun el estado = 1 En preparacion) y lo devuelve
    public ArrayList<Pedido> getPedidosCompletados() {
        ArrayList<Pedido> pedidosCompletados = new ArrayList<>();
        for (Pedido p : pedidosAsignados) {
            if (p.getEstado() == 3) pedidosCompletados.add(p);
        }
        return pedidosCompletados;
    }

    //Metodo que devuelve el número de pedidos pendientes
    public int numPedidosPendientes() {
        return getPedidosPendientes().size();
    }

}
