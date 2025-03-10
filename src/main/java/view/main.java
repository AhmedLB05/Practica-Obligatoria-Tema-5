package view;

import controller.Controlador;
import data.DataProductos;
import models.Producto;
import utils.Utils;

import java.util.Scanner;

public class main {

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
                String emailIntro, nombreIntro, localidadIntro, provinciaIntro, direccionIntro, claveIntro;
                int movilIntro;
                System.out.println();
                System.out.println("        Bienvenido al registro de clientes");
                System.out.println("===================================================");
                System.out.println(" - Introduzca email: ");
                emailIntro = s.nextLine();
                System.out.println(" - Introduzca nombre: ");
                nombreIntro = s.nextLine();
                System.out.println(" - Introduzca localidad: ");
                localidadIntro = s.nextLine();
                System.out.println(" - Introduzca provincia: ");
                provinciaIntro = s.nextLine();
                System.out.println(" - Introduzca direccion: ");
                direccionIntro = s.nextLine();
                do {
                    try {
                        System.out.println(" - Introduzca número de telefono: ");
                        movilIntro = Integer.parseInt(s.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR AL INTRODUCIR NUMERO DE TELEFONO");
                    }
                } while (true);
                System.out.println(" - Introduzca clave: ");
                claveIntro = s.nextLine();
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

    private static void pintaCatalogo() {
        int cont = 0;
        for (Producto p : DataProductos.getProductosMock()) {
            if (cont == 5) {
                Utils.PulsaParaContinuar();
                cont = 0;
            }
            System.out.println(p.pintaProductoADetalle());
            cont++;
        }

    }

    private static void menuUsuario(Controlador controlador, Object user) {

    }
}
