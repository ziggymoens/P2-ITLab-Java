package exceptions.domein;

public class LokaalException extends IllegalArgumentException {
    public LokaalException() {
        super();
    }

    public LokaalException(String s) {
        super(s);
    }

    public LokaalException(String message, Throwable cause) {
        super(message, cause);
    }

    public LokaalException(Throwable cause) {
        super(cause);
    }
}
