package exceptions.persistentie.repositories;

public class LokalenRepositoryException extends RuntimeException {
    public LokalenRepositoryException() {
        super();
    }

    public LokalenRepositoryException(String message) {
        super(message);
    }

    public LokalenRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public LokalenRepositoryException(Throwable cause) {
        super(cause);
    }
}
