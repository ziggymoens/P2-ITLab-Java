package exceptions.domein;

public class MediaException extends IllegalArgumentException {
    public MediaException() {
        super();
    }

    public MediaException(String s) {
        super(s);
    }

    public MediaException(String message, Throwable cause) {
        super(message, cause);
    }

    public MediaException(Throwable cause) {
        super(cause);
    }
}
