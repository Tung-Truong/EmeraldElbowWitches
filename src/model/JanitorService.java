package model;

import java.util.ArrayList;

public class JanitorService extends ServiceRequest{

    // Attributes
    private ArrayList<String> suppliesNeeded;
    private String janitorEmail = "kgrant@wpi.edu";
    private long totalTime = 0;
    private int used = 0;

    // Constructors
    public JanitorService (){
        used ++;
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

    @Override
    public String generateReport() {
        /*
            Information required:
            - How long did each janitor take?
            - What equipment was used for this request (Not sure how to find this)
            - What location was visited to fulfill this request
            - What type of cleanup was necessary for this request (Not sure how to find this)
         */
        if (!isActive()){
            String report = "Janitorial Report: ";

            long timeSent = sent.getTime();
            long timeReceived = received.getTime();

            long diffSeconds = (timeReceived - timeSent) / 1000;

            // This part is the meat of the report
            totalTime += diffSeconds;

            report.concat("Average time taken: " + findTime(totalTime/used) + " at " + location);

            return report;
        } else {
            return "This request has not yet been resolved";
        }
    }
}
