package exceptions.persistentie;

public class SessieKalenderPersistentieException extends DomeinPersistentieException {
    public SessieKalenderPersistentieException() {
        super();
    }

    public SessieKalenderPersistentieException(String message) {
        super(message);
    }

    public SessieKalenderPersistentieException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessieKalenderPersistentieException(Throwable cause) {
        super(cause);
    }
}
