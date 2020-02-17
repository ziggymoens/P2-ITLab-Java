package domein;

import exceptions.InschrijvingException;

import java.util.Date;
import java.util.Objects;

public class Inschrijving {
    //Primairy key
    private String inschrijvingsId;

    private Gebruiker gebruiker;
    private Date inschrijvingsdatum;
    private boolean statusAanwezigheid = false;

    public Inschrijving(Gebruiker gebruiker, Date inschrijvingsdatum) {
        setGebruiker(gebruiker);
        setInschrijvingsdatum(inschrijvingsdatum);
    }

    private void setGebruiker(Gebruiker gebruiker) {
        if (gebruiker == null){
            throw new InschrijvingException();
        }
        this.gebruiker = gebruiker;
    }

    private void setInschrijvingsdatum(Date inschrijvingsdatum) {
        if (inschrijvingsdatum == null){
            throw new InschrijvingException();
        }
        this.inschrijvingsdatum = inschrijvingsdatum;
    }

    public void setStatusAanwezigheid(boolean statusAanwezigheid) {
        this.statusAanwezigheid = statusAanwezigheid;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public Date getInschrijvingsdatum() {
        return inschrijvingsdatum;
    }

    public boolean isStatusAanwezigheid() {
        return statusAanwezigheid;
    }

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
}
