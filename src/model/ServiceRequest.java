package model;

import controller.Main;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public class ServiceRequest implements IReport {

    // Attributes
    private Properties properties;
    private Session session;
    private String username = "elbowwitchemerald@gmail.com"; // E-mail corresponding to the sending e-mail address
    private String password = "passwordhuh";
    private boolean isActive;

    protected String email;
    protected String messageText;
    protected String messageHeader;
    protected String location;
    protected Date received;
    protected Date sent;
    protected Employee assigned;
    protected String classType;

    // Constructor

    // Sets up attributes in order to send emails
    /*
        Info Format:
        34567812345,class model.InterpreterService,true,Mon Dec 04 17:08:11 EST 2017
     */
    public ServiceRequest(String id, String type, String active, String submitted) {
        isActive = Boolean.parseBoolean(active);
        for (Employee e : Main.employees) {
            if (Long.parseLong(id) == e.getId()) {
                assigned = e;
            }
        }
        sent = new Date(Date.parse(submitted));
        email = assigned.getEmail();
        messageHeader = type;


        properties = new Properties();

        // property attributes for replying to the email
        properties.put("mail.store.protocol", "imaps");
        // properties.put("mail.imaps.host", "imap.gmail.com");
        properties.put("mail.imaps.ssl.trust", "imap.gmail.com");
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.starttls.enable", "true");

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

    public ServiceRequest() {
        isActive = true;

        properties = new Properties();

        // property attributes for replying to the email
        properties.put("mail.store.protocol", "imaps");
        // properties.put("mail.imaps.host", "imap.gmail.com");
        properties.put("mail.imaps.ssl.trust", "imap.gmail.com");
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.starttls.enable", "true");

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

    // Getters
    public boolean isActive() {
        return isActive;
    }

    public Employee getAssigned() {
        return assigned;
    }

    public Date getSent() {
        return sent;
    }

    // Resets the e-mail account that the message is coming from
    public void setAccountFrom(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setActive(boolean bool) {
        this.isActive = bool;
    }

    public void setAssigned(Employee e) {
        this.assigned = e;
    }

    // Sets the e-mail address that the message is going to
    public void setAccountTo(String email) {
        this.email = email;
    }

    public void setMessageText(String message) {
        this.messageText = message;
    }

    public void setMessageHeader(String header) {
        this.messageHeader = header;
    }

    public String getMessageHeader() {
        return this.messageHeader;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Remove these two methods after testing, they are solely here for testing purposes
    public void setSent() {
        sent = new Date();
    }

    public void setReceived() {
        received = new Date();
    }

    public void setReceived(long part) {
        received = new Date(sent.getTime() + part);
    }

    public boolean sendEmailServiceRequest() {

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
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
    }

    public void resolveRequest() {
        if (this.isActive) {
            int gates = 0;

            try {
                Store store = session.getStore("imaps");

                store.connect("imap.gmail.com", username, password);

                Folder folder = store.getFolder("inbox");

                if (folder.exists()) {
                    folder.open(Folder.READ_ONLY);

                    Message[] messages = folder.getMessages();

                    if (messages.length != 0) {
                        for (int i = 0, n = messages.length; i < n; i++) {
                            gates = 0;
                            Message message = messages[i];
                            // Get all the information from the message
                            String from = InternetAddress.toString(message.getFrom());
                            try {
                                if (from != null) {
                                    int begin = from.indexOf("<");
                                    int end = from.indexOf(">");
                                    if (email.equals(from.substring(begin + 1, end))) {
                                        gates += 1;
                                    }
                                }

                                System.out.println(email);


                                String to = InternetAddress.toString(message
                                        .getRecipients(Message.RecipientType.TO));
                                if (to != null) {
                                    int begin = to.indexOf("<");
                                    int end = to.indexOf(">");
                                    if (begin >= 0) {
                                        if (username.equals(to.substring(begin + 1, end))) {
                                            gates += 1;
                                        }
                                    } else if (username.equals(to)) {
                                        gates += 1;
                                    }
                                }
                            } catch (StringIndexOutOfBoundsException e) {
                                e.getMessage();
                            }

                            System.out.println(username);

                            String subject = message.getSubject();
                            if (subject != null && subject.equals("Re: " + messageHeader)) {
                                gates += 1;
                            }

                            System.out.println(messageHeader);

                            received = message.getSentDate();

                            if (received != null && received.after(sent)) {
                                gates += 1;
                            }
                            System.out.println(received);
                            System.out.println(sent);
                            System.out.println(received.after(sent));

                            if (gates == 4) {
                                // replyInfo = message.getContent().toString();
                                setActive(false);
                                break;
                            }
                        }//end of for loop

                        folder.close(false);
                        store.close();
                    } else {
                        System.out.println("This folder is empty");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Did Not Refresh");
            }
        }
        // when the refresh button is pressed check for resolutions to request
        // There will also be a resolve button in the UI and if this is pressed remove the request from the active list
    }

    public String findTime(long t) {
        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        while ((t - 3600) >= 0) {
            t -= 3600;
            hours++;
        }
        while ((t - 60) >= 0) {
            t -= 60;
            minutes++;
        }
        while ((t - 1) >= 0) {
            t -= 1;
            seconds++;
        }

        return String.format("%02:%02:%02", hours, minutes, seconds);
    }

    public void generateReport() {

    }
}
