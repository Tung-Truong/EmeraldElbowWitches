package HealthAPI;

public class ServiceException extends Exception {

    // subclass of Exception that provides ease of use for locating specific problems related to nodes
    public ServiceException() {
        super();
    }

    public ServiceException(String errorMessage) {
        super(errorMessage);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}