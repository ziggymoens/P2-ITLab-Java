package exceptions.persistentie;

public class AankondigingPersistentieException extends DomeinPersistentieException {
    public AankondigingPersistentieException() {
        super();
    }

    public AankondigingPersistentieException(String message) {
        super(message);
    }

    public AankondigingPersistentieException(String message, Throwable cause) {
        super(message, cause);
    }

    public AankondigingPersistentieException(Throwable cause) {
        super(cause);
    }
}
