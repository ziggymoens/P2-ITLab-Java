package domein;

import java.io.Serializable;
import java.util.Objects;

public class Herinnering implements Serializable {
    //region Variabelen
    //Primairy key
    private String herinneringsId;

    private int dagenVooraf;
    private String inhoud;
    //endregion

    //region Constructor
    public Herinnering(int dagenVooraf, String inhoud) {
        this.dagenVooraf = dagenVooraf;
        this.inhoud = inhoud;
    }
    //endregion

    //region Setters
    public void setHerinneringsId(String herinneringsId) {
        this.herinneringsId = herinneringsId;
    }

    private void setDagenVooraf(int dagenVooraf) {
        this.dagenVooraf = dagenVooraf;
    }

    private void setInhoud(String inhoud) {
        this.inhoud = inhoud;
    }
    //endregion

    //region Getters
    public int getDagenVooraf() {
        return dagenVooraf;
    }

    public String getInhoud() {
        return inhoud;
    }
    //endregion

    //region Equals & Hashcode
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
    //endregion
}
