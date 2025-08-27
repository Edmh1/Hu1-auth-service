package co.com.pragma.model.user.commons;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}