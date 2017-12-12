package HealthAPI;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

import java.sql.SQLException;
import java.util.ArrayList;

public class HealthcareAdminController {

    @FXML
    private JFXTextField personName;

    @FXML
    private JFXTextField persondate;


    @FXML
    private JFXComboBox<String> CurrRequ;

    @FXML
    private JFXTextField personEmail;

    @FXML
    private JFXTextField Provider;

        @FXML
        void RemoveRequest() {
            try{
                ServiceRequestHealth serv = null;
                String reqDate = CurrRequ.getValue().substring(CurrRequ.getValue().indexOf('|') + 2).trim();
                for (ServiceRequestHealth aserv : HealthCareRun.getRequests()) {
                    try {
                        System.out.println(aserv.getSent().toString());
                        System.out.println(reqDate);
                        if (aserv.getSent().toString().trim().equals(reqDate)) {

                            aserv.setActive(false);
                            serv = aserv;
                        }
                    } catch (NullPointerException e) {
                        e.getMessage();
                    }
                }

                if (serv != null) {
                    HealthCareRun.getRequests().remove(serv);
                    DeleteDB2.delRequest(serv.getSent());
                }
            }catch (NullPointerException e) {
                e.getMessage();
            }catch (SQLException e) {
                e.printStackTrace();
            }

        }

        @FXML
        void NotesTextField() {

        }


        @FXML
        void selectHealthProviderChoice() {
            if (CurrRequ != null && !CurrRequ.equals("Active Requests")) {
            try {
                ServiceRequestHealth serv = null;
                    String reqDate = CurrRequ.getValue().substring(CurrRequ.getValue().indexOf('|') + 2).trim();
                    for (ServiceRequestHealth aserv : HealthCareRun.getRequests()) {
                        try {
                            System.out.println(aserv.getSent().toString());
                            System.out.println(reqDate);
                            if (aserv.getSent().toString().trim().equals(reqDate)) {
                                serv = aserv;
                            }
                        } catch (NullPointerException e) {
                            e.getMessage();
                        }
                    }

                    Provider.setText(serv.provider);
                    personName.setText(serv.name);
                    personEmail.setText(serv.email);
                    persondate.setText(serv.getSent().toString());

                }catch(NullPointerException e){
                    e.printStackTrace();
                }
            }

        }

        @FXML
        void MyRequests() {
            CurrRequ.getItems().clear();
            Refresh();

            for(ServiceRequestHealth h: HealthCareRun.getRequests()){
                try {
                    CurrRequ.getItems().add("" + h.getEmail() + " | " + h.getSent().toString());
                }
                catch (NullPointerException e){
                    e.getMessage();
                }
            }

        }



    void Refresh(){
        // Printing this stuff is a later order concern so get back to it later
        String finalString = " ";
        ArrayList<ServiceRequestHealth> searchInactive = new ArrayList<ServiceRequestHealth>();
        searchInactive.addAll(HealthCareRun.getRequests());

        for(ServiceRequestHealth serve : searchInactive) {
            if(serve != null) {
                serve.resolveRequest();
            }
            else {
                finalString.concat("No active Requests");
            }
        }
        for(ServiceRequestHealth s : searchInactive){
            if (!s.isActive()){
                HealthCareRun.getRequests().remove(s);
                try {
                    DeleteDB2.delRequest(s.getSent());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
    /*
    public void initialize(){

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
                    providerEmail.setText("");
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
        String subjectLine = "IMPORTANT: Member Healthcare Request";
        String emailBody = "You've been sent a message by: " + personName.getText() + "\n User ID: " + providerNumber
                + "\n Message: " + message + "\n\n Please respond to this email to notify the user of your services";

        try {
            MimeMessage mime = new MimeMessage(session);

            mime.saveChanges(); // the hope is that this updates the fields before sending the message
            mime.setFrom(new InternetAddress("random@gmail.com"));
            mime.setReplyTo(new InternetAddress[]{new InternetAddress(personEmail.getText().trim())});

            // Email to be sent to the Healthcare Provider
            mime.addRecipient(Message.RecipientType.TO, new InternetAddress(personEmail.getText().trim()));
            // Confirmation Email to be sent to the user
            mime.addRecipient(Message.RecipientType.TO, new InternetAddress(personEmail.getText().trim()));

            mime.setSubject(subjectLine);
            mime.setText(emailBody);

            Transport.send(mime);

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
        FXMLLoader LogIn = new FXMLLoader(getClass().getClassLoader().getResource("Healthcare/login2.fxml"));
        Parent root = LogIn.load();
        Stage servStage = new Stage();
        servStage.setTitle("HealthcareLogin");
        root.getStylesheets().add(HealthCareRun.getPath());
        servStage.setScene(new Scene(root, 380, 358));
        servStage.show();
        servStage.setX(HealthCareRun.xPosition);
        servStage.setY(HealthCareRun.yPosition);
    }

    @FXML
    void SubmitRequest() {
        /*try {
            this.setService();
            String location = servLocField.getText();

            if (service instanceof JanitorService) {
                service.setMessageHeader("Supplies needed at: " + location);
            } else if (service instanceof InterpreterService) {
                service.setMessageHeader("Interpreter needed at: " + location);

            service.setLocation(servLocField.getText());

            service.setMessageText(NotesTextField.getText());
            service.sendEmailServiceRequest();

            service.toString();
            System.out.println("add");
            System.out.println(Main.getRequestList().size());
            Main.requests.add(service);
            System.out.println(Main.getRequestList().size());
            System.out.println("Message sent succesfully");
            close();
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    @FXML
    void NotesTextField() {

    }*/

