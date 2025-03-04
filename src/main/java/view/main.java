package view;

import controller.Controlador;

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
        System.out.println("         Bienvenidos a nuestra tienda online         ");
        System.out.println("=====================================================");
        System.out.println(" 1. Ver el catálogo");
        System.out.println(" 2. Registrarse");
        System.out.println(" 3. Iniciar sesión");
        System.out.print(" Marque su opción: ");

    }

    private static void menuUsuario(Controlador controlador, Object user) {

    }
}
