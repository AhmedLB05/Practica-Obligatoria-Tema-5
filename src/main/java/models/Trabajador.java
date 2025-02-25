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

    public boolean login(String email, String pass) {
        return false;
    }

    public Pedido buscaPedidoAsignadoPendiente(int idPedido) {
        return null;
    }

    public Pedido buscaPedidoAsignadoCompletado(int idPedido) {
        return null;
    }

    public boolean asignaPedido(Pedido p) {
        return false;
    }

    public ArrayList<Pedido> getPendidosPendientes() {
        return new ArrayList<>();
    }

    public ArrayList<Pedido> getPedidosCompletados() {
        return new ArrayList<>();
    }

    public int numPedidosPendientes() {
        return 0;
    }

}
