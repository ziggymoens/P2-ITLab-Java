package domein.domeinklassen;

import domein.interfacesDomein.IInschrijving;
import exceptions.domein.InschrijvingException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "inschijving")
public class Inschrijving implements IInschrijving {
    //region Variabelen
    //Primairy key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int inschrijvingsId;

    private LocalDateTime inschrijvingsdatum;
    private boolean statusAanwezigheid = false;
    //endregion

    //region Constructor

    /**
     * Constructor voor JPA
     */
    protected Inschrijving() {
    }

    /**
     * Default constructor voor Inschrijving
     *
     * @param inschrijvingsdatum (LocalDateTime) ==> Datum van inschrijven
     * @param statusAanwezigheid (boolean) ==> status aanwezigheid van de gebruiker
     */
    public Inschrijving(LocalDateTime inschrijvingsdatum, boolean statusAanwezigheid) {
        setInschrijvingsdatum(inschrijvingsdatum);
        setStatusAanwezigheid(statusAanwezigheid);
    }

    /**
     * Constructor voor aanmaken inschrijving zonder boolean
     *
     * @param inschrijvingsdatum (LocalDateTime) ==> Datum van inschrijven
     */
    public Inschrijving(LocalDateTime inschrijvingsdatum) {
        this(inschrijvingsdatum, false);
    }

    //endregion

    //region Setters
    private void setInschrijvingsdatum(LocalDateTime inschrijvingsdatum) {
        if (inschrijvingsdatum == null) {
            throw new InschrijvingException();
        }
        this.inschrijvingsdatum = inschrijvingsdatum;
    }

    private void setStatusAanwezigheid(boolean statusAanwezigheid) {
        this.statusAanwezigheid = statusAanwezigheid;
    }
    //endregion

    //region Getters
    @Override
    public LocalDateTime getInschrijvingsdatum() {
        return inschrijvingsdatum;
    }

    @Override
    public boolean isStatusAanwezigheid() {
        return statusAanwezigheid;
    }

    @Override
    public int getInschrijvingsId() {
        return inschrijvingsId;
    }
    //endregion

    //region Equals & Hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inschrijving that = (Inschrijving) o;
        return Objects.equals(inschrijvingsId, that.inschrijvingsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inschrijvingsId);
    }
    //endregion

    //region toString

    @Override
    public String toString() {
        return "Inschrijving{" +
                "inschrijvingsId='" + inschrijvingsId + '\'' +
                ", inschrijvingsdatum=" + inschrijvingsdatum.toString() +
                ", statusAanwezigheid=" + statusAanwezigheid +
                '}';
    }

    //endregion
}
