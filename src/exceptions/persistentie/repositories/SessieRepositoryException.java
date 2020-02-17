package exceptions.persistentie.repositories;

public class SessieRepositoryException extends RuntimeException{
    public SessieRepositoryException() {
        super();
    }

    public SessieRepositoryException(String message) {
        super(message);
    }

    public SessieRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessieRepositoryException(Throwable cause) {
        super(cause);
    }
}
