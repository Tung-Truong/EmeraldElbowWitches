package HealthAPI;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class HealthcareController {

    @FXML
    private JFXTextField personName;

    @FXML
    private JFXButton providerEmail;

    @FXML
    private JFXButton providerPhoneNumber;

    @FXML
    private JFXButton submitButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXTextField personEmail;

    @FXML
    private JFXComboBox<String> healthProviderDropdown;

    @FXML
    private JFXTextArea message;

    @FXML
    private JFXPasswordField providerNumber;


    // Attributes for email response
    private Properties properties;
    private Session session;
    private String username; // E-mail corresponding to the sending e-mail address
    private String password;

    public void initialize(){
        username = HealthCareRun.getUsername();
        password = HealthCareRun.getPassword();

        healthProviderDropdown.getItems().addAll("United Healthcare", "Humana", "Aetna", "Blue Cross Blue Shield of MA", "Cigna");

        personName.getStyleClass().add("jfx-text-field");
        submitButton.getStyleClass().add("jfx-button");
        cancelButton.getStyleClass().add("jfx-button");
        personEmail.getStyleClass().add("jfx-text-field");
        healthProviderDropdown.getStyleClass().add("jfx-combo-box");
        message.getStyleClass().add("jfx-text-area");
        providerNumber.getStyleClass().add("jfx-text-area");

        // Stuff to do with email sending
        properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }



    @FXML
    void setHealthProviderChoice() {
        try {
            switch (healthProviderDropdown.getValue()) {
                case "United Healthcare":
                    providerPhoneNumber.setText("888-735-5842");
                    providerEmail.setText("NorthernNE_PRTeam@uhc.com");
                    break;
                case "Humana":
                    providerPhoneNumber.setText("1-800-457-4708");
                    providerEmail.setText("");
                    break;
                case "Aetna":
                    providerPhoneNumber.setText("1-800-872-3862");
                    providerEmail.setText("communications@email.aetna.com");
                    break;
                case "Blue Cross Blue Shield of MA":
                    providerPhoneNumber.setText("1-800-262-2583");
                    providerEmail.setText("email@emailbcbsma.com");
                    break;
                case "Cigna":
                    providerPhoneNumber.setText("1-800-668-3813");
                    providerEmail.setText("");
                    break;

                    default: break;
            }
        }
        catch (NullPointerException e){
            e.getMessage();
        }
    }

    public boolean sendHealthCareRequest(){
        String subjectLine = "IMPORTANT:" + healthProviderDropdown.getValue().trim() + " Member Healthcare Request: " + personEmail.getText().trim();
        String emailBody = "You've been sent a message by: " + personName.getText() + "\n User ID: " + providerNumber.getText()
                + "\n Message: " + message.getText() + "\n\n Please respond to this email to notify the user of your services";

        try {

            MimeMessage mime = new MimeMessage(session);

            mime.saveChanges(); // the hope is that this updates the fields before sending the message
            mime.setFrom(new InternetAddress(username));
            mime.setReplyTo(new InternetAddress[]{new InternetAddress(username.trim())});

            // Email to be sent to the Healthcare Provider
            mime.addRecipient(Message.RecipientType.TO, new InternetAddress(username));
            // Confirmation Email to be sent to the user
            mime.addRecipient(Message.RecipientType.TO, new InternetAddress(personEmail.getText().trim()));

            mime.setSubject(subjectLine);
            mime.setText(emailBody);
            Transport.send(mime);
            //Email, MessageHeader, Provider, Name, timeSubmitted

            ServiceRequestHealth newHealth = new ServiceRequestHealth(personEmail.getText().trim(),
                            subjectLine,
                    healthProviderDropdown.getValue().trim(), personName.getText().trim(),
                    mime.getSentDate().toString());

            HealthCareRun.getRequests().add(newHealth);
            try {
                AddDB2.addRequest(newHealth);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
        catch (MessagingException mex){
            mex.printStackTrace();
            return false;
        }
    }

    @FXML
    void close(){
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void adminLogin() throws IOException {
        FXMLLoader LogIn = new FXMLLoader(getClass().getClassLoader().getResource("view/ui/login2.fxml"));
        Parent root = LogIn.load();
        Stage servStage = new Stage();
        servStage.setTitle("HealthcareLogin");
        root.getStylesheets().add(HealthCareRun.getPath());
        servStage.setScene(new Scene(root, 380, 358));
        servStage.show();
        if(HealthCareRun.xPosition>=0&& HealthCareRun.yPosition>=0) {
            servStage.setX(HealthCareRun.xPosition);
            servStage.setY(HealthCareRun.yPosition);
        }
    }

    @FXML
    void SubmitRequest() {
        if(sendHealthCareRequest()){
            System.out.println("Email Is Sent");
        }
    }

    @FXML
    void NotesTextField() {

    }
}
