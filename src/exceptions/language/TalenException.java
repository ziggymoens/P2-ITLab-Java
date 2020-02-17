package exceptions.language;

public class TalenException extends RuntimeException {
    public TalenException() {
        super();
    }

    public TalenException(String message) {
        super(message);
    }

    public TalenException(String message, Throwable cause) {
        super(message, cause);
    }

    public TalenException(Throwable cause) {
        super(cause);
    }
}
