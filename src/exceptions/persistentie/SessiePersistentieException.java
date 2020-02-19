package exceptions.persistentie;

public class SessiePersistentieException extends DomeinPersistentieException {
    public SessiePersistentieException() {
        super();
    }

    public SessiePersistentieException(String message) {
        super(message);
    }

    public SessiePersistentieException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessiePersistentieException(Throwable cause) {
        super(cause);
    }
}
