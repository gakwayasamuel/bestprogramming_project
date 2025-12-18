package exception;

/**
 * Validation Exception Class
 *
 * Custom exception for validation errors.
 * Used when user input or data doesn't meet validation rules.
 */
public class ValidationException extends Exception {

    /**
     * Constructor with message
     * @param message Error message
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message Error message
     * @param cause Original exception
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}