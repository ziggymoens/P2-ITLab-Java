package domein;

import exceptions.domein.SessieException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Sessie implements Serializable {
    private static final long serialVersionUID = 6484163469782688241L;

    //region variabelen
    //Primairy key
    private String sessieId;

    private String titel;
    private String naamGastspreker;
    private LocalDateTime startSessie;
    private LocalDateTime eindeSessie;
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
    //endregion

    //region Constructors
    public Sessie(String sessieId, String titel, LocalDateTime startSessie, LocalDateTime eindeSessie, Lokaal lokaal, Gebruiker verantwoordelijke) {
        setSessieId(sessieId);
        setVerantwoordelijke(verantwoordelijke);
        setTitel(titel);
        setStartSessie(startSessie);
        setEindeSessie(eindeSessie);
        controleData();
        setLokaal(lokaal);
        setMaximumAantalPlaatsen(this.lokaal.getAantalPlaatsen());
        setNaamGastspreker("Onbekend");
        initLijsten();
    }

    public Sessie(String titel, LocalDateTime startSessie, LocalDateTime eindeSessie, Lokaal lokaal, Gebruiker verantwoordelijke) {
        this("onbekend", titel, startSessie, eindeSessie, lokaal, verantwoordelijke);
    }
    //endregion

    //region Init
    private void initLijsten() {
        this.mediaBijSessie = new ArrayList<>();
        this.ingeschrevenGebruikers = new ArrayList<>();
        this.aankondigingenSessie = new ArrayList<>();
        this.feedbackSessie = new ArrayList<>();
    }
    //endregion

    //region Setters
    private void setTitel(String titel) {
        if (titel.isBlank()) {
            throw new SessieException("SessieException.titel");
        }
        this.titel = titel;
    }

    private void setNaamGastspreker(String naamGastspreker) {
        if (naamGastspreker == null || naamGastspreker.isBlank()) {
            throw new SessieException();
        }
        this.naamGastspreker = naamGastspreker;
    }

    private void setStartSessie(LocalDateTime startSessie) {
        this.startSessie = startSessie;
    }

    private void setEindeSessie(LocalDateTime eindeSessie) {
        this.eindeSessie = eindeSessie;
    }

    private void setMaximumAantalPlaatsen(int maximumAantalPlaatsen) {
        if (lokaal.getAantalPlaatsen() < maximumAantalPlaatsen) {
            throw new SessieException("SessieException.aantalPlaatsenTeVeel");
        }
        this.maximumAantalPlaatsen = maximumAantalPlaatsen;
    }

    private void setAutomatischeHerinnering(boolean automatischeHerinnering) {
        this.automatischeHerinnering = automatischeHerinnering;
    }

    private void setHerinnering(Herinnering herinnering) {
        if (!automatischeHerinnering) {
            throw new SessieException();
        }
        this.herinnering = herinnering;
    }

    private void setVerantwoordelijke(Gebruiker verantwoordelijke) {
        this.verantwoordelijke = verantwoordelijke;
    }

    private void setGeopend(boolean geopend) {
        this.geopend = geopend;
    }

    private void setLokaal(Lokaal lokaal) {
        if (lokaal == null) {
            throw new SessieException("SessieException.lokaalNull");
        }
        this.lokaal = lokaal;
    }

    public void setSessieId(String sessieId) {
        this.sessieId = sessieId;
    }

    //endregion

    //region Getters
    public String getTitel() {
        return titel;
    }

    public String getNaamGastspreker() {
        return naamGastspreker;
    }

    public LocalDateTime getStartSessie() {
        return startSessie;
    }

    public LocalDateTime getEindeSessie() {
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

    public boolean isGeopend() {
        return geopend;
    }

    public String getSessieId() {
        return sessieId;
    }

    //endregion

    //region toString

    @Override
    public String toString() {
        return String.format("SessieId: %s%nVerantwoordelijke: %s%nTitel: %s%nNaam gastspreker: %s%nLokaal: %s%nStart- & einduur: %s - %s%nMaximum plaatsten: %d",sessieId,verantwoordelijke.getNaam(), titel, naamGastspreker, lokaal.getLokaalCode(), startSessie.toString(), eindeSessie.toString(), lokaal.getAantalPlaatsen());
    }

    public String toString_Overzicht(){
        return String.format("%s. %s - %s - %s -> %s - %s", sessieId, verantwoordelijke.getNaam(), titel, startSessie.toString(),
                eindeSessie.toString(), isGeopend() ? String.format("%s%d", "aantal vrije plaatsen: ",aantalVrijePlaatsen()) : String.format("%s%d", "aantal aanwezigheden: ",aantalAanwezigenNaSessie()));
    }

    public String toString_OverzichtInschrijvingenNietGeopend() {
        StringBuilder sb = new StringBuilder();
        for (Inschrijving i : ingeschrevenGebruikers) {
            sb.append(String.format("%s: %s%n", i.getGebruiker().getNaam(), i.getInschrijvingsdatum().toString()));
        }
        return sb.toString();
    }

    public String toString_OverzichtAankondigingen() {
        StringBuilder sb = new StringBuilder();
        for (Aankondiging a : aankondigingenSessie) {
            sb.append(String.format("%s - %s%n\t%s%n", a.getPublicatiedatum().toString(), a.getPublicist().getNaam(), a.getInhoud()));
        }
        return sb.toString();
    }

    public String toString_OverzichtInschrijvingenGeopend() {
        StringBuilder sb = new StringBuilder();
        for (Inschrijving i : ingeschrevenGebruikers) {
            sb.append(String.format("%s: %s%n", i.getGebruiker().getNaam(), i.isStatusAanwezigheid() ? "aanwezig" : "afwezig"));
        }
        return sb.toString();
    }

    public String toString_OverzichtFeedback() {
        StringBuilder sb = new StringBuilder();
        for (Feedback f : feedbackSessie) {
            sb.append(String.format("%s%n\t%s%n", f.getGebruiker().getNaam(), f.getTekst()));
        }
        return sb.toString();
    }
    //endregion

    //region Equals & Hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sessie sessie = (Sessie) o;
        return Objects.equals(sessieId, sessie.sessieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessieId);
    }
    //endregion

    //region andere methoden
    public void mediaToevoegenAanSessie(Media media) {
        if (media == null) {
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

    public int aantalVrijePlaatsen() {
        return maximumAantalPlaatsen - ingeschrevenGebruikers.size();
    }

    public int aantalAanwezigenNaSessie() {
        int aantal = 0;
        for (Inschrijving i : ingeschrevenGebruikers) {
            if (i.isStatusAanwezigheid()) {
                aantal++;
            }
        }
        return aantal;
    }

    private void controleData() {
        if (!eindeSessie.isAfter(startSessie.plusMinutes(29))) {
            throw new SessieException("SessieException.startEinde30Min");
        }

        if (!startSessie.isAfter(LocalDateTime.now(ZoneId.of("Europe/Brussels")))) {
            throw new SessieException("SessieException.startSessie1Dag");
        }

        if (LocalDateTime.now().isAfter(eindeSessie)) {
            throw new SessieException();
        }
    }
    //endregion
}
