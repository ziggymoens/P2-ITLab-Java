package exceptions.persistentie.repositories;

public class GebruikerRepositoryException extends RuntimeException {
    public GebruikerRepositoryException() {
        super();
    }

    public GebruikerRepositoryException(String message) {
        super(message);
    }

    public GebruikerRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public GebruikerRepositoryException(Throwable cause) {
        super(cause);
    }
}
