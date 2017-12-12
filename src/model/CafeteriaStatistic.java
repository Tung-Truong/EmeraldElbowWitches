package model;

import java.util.HashMap;
import java.util.Set;

public class CafeteriaStatistic {

    // Attributes
    private String foodType;
    private long numOfOrders = 0;
    private long avgTime = 0;
    private static CafeteriaStatistic cafe = new CafeteriaStatistic();
    private HashMap<String, long[]> values = new HashMap<String, long[]>();

    // Constructors
//    public CafeteriaStatistic(String food, long num, long avg){
//        foodType = food;
//        numOfOrders = num;
//        avgTime = avg;
//    }
    private CafeteriaStatistic() {
    }

    public static CafeteriaStatistic getCafe() {
        return cafe;
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

    public Set<String> getMenu() {
        return cafe.values.keySet();
    }

    public void setData(String food, long used, long taken) {
        if (values.containsKey(food)) {
            values.replace(food, new long[]{used, taken});
            avgTime = taken;
            numOfOrders = used;
        } else {
            values.put(food, new long[]{used, taken});
            avgTime = taken;
            numOfOrders = used;
        }
    }

}
