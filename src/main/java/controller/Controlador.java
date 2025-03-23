package controller;

import comunications.EnvioMail;
import comunications.EnvioTelegram;
import data.DataProductos;
import models.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class Controlador {

    //Atributos
    private ArrayList<Cliente> clientes;
    private ArrayList<Trabajador> trabajadores;
    private ArrayList<Admin> admins;
    private ArrayList<Producto> catalogo;

    //Constructor TODO ELIMINAR COSAS
    public Controlador() {
        clientes = new ArrayList<>();
        trabajadores = new ArrayList<>();
        admins = new ArrayList<>();
        catalogo = new ArrayList<>();
        mock();
    }

    private void mock() {
        clientes.add(new Cliente(generaIdCliente(), "ahmedlb26205@gmail.com", "123", "Ahmed", "Torredelcampo", "Jaén", "Federico Garcia Lorca", 631788372));
        clientes.add(new Cliente(generaIdCliente(), "marcos@gmail.com", "123", "Marcos", "Martos", "Jaén", "Ramon Garay", 672929324));
        trabajadores.add(new Trabajador(generaIdTrabajador(), "Carlos", "123", "ahmed.lhaouchi.2602@fernando3martos.com", 672839234));
        admins.add(new Admin(generaIdAdmin(), "admin", "admin", "admin@admin"));
        catalogo = DataProductos.getProductosMock();
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

    //Metodo para confirmar el pedido de un cliente y asignarlo a un trabajador
    public boolean confirmaPedidoCliente(int id) {
        Cliente clienteTemp = buscaClienteById(id);
        if (clienteTemp == null) {
            System.out.println(" * ERROR NO SE HA ENCONTRADO AL CLIENTE");
        } else {
            if (clienteTemp.getCarro().isEmpty()) return false;

            ArrayList<Producto> copiaCarro = new ArrayList<>();
            copiaCarro.addAll(clienteTemp.getCarro());

            Pedido pedidoTemp = new Pedido(generaIdPedido(), LocalDate.now(), "Pedido creado", copiaCarro);

            clienteTemp.addPedido(pedidoTemp);
            clienteTemp.vaciaCarro();

            Trabajador trabajadorTemp = buscaTrabajadorCandidatoParaAsignar();

            if (trabajadorTemp != null) {
                if (asignaPedido(pedidoTemp.getId(), trabajadorTemp.getId())) {
                    PedidoClienteDataClass dataTemp = null;

                    for (PedidoClienteDataClass p : getPedidosAsignadosTrabajador(trabajadorTemp.getId())) {
                        if (p.getIdPedido() == pedidoTemp.getId()) dataTemp = p;
                    }

                    EnvioMail.enviaCorreoPedido(trabajadorTemp, dataTemp, "SE LE HA ASIGNADO UN NUEVO PEDIDO");
                    EnvioTelegram.enviaMensajeTrabajadorPedidoAsignado(trabajadorTemp, pedidoTemp);
                }
            }
        }
        return true;
    }

    //Metodo que busca un trabajador con menos pedidos para asignarle uno nuevo
    public Trabajador buscaTrabajadorCandidatoParaAsignar() {
        int numPedidosMin = Integer.MAX_VALUE;
        Trabajador candidato = null;

        for (Trabajador t : trabajadores) {
            if (t.numPedidosPendientes() < numPedidosMin) {
                numPedidosMin = t.numPedidosPendientes();
                candidato = t;
            }
        }
        if (trabajadores.size() > 1) if (hayEmpateTrabajadoresCandidatos(candidato)) return null;
        return candidato;
    }

    //Metodo que nos indica si hay algún trabajador empatado en cuanto a pedidos pendientes con el trabajador que le pasamos
    public boolean hayEmpateTrabajadoresCandidatos(Trabajador candidato) {
        for (Trabajador t : trabajadores) {
            if (t.getId() != candidato.getId()) {
                if (t.getPedidosPendientes().size() == candidato.getPedidosPendientes().size()) return true;
            }
        }
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

    //Metodo que busca en el catálogo productos que tengan coincidencia en el nombre de la marca
    public ArrayList<Producto> buscaProductosByMarca(String marca) {
        ArrayList<Producto> productosCoincideMarca = new ArrayList<>();
        for (Producto p : catalogo) {
            if (p.getMarca().toLowerCase().contains(marca.toLowerCase())) productosCoincideMarca.add(p);
        }
        return productosCoincideMarca;
    }

    //Metodo que busca en el catálogo productos que tengan coincidencia en el nombre del modelo
    public ArrayList<Producto> buscaProductosByModelo(String modelo) {
        ArrayList<Producto> productosCoincideModelo = new ArrayList<>();
        for (Producto p : catalogo) {
            if (p.getModelo().toLowerCase().contains(modelo.toLowerCase())) productosCoincideModelo.add(p);
        }
        return productosCoincideModelo;
    }

    //Metodo que busca en el catálogo productos que tengan coincidencia en la descripcion
    public ArrayList<Producto> buscaProductosByDescripcion(String descripcion) {
        ArrayList<Producto> productosCoincideDescripcion = new ArrayList<>();
        for (Producto p : catalogo) {
            if (p.getDescripcion().toLowerCase().contains(descripcion.toLowerCase()))
                productosCoincideDescripcion.add(p);
        }
        return productosCoincideDescripcion;
    }

    //Metodo que busca en el catálogo producto un término que se encuentre en descripcion o marca o modelo
    public ArrayList<Producto> buscaProductosByTermino(String termino) {
        ArrayList<Producto> productosCoincideTermino = new ArrayList<>();
        String terminoLower = termino.toLowerCase();

        for (Producto p : catalogo) {
            if ((p.getDescripcion().toLowerCase().contains(terminoLower) ||
                    p.getMarca().toLowerCase().contains(terminoLower) ||
                    p.getModelo().toLowerCase().contains(terminoLower))
                    && !productosCoincideTermino.contains(p)) {

                productosCoincideTermino.add(p);
            }
        }
        return productosCoincideTermino;
    }


    //Metodo que busca en el catálogo producto que esten entre un rango de precios
    public ArrayList<Producto> buscaProductosByPrecio(float precioMin, float precioMax) {
        ArrayList<Producto> productosCoincidePrecio = new ArrayList<>();
        for (Producto p : catalogo) {
            if (p != null && p.getPrecio() <= precioMax && p.getPrecio() >= precioMin) productosCoincidePrecio.add(p);
        }
        return productosCoincidePrecio;
    }

    public boolean editarProducto(Producto p) {
        for (Producto producto : catalogo) {
            if (producto.getId() == p.getId()) {
                producto.setMarca(p.getMarca());
                producto.setModelo(p.getModelo());
                producto.setDescripcion(p.getDescripcion());
                producto.setPrecio(p.getPrecio());
                return true;
            }
        }
        return false;
    }

    //Metodo que devuelve todos los pedidos existentes
    public ArrayList<Pedido> getTodosPedidos() {
        ArrayList<Pedido> todosPedidos = new ArrayList<>();
        for (Cliente c : clientes) {
            if (!c.getPedidos().isEmpty()) todosPedidos.addAll(c.getPedidos());
        }
        return todosPedidos;
    }

    //Metodo para ver la cantidad de pedidos en el sistema
    public int numPedidosTotales() {
        int cont = 0;
        for (Cliente c : clientes) {
            if (c.getPedidos() != null) cont += c.getPedidos().size();
        }
        return cont;
    }

    //Metodo que busca un pedido por su ID
    public Pedido buscaPedidoById(int idPedido) {
        for (Pedido p : getTodosPedidos()) {
            if (p.getId() == idPedido) return p;
        }
        return null;
    }

    //Metodo que cambia el estado de un pedido
    public boolean cambiaEstadoPedido(int idPedido, int nuevoEstado) {
        for (Pedido p : getTodosPedidos()) {
            if (p.getId() == idPedido) {
                p.setEstado(nuevoEstado);
                return true;
            }
        }
        return false;
    }

    //Metodo que crea un trabajador y lo añade al array de trabajadores
    public boolean nuevoTrabajador(String email, String clave, String nombre, int movil) {
        return trabajadores.add(new Trabajador(generaIdTrabajador(), nombre, clave, email, movil));
    }

    //Metodo que devuelve el trabajador al que está asignado un pedido
    public Trabajador buscaTrabajadorAsignadoAPedido(int idPedido) {
        for (Trabajador t : trabajadores) {
            for (Pedido p : t.getPedidosAsignados()) {
                if (p.getId() == idPedido) return t;
            }
        }
        return null;
    }

    //Metodo que nos devuelve los pedidos sin asignar recorre todos los trabajadores recabando los pedidos asignados
    //luego recorre los clientes pillando los pedidos y luego de la lista de pedidosClientes le quita los pedidos ya asignados a trabajadores
    public ArrayList<Pedido> pedidosSinTrabajador() {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        if (!trabajadores.isEmpty()) {
            for (Cliente c : clientes) {
                for (Pedido p : c.getPedidos()) {
                    if (buscaTrabajadorAsignadoAPedido(p.getId()) == null) pedidos.add(p);
                }
            }
        } else {
            for (Cliente c : clientes) {
                pedidos.addAll(c.getPedidos());
            }
        }
        return pedidos;
    }

    //Metodo para asignar un pedido
    public boolean asignaPedido(int idPedido, int idTrabajador) {
        Pedido pedidoTemp = buscaPedidoById(idPedido);
        Trabajador trabajadorTemp = buscaTrabajadorByID(idTrabajador);

        if (pedidoTemp == null) return false;
        if (trabajadorTemp == null) return false;

        return trabajadorTemp.asignaPedido(pedidoTemp);
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

    //Metodo que busca un pedido asignado en el trabajador
    public ArrayList<PedidoClienteDataClass> getPedidosAsignadosTrabajador(int idTrabajador) {
        ArrayList<PedidoClienteDataClass> pedidosAsignadosT = new ArrayList<>();

        Trabajador temp = buscaTrabajadorByID(idTrabajador);

        for (Pedido pT : temp.getPedidosAsignados()) {
            for (Cliente c : clientes) {
                for (Pedido pA : c.getPedidos()) {
                    if (pA.getId() == pT.getId()) {
                        pedidosAsignadosT.add(new PedidoClienteDataClass(c.getId(), c.getEmail(), c.getNombre(), c.getLocalidad(), c.getProvincia(), c.getDireccion(), c.getMovil(), pA.getId(), pA.getFechaPedido(), pA.getFechaEntregaEstimada(), pA.getEstado(), pA.getComentario(), pA.getProductos()));
                    }
                }
            }
        }

        return pedidosAsignadosT;
    }

    //Metodo que devuelve un array de pedidos completados
    public ArrayList<PedidoClienteDataClass> getPedidosCompletadosTrabajador(int idTrabajador) {
        ArrayList<PedidoClienteDataClass> pedidosCompletadosT = new ArrayList<>();

        Trabajador temp = buscaTrabajadorByID(idTrabajador);

        //Bucle que mira los pedidos completados de los trabajadores
        for (Pedido pT : temp.getPedidosCompletados()) {
            // Bucle del cliente
            for (Cliente c : clientes) {
                // Bucle que mira los pedidos de los clientes
                for (Pedido pC : c.getPedidos()) {
                    if (pC.getId() == pT.getId()) {
                        pedidosCompletadosT.add(new PedidoClienteDataClass(c.getId(), c.getEmail(), c.getNombre(), c.getLocalidad(), c.getProvincia(), c.getDireccion(), c.getMovil(), pC.getId(), pC.getFechaPedido(), pC.getFechaEntregaEstimada(), pC.getEstado(), pC.getComentario(), pC.getProductos()));
                    }
                }
            }
        }

        return pedidosCompletadosT;
    }

    //Metodo que pasa los pedidos asignados y completados al trabajador
    public ArrayList<PedidoClienteDataClass> getPedidosAsignadosYCompletados(int idTrabajador) {
        ArrayList<PedidoClienteDataClass> pedidos = new ArrayList<>();

        Trabajador temp = buscaTrabajadorByID(idTrabajador);

        pedidos.addAll(getPedidosAsignadosTrabajador(temp.getId()));
        pedidos.addAll(getPedidosCompletadosTrabajador(temp.getId()));
        return pedidos;
    }

    //Metodo que genera el id del cliente SI EMPIEZA POR 2 ES CLIENTE
    private int generaIdCliente() {
        int idCliente;
        do {
            idCliente = (int) ((Math.random() * 90000) + 10000);
        } while (buscaClienteById(idCliente) != null);
        idCliente = Integer.parseInt(("2" + idCliente));
        return idCliente;
    }

    //Metodo que genera el id del producto
    private int generaIdProducto() {
        int idProducto;
        do {
            idProducto = (int) ((Math.random() * 900000) + 100000);
        } while (buscaProductoById(idProducto) != null);
        idProducto = Integer.parseInt(("9" + idProducto));
        return idProducto;
    }

    //Metodo que genera el id del pedido
    private int generaIdPedido() {
        int idPedido;
        do {
            idPedido = (int) ((Math.random() * 900000) + 100000);
        } while (buscaPedidoById(idPedido) != null);
        idPedido = Integer.parseInt(("9" + idPedido));
        return idPedido;
    }

    //Metodo que genera el id del admin SI EMPIEZA POR 3 ES ADMIN
    private int generaIdAdmin() {
        int idAdmin;
        do {
            idAdmin = (int) ((Math.random() * 90000) + 10000);
        } while (buscaAdminById(idAdmin) != null);
        idAdmin = Integer.parseInt(("3" + idAdmin));
        return idAdmin;
    }

    //Metodo para buscar un admin por su id aunque solo haya 1
    private Admin buscaAdminById(int idAdmin) {
        for (Admin a : admins) {
            if (a.getId() == idAdmin) return a;
        }
        return null;
    }

    //Metodo que genera el id del trabajador SI EMPIEZA POR 1 ES TRABAJADOR
    private int generaIdTrabajador() {
        int idTrabajador;
        do {
            idTrabajador = (int) ((Math.random() * 90000) + 10000);
        } while (buscaTrabajadorByID(idTrabajador) != null);
        idTrabajador = Integer.parseInt(("1" + idTrabajador));
        return idTrabajador;
    }

    //Metodo que devuelve el total de pedidos pendientes de entrega a un cliente (se hace mirando el estado)
    public int getTotalPedidosPendientesEntregaCliente(Cliente cliente) {
        int cont = 0;
        for (Pedido p : cliente.getPedidos()) {
            if (p.getEstado() != 3 && p.getEstado() != 4) cont++;
        }
        return cont;
    }

    //Metodo que se utiliza al cambiar los datos personales de un cliente
    public boolean actualizaDatosCliente(Cliente cliente, Cliente clienteCambiaDatos) {
        for (Cliente c : clientes) {
            if (c.getId() == cliente.getId() && c.getEmail().equalsIgnoreCase(cliente.getEmail())) {
                clientes.remove(c);
                clientes.add(clienteCambiaDatos);
                return true;
            }
        }
        return false;
    }

    //Metodo para el registro de clientes
    public boolean registraCliente(String emailIntro, String claveIntro, String nombreIntro, String localidadIntro, String provinciaIntro, String direccionIntro, int movilIntro) {
        Cliente cliente = new Cliente(generaIdCliente(), emailIntro, claveIntro, nombreIntro, localidadIntro, provinciaIntro, direccionIntro, movilIntro);
        return agregaClienteSistema(cliente);
    }

    //Metodo que se utiliza al cambiar los datos personales de un trabajador
    public boolean actualizaDatosTrabajador(Trabajador trabajador, Trabajador trabajadorCambiaDatos) {
        for (Trabajador t : trabajadores) {
            if (t.getId() == trabajador.getId() && t.getEmail().equalsIgnoreCase(trabajador.getEmail())) {
                trabajadores.remove(t);
                trabajadores.add(trabajadorCambiaDatos);
                return true;
            }
        }
        return false;
    }

    //Metodo que busca un trabajador por su email
    public Trabajador buscaTrabajadorByEmail(String email) {
        for (Trabajador t : trabajadores) {
            if (t.getEmail().equalsIgnoreCase(email)) return t;
        }
        return null;
    }

    //Metodo que devuelve la cantidad de pedidos pendientes de todos los trabajadores
    public int numPedidosPendientes() {
        int cont = 0;
        for (Trabajador t : trabajadores) {
            if (!t.getPedidosPendientes().isEmpty()) cont += t.getPedidosPendientes().size();
        }
        return cont;
    }

    //Metodo que devuelve la cantidad de pedidos completados o pendientes de todos los trabajadores
    public int numPedidosCompletadosCancelados() {
        int cont = 0;
        for (Trabajador t : trabajadores) {
            if (!t.getPedidosCompletados().isEmpty()) cont += t.getPedidosCompletados().size();
        }
        return cont;
    }

    //Metodo que devuelve todos los pedidos pero con el modelo PedidoClienteDataClass
    public ArrayList<PedidoClienteDataClass> getTodosPedidosClienteDataClass() {
        ArrayList<PedidoClienteDataClass> todosPedidosCliente = new ArrayList<>();
        for (Cliente c : clientes) {
            if (!c.getPedidos().isEmpty()) for (Pedido p : c.getPedidos()) {
                todosPedidosCliente.add(new PedidoClienteDataClass(c, p));
            }
        }
        return todosPedidosCliente;
    }

    //Metodo que cancela un pedido de un cliente
    public boolean cancelaPedidoCliente(int idCliente, int idPedido) {
        Cliente clienteTemp = buscaClienteById(idCliente);
        Pedido pedidoTemp = buscaPedidoById(idPedido);

        if (clienteTemp == null) return false;
        if (pedidoTemp == null) return false;

        return pedidoTemp.cambiaEstado(4);
    }
}
