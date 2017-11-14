package model;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class JanitorService {
    private Properties properties;
    private Session session;
    private String username = "elbowwitchemerald@gmail.com";
    private String password = "passwordhuh";

    public JanitorService(){
        properties = new Properties();

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void setAccount(String username, String password){
        this.username = username;
        this.password = password;
    }

    public boolean sendEmailServiceRequest(String message){

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        try {

            MimeMessage email = new MimeMessage(session);

            email.setFrom(new InternetAddress("random@gmail.com"));
            email.addRecipient(Message.RecipientType.TO, new InternetAddress("kgrant@wpi.edu"));
            email.setSubject("Maintenance Needed");
            email.setText(message);

            Transport.send(email);

            return true;
        }
        catch (MessagingException mex){
            mex.printStackTrace();
            return false;
        }
    }
}
