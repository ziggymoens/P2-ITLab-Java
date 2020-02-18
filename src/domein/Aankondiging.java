package domein;

import exceptions.domein.AankondigingException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class Aankondiging implements Serializable {
    private static final long serialVersionUID = -5032924408446952572L;

    //region Variabelen
    //Primairy key
    private String aankondigingsId;

    private LocalDateTime publicatiedatum;
    private Gebruiker publicist;
    private Sessie sessie;
    private String inhoud;
    //endregion

    //region Constructor
    public Aankondiging(String aankondigingsId, Sessie sessie, LocalDateTime publicatiedatum, Gebruiker publicist, String inhoud) {
        setAankondigingsId(aankondigingsId);
        setPublicatiedatum(publicatiedatum);
        setSessie(sessie);
        setPublicist(publicist);
        setInhoud(inhoud);
    }
    //endregion

    //region Setters
    private void setPublicatiedatum(LocalDateTime publicatiedatum) {
        if (publicatiedatum == null) {
            throw new AankondigingException();
        }
        this.publicatiedatum = publicatiedatum;
    }

    private void setPublicist(Gebruiker publicist) {
        if (publicist == null) {
            throw new AankondigingException();
        }
        this.publicist = publicist;
    }

    private void setInhoud(String inhoud) {
        if (inhoud == null || inhoud.isBlank()) {
            throw new AankondigingException();
        }
        this.inhoud = inhoud;
    }

    public void setAankondigingsId(String aankondigingsId) {
        this.aankondigingsId = aankondigingsId;
    }

    public void setSessie(Sessie sessie) {
        this.sessie = sessie;
    }
    //endregion

    //region Getters
    public LocalDateTime getPublicatiedatum() {
        return publicatiedatum;
    }

    public Gebruiker getPublicist() {
        return publicist;
    }

    public String getInhoud() {
        return inhoud;
    }

    public String getAankondigingsId() {
        return aankondigingsId;
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
        Aankondiging that = (Aankondiging) o;
        return Objects.equals(aankondigingsId, that.aankondigingsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aankondigingsId);
    }
    //endregion

    //region toString

    @Override
    public String toString() {
        return "Aankondiging{" +
                "aankondigingsId='" + aankondigingsId + '\'' +
                ", publicatiedatum=" + publicatiedatum +
                ", publicist=" + publicist.getNaam() +
                ", sessie=" + sessie.getSessieId() +
                ", inhoud='" + inhoud + '\'' +
                '}';
    }

    //endregion
}
