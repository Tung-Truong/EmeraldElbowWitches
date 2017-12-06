package model;

public class JanitorStatistic {

    // Attributes
    int numOfSupplies;
    long avgTime;


    // Constructors
    public JanitorStatistic(int numOfSupplies, long avgTimeTaken){
        this.numOfSupplies = numOfSupplies;
        avgTime = avgTimeTaken;
    }

    // getters--------------------------------------
    public int getNumOfSupplies() {
        return numOfSupplies;
    }

    public long getAvgTime() {
        return avgTime;
    }

    // Setters
    public void setNumOfSupplies(int numOfSupplies) {
        this.numOfSupplies = numOfSupplies;
    }

}
