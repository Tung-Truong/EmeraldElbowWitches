package model;

import java.util.ArrayList;

public class InterpreterService extends ServiceRequest {

    // Attributes
    private ArrayList<String> languages;
    private String[] emails;

    // Constructors
    public InterpreterService(){
        // TODO: get emails from database
    }

    // Getters
    public ArrayList<String> getLanguages() {
        return this.languages;
    }

    public String[] getEmails() {
        return this.emails;
    }

    // Setters
    public void setLanguages(ArrayList<String> languages){
        this.languages = languages;
    }

    public void setEmails(String[] emails){
        this.emails = emails;
    }

    // Methods
    public void updateEmails(){
        // TODO: get emails from database if edited in database
    }

    public void addLanguage(String add){
        this.languages.add(add);
    }

    public void removeLanguage(String remove){
        this.languages.remove(remove);
    }
}
