package models;

public class Admin {

    //Atributos
    private int id;
    private String nombre;
    private String clave;
    private String email;

    //Constructor
    public Admin(int id, String nombre, String clave, String email) {
        this.id = id;
        this.nombre = nombre;
        this.clave = clave;
        this.email = email;
    }

    //Getters y setters
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //Otros metodos

    //Metodo login
    public boolean login(String email, String clave) {
        if (email == null || clave == null) return false;
        return (email.equalsIgnoreCase(this.email) && clave.equals(this.clave));
    }

}
