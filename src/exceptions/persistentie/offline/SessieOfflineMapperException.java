package exceptions.persistentie.offline;

public class SessieOfflineMapperException extends RuntimeException {
    public SessieOfflineMapperException() {
        super();
    }

    public SessieOfflineMapperException(String message) {
        super(message);
    }

    public SessieOfflineMapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessieOfflineMapperException(Throwable cause) {
        super(cause);
    }
}
