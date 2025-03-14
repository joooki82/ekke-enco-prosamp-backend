package hu.jakab.ekkeencoprosampbackend.exception;

public class CustomFileNotFoundException extends RuntimeException {
    public CustomFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
