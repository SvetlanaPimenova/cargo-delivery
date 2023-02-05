package ua.pimenova.model.util;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.InputStream;
import java.util.Properties;

/**
 * Send emails to Users
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class EmailSender {
    private static final Logger LOGGER = Logger.getLogger(EmailSender.class);
    private final String user;
    private final Session session;

    public EmailSender() {
        Properties properties = getProperties();
        user = properties.getProperty("mail.user");
        session = getSession(user, properties);
    }

    /**
     * Sends email to User.Email contains html tags and text.
     * @param subject - email's greetings
     * @param body - email's letter
     * @param sendTo - email's recipient
     */
    public void send(String subject, String body, String sendTo) {
        MimeMessage message = new MimeMessage(session);
        try {
            sendEmail(subject, body, sendTo, message);
        } catch (MessagingException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void sendEmail(String subject, String body, String sendTo, MimeMessage message)
            throws MessagingException {
        message.setFrom(new InternetAddress(user));
        message.setSubject(subject);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
        mimeBodyPart.setContent(body, "text/html; charset=utf-8");
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        Transport.send(message);
    }

    private Session getSession(String user, Properties properties) {
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, properties.getProperty("mail.password"));
            }
        });
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        String connectionFile = "email.properties";
        try (InputStream resource = EmailSender.class.getClassLoader().getResourceAsStream(connectionFile)){
            properties.load(resource);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return properties;
    }
}
