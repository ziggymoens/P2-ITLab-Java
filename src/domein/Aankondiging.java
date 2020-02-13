package domein;

import exceptions.AankondigingException;

import java.util.Date;

public class Aankondiging {
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
}
