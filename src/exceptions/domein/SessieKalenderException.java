package exceptions.domein;

public class SessieKalenderException extends IllegalArgumentException {
    public SessieKalenderException() {
        super();
    }

    public SessieKalenderException(String s) {
        super(s);
    }

    public SessieKalenderException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessieKalenderException(Throwable cause) {
        super(cause);
    }
}
