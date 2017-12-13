package model;

public class ServiceContext {

    public ServiceContext() {

    }

    public static ServiceRequest selectService(String service) {
        if (service.toLowerCase() == "janitor") {
            return new JanitorService();
        } else if (service.toLowerCase() == "interpreter") {
            return new InterpreterService();
        } else {
            return new CafeteriaService();
        }
    }
}
