package view;

import comunications.EnvioMail;
import controller.Controlador;
import models.*;
import utils.Utils;

import java.util.ArrayList;
import java.util.Scanner;

public class main {

    public static final Scanner S = new Scanner(System.in);

    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        do {
            System.out.println("\n");
            System.out.println("        Bienvenidos a nuestra tienda online");
            System.out.println("===================================================");
            Object user = menuInicio(controlador);
            if (user != null) {
                menuUsuario(controlador, user);
            }

        } while (true);
    }

    private static Object menuInicio(Controlador controlador) {
        Scanner s = new Scanner(System.in);
        int op = 0;
        System.out.println(" 1. Ver el catálogo");
        System.out.println(" 2. Registrarse");
        System.out.println(" 3. Iniciar sesión");
        do {
            try {
                System.out.print(" Marque su opción: ");
                op = Integer.parseInt(new Scanner(System.in).nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("ERROR AL INTRODUCIR UNA OPCIÓN ");
            }
        } while (true);

        switch (op) {
            case 1: //Ver el catálogo
                pintaCatalogo(controlador);
                break;
            case 2: //Registrarse
                registroCliente(controlador);
                break;
            case 3: //Iniciar sesión
                login(controlador);
            default:
        }
        return null;
    }

    //Metodo login
    private static void login(Controlador controlador) {
        System.out.print("\n - Introduzca su email: ");
        String emailIntro = S.nextLine();
        System.out.print(" - Introduzca su contraseña: ");
        String claveIntro = S.nextLine();
        Object usuario = controlador.login(emailIntro, claveIntro);

        if (usuario == null) System.out.println("\nERROR AL INICIAR SESIÓN, VOLVIENDO AL MENÚ PRINCIPAL\n");
        else menuUsuario(controlador, usuario);
    }

    //Metodo para el registro de clientes
    private static void registroCliente(Controlador controlador) {
        String emailIntro, nombreIntro, localidadIntro, provinciaIntro, direccionIntro, claveIntro;
        int movilIntro;
        System.out.println();
        System.out.println("        Bienvenido al registro de clientes");
        System.out.println("===================================================");

        //Aquí comprobamos que el email no exista ya
        do {
            System.out.print(" - Introduzca su email: ");
            emailIntro = S.nextLine();
            if (controlador.buscaClienteByEmail(emailIntro) != null)
                System.out.println("ERROR: EMAIL INTRODUCIDO YA EXISTE");
        } while (controlador.buscaClienteByEmail(emailIntro) != null);

        System.out.print(" - Introduzca su nombre: ");
        nombreIntro = S.nextLine();
        System.out.print(" - Introduzca su localidad: ");
        localidadIntro = S.nextLine();
        System.out.print(" - Introduzca su provincia: ");
        provinciaIntro = S.nextLine();
        System.out.print(" - Introduzca su direccion: ");
        direccionIntro = S.nextLine();
        do {
            try {
                System.out.print(" - Introduzca su número de telefono: ");
                movilIntro = Integer.parseInt(S.nextLine());
                if (movilIntro > 99999999 && movilIntro <= 999999999) {
                    break;
                } else {
                    System.out.println(" * ERROR: El número de teléfono debe tener 9 dígitos.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" * ERROR AL INTRODUCIR NUMERO DE TELEFONO");
            }
        } while (true);
        System.out.print(" - Introduzca su clave: ");
        claveIntro = S.nextLine();

        if (verificacionCliente(emailIntro)) {
            if (controlador.registraCliente(emailIntro, claveIntro, nombreIntro, localidadIntro, provinciaIntro, direccionIntro, movilIntro))
                System.out.println("\nREGISTRO EXISTOSO\n");
            else System.out.println("\nERROR: REGISTRO ERRÓNEO\n");
        }
    }

    //Metodo que se encarga de verificar el token del cliente (inventado por Ahmed)
    private static boolean verificacionCliente(String email) {
        int token = Utils.generaTokenRegistro(), tokenIntro = 0;
        System.out.println("\n\nA continuación se le va a enviar un código de verificación a su correo");
        EnvioMail.enviaTokenRegistro(email, token);
        try {
            System.out.print("\nIntroduzca el código recibido: ");
            tokenIntro = Integer.parseInt(S.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ERROR AL INTRODUCIR CODIGO");
        }

        return (token == tokenIntro);
    }

    //Metodo que pinta el catálogo
    private static void pintaCatalogo(Controlador controlador) {
        int cont = 0;
        String continuar = "";
        for (Producto p : controlador.getCatalogo()) {
            if (cont == 5) {
                System.out.print("Pulsa ENTER para continuar o introduzca cualquier tecla para SALIR: ");
                continuar = S.nextLine();
                if (!continuar.isEmpty()) break;
                //Utils.pulsaParaContinuar();
                cont = 0;
            }
            System.out.println(p.pintaProductoADetalle());
            Utils.tiempoEspera(1000);
            cont++;
        }

    }

    //Metodo que pinta el menu según el tipo de usuario que sea
    private static void menuUsuario(Controlador controlador, Object user) {

        //Pintamos el menú de cliente y cogemos la opción
        if (user instanceof Cliente) {
            int opCliente = 0;
            Cliente cliente = new Cliente((Cliente) user);
            do {
                System.out.println();
                System.out.println("Bienvenido " + cliente.getNombre() + ". Tiene " + controlador.getTotalPedidosPendientesEntregaCliente(cliente) + " pedido/s pendiente/s de entrega.");
                System.out.println("""
                        1. Consultar el catálogo de productos
                        2. Realizar un pedido
                        3. Ver mis pedidos
                        4. Ver mis datos personales
                        5. Modificar mis datos personales
                        6. Salir""");
                do {
                    try {
                        System.out.print("Introduzca una opción: ");
                        opCliente = Integer.parseInt(S.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                    }
                } while (true);
                menuCliente(controlador, opCliente, cliente);
            } while (opCliente != 6);

        }

        //Pintamos el menu de trabajador y cogemos la opcion
        if (user instanceof Trabajador) {
            Trabajador trabajador = new Trabajador((Trabajador) user);
            int opTrabajador = 0;
            do {
                System.out.println("\n\nBienvenido trabajador " + trabajador.getNombre() + ". Tiene " + trabajador.numPedidosPendientes() + " pedido/s pendiente/s.");
                System.out.println("""
                        1. Consultar los pedidos que tengo asignados
                        2. Modificar el estado de un pedido
                        3. Consultar el catálogo de productos
                        4. Modificar un producto
                        5. Ver el histórico de pedidos terminados
                        6. Ver mi perfil
                        7. Modificar mis datos personales
                        8. Salir""");
                do {
                    try {
                        System.out.print("Introduzca una opción: ");
                        opTrabajador = Integer.parseInt(S.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                    }
                } while (true);
                menuTrabajador(controlador, opTrabajador, trabajador);
            } while (opTrabajador != 8);
        }

        //Pintamos el menu de admin y cogemos la opcion
        if (user instanceof Admin) {
            Admin admin = new Admin((Admin) user);
            int opAdmin = 0;
            do {
                //TODO
                System.out.println("Bienvenido Administrador. Tenemos " + controlador.numPedidosSinTrabajador() + " pedido/s sin asignar. " + "Debe asignarlos a un trabajador.");
                System.out.println("===============================================");
                System.out.println("Número de clientes: " + controlador.getClientes().size());
                System.out.println("Número de trabajadores: " + controlador.getTrabajadores().size());
                System.out.println("Numero de pedidos: " + controlador.numPedidosTotales());
                System.out.println("Número de pedidos pendientes: ");
                System.out.println("Número de pedidos completados o cancelados: ");
                System.out.println("Número de pedidos sin asignar: ");
                System.out.println("===============================================");
                System.out.println("\n\n");
                System.out.print("""
                        1. - Ver todo el catálogo
                        2. - Editar un producto
                        3. - Ver un resumen de todos los clientes
                        4. - Ver un resumen de todos los pedidos
                        5. - Ver un resumen de todos los trabajadores
                        6. - Ver las estadísticas de la aplicación
                        7. - Cambiar el estado de un pedido
                        8. - Dar de alta un trabajador
                        9. - Dar de baja un trabajador
                        10. - Asignar un pedido a un trabajador
                        11. - Salir""");
                do {
                    try {
                        System.out.print("Introduzca una opción: ");
                        opAdmin = Integer.parseInt(S.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                    }
                } while (true);
                menuAdmin(controlador, opAdmin);
            } while (opAdmin != 8);


        }
    }

    //Metodo que contiene el switch del menu administrador
    private static void menuAdmin(Controlador controlador, int opAdmin) {
        switch (opAdmin){
            case 1: //Ver todoo el catálogo
                break;
            case 2: //Editar un producto
                modificaProducto(controlador);
                break;
            case 3: //Ver un resumen de todos los clientes
                break;
            case 4: //Ver un resumen de todos los pedidos
                break;
            case 5: //Ver un resumen de todos los trabajadores
                break;
            case 6: //Ver las estadísticas de la aplicación
                break;
            case 7: //Cambiar el estado de un pedido
                break;
            case 8: //Dar de alta un trabajador
                break;
            case 9: //Dar de baja un trabajador
                break;
            case 10: //Asignar un pedido a un trabajador
                break;
            case 11:
                Utils.mensajeCierraPrograma();
                break;
        }
    }

    //Metodo que contiene el switch del menu trabajador
    private static void menuTrabajador(Controlador controlador, int opTrabajador, Trabajador trabajador) {
        switch (opTrabajador) {
            case 1: //Consultar los pedidos que tengo asignados
                consultaPedidosAsignados(controlador, trabajador);
                break;
            case 2: //Modificar el estado de un pedido
                modificaEstadoComentarioPedido(controlador, trabajador);
                break;
            case 3: //Consultar el catálogo de productos
                consultaCatalogoTrabajador(controlador);
                break;
            case 4: //Modificar un producto
                modificaProducto(controlador);
                break;
            case 5: //Ver el histórico de pedidos terminados
                historicoPedidosTerminados(trabajador, controlador);
                break;
            case 6: //Ver mi perfil
                pintaPerfilTrabajador(trabajador);
                break;
            case 7: //Modificar mis datos personales
                modificaDatosTrabajador(controlador, trabajador);
                break;
            case 8: //Salir
                Utils.mensajeCierraPrograma();
                break;

        }
    }

    //Metodo que modifica un producto
    private static void modificaProducto(Controlador controlador) {
        int id, op;
        do {
            try {
                System.out.print("Introduzca el ID del producto a modificar: ");
                id = Integer.parseInt(S.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println(" * ERROR AL INTRODUCIR EL ID DEL PRODUCTO");
            }
        } while (true);
        Producto p = controlador.buscaProductoById(id);
        if (p == null) System.out.println(" * ERROR NO SE HA ENCONTRADO NINGÚN PRODUCTO");
        else {
            System.out.println("Has seleccionado este producto: ");
            System.out.println();
            System.out.println(p.pintaProductoADetalle());
            System.out.println();
            do {
                System.out.println(" === MODIFICA PRODUCTO ===");
                System.out.println("""
                        1. - Modificar todos los campos
                        2. - Modificar marca
                        3. - Modificar modelo
                        4. - Modificar descripción
                        5. - Modificar precio
                        6. - Salir""");
                do {
                    try {
                        System.out.print("Introduzca una opción: ");
                        op = Integer.parseInt(S.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                    }
                } while (true);
                if (op == 1 || op == 2) {
                    System.out.print("Introduzca nueva marca del producto: ");
                    String marca = S.nextLine();
                    p.setMarca(marca);
                }
                if (op == 1 || op == 3) {
                    System.out.print("Introduzca nuevo modelo del producto: ");
                    String modelo = S.nextLine();
                    p.setModelo(modelo);
                }
                if (op == 1 || op == 4) {
                    System.out.print("Introduzca nueva descripción del producto: ");
                    String descripcion = S.nextLine();
                    p.setDescripcion(descripcion);
                }
                if (op == 1 || op == 5) {
                    float precio;
                    do {
                        try {
                            System.out.print("Introduzca nuevo precio: ");
                            precio = Float.parseFloat(S.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println(" * ERROR AL INTRODUCIR NUEVO PRECIO");
                        }
                    } while (true);
                    if (precio <= 0) System.out.println(" * ERROR EL PRECIO NO PUEDE SER NEGATIVO");
                    else p.setPrecio(precio);
                }
                if (op == 6) Utils.mensajeCierraPrograma();
            } while (op != 6);
        }
    }

    //Metodo que pinta todos los pedidos terminados/completados de un trabajador
    private static void historicoPedidosTerminados(Trabajador trabajador, Controlador controlador) {
        if (controlador.getPedidosCompletadosTrabajador(trabajador.getId()).isEmpty())
            System.out.println("\n * ERROR NO TIENES PEDIDOS COMPLETADOS EN TU PERFIL\n");
        else {
            System.out.println("""
                    ╔═╗╔═╗╔╦╗╦╔╦╗╔═╗╔═╗  ╔═╗╔═╗╔╦╗╔═╗╦  ╔═╗╔╦╗╔═╗╔╦╗╔═╗╔═╗
                    ╠═╝║╣  ║║║ ║║║ ║╚═╗  ║  ║ ║║║║╠═╝║  ║╣  ║ ╠═╣ ║║║ ║╚═╗
                    ╩  ╚═╝═╩╝╩═╩╝╚═╝╚═╝  ╚═╝╚═╝╩ ╩╩  ╩═╝╚═╝ ╩ ╩ ╩═╩╝╚═╝╚═╝
                    """);
            for (PedidoClienteDataClass p : controlador.getPedidosCompletadosTrabajador(trabajador.getId())) {
                System.out.println(p);
                System.out.println();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //Metodo que se encarga de modificar los datos de un trabajador
    private static void modificaDatosTrabajador(Controlador controlador, Trabajador trabajador) {
        int opcion;
        Trabajador trabajadorCambiaDatos = new Trabajador(trabajador);
        do {
            System.out.println();
            System.out.println("=================================");
            System.out.println("    MODIFICA DATOS PERSONALES");
            System.out.println("=================================");
            System.out.println("""
                    1. - Modificar todos los datos del cliente
                    2. - Modificar nombre
                    3. - Modificar contraseña
                    4. - Modificar email
                    5. - Modificar movil
                    6. - Salir""");
            do {
                try {
                    System.out.print("Introduzca una opción: ");
                    opcion = Integer.parseInt(S.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                }
            } while (true);
            if (opcion == 1 || opcion == 2) { //NOMBRE
                System.out.print("Introduzca nuevo nombre: ");
                String nombre = S.nextLine();
                trabajadorCambiaDatos.setNombre(nombre);
                controlador.actualizaDatosTrabajador(trabajador, trabajadorCambiaDatos);
                trabajador.setNombre(nombre);
            }
            if (opcion == 1 || opcion == 8) {
                String claveAnterior, claveNueva;
                System.out.print("Introduzca clave anterior: ");
                claveAnterior = S.nextLine();

                if (!trabajadorCambiaDatos.getPass().equals(claveAnterior))
                    System.out.println(" * ERROR CLAVE NO VÁLIDA");
                else {
                    System.out.println();
                    System.out.println(" * CLAVE CORRECTA");
                    System.out.print("Introduzca nueva clave: ");
                    claveNueva = S.nextLine();

                    if (claveNueva.equals(claveAnterior))
                        System.out.println(" * ERROR LA NUEVA CLAVE ES IGUAL A LA ANTERIOR");
                    else {
                        trabajadorCambiaDatos.setPass(claveNueva);
                        controlador.actualizaDatosTrabajador(trabajador, trabajadorCambiaDatos);
                        trabajador.setPass(claveNueva);
                        System.out.println(" - SU CLAVE HA SIDO CAMBIADA CON ÉXITO: " + claveNueva);
                    }
                }
            }
            if (opcion == 1 || opcion == 4) { //EMAIL
                System.out.print("Introduzca nuevo email: ");
                String email = S.nextLine();

                if (controlador.buscaTrabajadorByEmail(email) != null)
                    System.out.println(" * ERROR YA EXISTE UN CLIENTE CON ESTE EMAIL");
                else if (verificacionTrabajador(email)) {
                    trabajadorCambiaDatos.setEmail(email);
                    System.out.println(" - SU EMAIL HA SIDO CAMBIADO CON ÉXITO");
                } else System.out.println(" * ERROR AL VERTIFICAR EL NUEVO EMAIL");
                controlador.actualizaDatosTrabajador(trabajador, trabajadorCambiaDatos);
                trabajador.setEmail(email);
            }
            if (opcion == 1 || opcion == 5) { //MOVIL
                int movil;
                do {
                    try {
                        System.out.print("Introduzca nuevo numero de teléfono: ");
                        movil = Integer.parseInt(S.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println(" * ERROR AL INTRODUCIR EL NUMERO DE TELÉFONO");
                    }
                } while (true);
                if (!(movil > 99999999 && movil <= 999999999))
                    System.out.println(" * ERROR: NUMERO INTRODUCIDO ERRÓNEO");
                else trabajadorCambiaDatos.setMovil(movil);
                controlador.actualizaDatosTrabajador(trabajador, trabajadorCambiaDatos);
                trabajador.setMovil(movil);
            }
            if (opcion == 6) Utils.mensajeCierraPrograma();
        } while (opcion != 6);
    }

    //Metodo que se encarga de toda la verificacion del email de un trabajador enviandole un token
    private static boolean verificacionTrabajador(String email) {
        int token = Utils.generaTokenRegistro(), tokenIntro = 0;
        System.out.println("\n\nA continuación se le va a enviar un código de verificación a su correo");
        EnvioMail.enviaTokenRegistro(email, token);
        try {
            System.out.print("\nIntroduzca el código recibido: ");
            tokenIntro = Integer.parseInt(S.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ERROR AL INTRODUCIR CODIGO");
        }

        return (token == tokenIntro);
    }

    //Metodo que se encarga de pinta el perfil del trabajador
    private static void pintaPerfilTrabajador(Trabajador trabajador) {
        System.out.println(trabajador);
    }

    //Metodo que se encarga de modificar el estado y el comentario de un pedido asignado a un trabajador
    private static void modificaEstadoComentarioPedido(Controlador controlador, Trabajador trabajador) {
        int op;
        do {
            System.out.println(" === MODIFICADOR DE ESTADO PEDIDOS === ");
            System.out.println("""
                    1. - Modificar el estado
                    2. - Añadir o modificar un comentario
                    3. - Salir
                    =======================================""");
            do {
                try {
                    System.out.print("Introduzca una opción: ");
                    op = Integer.parseInt(S.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                }
            } while (true);
            switch (op) {
                case 1:
                    modificaEstadoPedido(controlador, trabajador);
                    break;
                case 2:
                    modificaComentarioPedido(controlador, trabajador);
                    break;
                case 3:
                    Utils.mensajeCierraPrograma();
                    break;
            }
        } while (op != 3);

    }

    //Metodo que se encarga de modificar el comentario de un pedido asignado a un trabajador
    private static void modificaComentarioPedido(Controlador controlador, Trabajador trabajador) {
        System.out.println();
        System.out.println(" - BIENVENIDO A MODIFICAR EL COMENTARIO DE UN PEDIDO A TU CARGO");
        System.out.println("===============================================================");
        System.out.println();
        int idPedido = 0;
        ArrayList<PedidoClienteDataClass> pedidosCopia = new ArrayList<>();
        //PedidoClienteDataClass temp = null;
        do {
            try {
                System.out.print(" - Introduzca id del pedido (-1 para salir): ");
                idPedido = Integer.parseInt(S.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println(" * ERROR AL INTRODUCIR EL ID");
            }
        } while (true);
        if (idPedido == -1) {
            Utils.mensajeCierraPrograma();
        } else { //Si no ha seleccionado salir
            int cont = 0;
            for (PedidoClienteDataClass pedidoSeleccionado : controlador.getPedidosAsignadosTrabajador(trabajador.getId())) {
                System.out.println(" ***** PEDIDO " + cont + " ***** \n");
                System.out.println(pedidoSeleccionado);
                pedidosCopia.add(pedidoSeleccionado);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            int numPedido;

            do {
                try {
                    System.out.print("Introduce el pedido: ");
                    numPedido = Integer.parseInt(S.nextLine());
                    numPedido--;
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(" * ERROR AL INTRODUCIR EL NUMERO DEL PEDIDO");
                }
            } while (true);
            System.out.println("Este es el pedido seleccionado: ");
            PedidoClienteDataClass p = pedidosCopia.get(numPedido);
            System.out.println(p);
            System.out.print("Introduzca el comentario a añadir: ");
            String comentarioNuevo = S.nextLine();
            p.setComentario(comentarioNuevo);
        }
    }

    //Metodo que se encarga de modificar el estado de un pedido asignado a un trabajador
    private static void modificaEstadoPedido(Controlador controlador, Trabajador trabajador) {
        System.out.println();
        System.out.println(" - BIENVENIDO A MODIFICAR EL ESTADO DE UN PEDIDO A TU CARGO");
        System.out.println();
        int idPedido = 0;
        ArrayList<PedidoClienteDataClass> pedidosCopia = new ArrayList<>();
        do {
            try {
                System.out.println(" - Introduzca id del pedido (-1 para salir): ");
                idPedido = Integer.parseInt(S.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println(" * ERROR AL INTRODUCIR EL ID");
            }
        } while (true);
        if (idPedido == -1) {
            Utils.mensajeCierraPrograma();
        } else { //Si no ha seleccionado salir
            int cont = 0;
            for (PedidoClienteDataClass pedidoSeleccionado : controlador.getPedidosAsignadosTrabajador(trabajador.getId())) {
                System.out.println(" ***** PEDIDO " + cont + " ***** \n");
                System.out.println(pedidoSeleccionado);
                pedidosCopia.add(pedidoSeleccionado);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            int numPedido;

            do {
                try {
                    System.out.print("Introduce el pedido: ");
                    numPedido = Integer.parseInt(S.nextLine());
                    numPedido--;
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(" * ERROR AL INTRODUCIR EL NUMERO DEL PEDIDO");
                }
            } while (true);
            System.out.println("Este es el pedido seleccionado: ");
            PedidoClienteDataClass p = pedidosCopia.get(numPedido);
            System.out.println(p);
            int numEstado;
            System.out.println(" === Selecciona el nuevo estado === ");
            System.out.print("""
                    1. En preparación
                    2. Enviado
                    3. Entregado
                    4. Cancelado""");
            do {
                try {
                    System.out.print("Introduzca una opción: ");
                    numEstado = Integer.parseInt(S.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                }
            } while (true);
            p.setEstado(numEstado);
        }
    }

    //Metodo que consulta los pedidos que tengo asignados (siendo trabajador)
    private static void consultaPedidosAsignados(Controlador controlador, Trabajador trabajador) {
        if (trabajador.numPedidosPendientes() == 0) System.out.println("\n * NO TIENE PEDIDOS PENDIENTES");
        else {
            System.out.println();
            System.out.println(" - Tiene " + trabajador.numPedidosPendientes() + " pedidos pendientes");
            System.out.println();
            for (PedidoClienteDataClass p : controlador.getPedidosAsignadosTrabajador(trabajador.getId())) {
                System.out.println(p);
                System.out.println();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    //Metodo que contiene el switch del menu cliente TODO
    private static void menuCliente(Controlador controlador, int opCliente, Cliente cliente) {
        int opcion;
        switch (opCliente) {
            case 1: //Consultar el catálogo de productos
                consultaCatalogoTrabajador(controlador);
                break;
            case 2: //Realizar un pedido TODO
                realizaPedidoCliente(cliente, controlador);
                break;
            case 3: //Ver mis pedidos
                pintaPedidosCliente(cliente);
                break;
            case 4: //Ver mis datos personales
                pintaDatosPersonalesCliente(cliente);
                break;
            case 5: //Modificar mis datos personales
                modificaDatosCliente(controlador, cliente);
                break;
            case 6: //Salir
                Utils.mensajeCierraPrograma();
                break;
        }
    }

    //Metodo que pinta los datos personales de un cliente que le pasemos
    private static void pintaDatosPersonalesCliente(Cliente cliente) {
        System.out.println("╭───────────────────────────────────────────────────────────────────────╮");
        System.out.println("""
                                ╔╦╗┌─┐┌┬┐┌─┐┌─┐  ╔═╗┌─┐┬─┐┌─┐┌─┐┌┐┌┌─┐┬  ┌─┐┌─┐
                                 ║║├─┤ │ │ │└─┐  ╠═╝├┤ ├┬┘└─┐│ ││││├─┤│  ├┤ └─┐
                                ═╩╝┴ ┴ ┴ └─┘└─┘  ╩  └─┘┴└─└─┘└─┘┘└┘┴ ┴┴─┘└─┘└─┘
                                           \s
                """);
        System.out.println(cliente);
        System.out.println("╰───────────────────────────────────────────────────────────────────────╯");
    }

    //Metodo para realizar un pedido siendo cliente TODO
    private static void realizaPedidoCliente(Cliente cliente, Controlador controlador) {
        int op = 0;
        System.out.println("Actualmente tiene " + cliente.getCarro().size() + "productos en su carro.");
        System.out.println("""
                1. Inserta un producto en el carro
                2. Ver el carro
                3. Eliminar un producto del carro
                4. Confirmar el pedido
                5. Cancelar el pedido
                6. Salir""");
        do {
            try {
                System.out.print("Introduzca una opción: ");
                op = Integer.parseInt(S.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
            }
        } while (true);
    }

    //Metodo que nos indica varias maneras de consultar el catálogo siendo cliente
    private static void consultaCatalogoTrabajador(Controlador controlador) {
        int opcion;
        do {
            System.out.println();
            System.out.println("""
                    1. Ver todo el catálogo
                    2. Búsqueda por marca
                    3. Búsqueda por modelo
                    4. Búsqueda por descripción
                    5. Búsqueda por término
                    6. Búsqueda por precio
                    7. Salir""");
            do {
                try {
                    System.out.print("Introduzca una opción: ");
                    opcion = Integer.parseInt(S.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                }
            } while (true);
            switch (opcion) {
                case 1: //Ver tódo el catálogo
                    pintaCatalogo(controlador);
                    break;
                case 2: //Búsqueda por marca
                    buscaProductoMarca(controlador);
                    break;
                case 3: //Búsqueda por modelo
                    buscaProductoModelo(controlador);
                    break;
                case 4: //Búsqueda por descripción
                    buscaProductoDescripcion(controlador);
                    break;
                case 5: //Búsqueda por término
                    buscaProductoTermino(controlador);
                    break;
                case 6: //Búsqueda por precio
                    buscaProductoPrecio(controlador);
                    break;
                case 7: //Salir
                    Utils.mensajeCierraPrograma();
                    break;
            }
        } while (opcion != 7);
    }

    //Metodo que modifica los datos del cliente
    private static void modificaDatosCliente(Controlador controlador, Cliente cliente) {
        int opcion;
        Cliente clienteCambiaDatos = new Cliente(cliente);
        do {
            System.out.println();
            System.out.println("=================================");
            System.out.println("    MODIFICA DATOS PERSONALES");
            System.out.println("=================================");
            System.out.println("""
                    1. - Modificar todos los datos del cliente
                    2. - Modificar nombre
                    3. - Modificar localidad
                    4. - Modificar provincia
                    5. - Modificar direccion
                    6. - Modificar móvil
                    7. - Modificar email
                    8. - Modificar clave
                    9. - Salir""");
            do {
                try {
                    System.out.print("Introduzca una opción: ");
                    opcion = Integer.parseInt(S.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                }
            } while (true);
            if (opcion == 1 || opcion == 2) {
                System.out.print("Introduzca nuevo nombre: ");
                String nombre = S.nextLine();
                clienteCambiaDatos.setNombre(nombre);
                controlador.actualizaDatosCliente(cliente, clienteCambiaDatos);
                cliente.setNombre(nombre);
            }
            if (opcion == 1 || opcion == 3) {
                System.out.print("Introduzca nueva localidad: ");
                String localidad = S.nextLine();
                clienteCambiaDatos.setLocalidad(localidad);
                controlador.actualizaDatosCliente(cliente, clienteCambiaDatos);
                cliente.setLocalidad(localidad);
            }
            if (opcion == 1 || opcion == 4) {
                System.out.print("Introduzca nueva provincia: ");
                String provincia = S.nextLine();
                clienteCambiaDatos.setProvincia(provincia);
                controlador.actualizaDatosCliente(cliente, clienteCambiaDatos);
                cliente.setProvincia(provincia);
            }
            if (opcion == 1 || opcion == 5) {
                System.out.print("Introduzca nueva dirección: ");
                String direccion = S.nextLine();
                clienteCambiaDatos.setDireccion(direccion);
                controlador.actualizaDatosCliente(cliente, clienteCambiaDatos);
                cliente.setDireccion(direccion);
            }
            if (opcion == 1 || opcion == 6) {
                int movil;
                do {
                    try {
                        System.out.print("Introduzca nuevo numero de teléfono: ");
                        movil = Integer.parseInt(S.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println(" * ERROR AL INTRODUCIR EL NUMERO DE TELÉFONO");
                    }
                } while (true);
                if (!(movil > 99999999 && movil <= 999999999))
                    System.out.println(" * ERROR: NUMERO INTRODUCIDO ERRÓNEO");
                else clienteCambiaDatos.setMovil(movil);
                controlador.actualizaDatosCliente(cliente, clienteCambiaDatos);
                cliente.setMovil(movil);
            }
            if (opcion == 1 || opcion == 7) {
                System.out.print("Introduzca nuevo email: ");
                String email = S.nextLine();

                if (controlador.buscaClienteByEmail(email) != null)
                    System.out.println(" * ERROR YA EXISTE UN CLIENTE CON ESTE EMAIL");
                else if (verificacionCliente(email)) {
                    clienteCambiaDatos.setEmail(email);
                    System.out.println(" - SU EMAIL HA SIDO CAMBIADO CON ÉXITO");
                } else System.out.println(" * ERROR AL VERTIFICAR EL NUEVO EMAIL");
                controlador.actualizaDatosCliente(cliente, clienteCambiaDatos);
                cliente.setEmail(email);
            }
            if (opcion == 1 || opcion == 8) {
                String claveAnterior, claveNueva;
                System.out.print("Introduzca clave anterior: ");
                claveAnterior = S.nextLine();

                if (!clienteCambiaDatos.getClave().equals(claveAnterior))
                    System.out.println(" * ERROR CLAVE NO VÁLIDA");
                else {
                    System.out.println();
                    System.out.println(" * CLAVE CORRECTA");
                    System.out.print("Introduzca nueva clave: ");
                    claveNueva = S.nextLine();

                    if (claveNueva.equals(claveAnterior))
                        System.out.println(" * ERROR LA NUEVA CLAVE ES IGUAL A LA ANTERIOR");
                    else {
                        clienteCambiaDatos.setClave(claveNueva);
                        controlador.actualizaDatosCliente(cliente, clienteCambiaDatos);
                        cliente.setClave(claveNueva);
                        System.out.println(" - SU CLAVE HA SIDO CAMBIADA CON ÉXITO: " + claveNueva);
                    }
                }
            }
            if (opcion == 9) Utils.mensajeCierraPrograma();
        } while (opcion != 9);
    }

    //Metodo que se encarga de pintar los pedidos hechos por el cliente
    private static void pintaPedidosCliente(Cliente cliente) {
        if (cliente == null) System.out.println("\n * ERROR: HA OCURRIDO UN ERROR");

        if (cliente.getPedidos().isEmpty())
            System.out.println(" * ERROR: Actualmente no tienes ningún pedido efectuado");

        for (Pedido p : cliente.getPedidos()) {
            if (p != null) System.out.println(p);
        }
    }

    //Metodo que busca productos por descripcion - modelo - marca
    private static void buscaProductoTermino(Controlador controlador) {
        System.out.println();
        System.out.print(" - Introduzca el TERMINO a buscar: ");
        String termino = S.nextLine();
        ArrayList<Producto> productosCoincideTermino = controlador.buscaProductosByTermino(termino);
        if (!productosCoincideTermino.isEmpty()) pintaListaProductos(productosCoincideTermino);
        else System.out.println("No se han encontrado coincidencias");
    }

    //Metodo que busca productos entre un rango de precio
    private static void buscaProductoPrecio(Controlador controlador) {
        boolean correcto = false;
        float precioMin = 0, precioMax = 0;
        do {
            System.out.println();
            System.out.println(" * VAMOS A BUSCAR PRODUCTOS ENTRE UN RANGO DE PRECIOS: ");
            try {
                System.out.print(" - Introduzca precio mínimo del producto a buscar: ");
                precioMin = Float.parseFloat(S.nextLine());
                System.out.print(" - Introduzca precio máximo del producto a buscar: ");
                precioMax = Float.parseFloat(S.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(" * ERROR AL INTRODUCIR EL PRECIO");
            }

            if (precioMin < 0 || precioMax < 0) System.out.println(" * ERROR: Los precios no pueden ser negativos.");
            if (precioMin >= precioMax)
                System.out.println(" * ERROR AL INTRODUCIR LOS PRECIOS: PRECIO MINIMO NO PUEDE SER MAYOR QUE PRECIO MAXIMO");
            if (precioMin < precioMax) correcto = true;

        } while (!correcto);

        ArrayList<Producto> productosCoincidePrecio = controlador.buscaProductosByPrecio(precioMin, precioMax);
        if (!productosCoincidePrecio.isEmpty()) pintaListaProductos(productosCoincidePrecio);
        else System.out.println("No se han encontrado coincidencias");

    }

    //Metodo que busca productos por descripcion
    private static void buscaProductoDescripcion(Controlador controlador) {
        System.out.println();
        System.out.print(" - Introduzca la DESCRIPCIÓN del producto a buscar: ");
        String descripcion = S.nextLine();
        ArrayList<Producto> productosCoincideDescripcion = controlador.buscaProductosByDescripcion(descripcion);
        if (!productosCoincideDescripcion.isEmpty()) pintaListaProductos(productosCoincideDescripcion);
        else System.out.println("No se han encontrado coincidencias");
    }

    //Metodo que busca productos por modelo
    private static void buscaProductoModelo(Controlador controlador) {
        System.out.println();
        System.out.print(" - Introduzca el MODELO del producto a buscar: ");
        String modelo = S.nextLine();
        ArrayList<Producto> productosCoincideModelo = controlador.buscaProductosByModelo(modelo);
        if (!productosCoincideModelo.isEmpty()) pintaListaProductos(productosCoincideModelo);
        else System.out.println("No se han encontrado coincidencias");
    }

    //Metodo de la opcion para buscar productos por su marca
    private static void buscaProductoMarca(Controlador controlador) {
        System.out.println();
        System.out.print(" - Introduzca la MARCA del producto a buscar: ");
        String marca = S.nextLine();
        ArrayList<Producto> productosCoincideMarca = controlador.buscaProductosByMarca(marca);
        if (!productosCoincideMarca.isEmpty()) pintaListaProductos(productosCoincideMarca);
        else System.out.println("No se han encontrado coincidencias");
    }


    //Metodo que pinta una lista de productos que le pasemos
    private static void pintaListaProductos(ArrayList<Producto> productos) {
        for (Producto p : productos) {
            if (p != null) System.out.println(p.pintaProductoADetalle());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

