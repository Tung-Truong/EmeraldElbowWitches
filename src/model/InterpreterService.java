package model;

import java.util.ArrayList;
import java.util.HashMap;

public class InterpreterService extends ServiceRequest {

    // Attributes
    private ArrayList<String> languages;
    private ArrayList<String> emails;
    private HashMap<String, long[]> reportInfo;

    // ToDo: Possibly make each language for an interpreter its own class so that reports generate per language

    // Constructors
    public InterpreterService(){
        // TODO: get emails from database

        for (String s : getLanguages()){
            reportInfo.put(s, new long[2]);
        }
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
        String report = "";
        if (isActive()) {
            // This first part figures out how much time each instance of the Interpreter takes to resolve a request
            int hours = 0;
            int minutes = 0;
            int seconds = 0;

            long timeSent = sent.getTime();
            long timeReceived = received.getTime();

            long diffSeconds = (timeReceived - timeSent) / 1000;

            while ((diffSeconds - 3600) >= 0){
                diffSeconds -= 3600;
                hours ++;
            }
            while ((diffSeconds - 60) >= 0){
                diffSeconds -= 60;
                minutes ++;
            }
            while ((diffSeconds - 1) >= 0){
                diffSeconds -= 1;
                seconds ++;
            }

            report.concat(String.format("Time taken to complete task: %02:%02:%02" , hours, minutes, seconds));

        /*
            Information required:
            - How much time did each interpreter take
            - How many interpreters of each language have been requested
         */
            // this should only be done if the request status is inactive
            System.out.println("Interpreter's Used: ");
        } else {
            System.out.println("This request has yet to be resolved");
        }
    }
}
