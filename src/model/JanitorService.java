package model;

public class JanitorService extends ServiceRequest{

    // Attributes
    private String[] suppliesNeeded;
    private String janitorEmail;

    // Constructors
    public JanitorService (){
        this.setAccountTo(janitorEmail);
        this.setMessageHeader("Supplies Needed");
    }

    // Getters
    public String[] getSuppliesNeeded(){
        return this.suppliesNeeded;
    }

    public String getJanitorEmail() {
        return this.janitorEmail;
    }

    // Setters
    public void setSuppliesNeeded (String[] supplies){
        this.suppliesNeeded = supplies;
    }

    public void setJanitorEmail (String mail){
        this.janitorEmail = mail;
    }

}
