package model;

public class JanitorStatistic {

    // Attributes
    int numOfSupplies = 0;
    long avgTime = 0;
    // int requestsMade = 0;
    private static JanitorStatistic soap = new JanitorStatistic();


    // Constructors
//    public JanitorStatistic(int numOfSupplies, long avgTimeTaken){
//        this.numOfSupplies = numOfSupplies;
//        avgTime = avgTimeTaken;
//    }
    private JanitorStatistic(){  }

    public static JanitorStatistic getSoap(){
        return soap;
    }

    // getters--------------------------------------
    public int getNumOfSupplies() {
        return numOfSupplies;
    }

    public long getAvgTime() {
        return avgTime;
    }

//    public int getRequestsMade(){
//        return requestsMade;
//    }

    // Setters
    public void setNumOfSupplies(int numOfSupplies) {
        this.numOfSupplies = numOfSupplies;
    }

    public void setAvgTime(long avgTime) {
        this.avgTime = avgTime;
    }

//    public void setRequestsMade(int requestsMade) {
//        this.requestsMade = requestsMade;
//    }
}
