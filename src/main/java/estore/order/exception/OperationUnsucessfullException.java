package estore.order.exception;


public class OperationUnsucessfullException extends RuntimeException{
    public OperationUnsucessfullException() {
    }

    public OperationUnsucessfullException(String message) {
        super(message);
    }
}
