package exceptions.persistentie.mappers;

public class LokaalMapperException extends RuntimeException{
    public LokaalMapperException() {
        super();
    }

    public LokaalMapperException(String message) {
        super(message);
    }

    public LokaalMapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public LokaalMapperException(Throwable cause) {
        super(cause);
    }
}
