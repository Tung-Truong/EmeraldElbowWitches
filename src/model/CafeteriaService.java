package model;


import controller.Main;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CafeteriaService extends ServiceRequest {

    // Attributes
    private ArrayList<String> menu = new ArrayList<String>();
    private HashMap<String, long[]> valItem = new HashMap<String, long[]>();
    private String itemSold;
    private int soldItems = 0;

    // TODO: have each menu item have an associated number of items sold and who delivered them

    // Constructor
    public CafeteriaService() {
        classType = this.getClass().toString();

        menu.add("Cake");
        menu.add("Noodles");
        menu.add("Tea");
        menu.add("Pie");
        // fill in more food things later

        for (String s : menu){
            valItem.put(s, new long[2]);
        }

    }

    // Getters
    public ArrayList<String> getMenu() {
        return this.menu;
    }

    public int getSoldItems() {
        return soldItems;
    }

    // Setters
    public void setMenu(ArrayList<String> items){
        this.menu = items;
    }

    // Methods
    public void addMenuItem(String food){
        menu.add(food);
    }

    public void removeMenuItem(String food){
        menu.remove(food);
    }

    public void sellItem(){
        soldItems++;
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

            valItem.get(food)[0] += 1;

            tempUsed = valItem.get(food)[0];
            tempAvg = valItem.get(food)[1];

            valItem.get(food)[1] = ((tempAvg * (tempUsed - 1)) + diffSeconds) / tempUsed;

            // Food, Number of requests for that food, Average time taken to fulfill request for that food
            try {
                AddDB.addCafeteriaStatistic(new CafeteriaStatistic(food, valItem.get(food)[0], valItem.get(food)[1]));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
