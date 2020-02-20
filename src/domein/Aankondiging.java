package domein;

import domein.interfacesDomein.IAankondiging;
import exceptions.domein.AankondigingException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Aankondiging implements Serializable, IAankondiging {
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

    public Aankondiging(Gebruiker gebruiker, Sessie sessie, LocalDateTime aangemaakt, String inhoud) {
        this("Aankondiging", sessie, aangemaakt, gebruiker, inhoud);
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

    protected void setAankondigingsId(String aankondigingsId) {
        if(aankondigingsId == null || aankondigingsId.isBlank())
            throw new AankondigingException();
        this.aankondigingsId = aankondigingsId;
    }

    private void setSessie(Sessie sessie) {
        if(sessie == null)
            throw new AankondigingException();
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
