package domein;

import exceptions.domein.AankondigingException;

import java.util.Date;
import java.util.Objects;

public class Aankondiging {
    //region Variabelen
    //Primairy key
    private String aankondigingsId;

    private Date publicatiedatum;
    private Gebruiker publicist;
    private String inhoud;
    //endregion

    //region Constructor
    public Aankondiging(Date publicatiedatum, Gebruiker publicist, String inhoud) {
        setPublicatiedatum(publicatiedatum);
        setPublicist(publicist);
        setInhoud(inhoud);
    }
    //endregion

    //region Setters
    private void setPublicatiedatum(Date publicatiedatum) {
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
    //endregion

    //region Getters
    public Date getPublicatiedatum() {
        return publicatiedatum;
    }

    public Gebruiker getPublicist() {
        return publicist;
    }

    public String getInhoud() {
        return inhoud;
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
}
