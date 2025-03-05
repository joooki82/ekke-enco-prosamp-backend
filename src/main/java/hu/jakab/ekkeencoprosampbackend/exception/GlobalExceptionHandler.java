package hu.jakab.ekkeencoprosampbackend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoResourceFoundException(NoResourceFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Resource not found");
        errorResponse.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        logger.error("Resource not found: {} - Request: {}", ex.getMessage(), request.getDescription(false));
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Resource Not Found")
                .message(ex.getMessage())
                .code("ERR_RESOURCE_NOT_FOUND")
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }


    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResourceException(DuplicateResourceException ex, WebRequest request) {
        logger.error("Duplicate resource error: {} - Request: {}", ex.getMessage(), request.getDescription(false));
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Duplicate Resource")
                .message(ex.getMessage())
                .code("ERR_DUPLICATE_RESOURCE")
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        logger.error("Validation failed: {} - Request: {}", errors, request.getDescription(false));
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Validation Failed")
                .message("Validation error occurred")
                .code("ERR_VALIDATION_FAILED")
                .timestamp(Instant.now())
                .details(errors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        logger.error("Database constraint violation: {} - Request: {}", ex.getMessage(), request.getDescription(false));
        String rootMessage = ex.getRootCause() != null ? ex.getRootCause().getMessage() : "Database constraint violation occurred.";
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Constraint Violation")
                .message(rootMessage)
                .code("ERR_CONSTRAINT_VIOLATION")
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        logger.warn("Access denied: {} - Request: {}", ex.getMessage(), request.getDescription(false));
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Access Denied")
                .message("You do not have permission to perform this action.")
                .code("ERR_ACCESS_DENIED")
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Internal Server Error: {} - Request: {}", ex.getMessage(), request.getDescription(false), ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Internal Server Error")
                .message("An unexpected error occurred. Please contact support.")
                .code("ERR_INTERNAL_SERVER")
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex, WebRequest request) {

        logger.warn("Method Not Allowed: {} - Supported Methods: {} - Request: {}",
                ex.getMethod(), ex.getSupportedHttpMethods(), request.getDescription(false));

        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Method Not Allowed")
                .message("Request method '" + ex.getMethod() + "' is not supported. Use one of: " + ex.getSupportedHttpMethods())
                .code("ERR_METHOD_NOT_ALLOWED")
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException ex, WebRequest request) {

        logger.warn("Missing request parameter: {} - Request: {}", ex.getParameterName(), request.getDescription(false));

        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Missing Request Parameter")
                .message("Required parameter '" + ex.getParameterName() + "' is missing.")
                .code("ERR_MISSING_PARAMETER")
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, WebRequest request) {

        logger.error("Malformed JSON request: {} - Request: {}", ex.getMessage(), request.getDescription(false));

        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Malformed JSON Request")
                .message("Invalid JSON format in request body. Please check the structure.")
                .code("ERR_INVALID_JSON")
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
