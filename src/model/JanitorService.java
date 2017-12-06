package model;

import java.sql.SQLException;
import java.util.ArrayList;


public class JanitorService extends ServiceRequest{

    public static JanitorStatistic numSuppliesUsed;
    public static int numSupplies;

    // Attributes
    private ArrayList<String> suppliesNeeded;
    private String janitorEmail = "kgrant@wpi.edu";
    private long avgTime = 0;
    private int used = 0;

    // Constructors
    public JanitorService (){
        classType = this.getClass().toString();
        used ++;
        this.setAccountTo(janitorEmail);
    }

    // Getters
    public ArrayList<String> getSuppliesNeeded(){
        numSupplies += 1;
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

    public int getNumSuppliesUsed() {
        return numSupplies;
    }

    public void generateReport() {
        /*
            Information required:
            - How long did each janitor take?
            - What equipment was used for this request (Not sure how to find this)
            - What location was visited to fulfill this request
            - What type of cleanup was necessary for this request (Not sure how to find this)
         */
        long timeSent = sent.getTime();
        long timeReceived = received.getTime();

        long diffSeconds = (timeReceived - timeSent) / 1000;
        // This part increments the number of interpreters used for the language and time taken for this interpreter
        long tempUsed = used;
        long tempAvg = avgTime;

        avgTime = ((tempAvg * (tempUsed - 1)) + diffSeconds)/tempUsed;

        try {
            AddDB.addJanitorStatistic(new JanitorStatistic(getNumSuppliesUsed(), avgTime));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
