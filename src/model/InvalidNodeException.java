package model;

public class InvalidNodeException extends Exception {

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
