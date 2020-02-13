package domein;

import exceptions.InschrijvingException;

import java.util.Date;

public class Inschrijving {
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
}
