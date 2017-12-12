package model;

import java.sql.SQLException;
import java.util.ArrayList;

public class InterpreterService extends ServiceRequest {

    // Attributes
    private static ArrayList<String> languages = new ArrayList<String>();
    public InterpreterStatistic statistic = InterpreterStatistic.getStats();

    // ToDo: Possibly make each language for an interpreter its own class so that reports generate per language

    // Constructors
    public InterpreterService(){
        languages.add("French");
        languages.add("Dutch");
        languages.add("Icelandic");
    }

    // Getters
    public static ArrayList<String> getLanguages() {
        return languages;
    }

    // Setters
    public void setLanguages(ArrayList<String> languages){
        this.languages = languages;
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
        /*
            Information required:
            - How much time did each language take to interpret?
            - How many interpreters of each language have been requested?
         */
        String lang = "";

        if (!isActive()) {
            if(assigned.getLanguage() == null) {

            } else {
                lang = assigned.getLanguage();

                long timeSent = sent.getTime();
                long timeReceived = received.getTime();

                long diffSeconds = (timeReceived - timeSent) / 1000;

                // This part increments the number of interpreters used for the language and time taken for this interpreter
                long tempUsed = 0;
                long tempAvg = 0;
                long newAvg = 0;

                tempUsed = statistic.getNumOfInterpreters() + 1;
                tempAvg = statistic.getAvgTimeTaken();

                newAvg = ((tempAvg * (tempUsed - 1)) + diffSeconds)/tempUsed;

                try {
                    statistic.setData(lang, tempUsed, newAvg);
                    AddDB.addInterpreterStatistic(statistic); //add at the very end of the program
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NullPointerException n) {
                    n.printStackTrace();
                }
            }
        }


    }
}
