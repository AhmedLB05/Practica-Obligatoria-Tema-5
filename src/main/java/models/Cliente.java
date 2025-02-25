package models;

import java.util.ArrayList;

public class Cliente {

    //Atributos
    private int id;
    private String email;
    private String clave;
    private String nombre;
    private String localidad;
    private String provincia;
    private String direccion;
    private int movil;
    private ArrayList<Pedido> pedidos;
    private ArrayList<Producto> carro;

    //Constructor
    public Cliente(int id, String email, String clave, String nombre, String localidad, String provincia, String direccion, int movil, ArrayList<Pedido> pedidos, ArrayList<Producto> carro) {
        this.id = id;
        this.email = email;
        this.clave = clave;
        this.nombre = nombre;
        this.localidad = localidad;
        this.provincia = provincia;
        this.direccion = direccion;
        this.movil = movil;
        this.pedidos = pedidos;
        this.carro = carro;
    }

    //Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getMovil() {
        return movil;
    }

    public void setMovil(int movil) {
        this.movil = movil;
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public ArrayList<Producto> getCarro() {
        return carro;
    }

    public void setCarro(ArrayList<Producto> carro) {
        this.carro = carro;
    }

    //Otros metodos

    public boolean login(String email, String pass) {
        if (email == null || pass == null || this.email == null || this.clave == null) return false;
        return (email.equalsIgnoreCase(this.email) && pass.equals(this.clave));
    }

    public void addProductoCarro(Producto p) {

    }

    public boolean quitaProductoCarro(int idProducto) {
        return false;
    }

    public int numProductosCarro() {
        return 0;
    }

    public void vaciaCarro() {

    }

    public void addPedido(Pedido p) {

    }

    public float precioCarroSinIVA(int IVA) {
        return 0.0f;
    }

    public float precioIVACarro(int IVA) {
        return 0.0f;
    }

    public float precioCarroConIVA(int IVA) {
        return 0.0f;
    }

    public boolean existeProductoCarro(int idProducto) {
        return false;
    }


}
