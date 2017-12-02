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

    /**
     * Adds food item on the menu.
     * @param food Item menu to be added.
     */
    public void addMenuItem(String food){
        menu.add(food);
    }

    /**
     * Removes food item on the menu.
     * @param food Item menu to be removed.
     */
    public void removeMenuItem(String food){
        menu.remove(food);
    }

    /**
     * Counts how many items have been sold.
     */
    public void sellItem(){
        soldItems++;
    }

    /**
     * Generates a report, listing the number of items the order contains.
     */
    public void generateReport(){
        System.out.println("From Cafeteria: ");
        System.out.println("    Items Ordered: " + soldItems);
    }
}
