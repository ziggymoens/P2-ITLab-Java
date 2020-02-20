package domein;

import domein.interfacesDomein.IInschrijving;
import exceptions.domein.InschrijvingException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Inschrijving implements IInschrijving {
    //region Variabelen
    //Primairy key
    private String inschrijvingsId;

    private Gebruiker gebruiker;
    private Sessie sessie;
    private LocalDateTime inschrijvingsdatum;
    private boolean statusAanwezigheid = false;
    //endregione

    //region Constructor
    public Inschrijving(String inschrijvingsId, Gebruiker gebruiker, Sessie sessie,LocalDateTime inschrijvingsdatum, boolean statusAanwezigheid) {
        setInschrijvingsId(inschrijvingsId);
        setGebruiker(gebruiker);
        setSessie(sessie);
        setInschrijvingsdatum(inschrijvingsdatum);
        setStatusAanwezigheid(statusAanwezigheid);
    }
    //endregion

    //region Setters
    private void setGebruiker(Gebruiker gebruiker) {
        if (gebruiker == null) {
            throw new InschrijvingException();
        }
        this.gebruiker = gebruiker;
    }

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

    private void setSessie(Sessie sessie) {
        if(sessie == null)
            throw new InschrijvingException();
        this.sessie = sessie;
    }

    //endregion

    //region Getters
    public Gebruiker getGebruiker() {
        return gebruiker;
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
        return sessie;
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
                ", gebruiker=" + gebruiker.getNaam() +
                ", sessie=" + sessie.getSessieId() +
                ", inschrijvingsdatum=" + inschrijvingsdatum.toString() +
                ", statusAanwezigheid=" + statusAanwezigheid +
                '}';
    }

    //endregion
}
