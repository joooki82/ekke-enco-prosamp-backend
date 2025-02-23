package hu.jakab.ekkeencoprosampbackend.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle resource not found errors
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                Map.of("error", "Resource Not Found", "message", ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    // Handle validation errors
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(
                Map.of("error", "Validation Error", "message", ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    // Handle duplicate resource errors (e.g., unique constraints)
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Object> handleDuplicateResourceException(DuplicateResourceException ex) {
        return new ResponseEntity<>(
                Map.of("error", "Duplicate Resource", "message", ex.getMessage()),
                HttpStatus.CONFLICT // 409 Conflict
        );
    }

    // Handle database constraint violations
    @ExceptionHandler(DatabaseConstraintException.class)
    public ResponseEntity<Object> handleDatabaseConstraintException(DatabaseConstraintException ex) {
        return new ResponseEntity<>(
                Map.of("error", "Database Constraint Violation", "message", ex.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    // Handle generic validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ResponseEntity<>(
                Map.of("error", "Validation Failed", "details", errors),
                HttpStatus.BAD_REQUEST
        );
    }

    // Handle general database integrity violation
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = "Database constraint violation occurred.";
        if (ex.getRootCause() != null) {
            message = ex.getRootCause().getMessage();
        }

        return new ResponseEntity<>(
                Map.of("error", "Constraint Violation", "message", message),
                HttpStatus.CONFLICT
        );
    }

    // Handle other unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(
                Map.of("error", "Internal Server Error", "message", ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex) {
        return new ResponseEntity<>(
                Map.of("error", "Missing Path Variable", "message", ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

}
