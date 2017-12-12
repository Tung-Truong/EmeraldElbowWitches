package model;

import java.util.HashMap;
import java.util.Set;

public class InterpreterStatistic {

    // Attributes
    String language;
    long numOfInterpreters = 0;
    long avgTimeTaken = 0;
    private static InterpreterStatistic stats = new InterpreterStatistic();
    private HashMap<String, long[]> values = new HashMap<String, long[]>();

    // Constructors
//    public InterpreterStatistic(String language, long numOfInterpreters, long avgTimeTaken){
//        this.language= language;
//        this.numOfInterpreters = numOfInterpreters;
//        this.avgTimeTaken = avgTimeTaken;
//    }
    private InterpreterStatistic() {

    }

    public static InterpreterStatistic getStats() {
        return stats;
    }

    // getters--------------------------------------
    public String getLanguage() {
        return language;
    }

    public long getNumOfInterpreters() {
        return numOfInterpreters;
    }

    public long getAvgTimeTaken() {
        return avgTimeTaken;
    }

    public Set<String> getLanguages() {
        return stats.values.keySet();
    }

    // Setters
    public void setData(String lang, long used, long taken) {
        if (values.containsKey(lang)) {
            values.replace(lang, new long[]{used, taken});
            avgTimeTaken = taken;
            numOfInterpreters = used;
        } else {
            values.put(lang, new long[]{used, taken});
            avgTimeTaken = taken;
            numOfInterpreters = used;
        }
    }


}
