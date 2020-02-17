package exceptions.persistentie.mappers;

public class GebruikerMapperException extends RuntimeException{
    public GebruikerMapperException() {
        super();
    }

    public GebruikerMapperException(String message) {
        super(message);
    }

    public GebruikerMapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public GebruikerMapperException(Throwable cause) {
        super(cause);
    }
}
