package exceptions;

public class FeedbackException extends IllegalArgumentException {
    public FeedbackException() {
        super();
    }

    public FeedbackException(String s) {
        super(s);
    }

    public FeedbackException(String message, Throwable cause) {
        super(message, cause);
    }

    public FeedbackException(Throwable cause) {
        super(cause);
    }
}
