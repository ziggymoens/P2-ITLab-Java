package exceptions.persistentie;

public class HerinneringPersistentieException extends DomeinPersistentieException {
    public HerinneringPersistentieException() {
        super();
    }

    public HerinneringPersistentieException(String message) {
        super(message);
    }

    public HerinneringPersistentieException(String message, Throwable cause) {
        super(message, cause);
    }

    public HerinneringPersistentieException(Throwable cause) {
        super(cause);
    }
}
