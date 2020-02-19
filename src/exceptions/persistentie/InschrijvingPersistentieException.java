package exceptions.persistentie;

public class InschrijvingPersistentieException extends DomeinPersistentieException {
    public InschrijvingPersistentieException() {
        super();
    }

    public InschrijvingPersistentieException(String message) {
        super(message);
    }

    public InschrijvingPersistentieException(String message, Throwable cause) {
        super(message, cause);
    }

    public InschrijvingPersistentieException(Throwable cause) {
        super(cause);
    }
}
