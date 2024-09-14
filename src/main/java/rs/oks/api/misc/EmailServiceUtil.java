package rs.oks.api.misc;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailServiceUtil {

    public static class EmailService {

        public void sendEmail(String toEmail, String subject, String messageContent) {

//            Properties properties = new Properties();
//            properties.put("mail.smtp.host", "smtp.gmail.com");
//            properties.put("mail.smtp.port", "587");
//            properties.put("mail.smtp.auth", "true");
//            properties.put("mail.smtp.starttls.enable", "true");
//
//            Authenticator auth = new Authenticator() {
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication(fromEmail, password);
//                }
//            };

//            Session session = Session.getInstance(properties, auth);

//            try {
//                MimeMessage msg = new MimeMessage(session);
//                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
//                msg.setSubject(subject);
//                msg.setText(messageContent);
//
//                Transport.send(msg);
//                System.out.println("Email sent successfully");
//            } catch (MessagingException e) {
//                e.printStackTrace();
//            }
        }
    }
}
