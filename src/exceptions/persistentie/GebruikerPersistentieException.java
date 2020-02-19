package exceptions.persistentie;

public class GebruikerPersistentieException extends DomeinPersistentieException {
    public GebruikerPersistentieException() {
        super();
    }

    public GebruikerPersistentieException(String message) {
        super(message);
    }

    public GebruikerPersistentieException(String message, Throwable cause) {
        super(message, cause);
    }

    public GebruikerPersistentieException(Throwable cause) {
        super(cause);
    }
}
