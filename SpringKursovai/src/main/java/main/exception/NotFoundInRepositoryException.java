package main.exception;

public class NotFoundInRepositoryException extends RuntimeException{
    public NotFoundInRepositoryException(String message) {
        super(message);
    }
}
