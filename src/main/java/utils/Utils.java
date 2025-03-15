package utils;

import java.util.Scanner;

public class Utils {
    public static final Scanner S = new Scanner(System.in);

    //Metodo pulsar para continuar
    public static void pulsaParaContinuar() {
        System.out.print("Pulse para continuar...");
        S.nextLine();
    }

    //Mensaje que cierra un programa
    public static void mensajeCierraPrograma() {
        System.out.println("Saliendo del programa");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 3; i++) {
            System.out.println(".");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //Metodo para agregar un tiempo de espera
    public static void tiempoEspera(int tiempo) {
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO Metodo que se encarga de enviar y recibir un codigo de verificacion al registrar un cliente
    public static boolean CorreoVerificacion(String emailIntro) {
        return false;
    }
}
