package model;

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

    public String generateReport(){
        /*
            Information required:
            - How long did it  take for this request to be fulfilled
            - What foods were delivered/ordered
         */
        if (!isActive()){
            String report = "Item Sold: " + itemSold;

            long diff = 0;

            long timeSent = sent.getTime();
            long timeReceived = received.getTime();

            long diffSeconds = (timeReceived - timeSent) / 1000;

            // This part increments the number of interpreters used for the language and time taken for this interpreter

            valItem.get(itemSold)[0] += 1;
            valItem.get(itemSold)[1] += diffSeconds;
            diff = valItem.get(itemSold)[1]/valItem.get(itemSold)[0];
            report.concat(itemSold + "s Sold: " + valItem.get(itemSold)[0] + "\n" + "Average Time Taken: " + findTime(diff));

            return report;
        }
        else {
            return "This request has yet to be resolved";
        }
    }
}
