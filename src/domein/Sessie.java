package domein;

import exceptions.SessieException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Sessie {
    private String titel;
    private String naamGastspreker;
    private Date startSessie;
    private Date eindeSessie;
    private int maximumAantalPlaatsen;
    private boolean automatischeHerinnering;
    private Herinnering herinnering;
    private List<Media> mediaBijSessie;
    private List<Inschrijving> ingeschrevenGebruikers;
    private List<Aankondiging> aankondigingenSessie;
    private List<Feedback> feedbackSessie;
    private Lokaal lokaal;
    private Gebruiker verantwoordelijke;
    private boolean geopend;

    public Sessie(String titel, Date startSessie, Date eindeSessie, int maximumAantalPlaatsen, Lokaal lokaal, Gebruiker verantwoordelijke) {
        setVerantwoordelijke(verantwoordelijke);
        setTitel(titel);
        setStartSessie(startSessie);
        setEindeSessie(eindeSessie);
        controleData();
        setLokaal(lokaal);
        setMaximumAantalPlaatsen(maximumAantalPlaatsen);
        initLijsten();
    }

    private void initLijsten() {
        this.mediaBijSessie = new ArrayList<>();
        this.ingeschrevenGebruikers = new ArrayList<>();
        this.aankondigingenSessie = new ArrayList<>();
        this.feedbackSessie = new ArrayList<>();
    }

    private void controleData() {
        Date nu = new Date(System.currentTimeMillis());
        if (startSessie.getDay() < nu.getDay()+1){
            throw new SessieException("SessieException.startSessie1Dag");
        }
        if (startSessie.getMinutes()+30>eindeSessie.getMinutes()){
            throw new SessieException("SessieException.startEinde30Min");
        }
    }

    private void setTitel(String titel) {
        if (titel.isBlank()){
            throw new SessieException("SessieException.titel");
        }
        this.titel = titel;
    }

    private void setNaamGastspreker(String naamGastspreker) {
        if (naamGastspreker == null || naamGastspreker.isBlank()){
            throw new SessieException();
        }
        this.naamGastspreker = naamGastspreker;
    }

    private void setStartSessie(Date startSessie) {
        this.startSessie = startSessie;
    }

    private void setEindeSessie(Date eindeSessie) {
        this.eindeSessie = eindeSessie;
    }

    private void setMaximumAantalPlaatsen(int maximumAantalPlaatsen) {
        if (lokaal.getAantalPlaatsen() < maximumAantalPlaatsen){
            throw new SessieException("SessieException.aantalPlaatsenTeVeel");
        }
        this.maximumAantalPlaatsen = maximumAantalPlaatsen;
    }

    private void setAutomatischeHerinnering(boolean automatischeHerinnering) {
        this.automatischeHerinnering = automatischeHerinnering;
    }

    private void setHerinnering(Herinnering herinnering) {
        if (!automatischeHerinnering){
            throw new SessieException();
        }
        this.herinnering = herinnering;
    }

    public void mediaToevoegenAanSessie(Media media){
        if (media == null){
            throw new SessieException();
        }
        this.mediaBijSessie.add(media);
    }

    public void inschrijvingAanSessieToevoegen(Inschrijving inschrijving) {
        this.ingeschrevenGebruikers.add(inschrijving);
    }

    private void aankondigingToevoegenAanSessie(Aankondiging aankondiging) {
        this.aankondigingenSessie.add(aankondiging);
    }

    private void feedbackToevoegenAanSessie(Feedback feedback) {
        this.feedbackSessie.add(feedback);
    }

    private void setLokaal(Lokaal lokaal) {
        if(lokaal == null){
            throw new SessieException("SessieException.lokaalNull");
        }
        this.lokaal = lokaal;
    }

    private void setVerantwoordelijke(Gebruiker verantwoordelijke) {
        if (verantwoordelijke == null || verantwoordelijke.getType() == Gebruikersprofielen.VERANTWOORDELIJKE || verantwoordelijke.getType() == Gebruikersprofielen.HOOFDVERANTWOORDELIJKE){
            throw new SessieException("SessieException.verantwoordelijkeFoutType");
        }
        this.verantwoordelijke = verantwoordelijke;
    }

    private void setGeopend(boolean geopend){
        this.geopend = geopend;
    }

    public String getTitel() {
        return titel;
    }

    public String getNaamGastspreker() {
        return naamGastspreker;
    }

    public Date getStartSessie() {
        return startSessie;
    }

    public Date getEindeSessie() {
        return eindeSessie;
    }

    public int getMaximumAantalPlaatsen() {
        return maximumAantalPlaatsen;
    }

    public boolean isAutomatischeHerinnering() {
        return automatischeHerinnering;
    }

    public Herinnering getHerinnering() {
        return herinnering;
    }

    public List<Media> getMediaBijSessie() {
        return mediaBijSessie;
    }

    public List<Inschrijving> getIngeschrevenGebruikers() {
        return ingeschrevenGebruikers;
    }

    public List<Aankondiging> getAankondigingenSessie() {
        return aankondigingenSessie;
    }

    public List<Feedback> getFeedbackSessie() {
        return feedbackSessie;
    }

    public Lokaal getLokaal() {
        return lokaal;
    }

    public Gebruiker getVerantwoordelijke() {
        return verantwoordelijke;
    }

    public boolean isGeopend(){
        return geopend;
    }

    public int aantalVrijePlaatsen() {
        return maximumAantalPlaatsen - ingeschrevenGebruikers.size();
    }

    public int aantalAanwezigenNaSessie() {
        int aantal = 0;
        for (Inschrijving i: ingeschrevenGebruikers){
            if (i.isStatusAanwezigheid()){
                aantal++;
            }
        }
        return aantal;
    }

    public String geefOverzichtInschrijvingenNietGeopend() {
        StringBuilder sb = new StringBuilder();
        for(Inschrijving i : ingeschrevenGebruikers){
            sb.append(String.format("%s: %s%n",i.getGebruiker().getNaam(),i.getInschrijvingsdatum().toString()));
        }
        return sb.toString();
    }

    public String geefOverzichtAankondigingen() {
        StringBuilder sb = new StringBuilder();
        for(Aankondiging a : aankondigingenSessie){
            sb.append(String.format("%s - %s%n%t%s%n",a.getPublicatiedatum().toString(), a.getPublicist().getNaam(), a.getInhoud()));
        }
        return sb.toString();
    }

    public String geefOverzichtInschrijvingenGeopend() {
        StringBuilder sb = new StringBuilder();
        for(Inschrijving i : ingeschrevenGebruikers){
            sb.append(String.format("%s: %s%n",i.getGebruiker().getNaam(),i.isStatusAanwezigheid()?"aanwezig":"afwezig"));
        }
        return sb.toString();
    }

    public String geefOverzichtFeedback() {
        StringBuilder sb = new StringBuilder();
        for(Feedback f : feedbackSessie){
            sb.append(String.format("%s%n%t%s%n",f.getGebruiker().getNaam(),f.getTekst()));
        }
        return sb.toString();
    }


}
