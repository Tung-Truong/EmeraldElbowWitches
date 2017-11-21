package model;

import java.util.ArrayList;

public class CafeteriaService extends ServiceRequest {

    // Attributes
    private ArrayList<String> menu = new ArrayList<String>();
    private ArrayList<String> emails;

    // Constructor
    public CafeteriaService() {
        menu.add("Cake");
        menu.add("Noodles");
        menu.add("Tea");
        menu.add("Pie");

        // TODO: get emails from database and set them here
    }

    // Getters
    public ArrayList<String> getMenu() {
        return this.menu;
    }

    public ArrayList<String> getEmails() {
        return this.emails;
    }

    // Setters
    public void setMenu(ArrayList<String> items){
        this.menu = items;
    }

    // Methods
    public void updateEmails(ArrayList<String> emails){
        // TODO: get emails from database
    }

    public void addMenuItem(String food){
        menu.add(food);
    }

    public void removeMenuItem(String food){
        menu.remove(food);
    }
}
