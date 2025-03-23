package comunications;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import models.Cliente;
import models.PedidoClienteDataClass;
import models.Producto;
import models.Trabajador;
import utils.Utils;

import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class EnvioMail {

    private static final String host = "smtp.gmail.com";  // Servidor SMTP de Gmail
    private static final String user = "amfernanshop@gmail.com";  // Tu correo electrónico
    private static final String pass = "vwqf kinf oaap cedq";  // Tu contraseña (o una contraseña de aplicación si usas Gmail)

    //Metodo principal para probar el envío
    public static void main(String[] args) {
        String destinatario = "ahmedlb26205@gmail.com";  // Dirección de destino
        int num = Utils.generaTokenRegistro();

        // Llamamos al metodo para enviar el correo
        enviaTokenRegistro(destinatario, num);
    }


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


    public static void enviaCorreoPedido(Trabajador t, PedidoClienteDataClass pedidoCliente, String asunto) {
        // Formateador para las fechas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Contenido HTML del correo
        String mensaje = "<!DOCTYPE html>\n" +
                "<html lang=\"es\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Asignación de Pedido</title>\n" +
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
                "        .pedido-details {\n" +
                "            background-color: #f9f9f9;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 10px;\n" +
                "            margin: 20px 0;\n" +
                "        }\n" +
                "        .pedido-details h2 {\n" +
                "            margin-top: 0;\n" +
                "            font-size: 24px;\n" +
                "            color: #007BFF;\n" +
                "        }\n" +
                "        .pedido-details p {\n" +
                "            margin: 10px 0;\n" +
                "            font-size: 16px;\n" +
                "            color: #333;\n" +
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
                "            <h1>¡Hola, " + t.getNombre() + "!</h1>\n" +
                "        </div>\n" +
                "        <div class=\"email-content\">\n" +
                "            <p>Se te ha asignado un nuevo pedido. A continuación, los detalles:</p>\n" +
                "            <div class=\"pedido-details\">\n" +
                "                <h2>Detalles del Pedido</h2>\n" +
                "                <p><strong>ID del Pedido:</strong> " + pedidoCliente.getIdPedido() + "</p>\n" +
                "                <p><strong>Fecha del Pedido:</strong> " + pedidoCliente.getFechaPedido().format(formatter) + "</p>\n" +
                "                <p><strong>Fecha de Entrega Estimada:</strong> " + pedidoCliente.getFechaEntregaEstimada().format(formatter) + "</p>\n" +
                "                <p><strong>Estado:</strong> " + pedidoCliente.getEstado() + "</p>\n" +
                "                <p><strong>Comentarios:</strong> " + pedidoCliente.getComentario() + "</p>\n" +
                "                <p><strong>Cliente:</strong></p>\n" +
                "                <ul>\n" +
                "                    <li><strong>Nombre:</strong> " + pedidoCliente.getNombre() + "</li>\n" +
                "                    <li><strong>Email:</strong> " + pedidoCliente.getEmail() + "</li>\n" +
                "                    <li><strong>Teléfono:</strong> " + pedidoCliente.getMovil() + "</li>\n" +
                "                    <li><strong>Dirección:</strong> " + pedidoCliente.getDireccion() + ", " + pedidoCliente.getLocalidad() + ", " + pedidoCliente.getProvincia() + "</li>\n" +
                "                </ul>\n" +
                "                <p><strong>Productos:</strong></p>\n" +
                "                <ul>\n";

        // Añadir los productos al mensaje
        for (Producto producto : pedidoCliente.getProductos()) {
            mensaje += producto;
        }

        mensaje += "                </ul>\n" +
                "            </div>\n" +
                "            <p>Por favor, revisa la información y procede según lo planificado.</p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>&copy; 2025 FERNANSHOP. Todos los derechos reservados.</p>\n" +
                "            <p><a href=\"#\">Política de privacidad</a> | <a href=\"#\">Términos de servicio</a></p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        // Configuración del servidor SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");  // Puerto para TLS

        // Configuración de la sesión
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            // Crear el mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(t.getEmail()));
            message.setSubject(asunto);
            message.setContent(mensaje, "text/html");

            // Enviar el mensaje
            Transport.send(message);
            System.out.println("Correo enviado con éxito a " + t.getNombre() + " (" + t.getEmail() + ")");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Hubo un error al enviar el correo.");
        }
    }

    public static void enviaCorreoPedidoEstado(Cliente c, PedidoClienteDataClass p) {
        // Formateador para las fechas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Asunto del correo
        String asunto = "Actualización del Estado de tu Pedido";

        // Contenido HTML del correo
        String mensaje = "<!DOCTYPE html>\n" +
                "<html lang=\"es\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Actualización del Estado del Pedido</title>\n" +
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
                "        .pedido-details {\n" +
                "            background-color: #f9f9f9;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 10px;\n" +
                "            margin: 20px 0;\n" +
                "        }\n" +
                "        .pedido-details h2 {\n" +
                "            margin-top: 0;\n" +
                "            font-size: 24px;\n" +
                "            color: #007BFF;\n" +
                "        }\n" +
                "        .pedido-details p {\n" +
                "            margin: 10px 0;\n" +
                "            font-size: 16px;\n" +
                "            color: #333;\n" +
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
                "            <h1>¡Hola, " + c.getNombre() + "!</h1>\n" +
                "        </div>\n" +
                "        <div class=\"email-content\">\n" +
                "            <p>El estado de tu pedido ha sido actualizado. A continuación, los detalles:</p>\n" +
                "            <div class=\"pedido-details\">\n" +
                "                <h2>Detalles del Pedido</h2>\n" +
                "                <p><strong>ID del Pedido:</strong> " + p.getIdPedido() + "</p>\n" +
                "                <p><strong>Fecha del Pedido:</strong> " + p.getFechaPedido().format(formatter) + "</p>\n" +
                "                <p><strong>Fecha de Entrega Estimada:</strong> " + p.getFechaEntregaEstimada().format(formatter) + "</p>\n" +
                "                <p><strong>Nuevo Estado:</strong> " + p.getEstado() + "</p>\n" +
                "                <p><strong>Comentarios:</strong> " + p.getComentario() + "</p>\n" +
                "                <p><strong>Productos:</strong></p>\n" +
                "                <ul>\n";

        // Añadir los productos al mensaje
        for (Producto producto : p.getProductos()) {
            mensaje += "                    <li>" + producto.getMarca() + " " + producto.getModelo() + " - " + producto.getDescripcion() + "</li>\n";
        }

        mensaje += "                </ul>\n" +
                "            </div>\n" +
                "            <p>Gracias por confiar en nosotros. Si tienes alguna pregunta, no dudes en contactarnos.</p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>&copy; 2025 FERNANSHOP. Todos los derechos reservados.</p>\n" +
                "            <p><a href=\"#\">Política de privacidad</a> | <a href=\"#\">Términos de servicio</a></p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        // Configuración del servidor SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");  // Puerto para TLS

        // Configuración de la sesión
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            // Crear el mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(c.getEmail()));
            message.setSubject(asunto);
            message.setContent(mensaje, "text/html");

            // Enviar el mensaje
            Transport.send(message);
            System.out.println("Correo enviado con éxito a " + c.getNombre() + " (" + c.getEmail() + ")");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Hubo un error al enviar el correo.");
        }
    }
}
