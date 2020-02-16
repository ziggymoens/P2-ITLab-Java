package domein;

import exceptions.SessieException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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

        Date startPlus30Min = new Date(startSessie.getTime());
        Date nuPlus1Dag = new Date();
        startPlus30Min.setMinutes(startPlus30Min.getMinutes()+30);
        nuPlus1Dag.setDate(nuPlus1Dag.getDate()+1);

        if (startPlus30Min.after(eindeSessie)){
            throw new SessieException("SessieException.startEinde30Min");
        }

        if (nuPlus1Dag.after(startSessie)){
            throw new SessieException("SessieException.startSessie1Dag");
        }

//        LocalDate nu = LocalDate.now();
//        LocalDate localStartDate = startSessie.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        LocalDate localEndDate= eindeSessie.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//        if (localStartDate.atTime(startSessie.getHours(), startSessie.getMinutes()).plusMinutes(30).isAfter(localEndDate.atTime(eindeSessie.getHours(), eindeSessie.getMinutes()))) {
//            throw new SessieException("SessieException.startEinde30Min");
//        }
//
//        if (localStartDate.isBefore(nu.plusDays(1))) {
//            throw new SessieException("SessieException.startSessie1Dag");
//        }

//        Calendar now = Calendar.getInstance();
//        Calendar calendarStart = Calendar.getInstance();
//        Calendar calendarEinde = Calendar.getInstance();
//        now.setTime(new Date());
//        calendarStart.setTime(startSessie);
//        calendarEinde.setTime(eindeSessie);
//
//        if (calendarStart.add(Calendar.MINUTE, 30) > calendarEinde) {
//            throw new SessieException("SessieException.startEinde30Min");
//        }
//
//        if () {
//            throw new SessieException("SessieException.startSessie1Dag");
//        }
//      FACK MEN LEVEN :(


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

    public String toString_OverzichtInschrijvingenNietGeopend() {
        StringBuilder sb = new StringBuilder();
        for(Inschrijving i : ingeschrevenGebruikers){
            sb.append(String.format("%s: %s%n",i.getGebruiker().getNaam(),i.getInschrijvingsdatum().toString()));
        }
        return sb.toString();
    }

    public String toString_OverzichtAankondigingen() {
        StringBuilder sb = new StringBuilder();
        for(Aankondiging a : aankondigingenSessie){
            sb.append(String.format("%s - %s%n%t%s%n",a.getPublicatiedatum().toString(), a.getPublicist().getNaam(), a.getInhoud()));
        }
        return sb.toString();
    }

    public String toString_OverzichtInschrijvingenGeopend() {
        StringBuilder sb = new StringBuilder();
        for(Inschrijving i : ingeschrevenGebruikers){
            sb.append(String.format("%s: %s%n",i.getGebruiker().getNaam(),i.isStatusAanwezigheid()?"aanwezig":"afwezig"));
        }
        return sb.toString();
    }

    public String toString_OverzichtFeedback() {
        StringBuilder sb = new StringBuilder();
        for(Feedback f : feedbackSessie){
            sb.append(String.format("%s%n%t%s%n",f.getGebruiker().getNaam(),f.getTekst()));
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sessie)) return false;
        Sessie sessie = (Sessie) o;
        return maximumAantalPlaatsen == sessie.maximumAantalPlaatsen &&
                automatischeHerinnering == sessie.automatischeHerinnering &&
                geopend == sessie.geopend &&
                titel.equals(sessie.titel) &&
                Objects.equals(naamGastspreker, sessie.naamGastspreker) &&
                startSessie.equals(sessie.startSessie) &&
                eindeSessie.equals(sessie.eindeSessie) &&
                Objects.equals(herinnering, sessie.herinnering) &&
                Objects.equals(mediaBijSessie, sessie.mediaBijSessie) &&
                Objects.equals(ingeschrevenGebruikers, sessie.ingeschrevenGebruikers) &&
                Objects.equals(aankondigingenSessie, sessie.aankondigingenSessie) &&
                Objects.equals(feedbackSessie, sessie.feedbackSessie) &&
                lokaal.equals(sessie.lokaal) &&
                verantwoordelijke.equals(sessie.verantwoordelijke);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titel, naamGastspreker, startSessie, eindeSessie, maximumAantalPlaatsen, automatischeHerinnering, herinnering, mediaBijSessie, ingeschrevenGebruikers, aankondigingenSessie, feedbackSessie, lokaal, verantwoordelijke, geopend);
    }
}
