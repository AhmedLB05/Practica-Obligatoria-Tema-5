package utils;

import java.util.Scanner;

public class Utils {
    public static final Scanner S = new Scanner(System.in);

    public static void pulsaParaContinuar() {
        System.out.println("Pulse para continuar...");
        S.nextLine();
    }

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


}
