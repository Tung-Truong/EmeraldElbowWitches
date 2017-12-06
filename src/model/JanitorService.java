package model;

import java.sql.SQLException;
import java.util.ArrayList;


public class JanitorService extends ServiceRequest{

    // Attributes
    private ArrayList<String> suppliesNeeded;
    private String janitorEmail = "kgrant@wpi.edu";
    public JanitorStatistic soap = JanitorStatistic.getSoap();

    // Constructors
    public JanitorService (){
        this.setAccountTo(janitorEmail);
    }

    // Getters
    public ArrayList<String> getSuppliesNeeded(){
        return this.suppliesNeeded;
    }

    public String getJanitorEmail() {
        return this.janitorEmail;
    }

    // Setters
    public void setSuppliesNeeded (ArrayList<String> supplies){
        this.suppliesNeeded = supplies;
    }

    public void setJanitorEmail (String mail){
        this.janitorEmail = mail;
    }

    public void generateReport() {
        /*
            Information required:
            - How long did each janitor take?
            - What equipment was used for this request (Not sure how to find this)
            - What location was visited to fulfill this request
            - What type of cleanup was necessary for this request (Not sure how to find this)
         */
        if (!isActive()) {
            long timeSent = sent.getTime();
            long timeReceived = received.getTime();

            long diffSeconds = (timeReceived - timeSent) / 1000;
            // This part increments the number of interpreters used for the language and time taken for this interpreter
            int tempUsed = 0;
            long tempAvg = 0;
            long newAvg = 0;

            newAvg = ((tempAvg * (tempUsed - 1)) + diffSeconds) / tempUsed;

            try {
                soap.setNumOfSupplies(tempUsed);
                soap.setAvgTime(newAvg);
                AddDB.addJanitorStatistic(soap);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
