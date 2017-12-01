package model;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class ServiceRequest implements IReport {

    // Attributes
    private Properties properties;
    private Session session;
    private String username = "elbowwitchemerald@gmail.com"; // E-mail corresponding to the sending e-mail address
    private String password = "passwordhuh";
    private boolean isActive;
    private Employee assigned;
    private Date sent;
    private String replyInfo;

    protected String email;
    protected String messageText;
    protected String messageHeader;
    protected String location;

    // Constructor

    // Sets up attributes in order to send emails
    public ServiceRequest(){
        isActive = true;

        properties = new Properties();

        // property attributes for replying to the email
        properties.put("mail.store.protocol", "pop3");
        properties.put("mail.pop3s.host", "pop.gmail.com");
        properties.put("mail.pop3s.ssl.trust", "pop.gmail.com");
        properties.put("mail.pop3s.port", "995");
        properties.put("mail.pop3s.starttls.enable", "true");

        // property attributes for sending the email
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

    public boolean isActive() {
        return isActive;
    }

    // Resets the e-mail account that the message is coming from
    public void setAccountFrom(String username, String password){
        this.username = username;
        this.password = password;
    }

    public void setActive(boolean bool){
        this.isActive = bool;
    }

    // Sets the e-mail address that the message is going to
    public void setAccountTo(String email){
        this.email = email;
    }

    public void setMessageText(String message){
        this.messageText = message;
    }

    public void setMessageHeader(String header){
        this.messageHeader = header;
    }

    public void setLocation(String location) { this.location = location;}

    public boolean sendEmailServiceRequest(){

        try {
            MimeMessage mime = new MimeMessage(session);

            mime.saveChanges(); // the hope is that this updates the fields before sending the message
            mime.setFrom(new InternetAddress("random@gmail.com"));
            mime.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mime.setSubject(messageHeader);
            mime.setText(messageText + "\n" + "\n" + "Reply to this email to resolve this request");

            Transport.send(mime);

            sent = mime.getSentDate();

            return true;
        }
        catch (MessagingException mex){
            mex.printStackTrace();
            return false;
        }
    }

    public void resolveRequest(){
        if(this.isActive){
            int gates = 0;

            try {
                Store store = session.getStore("pop3s");

                /*
                    Alright, so here's where we stand, the line below is currently
                    the source of all of the errors coming through as so I commented
                    everything else out to focus on this.

                    This problem has to do with authentication certificates between java
                    , google, and the ssl server or something to that effect to help with searching
                    for a solution
                 */
                store.connect("pop.gmail.com", 995, username, password);

                Folder folder = store.getFolder("inbox");

                if(folder.exists()){
                    folder.open(Folder.READ_ONLY);

                    Message[] messages = folder.getMessages();

                    if (messages.length != 0) {
                        for (int i = 0, n = messages.length; i < n; i++) {
                            Message message = messages[i];
                            // Get all the information from the message
                            String from = InternetAddress.toString(message.getFrom());
                            if (from != null && from.equals(email)) {
                                gates += 1;
                            }

                            String to = InternetAddress.toString(message
                                    .getRecipients(Message.RecipientType.TO));
                            if (to != null && to.equals(username)) {
                                gates += 1;
                            }

                            String subject = message.getSubject();
                            if (subject != null && subject.equals("Re: " + messageHeader)) {
                                gates += 1;
                            }

                            Date received = message.getSentDate();

                            if (received != null && received.after(sent)) {
                                gates += 1;
                            }

                            if (gates == 4){
                                replyInfo = message.getContent().toString();
                                setActive(false);
                                break;
                            }
                        }//end of for loop
                        System.out.println(gates);
                        
                        folder.close(false);
                        store.close();
                    } else {
                        System.out.println("This folder is empty");
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        // when the refresh button is pressed check for resolutions to request
        // There will also be a resolve button in the UI and if this is pressed remove the request from the active list
    }
}
