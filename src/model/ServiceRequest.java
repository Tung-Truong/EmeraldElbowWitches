package model;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ServiceRequest {
    private Properties properties;
    private Session session;
    private String username = "elbowwitchemerald@gmail.com";
    private String password = "passwordhuh";
    protected String email;
    protected String messageText;
    protected String messageHeader;
    protected String location;

    public ServiceRequest() {
        properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void setAccountFrom(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setAccountTo(String email) {
        this.email = email;
    }

    public void setMessageText(String message) {
        this.messageText = message;
    }

    public void setMessageHeader(String header) {
        this.messageHeader = header;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean sendEmailServiceRequest() {

        try {
            MimeMessage mime = new MimeMessage(session);

            mime.setFrom(new InternetAddress("random@gmail.com"));
            mime.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mime.setSubject(messageHeader);
            mime.setText(this.messageText);

            Transport.send(mime);

            return true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
    }
}
