package model;

import java.util.ArrayList;

public class JanitorService extends ServiceRequest{

    // Attributes
    private ArrayList<String> suppliesNeeded;
    private String janitorEmail = "kgrant@wpi.edu";

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

    @Override
    public String generateReport() {
        /*
            Information required:
            - How long did each janitor take?
            - What equipment was used for this request
            - What location was visited to fulfill this request
            - What type of cleanup was necessary for this request
         */
        return "Supplies Used: " + suppliesNeeded;
    }
}
