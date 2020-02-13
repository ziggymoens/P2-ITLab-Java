package exceptions;

public class InschrijvingException extends IllegalArgumentException {
    public InschrijvingException() {
        super();
    }

    public InschrijvingException(String s) {
        super(s);
    }

    public InschrijvingException(String message, Throwable cause) {
        super(message, cause);
    }

    public InschrijvingException(Throwable cause) {
        super(cause);
    }
}
