package exceptions;

public class AankondigingException extends  IllegalArgumentException {
    public AankondigingException() {
        super();
    }

    public AankondigingException(String s) {
        super(s);
    }

    public AankondigingException(String message, Throwable cause) {
        super(message, cause);
    }

    public AankondigingException(Throwable cause) {
        super(cause);
    }
}