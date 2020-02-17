package domein;

import exceptions.AankondigingException;

import java.util.Date;
import java.util.Objects;

public class Aankondiging {
    //Primairy key
    private String aankondigingsId;

    private Date publicatiedatum;
    private Gebruiker publicist;
    private String inhoud;

    public Aankondiging(Date publicatiedatum, Gebruiker publicist, String inhoud) {
        setPublicatiedatum(publicatiedatum);
        setPublicist(publicist);
        setInhoud(inhoud);
    }

    public void setPublicatiedatum(Date publicatiedatum) {
        if(publicatiedatum == null){
            throw new AankondigingException();
        }
        this.publicatiedatum = publicatiedatum;
    }

    public void setPublicist(Gebruiker publicist) {
        if(publicist == null){
            throw new AankondigingException();
        }
        this.publicist = publicist;
    }

    public void setInhoud(String inhoud) {
        if (inhoud ==null || inhoud.isBlank()){
            throw new AankondigingException();
        }
        this.inhoud = inhoud;
    }

    public Date getPublicatiedatum() {
        return publicatiedatum;
    }

    public Gebruiker getPublicist() {
        return publicist;
    }

    public String getInhoud() {
        return inhoud;
    }

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
}
