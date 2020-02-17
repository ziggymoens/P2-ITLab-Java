package domein;

import java.util.Objects;

public class SessieKalender {
    //Primairy key
    private String sessieKalenderId;

    public SessieKalender() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessieKalender that = (SessieKalender) o;
        return Objects.equals(sessieKalenderId, that.sessieKalenderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessieKalenderId);
    }
}
