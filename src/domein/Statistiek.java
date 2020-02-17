package domein;

import java.util.Objects;

public class Statistiek {
    //Primairy key
    private String statistiekId;

    public Statistiek() {
    }

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
}
