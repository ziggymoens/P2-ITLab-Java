package domein;

import exceptions.SessieException;

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
        setMaximumAantalPlaatsen(maximumAantalPlaatsen);
        setLokaal(lokaal);
    }

    private void setTitel(String titel) {
        if (titel.isBlank()){
            throw new SessieException();
        }
        this.titel = titel;
    }

    private void setNaamGastspreker(String naamGastspreker) {
        this.naamGastspreker = naamGastspreker;
    }

    private void setStartSessie(Date startSessie) {
        this.startSessie = startSessie;
    }

    private void setEindeSessie(Date eindeSessie) {
        this.eindeSessie = eindeSessie;
    }

    private void setMaximumAantalPlaatsen(int maximumAantalPlaatsen) {
        this.maximumAantalPlaatsen = maximumAantalPlaatsen;
    }

    private void setAutomatischeHerinnering(boolean automatischeHerinnering) {
        this.automatischeHerinnering = automatischeHerinnering;
    }

    private void setHerinnering(Herinnering herinnering) {
        this.herinnering = herinnering;
    }

    private void setMediaBijSessie(List<Media> mediaBijSessie) {
        this.mediaBijSessie = mediaBijSessie;
    }

    private void setIngeschrevenGebruikers(List<Inschrijving> ingeschrevenGebruikers) {
        this.ingeschrevenGebruikers = ingeschrevenGebruikers;
    }

    private void setAankondigingenSessie(List<Aankondiging> aankondigingenSessie) {
        this.aankondigingenSessie = aankondigingenSessie;
    }

    private void setFeedbackSessie(List<Feedback> feedbackSessie) {
        this.feedbackSessie = feedbackSessie;
    }

    private void setLokaal(Lokaal lokaal) {
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
}
