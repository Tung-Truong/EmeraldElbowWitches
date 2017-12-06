package model;

public class InterpreterStatistic {

    // Attributes
    String language;
    long numOfInterpreters;
    long avgTimeTaken;

    // Constructors
    public InterpreterStatistic(String language, long numOfInterpreters, long avgTimeTaken){
        this.language= language;
        this.numOfInterpreters = numOfInterpreters;
        this.avgTimeTaken = avgTimeTaken;
    }

    // getters--------------------------------------
    public String getLanguage() { return language; }

    public long getNumOfInterpreters() {
        return numOfInterpreters;
    }

    public long getAvgTimeTaken() {
        return avgTimeTaken;
    }

    // Setters
    public void setLanguage(String language) {
        this.language = language;
    }

    public void setNumOfInterpreters(long numOfSupplies) {
        this.numOfInterpreters = numOfInterpreters;
    }

    public void setAvgTimeTaken(long avgTimeTaken) {
        this.avgTimeTaken = avgTimeTaken;
    }


}
