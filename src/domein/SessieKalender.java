package domein;

import java.io.Serializable;
import java.util.Objects;

public class SessieKalender implements Serializable {
    //region Variabelen
    //Primairy key
    private String sessieKalenderId;
    //endregion

    //region Constructor
    public SessieKalender() {
    }
    //endregion

    //region Setters
    public void setSessieKalenderId(String sessieKalenderId) {
        this.sessieKalenderId = sessieKalenderId;
    }
    //endregion

    //region Getters

    //endregion

    //region Equals & hashcode
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
    //endregion
}
