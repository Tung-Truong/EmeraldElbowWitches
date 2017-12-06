package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class InterpreterService extends ServiceRequest {

    // Attributes
    private static ArrayList<String> languages = new ArrayList<String>();
    private ArrayList<String> emails = new ArrayList<String>();
    private HashMap<String, long[]> reportInfo = new HashMap<String, long[]>();

    // ToDo: Possibly make each language for an interpreter its own class so that reports generate per language

    // Constructors
    public InterpreterService(){
        // TODO: get emails from database
        languages.add("French");
        languages.add("Dutch");
        languages.add("Icelandic");

        for (String s : getLanguages()){
            reportInfo.put(s, new long[2]);
        }
    }

    // Getters
    public static ArrayList<String> getLanguages() {
        return languages;
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
        /*
            Information required:
            - How much time did each language take to interpret?
            - How many interpreters of each language have been requested?
         */

        String lang = "";
        long diff = 0;

        if (!isActive()) {
            if(assigned.getLanguage() == null) {

            } else {
                lang = assigned.getLanguage();

                diff = 0;

                long timeSent = sent.getTime();
                long timeReceived = received.getTime();

                long diffSeconds = (timeReceived - timeSent) / 1000;

                // This part increments the number of interpreters used for the language and time taken for this interpreter

                reportInfo.get(lang)[0] += 1;
                reportInfo.get(lang)[1] += diffSeconds;
                diff = reportInfo.get(lang)[1] / reportInfo.get(lang)[0];

                try {
                    InterpreterStatistic curStat = new InterpreterStatistic(lang, reportInfo.get(lang)[0], reportInfo.get(lang)[1]);
                    AddDB.addInterpreterStatistic(curStat);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NullPointerException n) {
                    n.printStackTrace();
                }
            }
        }


    }

    private String findTime(long t){
        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        while ((t - 3600) >= 0){
            t -= 3600;
            hours ++;
        }
        while ((t - 60) >= 0){
            t -= 60;
            minutes ++;
        }
        while ((t - 1) >= 0){
            t -= 1;
            seconds ++;
        }

        return String.format("%02:%02:%02" , hours, minutes, seconds);
    }
}
