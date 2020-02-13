package exceptions;

public class HerinneringException extends IllegalArgumentException {
    public HerinneringException() {
        super();
    }

    public HerinneringException(String s) {
        super(s);
    }

    public HerinneringException(String message, Throwable cause) {
        super(message, cause);
    }

    public HerinneringException(Throwable cause) {
        super(cause);
    }
}
