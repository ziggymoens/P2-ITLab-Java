package domein;

import java.io.Serializable;
import java.util.Objects;

public class Statistiek implements Serializable {
    private static final long serialVersionUID = 6464019936069166219L;

    //region Variabelen
    //Primairy key
    private String statistiekId;
    //endregion

    //region Constructor
    public Statistiek() {
    }
    //endregion

    //region Setters

    public void setStatistiekId(String statistiekId) {
        this.statistiekId = statistiekId;
    }

    //endregion

    //region Getters

    //endregion

    //region Equals & Hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistiek that = (Statistiek) o;
        return Objects.equals(statistiekId, that.statistiekId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statistiekId);
    }
    //endregion
}
