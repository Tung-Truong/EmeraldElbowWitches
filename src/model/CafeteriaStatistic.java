package model;

public class CafeteriaStatistic {

    // Attributes
    private String foodType;
    private long numOfOrders;
    private long avgTime;


    // Constructors
    public CafeteriaStatistic(String food, long num, long avg){
        foodType = food;
        numOfOrders = num;
        avgTime = avg;
    }

    // getters--------------------------------------
    public String getFoodType() {
        return foodType;
    }

    public long getNumOfOrders() {
        return numOfOrders;
    }

    public long getAvgTime() {
        return avgTime;
    }
}
