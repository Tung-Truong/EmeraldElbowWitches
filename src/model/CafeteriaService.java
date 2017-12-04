package model;

import controller.Main;

import java.util.ArrayList;

public class CafeteriaService extends ServiceRequest {

    // Attributes
    private ArrayList<String> menu = new ArrayList<String>();
    private ArrayList<String> emails = new ArrayList<String>();
    private int soldItems = 0;

    // TODO: have each menu item have an associated number of items sold and who delivered them

    // Constructor
    public CafeteriaService() {
        menu.add("Cake");
        menu.add("Noodles");
        menu.add("Tea");
        menu.add("Pie");

        for(Employee e: Main.employees){
            emails.add(e.getEmail());
        }

    }

    // Getters
    public ArrayList<String> getMenu() {
        return this.menu;
    }

    public ArrayList<String> getEmails() {
        return this.emails;
    }

    public int getSoldItems() {
        return soldItems;
    }

    // Setters
    public void setMenu(ArrayList<String> items){
        this.menu = items;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
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
        //System.out.println("From Cafeteria: ");
        return "    Items Ordered: " + soldItems;
    }
}
