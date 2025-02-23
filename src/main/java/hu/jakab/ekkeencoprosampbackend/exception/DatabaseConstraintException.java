package hu.jakab.ekkeencoprosampbackend.exception;

public class DatabaseConstraintException extends RuntimeException {
    public DatabaseConstraintException(String message) {
        super(message);
    }
}
