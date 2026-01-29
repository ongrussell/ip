package duke;

/**
 * Represents an application-specific exception for Suu.
 * Used to signal invalid commands, invalid input formats, or storage-related errors.
 */
public class SuuException extends Exception {

    /**
     * Creates a new {@code SuuException} with the specified detail message.
     *
     * @param message Detail message describing the error.
     */
    public SuuException(String message) {
        super(message);
    }

}
