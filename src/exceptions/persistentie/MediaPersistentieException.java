package exceptions.persistentie;

public class MediaPersistentieException extends DomeinPersistentieException {
    public MediaPersistentieException() {
        super();
    }

    public MediaPersistentieException(String message) {
        super(message);
    }

    public MediaPersistentieException(String message, Throwable cause) {
        super(message, cause);
    }

    public MediaPersistentieException(Throwable cause) {
        super(cause);
    }
}
