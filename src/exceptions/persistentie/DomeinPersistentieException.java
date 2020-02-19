package exceptions.persistentie;

public class DomeinPersistentieException extends RuntimeException {
    public DomeinPersistentieException() {
        super();
    }

    public DomeinPersistentieException(String message) {
        super(message);
    }

    public DomeinPersistentieException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomeinPersistentieException(Throwable cause) {
        super(cause);
    }
}
