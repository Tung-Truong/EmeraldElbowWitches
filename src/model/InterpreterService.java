package model;

public class InterpreterService extends ServiceRequest {

    // Attributes
    private String[] languages;
    private String[] emails;

    // Constructors
    public InterpreterService(){

    }

    // Getters
    public String[] getLanguages() {
        return this.languages;
    }

    public String[] getEmails() {
        return this.emails;
    }

    // Setters
    public void setLanguages(String[] languages){
        this.languages = languages;
    }

    public void setEmails(String[] emails){
        this.emails = emails;
    }
}
