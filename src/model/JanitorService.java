package model;

public class JanitorService extends ServiceRequest{

    // Attributes
    private String[] suppliesNeeded;
    private String janitorEmail;
    private String location;

    // Constructors
    public JanitorService (){
        this.setAccountTo(janitorEmail);
        this.setMessageHeader("Supplies Needed at " + location);
    }

    // Getters
    public String[] getSuppliesNeeded(){
        return this.suppliesNeeded;
    }

    public String getJanitorEmail() {
        return this.janitorEmail;
    }

    public String getLocation() {
        return this.location;
    }

    // Setters
    public void setSuppliesNeeded (String[] supplies){
        this.suppliesNeeded = supplies;
    }

    public void setJanitorEmail (String mail){
        this.janitorEmail = mail;
    }

    public void setLocation (String location){
        this.location = location;
    }

}
