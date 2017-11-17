package model;

public class InvalidNodeException extends Exception {

    // subclass of Exception that provides ease of use for locating specific problems related to nodes
    public InvalidNodeException() {
        super();
    }

    public InvalidNodeException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidNodeException(Throwable cause) {
        super(cause);
    }

    public InvalidNodeException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
