package model;

public class Employee {
    private String email;
    private String firstName;
    private String lastName;
    private String department;
    private String language;
    private String availability;

    public Employee(String email, String firstName, String lastName, String department, String language, String availability){     // constructor for edges
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.language = language;
        this.availability = availability;
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
        return availability;
    }
}
