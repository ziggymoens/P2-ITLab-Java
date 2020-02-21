package domein;

import domein.interfacesDomein.IHerinnering;
import exceptions.domein.HerinneringException;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
//@Table(name = "herinnering")
public class Herinnering implements IHerinnering {
    //region Variabelen
    //Primairy key
    @Id
    private int herinneringsId;

    private int dagenVooraf;

    /*
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @MapsId
    private Aankondiging aankondiging;
     */


    //endregion

    //region Constructor
    protected Herinnering(){ }

    public Herinnering( int dagenVooraf) {
        //setHerinneringsId(herinneringsId);
        setDagenVooraf(dagenVooraf);
    }
    //endregion

    //region Setters
    /*public void setHerinneringsId(String herinneringsId) {
        if(herinneringsId == null || herinneringsId.isBlank())
            throw new HerinneringException();
        this.herinneringsId = herinneringsId;
    }*/

    private void setDagenVooraf(int dagenVooraf) {
        if (Arrays.stream(HerinneringTijdstippen.values()).filter(e -> e.getDagen() == dagenVooraf).findFirst().orElse(null) == null)
            throw new HerinneringException();
        this.dagenVooraf = dagenVooraf;
    }
    //endregion

    //region Getters
    public int getDagenVooraf() {
        return dagenVooraf;
    }

    public int getHerinneringsId() {
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
        return String.format("Herinnering: %s%nDagen vooraf: %d%n", herinneringsId, dagenVooraf);
    }
    //endregion
}
