package comunications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class EnvioTelegram {
    public static void main(String[] args) {
        String mensaje;
        Scanner s = new Scanner(System.in);
        do {
            System.out.print("Introduce el mensaje para Telegram: ");
            mensaje = s.nextLine();
            if (mensaje.equals("null")) break;
            if (enviaMensajeTelegram(mensaje)) {
                System.out.println("Mensaje enviado con exito");
            } else System.out.println("Fallo al enviar el mensaje");
        } while (!mensaje.equals("null"));
    }

    static boolean enviaMensajeTelegram(String mensaje) {
        String direccion; //URL de la API de mi bot en mi conversación
        String fijo = "https://api.telegram.org/bot7780416856:AAFf5vqnxNPuL-wYwrLuT4tD2C9PAnG69Zs/sendMessage?chat_id=1423538108&text=";
        direccion = fijo + mensaje; //Metemos el mensaje al final
        URL url;
        boolean dev;
        dev = false;
        try {
            url = new URL(direccion); //Creando un objeto URL con la dirección de la API de mi bot
            URLConnection con = url.openConnection(); //Realizando la petición GET
            //Con esto, copiamos en in la respuesta HTTP, por si lo necesitamos
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            dev = true; //Ha tenido exito
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return dev; //Devuelvo si ha tenido exito o no
    }

}
