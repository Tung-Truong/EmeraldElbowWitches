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
    public void generateReport() {
        System.out.println("Supplies Used: " + suppliesNeeded);
    }
}
