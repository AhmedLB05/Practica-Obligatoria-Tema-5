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
            Cliente clienteRegistrado = new Cliente(emailIntro, claveIntro, nombreIntro, localidadIntro, provinciaIntro, direccionIntro, movilIntro);
            if (controlador.agregaClienteSistema(clienteRegistrado)) System.out.println("\nREGISTRO EXISTOSO\n");
            else System.out.println("\nERROR: REGISTRO ERRÓNEO\n");
        }
    }

    //Metodo que se encarga de verificar el token del cliente (inventado por Ahmed)
    private static boolean verificacionCliente(String email) {
        int token = Utils.generaTokenRegistroCliente(), tokenIntro = 0;
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
                System.out.println("Bienvenido Trabajador" + controlador.getTrabajadores().indexOf(trabajador) + ". Tiene " + trabajador.numPedidosPendientes() + " pedido/s pendiente/s.");
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
                menuTrabajador(controlador, opTrabajador);
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

    }

    //Metodo que contiene el switch del menu trabajador
    private static void menuTrabajador(Controlador controlador, int opTrabajador) {
        switch (opTrabajador) {
            case 1: //Consultar los pedidos que tengo asignados
                break;
            case 2: //Modificar el estado de un pedido
                break;
            case 3: //Consultar el catálogo de productos
                break;
            case 4: //Modificar un producto
                break;
            case 5: //Ver el histórico de pedidos terminados
                break;
            case 6: //Ver mi perfil
                break;
            case 7: //Modificar mis datos personales
                break;
            case 8: //Salir
                break;

        }
    }

    //Metodo que contiene el switch del menu cliente
    private static void menuCliente(Controlador controlador, int opCliente, Cliente cliente) {
        int opcion;
        switch (opCliente) {
            case 1: //Consultar el catálogo de productos
                consultaCatalogoCliente(controlador);
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
    private static void consultaCatalogoCliente(Controlador controlador) {
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
                cliente.setNombre(S.nextLine());
            }
            if (opcion == 1 || opcion == 3) {
                System.out.print("Introduzca nueva localidad: ");
                cliente.setLocalidad(S.nextLine());
            }
            if (opcion == 1 || opcion == 4) {
                System.out.print("Introduzca nueva provincia: ");
                cliente.setProvincia(S.nextLine());
            }
            if (opcion == 1 || opcion == 5) {
                System.out.print("Introduzca nueva dirección: ");
                cliente.setDireccion(S.nextLine());
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
                else cliente.setMovil(movil);

            }
            if (opcion == 1 || opcion == 7) {
                System.out.print("Introduzca nuevo email: ");
                String email = S.nextLine();

                if (controlador.buscaClienteByEmail(email) != null)
                    System.out.println(" * ERROR YA EXISTE UN CLIENTE CON ESTE EMAIL");
                else if (verificacionCliente(email)) cliente.setEmail(email);

            }
            if (opcion == 1 || opcion == 8) {
                String claveAnterior, claveNueva;
                System.out.print("Introduzca clave anterior: ");
                claveAnterior = S.nextLine();
                if (!cliente.getClave().equals(claveAnterior)) System.out.println(" * ERROR CLAVE NO VÁLIDA");
                if (cliente.getClave().equals(claveAnterior)) {
                    System.out.println();
                    System.out.println(" * CLAVE CORRECTA");
                    System.out.print("Introduzca nueva clave: ");
                    claveNueva = S.nextLine();
                    if (!claveNueva.equals(claveAnterior)) cliente.setClave(S.nextLine());
                    if (claveNueva.equals(claveAnterior))
                        System.out.println(" * ERROR LA NUEVA CLAVE ES IGUAL A LA ANTERIOR");
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

