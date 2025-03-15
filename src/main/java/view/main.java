package view;

import comunications.EnvioMail;
import controller.Controlador;
import models.Admin;
import models.Cliente;
import models.Producto;
import models.Trabajador;
import utils.Utils;

import java.util.Scanner;

public class main {

    public static final Scanner S = new Scanner(System.in);

    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        do {
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

    private static void login(Controlador controlador) {
        System.out.print(" - Introduzca su email: ");
        String emailIntro = S.nextLine();
        System.out.print(" - Introduzca su contraseña: ");
        String claveIntro = S.nextLine();
        Object usuario = controlador.login(emailIntro, claveIntro);

        if (usuario == null) System.out.println("ERROR AL INICIAR SESIÓN, VOLVIENDO AL MENÚ PRINCIPAL");
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
                break;
            } catch (NumberFormatException e) {
                System.out.println("ERROR AL INTRODUCIR NUMERO DE TELEFONO");
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
        for (Producto p : controlador.getCatalogo()) {
            if (cont == 5) {
                Utils.pulsaParaContinuar();
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
                System.out.println("Bienvenido " + ((Cliente) user).getNombre() + ". Tiene " +
                        controlador.getTotalPedidosPendientesEntregaCliente(cliente) + " pedido/s pendiente/s de entrega");
                System.out.println("""
                        1. Consultar el catálogo de productos
                        2. Realizar un pedido
                        3. Ver mis pedidos
                        4. Ver mis datos personales
                        5. Modificar mis datos personales
                        6. Salir""");
                do {
                    try {
                        System.out.print("Introduzca una opción:");
                        opCliente = Integer.parseInt(S.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                    }
                } while (true);
                menuCliente(controlador, opCliente);
            } while (opCliente != 6);

        }

        if (user instanceof Trabajador) {
            Trabajador trabajador = new Trabajador((Trabajador) user);
            System.out.println("menu trabajdor");
        }

        if (user instanceof Admin) {
            Admin admin = new Admin((Admin) user);

            System.out.println("menu admin");
        }
    }

    private static void menuCliente(Controlador controlador, int opCliente) {
        switch (opCliente) {
            case 1: //Consultar el catálogo de productos
                break;
            case 2: //Realizar un pedido
                break;
            case 3: //Ver mis pedidos
                break;
            case 4: //Ver mis datos personales
                break;
            case 5: //Modificar mis datos personales
                break;
            case 6: //Modificar mis datos personales
                break;
        }
    }
}
