package domein.domeinklassen;

import domein.enums.HerinneringTijdstippen;
import domein.interfacesDomein.IHerinnering;
import exceptions.domein.HerinneringException;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "herinnering")
public class Herinnering implements IHerinnering {
    //region Variabelen
    //Primairy key
    @Id
    private int herinneringsId;

    private HerinneringTijdstippen dagenVooraf;
    //endregion

    //region Constructor

    /**
     * Constructor voor JPA
     */
    protected Herinnering() {
    }

    /**
     * Default constructor voor Herinnering
     *
     * @param dagenVooraf (HerinneringTijdstippen) ==> aantal dagen voordien dat de herinnering moet verstuurd worden
     */
    public Herinnering(HerinneringTijdstippen dagenVooraf) {
        setDagenVooraf(dagenVooraf);
    }

    /**
     * Constructor voor aanmaken van herinnering adhv int
     *
     * @param dagenVooraf (int) ==> aantal dagen voordien dat de herinnering moet verstuurd worden
     */
    public Herinnering(int dagenVooraf) {
        this(Arrays.stream(HerinneringTijdstippen.values()).filter(t -> t.getDagen() == dagenVooraf).findFirst().orElse(HerinneringTijdstippen.NUL));
    }
    //endregion

    //region Setters
    private void setDagenVooraf(HerinneringTijdstippen dagenVooraf) {
        this.dagenVooraf = dagenVooraf;
    }
    //endregion

    //region Getters
    public HerinneringTijdstippen getDagenVooraf() {
        return dagenVooraf;
    }

    @Override
    public int getDagenVoorafInt() {
        return dagenVooraf.getDagen();
    }

    @Override
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
        return String.format("Herinnering: %s%nDagen vooraf: %d%n", herinneringsId, dagenVooraf.getDagen());
    }
    //endregion
}
