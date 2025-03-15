package view;

import controller.Controlador;
import data.DataProductos;
import models.Cliente;
import models.Producto;
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
                pintaCatalogo();
                break;
            case 2: //Registrarse
                registroCliente(controlador);
                break;
            case 3: //Iniciar sesión
                System.out.println(" - Introduzca su email: ");
                String email = s.nextLine();
                System.out.println(" - Introduzca su contraseña: ");
                String clave = s.nextLine();
                return controlador.login(email, clave);
            default:
        }
        return null;
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
            System.out.println(" - Introduzca su email: ");
            emailIntro = S.nextLine();
            if (controlador.buscaClienteByEmail(emailIntro) != null)
                System.out.println("ERROR: EMAIL INTRODUCIDO YA EXISTE");
        } while (controlador.buscaClienteByEmail(emailIntro) != null);

        System.out.println(" - Introduzca su nombre: ");
        nombreIntro = S.nextLine();
        System.out.println(" - Introduzca su localidad: ");
        localidadIntro = S.nextLine();
        System.out.println(" - Introduzca su provincia: ");
        provinciaIntro = S.nextLine();
        System.out.println(" - Introduzca su direccion: ");
        direccionIntro = S.nextLine();
        do {
            try {
                System.out.println(" - Introduzca su número de telefono: ");
                movilIntro = Integer.parseInt(S.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("ERROR AL INTRODUCIR NUMERO DE TELEFONO");
            }
        } while (true);
        System.out.println(" - Introduzca su clave: ");
        claveIntro = S.nextLine();

        //TODO correo verificacion
        if (Utils.CorreoVerificacion(emailIntro)) {
            Cliente clienteRegistrado = new Cliente(emailIntro, claveIntro, nombreIntro, localidadIntro, provinciaIntro, direccionIntro, movilIntro);
            if (controlador.agregaClienteSistema(clienteRegistrado)) System.out.println("REGISTRO EXISTOSO");
            else System.out.println("ERROR: REGISTRO ERRÓNEO");
        }
    }

    private static void pintaCatalogo() {
        int cont = 0;
        for (Producto p : DataProductos.getProductosMock()) {
            if (cont == 5) {
                Utils.pulsaParaContinuar();
                cont = 0;
            }
            System.out.println(p.pintaProductoADetalle());
            Utils.tiempoEspera(500);
            cont++;
        }

    }

    private static void menuUsuario(Controlador controlador, Object user) {

    }
}
