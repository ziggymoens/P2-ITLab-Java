package exceptions.domein;

public class SessieException extends IllegalArgumentException{
    public SessieException() {
        super();
    }

    public SessieException(String s) {
        super(s);
    }

    public SessieException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessieException(Throwable cause) {
        super(cause);
    }
}
