package io.shashanksm.pms.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler is a RestControllerAdvice that provides a centralized
 * and uniform way of handling exceptions across all controllers in the application.
 *
 * It intercepts exceptions thrown by controllers and translates them into meaningful
 * HTTP responses, which helps to keep controller methods clean and focused on
 * business logic.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions related to method argument validation failures (e.g., @Valid annotation).
     *
     * @param ex The exception thrown when method arguments are not valid.
     * @return A ResponseEntity with a map of validation errors and a 400 Bad Request status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles a custom or general Exception class.
     *
     * @param ex The exception that was thrown.
     * @param request The web request during which the exception occurred.
     * @return A ResponseEntity with a specific error message and a 500 Internal Server Error status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        // Log the exception for debugging purposes.
        System.err.println("An unexpected error occurred: " + ex.getMessage());

        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "An unexpected error occurred.");
        errorDetails.put("message", ex.getLocalizedMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

