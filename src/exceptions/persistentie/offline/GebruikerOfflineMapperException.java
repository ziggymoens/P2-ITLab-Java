package exceptions.persistentie.offline;

public class GebruikerOfflineMapperException extends RuntimeException {
    public GebruikerOfflineMapperException() {
        super();
    }

    public GebruikerOfflineMapperException(String message) {
        super(message);
    }

    public GebruikerOfflineMapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public GebruikerOfflineMapperException(Throwable cause) {
        super(cause);
    }
}
