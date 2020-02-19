package exceptions.persistentie;

public class FeedbackPersistentieException extends DomeinPersistentieException {
    public FeedbackPersistentieException() {
        super();
    }

    public FeedbackPersistentieException(String message) {
        super(message);
    }

    public FeedbackPersistentieException(String message, Throwable cause) {
        super(message, cause);
    }

    public FeedbackPersistentieException(Throwable cause) {
        super(cause);
    }
}
