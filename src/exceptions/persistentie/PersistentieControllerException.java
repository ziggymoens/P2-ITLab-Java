package exceptions.persistentie;

public class PersistentieControllerException extends RuntimeException {
    public PersistentieControllerException() {
        super();
    }

    public PersistentieControllerException(String message) {
        super(message);
    }

    public PersistentieControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistentieControllerException(Throwable cause) {
        super(cause);
    }
}
