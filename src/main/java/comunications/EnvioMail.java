package comunications;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import utils.Utils;

import java.util.Properties;

public class EnvioMail {

    private static final String host = "smtp.gmail.com";  // Servidor SMTP de Gmail
    private static final String user = "amfernanshop@gmail.com";  // Tu correo electrónico
    private static final String pass = "vwqf kinf oaap cedq";  // Tu contraseña (o una contraseña de aplicación si usas Gmail)


    // Metodo para enviar el mensaje
    public static boolean enviaTokenRegistro(String destino) {

        String asunto = "VERIFICACIÓN FERNANSHOP";

        // Contenido HTML con el marcador ${numAleatorio}
        String mensaje = "<!DOCTYPE html>" +
                "<html lang='es'>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<title>Código de Verificación</title>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; background-color: #f4f7fa; color: #333; margin: 0; padding: 0; display: flex; justify-content: center; align-items: center; height: 100vh; }" +
                ".container { width: 100%; max-width: 600px; margin: 0 auto; padding: 20px; }" +
                ".email-header { background-color: #007BFF; color: white; padding: 20px; text-align: center; border-radius: 5px 5px 0 0; }" +
                ".email-header h1 { margin: 0; font-size: 24px; }" +
                ".email-content { background-color: #ffffff; padding: 20px; border-radius: 0 0 5px 5px; box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1); }" +
                ".email-content p { font-size: 16px; line-height: 1.6; }" +
                ".verification-code { display: inline-block; padding: 15px 30px; background-color: #007BFF; color: white; font-size: 30px; font-weight: bold; border-radius: 5px; margin-top: 20px; text-align: center; width: 100%; max-width: 250px; margin-left: auto; margin-right: auto; }" +
                ".footer { text-align: center; margin-top: 30px; font-size: 14px; color: #888; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='email-header'><h1>Bienvenido a nuestro servicio</h1></div>" +
                "<div class='email-content'>" +
                "<p>Gracias por registrarte en nuestro servicio.</p>" +
                "<p>Para completar tu registro y verificar tu cuenta, por favor usa el siguiente código de verificación:</p>" +
                "<div class='verification-code'>${numAleatorio}</div>" +
                "<p>Este código es válido por 10 minutos. Si no solicitaste este código, ignora este mensaje.</p>" +
                "</div>" +
                "<div class='footer'><p>&copy; 2025 Nombre del servicio. Todos los derechos reservados.</p></div>" +
                "</div>" +
                "</body>" +
                "</html>";

        // Configuramos las propiedades del servidor SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");  // El puerto 587 es para TLS

        // Configuramos la sesión con el servidor de correo usando el usuario y la contraseña
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            // Creamos un objeto Message con la sesión configurada
            Message message = new MimeMessage(session);

            // Establecemos el remitente
            message.setFrom(new InternetAddress(user));

            // Establecemos el destinatario
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));

            // Establecemos el asunto del correo
            message.setSubject(asunto);

            // Generamos un número aleatorio
            String numAleatorio = Utils.generaTokenRegistroCliente();

            // Reemplazamos el marcador ${numAleatorio} en el mensaje HTML
            mensaje = mensaje.replace("${numAleatorio}", numAleatorio);

            // Establecemos el contenido del mensaje en formato HTML
            message.setContent(mensaje, "text/html");

            // Enviamos el mensaje
            Transport.send(message);
            System.out.println("Correo enviado con éxito!");
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Hubo un error al enviar el correo.");
        }
        return false;
    }

    // Metodo principal para probar el envío
    public static void main(String[] args) {
        String destinatario = "ahmedlb26205@gmail.com";  // Dirección de destino

        // Llamamos al metodo para enviar el correo
        enviaTokenRegistro(destinatario);
    }
}
