package model;

import controller.Main;

import java.util.ArrayList;

public class CafeteriaService extends ServiceRequest {

    // Attributes
    private ArrayList<String> menu = new ArrayList<String>();
    private ArrayList<String> emails = new ArrayList<String>();

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
}
