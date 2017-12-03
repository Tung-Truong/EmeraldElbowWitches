package model;

public class Employee {

    // Attributes
    private String email;
    private String firstName;
    private String lastName;
    private String department;
    private String language;
    private String available;
    private int id;

    // Constructor
    public Employee(){
    }
    public Employee(String email, String firstName, String lastName, String department, String language, String available){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.language = language;
        this.available = available;
        id = email.hashCode();
    }

    // getters--------------------------------------
    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDepartment() {
        return department;
    }

    public String getLanguage() {
        return language;
    }

    public String getAvailability() {
        return available;
    }

    public int getId(){
        return id;
    }

    // Setters
    public void setEmail(String email){
        this.email = email;
        id = email.hashCode();
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setDepartment(String department){
        this.department = department;
    }

    public void setLanguage(String language){
        this.language = language;
    }

    public void setAvailability(String available){
        this.available = available;
    }
}
