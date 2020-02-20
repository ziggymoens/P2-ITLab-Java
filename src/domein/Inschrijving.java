package domein;

import domein.interfacesDomein.IInschrijving;
import exceptions.domein.InschrijvingException;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
//@Table(name = "inschijving")
public class Inschrijving implements IInschrijving {
    //region Variabelen
    //Primairy key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String inschrijvingsId;

    private LocalDateTime inschrijvingsdatum;
    private boolean statusAanwezigheid = false;
    //endregione

    //region Constructor
    protected Inschrijving(){}

    public Inschrijving(LocalDateTime inschrijvingsdatum, boolean statusAanwezigheid) {
        //setInschrijvingsId(inschrijvingsId);
        setInschrijvingsdatum(inschrijvingsdatum);
        setStatusAanwezigheid(statusAanwezigheid);
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

    private void setInschrijvingsId(String inschrijvingsId) {
        if(inschrijvingsId == null || inschrijvingsId.isBlank())
            throw new InschrijvingException();
        this.inschrijvingsId = inschrijvingsId;
    }

    //endregion

    //region Getters
    public Gebruiker getGebruiker() {
        throw new UnsupportedOperationException();
    }

    public LocalDateTime getInschrijvingsdatum() {
        return inschrijvingsdatum;
    }

    public boolean isStatusAanwezigheid() {
        return statusAanwezigheid;
    }

    public String getInschrijvingsId() {
        return inschrijvingsId;
    }

    public Sessie getSessie() {
        throw new UnsupportedOperationException();
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
    @ManyToOne(optional = false)
    private Sessie inschrijving;

    public Sessie getInschrijving() {
        return inschrijving;
    }

    public void setInschrijving(Sessie inschrijving) {
        this.inschrijving = inschrijving;
    }
}
