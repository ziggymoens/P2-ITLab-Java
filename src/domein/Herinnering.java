package domein;

import exceptions.domein.HerinneringException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Herinnering extends Aankondiging implements Serializable {
    private static final long serialVersionUID = 6099128486423253923L;

    //region Variabelen
    //Primairy key
    private String herinneringsId;

    private int dagenVooraf;
    //endregion

    //region Constructor
    public Herinnering(String herinneringsId, int dagenVooraf, Gebruiker gebruiker, Sessie sessie, LocalDateTime aangemaakt, String inhoud) {
        super(gebruiker, sessie, aangemaakt, inhoud);
        setHerinneringsId(herinneringsId);
        setDagenVooraf(dagenVooraf);
    }
    //endregion

    //region Setters
    public void setHerinneringsId(String herinneringsId) {
        if(herinneringsId == null || herinneringsId.isBlank())
            throw new HerinneringException();
        this.herinneringsId = herinneringsId;
    }

    private void setDagenVooraf(int dagenVooraf) {
        if (dagenVooraf < 0)
            throw new HerinneringException();
        this.dagenVooraf = dagenVooraf;
    }
    //endregion

    //region Getters
    public int getDagenVooraf() {
        return dagenVooraf;
    }

    public String getInhoud() {
        return super.getInhoud();
    }

    public String getHerinneringsId() {
        return herinneringsId;
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

    //region toString

    @Override
    public String toString() {
        return String.format("Herinnering: %s%nDagen vooraf: %d%nGebruiker: %s%nGeplaatst op: %s%nTekst: %s%n", herinneringsId, dagenVooraf, getPublicist().getNaam(), getPublicatiedatum().toString(), getInhoud() );
    }
    //endregion
}
