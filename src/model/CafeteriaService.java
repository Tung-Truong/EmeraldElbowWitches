package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CafeteriaService extends ServiceRequest {

    // Attributes
    private ArrayList<String> menu = new ArrayList<String>();
    private CafeteriaStatistic caff = CafeteriaStatistic.getCafe();
    private String itemSold;

    // TODO: have each menu item have an associated number of items sold and who delivered them

    // Constructor
    public CafeteriaService() {

        menu.add("Cake");
        menu.add("Noodles");
        menu.add("Tea");
        menu.add("Pie");
        // fill in more food things later

    }

    // Getters
    public ArrayList<String> getMenu() {
        return this.menu;
    }

    public String getItemSold() { return itemSold; }

    // Setters
    public void setMenu(ArrayList<String> items){
        this.menu = items;
    }

    public void setItemSold(String item) {
        itemSold = item;
    }

    // Methods
    public void addMenuItem(String food){
        menu.add(food);
    }

    public void removeMenuItem(String food){
        menu.remove(food);
    }

    public void generateReport(){
        /*
            Information required:
            - How long did it  take for this request to be fulfilled
            - What foods were delivered/ordered
         */
        String food = "";
        if(!isActive()) {
            for (String item : menu) {
                if (item.equals(itemSold)) {
                    food = item;
                }
            }

            long timeSent = sent.getTime();
            long timeReceived = received.getTime();

            long diffSeconds = (timeReceived - timeSent) / 1000;

            long tempUsed = 0;
            long tempAvg = 0;
            long newAvg = 0;

            tempUsed = caff.getNumOfOrders() + 1;
            tempAvg = caff.getAvgTime();

            newAvg = ((tempAvg * (tempUsed - 1)) + diffSeconds) / tempUsed;

            // Food, Number of requests for that food, Average time taken to fulfill request for that food
            try {
                caff.setData(food, tempUsed, newAvg);
                AddDB.addCafeteriaStatistic(caff);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
