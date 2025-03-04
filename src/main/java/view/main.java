package view;

import controller.Controlador;

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
        int op = 0;
        System.out.println("         Bienvenidos a nuestra tienda online         ");
        System.out.println("=====================================================");
        System.out.println(" 1. Ver el catálogo");
        System.out.println(" 2. Registrarse");
        System.out.println(" 3. Iniciar sesión");
        do {
            try {
                System.out.print(" Marque su opción: ");
                op = Integer.parseInt(new Scanner(System.in).nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("ERROR AL INTRODUCIR UNA OPCIÓN: ");
            }
        } while (true);

        switch (op) {
            case 1: //Ver el catálogo
                break;
            case 2: //Registrarse
                break;
            case 3: //Iniciar sesión
                System.out.println(" - Introduzca su email: ");
                String email = new Scanner(System.in).nextLine();
                System.out.println(" - Introduzca su contraseña: ");
                String clave = new Scanner(System.in).nextLine();
                return controlador.login(email, clave);
            default:
        }
        return null;
    }

    private static void menuUsuario(Controlador controlador, Object user) {

    }
}
