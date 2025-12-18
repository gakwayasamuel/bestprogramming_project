package exception;

/**
 * Data Exception Class
 *
 * Custom exception for data-related errors.
 * Used when there are issues with data loading, saving, or processing.
 */
public class DataException extends Exception {

    /**
     * Constructor with message
     * @param message Error message
     */
    public DataException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message Error message
     * @param cause Original exception
     */
    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
}
