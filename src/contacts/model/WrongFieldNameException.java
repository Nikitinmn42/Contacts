package contacts.model;


/**
 * Represents exception, which occur on trying to access field, which is not accessible for that record.
 */
public class WrongFieldNameException extends Exception {
    public WrongFieldNameException(String message) {
        super(message);
    }
}
