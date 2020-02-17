package domein;

import java.util.Objects;

public class Herinnering {
    //Primairy key
    private String herinneringsId;

    private int dagenVooraf;
    private String inhoud;

    public int getDagenVooraf() {
        return dagenVooraf;
    }

    public String getInhoud() {
        return inhoud;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Herinnering that = (Herinnering) o;
        return Objects.equals(herinneringsId, that.herinneringsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(herinneringsId);
    }
}
