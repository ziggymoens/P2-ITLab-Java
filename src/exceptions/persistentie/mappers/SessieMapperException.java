package exceptions.persistentie.mappers;

public class SessieMapperException extends RuntimeException {
    public SessieMapperException() {
        super();
    }

    public SessieMapperException(String message) {
        super(message);
    }

    public SessieMapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessieMapperException(Throwable cause) {
        super(cause);
    }
}
