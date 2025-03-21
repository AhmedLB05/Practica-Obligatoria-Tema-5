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
    public static boolean enviaTokenRegistro(String destino, int token) {

        String asunto = "VERIFICACIÓN FERNANSHOP";

        // Contenido HTML con el marcador ${numAleatorio}
        String mensaje = "<!DOCTYPE html>\n" +
                "<html lang=\"es\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Código de Verificación</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: 'Arial', sans-serif;\n" +
                "            background-color: #f4f7fa;\n" +
                "            color: #333;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            align-items: center;\n" +
                "            height: 100vh;\n" +
                "        }\n" +
                "        .container {\n" +
                "            width: 100%;\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "        .email-header {\n" +
                "            background-color: #007BFF;\n" +
                "            color: white;\n" +
                "            padding: 30px;\n" +
                "            text-align: center;\n" +
                "            border-radius: 10px 10px 0 0;\n" +
                "        }\n" +
                "        .email-header h1 {\n" +
                "            margin: 0;\n" +
                "            font-size: 28px;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "        .email-content {\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 30px;\n" +
                "            border-radius: 0 0 10px 10px;\n" +
                "            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .email-content p {\n" +
                "            font-size: 16px;\n" +
                "            line-height: 1.6;\n" +
                "            color: #555;\n" +
                "        }\n" +
                "        .verification-code {\n" +
                "            display: inline-block;\n" +
                "            padding: 20px 40px;\n" +
                "            background-color: #007BFF;\n" +
                "            color: white;\n" +
                "            font-size: 32px;\n" +
                "            font-weight: bold;\n" +
                "            border-radius: 10px;\n" +
                "            margin: 20px 0;\n" +
                "            text-align: center;\n" +
                "            width: 100%;\n" +
                "            max-width: 300px;\n" +
                "            margin-left: auto;\n" +
                "            margin-right: auto;\n" +
                "            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .footer {\n" +
                "            text-align: center;\n" +
                "            margin-top: 30px;\n" +
                "            font-size: 14px;\n" +
                "            color: #888;\n" +
                "        }\n" +
                "        .footer a {\n" +
                "            color: #007BFF;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "        .footer a:hover {\n" +
                "            text-decoration: underline;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"email-header\">\n" +
                "            <h1>Bienvenido a nuestro servicio</h1>\n" +
                "        </div>\n" +
                "        <div class=\"email-content\">\n" +
                "            <p>Gracias por registrarte en nuestro servicio. Estamos emocionados de tenerte con nosotros.</p>\n" +
                "            <p>Para completar tu registro y verificar tu cuenta, por favor usa el siguiente código de verificación:</p>\n" +
                "            <div class=\"verification-code\">${numAleatorio}</div>\n" +
                "            <p>Este código es válido por 10 minutos. Si no solicitaste este código, ignora este mensaje.</p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>&copy; 2025 PRACTICA OBLIGATORIA AHMED LB. Todos los derechos reservados.</p>\n" +
                "            <p><a href=\"#\">Política de privacidad</a> | <a href=\"#\">Términos de servicio</a></p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
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
            String numAleatorio = String.valueOf(token);

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

    //Metodo principal para probar el envío
    public static void main(String[] args) {
        String destinatario = "ahmedlb26205@gmail.com";  // Dirección de destino
        int num = Utils.generaTokenRegistro();

        // Llamamos al metodo para enviar el correo
        enviaTokenRegistro(destinatario, num);
    }
}
