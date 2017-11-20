package controller;

import model.CafeteriaService;
import model.InterpreterService;
import model.JanitorService;
import model.ServiceRequest;

public class ServiceController {
    // Attributes
    private String serviceNeeded;
    private ServiceRequest service;

    // Constructor
    public ServiceController (String needed){
        this.serviceNeeded = needed;

        if(needed.toUpperCase() == "INTERPRETER"){
            service = new InterpreterService();
        } else if(needed.toUpperCase() == "JANITOR"){
            service = new JanitorService();
        } else {
            service = new CafeteriaService();
        }
    }

    // Getters
    public String getServiceNeeded() {
        return this.serviceNeeded;
    }

    public ServiceRequest getService() {
        return this.service;
    }

    // Setters
    public void setServiceNeeded(String service){
        this.serviceNeeded = service;
    }

    public void setService(ServiceRequest serve){

    }
}
