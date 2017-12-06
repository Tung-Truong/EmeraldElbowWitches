package model;

public class ServiceContext {

    public ServiceContext(){

    }

    /**
     * Determines the type of service request from a string.
     * @param service String for the type of service.
     * @return ServiceRequest Sends the corresponding request.
     */
    public static ServiceRequest selectService(String service){
        if(service.toLowerCase() == "janitor"){
            return new JanitorService();
        }
        else if(service.toLowerCase() == "interpreter"){
            return new InterpreterService();
        }
        else {
            return new CafeteriaService();
        }
    }
}
