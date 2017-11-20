package model;

public class CafeteriaService extends ServiceRequest {

    // Attributes
    private String[] menu = {"Cake", "French Fries", "Soup",
                            "Water", "Club Sandwich"};
    private String[] emails;

    // Constructor
    public CafeteriaService() {
        // TODO: get emails from database and set them here
    }
    // Getters

    public String[] getMenu() {
        return this.menu;
    }

    public String[] getEmails() {
        return this.emails;
    }

    // Setters
    public void setMenu(String[] items){
        this.menu = items;
    }
    public void setEmails(String[] emails){
        this.emails = emails;
    }
}
