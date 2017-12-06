package model;

import java.sql.SQLException;
import java.util.ArrayList;


public class JanitorService extends ServiceRequest{

    public static JanitorStatistic numSuppliesUsed;
    public static int numSupplies;

    // Attributes
    private ArrayList<String> suppliesNeeded;
    private String janitorEmail = "kgrant@wpi.edu";
    private long totalTime = 0;
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
        try {
            AddDB.addJanitorStatistic(new JanitorStatistic(getNumSuppliesUsed()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
