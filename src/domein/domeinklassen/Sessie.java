package domein.domeinklassen;

import domein.interfacesDomein.*;
import exceptions.domein.SessieException;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@SuppressWarnings("unchecked")
@Entity
@Table(name = "sessie")
public class Sessie implements ISessie {
    //region variabelen
    //Primairy key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sessieKey")
    @GenericGenerator(
            name = "sessieKey",
            strategy = "domein.domeinklassen.JPAIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = JPAIdGenerator.INCREMENT_PARAM, value = "1"),
                    @org.hibernate.annotations.Parameter(name = JPAIdGenerator.VALUE_PREFIX_PARAMETER, value = "S20-"),
                    @org.hibernate.annotations.Parameter(name = JPAIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%06d")})
    private String sessieId;

    private String titel;
    private String naamGastspreker;
    private LocalDateTime startSessie;
    private LocalDateTime eindeSessie;
    private int maximumAantalPlaatsen;
    private boolean geopend;

    @OneToMany
    private List<Media> mediaBijSessie;
    @OneToMany
    private List<Inschrijving> ingeschrevenGebruikers;
    @OneToMany
    private List<Aankondiging> aankondigingenSessie;
    @OneToMany
    private List<Feedback> feedbackSessie;
    @OneToOne
    private Lokaal lokaal;
    @OneToOne()
    private Gebruiker verantwoordelijke;
    //endregion

    //region Constructors

    /**
     * Constructor voor JPA
     */
    protected Sessie() {
    }

    /**
     * Default constructor voor een Sessie
     *
     * @param titel             (String) ==> De titel van de sessie
     * @param startSessie       (LocalDateTime) ==> het startmoment van de sessie
     * @param eindeSessie       (LocalDateTime) ==> het eindmoment van de sessie
     * @param lokaal            (Lokaal) ==> lokaal waar de sessie plaats vind
     * @param verantwoordelijke ==> de gebruiker die de sessie organiseert
     */
    public Sessie(String titel, LocalDateTime startSessie, LocalDateTime eindeSessie, Lokaal lokaal, Gebruiker verantwoordelijke) {
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
    //endregion

    //region Getters
    @Override
    public String getTitel() {
        return titel;
    }

    @Override
    public String getNaamGastspreker() {
        return naamGastspreker;
    }

    @Override
    public LocalDateTime getStartSessie() {
        return startSessie;
    }

    @Override
    public LocalDateTime getEindeSessie() {
        return eindeSessie;
    }

    @Override
    public int getMaximumAantalPlaatsen() {
        return maximumAantalPlaatsen;
    }

    @Override
    public List<IMedia> getIMediaBijSessie() {
        return (List<IMedia>) ((Object) mediaBijSessie);
    }

    @Override
    public List<IInschrijving> getIIngeschrevenGebruikers() {
        return (List<IInschrijving>) ((Object) ingeschrevenGebruikers);
    }

    @Override
    public List<IAankondiging> getIAankondigingenSessie() {
        return (List<IAankondiging>) ((Object) aankondigingenSessie);
    }

    @Override
    public List<IFeedback> getIFeedbackSessie() {
        return (List<IFeedback>) ((Object) feedbackSessie);
    }

    @Override
    public Lokaal getLokaal() {
        return lokaal;
    }

    @Override
    public Gebruiker getVerantwoordelijke() {
        return verantwoordelijke;
    }

    @Override
    public boolean isGeopend() {
        return geopend;
    }

    @Override
    public String getSessieId() {
        return sessieId;
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

    //endregion

    //region toString

    @Override
    public String toString() {
        return String.format("%s - %s", sessieId, titel);
    }


    public String toStringCompleet() {
        return String.format("SessieId: %s%nVerantwoordelijke: %s%nTitel: %s%nNaam gastspreker: %s%nLokaal: %s%nStart- & einduur: %s - %s%nMaximum plaatsten: %d", sessieId, verantwoordelijke.getNaam(), titel, naamGastspreker, lokaal.getLokaalCode(), startSessie.toString(), eindeSessie.toString(), lokaal.getAantalPlaatsen());
    }

    public String toString_Overzicht() {
        return String.format("%s. %s - %s - %s -> %s - %s", sessieId, verantwoordelijke.getNaam(), titel, startSessie.toString(),
                eindeSessie.toString(), isGeopend() ? String.format("%s%d", "aantal vrije plaatsen: ", aantalVrijePlaatsen()) : String.format("%s%d", "aantal aanwezigheden: ", aantalAanwezigenNaSessie()));
    }

    public String toString_OverzichtInschrijvingenNietGeopend() {
        StringBuilder sb = new StringBuilder();
        for (Inschrijving i : ingeschrevenGebruikers) {
            sb.append(String.format("%s: %s%n",/* i.getGebruiker().getNaam(), */i.getInschrijvingsdatum().toString()));
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
            sb.append(String.format("%s: %s%n",/* i.getGebruiker().getNaam(),*/ i.isStatusAanwezigheid() ? "aanwezig" : "afwezig"));
        }
        return sb.toString();
    }

    public String toString_OverzichtFeedback() {
        StringBuilder sb = new StringBuilder();
        for (Feedback f : feedbackSessie) {
            sb.append(String.format("%s%n\t%s%n",/* f.getGebruiker().getNaam(),*/ f.getTekst()));
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
    public void addMedia(Media media) {
        if (media == null) {
            throw new SessieException();
        }
        this.mediaBijSessie.add(media);
    }

    public void addInschrijving(Inschrijving inschrijving) {
        if (inschrijving == null) {
            throw new SessieException();
        }
        this.ingeschrevenGebruikers.add(inschrijving);
    }

    public void addAankondiging(Aankondiging aankondiging) {
        if (aankondiging == null) {
            throw new SessieException();
        }
        this.aankondigingenSessie.add(aankondiging);
    }

    public void addFeedback(Feedback feedback) {
        if (feedback == null) {
            throw new SessieException();
        }
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
