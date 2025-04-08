package programacion.avanzada.programacion_avanzada_project;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailServer {

    private static final String REMITENTE = "javafxuthprogramacionavanzada@gmail.com";
    private static final String CLAVE = "dnva icwt oicp eisv";

    public static boolean sendEmail(String titulo, String cuerpoHtml, String destinatario) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMITENTE, CLAVE);
            }
        });

        try {
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(REMITENTE));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensaje.setSubject(titulo);
            mensaje.setContent(cuerpoHtml, "text/html; charset=utf-8");

            Transport.send(mensaje);
            Alertas.success("Correo enviado correctamente!", "Se envio un codigo de verificacion a "+destinatario);
            return true;
        } catch (MessagingException e) {
            Alertas.error("Error Enviando correo...",e.getMessage());
            return false;
        }
    }
}
