package exceptions.persistentie;

public class LokaalPersistentieException extends DomeinPersistentieException {
    public LokaalPersistentieException() {
        super();
    }

    public LokaalPersistentieException(String message) {
        super(message);
    }

    public LokaalPersistentieException(String message, Throwable cause) {
        super(message, cause);
    }

    public LokaalPersistentieException(Throwable cause) {
        super(cause);
    }
}
