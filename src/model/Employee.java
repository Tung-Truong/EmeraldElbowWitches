package model;

public class Employee {

    // Attributes
    private String email;
    private String firstName;
    private String lastName;
    private String department;
    private String language;
    private String available;
    private String username;
    private String password;
    private int id;

    // Constructor
    public Employee(){
    }
    public Employee(String email, String firstName, String lastName, String department, String language, String
            available, String username, String password){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.language = language;
        this.available = available;
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
