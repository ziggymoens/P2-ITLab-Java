package domein;

import domein.enums.HerinneringTijdstip;
import domein.interfacesDomein.IHerinnering;
import exceptions.domein.HerinneringException;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "herinnering")
public class Herinnering implements IHerinnering {
    //region Variabelen
    //Primairy key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "herinneringKey")
    @GenericGenerator(
            name = "herinneringKey",
            strategy = "domein.JPAIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = JPAIdGenerator.VALUE_PREFIX_PARAMETER, value = "H1920-"),
                    @org.hibernate.annotations.Parameter(name = JPAIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%06d")})
    private String herinneringsId;

    @Enumerated(EnumType.ORDINAL)
    private HerinneringTijdstip dagenVooraf;
    private boolean verwijderd = false;
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
    public Herinnering(HerinneringTijdstip dagenVooraf) {
        setDagenVooraf(dagenVooraf);
    }

    /**
     * Constructor voor aanmaken van herinnering adhv int
     *
     * @param dagenVooraf (int) ==> aantal dagen voordien dat de herinnering moet verstuurd worden
     */
    public Herinnering(int dagenVooraf) {
        this(Arrays.stream(HerinneringTijdstip.values()).filter(t -> t.getDagen() == dagenVooraf).findFirst().orElse(null));
    }
    //endregion

    //region Setters
    private void setDagenVooraf(HerinneringTijdstip dagenVooraf) {
        if (dagenVooraf == null) {
            throw new HerinneringException();
        }
        this.dagenVooraf = dagenVooraf;
    }

    public void setVerwijderd(boolean verwijderd) {
        this.verwijderd = verwijderd;
    }
    //endregion

    //region Getters
    @Override
    public HerinneringTijdstip getDagenVooraf() {
        return dagenVooraf;
    }

    @Override
    public int getDagenVoorafInt() {
        return dagenVooraf.getDagen();
    }

    @Override
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
        return String.format("Herinnering: %s%nDagen vooraf: %d%n", herinneringsId, dagenVooraf.getDagen());
    }

    /**
     *
     * @param gegevens (int aantalDagen)
     */
    public void update(int gegevens) {
        try {
            setDagenVooraf(Arrays.stream(HerinneringTijdstip.values()).filter(t -> t.getDagen() == gegevens).findFirst().orElse(null));
        }catch (Exception e){
            throw new HerinneringException("Update");
        }
    }
    //endregion
}
