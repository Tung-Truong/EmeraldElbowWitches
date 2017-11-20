package controller;

import model.ServiceRequest;

public class ServiceController {
    // Attributes
    private String serviceNeeded;
    private ServiceRequest service;

    // Constructor
    public ServiceController (){

    }

    // Getters
    public String getServiceNeeded(){
        return this.serviceNeeded;
    }

    public ServiceRequest getService() {
        return this.service;
    }

    // Setters
    public void setServiceNeeded(String needed){
        this.serviceNeeded = needed;
    }

    public void setService(ServiceRequest serve){
        this.service = serve;
    }
}
