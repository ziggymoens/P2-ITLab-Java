package exceptions;

public class GebruikerException extends IllegalArgumentException {
    public GebruikerException() {
        super();
    }

    public GebruikerException(String s) {
        super(s);
    }

    public GebruikerException(String message, Throwable cause) {
        super(message, cause);
    }

    public GebruikerException(Throwable cause) {
        super(cause);
    }
}
