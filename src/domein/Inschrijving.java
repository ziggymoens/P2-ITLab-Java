package domein;

import exceptions.domein.InschrijvingException;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Inschrijving implements Serializable {
    //region Variabelen
    //Primairy key
    private String inschrijvingsId;

    private Gebruiker gebruiker;
    private Date inschrijvingsdatum;
    private boolean statusAanwezigheid = false;
    //endregion

    //region Constructor
    public Inschrijving(Gebruiker gebruiker, Date inschrijvingsdatum) {
        setGebruiker(gebruiker);
        setInschrijvingsdatum(inschrijvingsdatum);
    }
    //endregion

    //region Setters
    private void setGebruiker(Gebruiker gebruiker) {
        if (gebruiker == null) {
            throw new InschrijvingException();
        }
        this.gebruiker = gebruiker;
    }

    private void setInschrijvingsdatum(Date inschrijvingsdatum) {
        if (inschrijvingsdatum == null) {
            throw new InschrijvingException();
        }
        this.inschrijvingsdatum = inschrijvingsdatum;
    }

    public void setStatusAanwezigheid(boolean statusAanwezigheid) {
        this.statusAanwezigheid = statusAanwezigheid;
    }

    public void setInschrijvingsId(String inschrijvingsId) {
        this.inschrijvingsId = inschrijvingsId;
    }

    //endregion

    //region Getters
    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public Date getInschrijvingsdatum() {
        return inschrijvingsdatum;
    }

    public boolean isStatusAanwezigheid() {
        return statusAanwezigheid;
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
}
