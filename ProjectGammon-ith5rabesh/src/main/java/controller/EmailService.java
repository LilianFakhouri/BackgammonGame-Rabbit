package controller;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {

    private static final String SMTP_HOST = "smtp.gmail.com"; // Gmail SMTP server
    private static final String SMTP_PORT = "587";            // TLS port
    private static final String EMAIL_USERNAME = "rabbitdmn@gmail.com"; // Replace with your Gmail address
    private static final String EMAIL_PASSWORD = "wlhg kfao lpzh pwwt";  // Replace with your Gmail App Password

    /**
     * Constructor for EmailService.
     * Used to initialize the email service.
     */
    public EmailService() {
        System.out.println("✅ EmailService initialized.");
    }

    /**
     * Initializes the email session with Gmail SMTP settings.
     *
     * @return A Session object configured for Gmail SMTP.
     */
    private Session initMailConnection() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // TLS
        properties.put("mail.smtp.ssl.trust", SMTP_HOST);

        return Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });
    }




    /**
     * Sends an email notification.
     *
     * @param recipient The recipient's email address.
     * @param subject   The subject of the email.
     * @param content   The body of the email.
     */
    public void sendEmail(String recipient, String subject, String content) {
        try {
            // Create a new session
            Session session = initMailConnection();

            // Create the message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));               // Sender's email
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient)); // Recipient's email
            message.setSubject(subject);                                        // Email subject
            message.setText(content);                                          // Email content

            // Send the email
            Transport.send(message);
            System.out.println("✅ Email sent successfully to: " + recipient);
        } catch (MessagingException e) {
            System.err.println("❌ Failed to send email to: " + recipient);
            e.printStackTrace();
        }
    }

    /**
     * Main method for testing the EmailService.
     * Sends a test email.
     */
    public static void main(String[] args) {
        // Test the EmailService
        EmailService emailService = new EmailService();
        emailService.sendEmail(
            "rabbitdmn@gmail.com", // Replace with recipient's email
            "Test Email Subject",      // Replace with your subject
            "This is a test email sent using JavaMail!" // Replace with your content
        );
    }
}
