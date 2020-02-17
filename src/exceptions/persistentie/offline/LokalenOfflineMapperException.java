package exceptions.persistentie.offline;

public class LokalenOfflineMapperException extends RuntimeException{
    public LokalenOfflineMapperException() {
        super();
    }

    public LokalenOfflineMapperException(String message) {
        super(message);
    }

    public LokalenOfflineMapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public LokalenOfflineMapperException(Throwable cause) {
        super(cause);
    }
}
