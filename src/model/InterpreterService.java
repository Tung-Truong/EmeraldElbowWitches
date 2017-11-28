package model;

import java.util.ArrayList;

public class InterpreterService extends ServiceRequest {

    // Attributes
    private ArrayList<String> languages;
    private ArrayList<String> emails;
    private int interpretersUsed = 0;
    // ToDo: Possibly make each language for an interpreter its own class so that reports generate per language

    // Constructors
    public InterpreterService(){
        // TODO: get emails from database
        interpretersUsed ++;
    }

    // Getters
    public ArrayList<String> getLanguages() {
        return this.languages;
    }

    public ArrayList<String> getEmails() {
        return this.emails;
    }

    // Setters
    public void setLanguages(ArrayList<String> languages){
        this.languages = languages;
    }

    public void setEmails(ArrayList<String> emails){
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

    public void generateReport(){
        System.out.println("Interpreter's Used: " + interpretersUsed);
    }
}
