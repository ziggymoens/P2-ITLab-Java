package exceptions;

public class StatistiekException extends IllegalArgumentException {
    public StatistiekException() {
        super();
    }

    public StatistiekException(String s) {
        super(s);
    }

    public StatistiekException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatistiekException(Throwable cause) {
        super(cause);
    }
}
