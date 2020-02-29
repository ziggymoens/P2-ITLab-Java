package domein;

import domein.interfacesDomein.*;
import exceptions.domein.SessieException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
            strategy = "domein.JPAIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = JPAIdGenerator.VALUE_PREFIX_PARAMETER, value = "S1920-"),
                    @org.hibernate.annotations.Parameter(name = JPAIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%06d")})
    private String sessieId;

    private String titel;
    private String naamGastspreker;
    private LocalDateTime startSessie;
    private LocalDateTime eindeSessie;
    private int maximumAantalPlaatsen;
    private int academiejaar;
    private boolean geopend;
    private boolean verwijderd = false;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Media> media;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Inschrijving> inschrijvingen;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Aankondiging> aankondigingen;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Feedback> feedback;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Lokaal lokaal;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
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
        setAcademiejaar();
    }
    //endregion

    //region Init
    private void initLijsten() {
        this.media = new ArrayList<>();
        this.inschrijvingen = new ArrayList<>();
        this.aankondigingen = new ArrayList<>();
        this.feedback = new ArrayList<>();
    }
    //endregion

    //region Setters
    public void setTitel(String titel) {
        if (titel.isBlank()) {
            throw new SessieException("SessieException.titel");
        }
        this.titel = titel;
    }

    public void setNaamGastspreker(String naamGastspreker) {
        if (naamGastspreker == null || naamGastspreker.isBlank()) {
            throw new SessieException();
        }
        this.naamGastspreker = naamGastspreker;
    }

    public void setStartSessie(LocalDateTime startSessie) {
        this.startSessie = startSessie;
    }

    public void setEindeSessie(LocalDateTime eindeSessie) {
        this.eindeSessie = eindeSessie;
    }

    public void setMaximumAantalPlaatsen(int maximumAantalPlaatsen) {
        if (lokaal.getAantalPlaatsen() < maximumAantalPlaatsen) {
            throw new SessieException("SessieException.aantalPlaatsenTeVeel");
        }
        this.maximumAantalPlaatsen = maximumAantalPlaatsen;
    }

    public void setVerantwoordelijke(Gebruiker verantwoordelijke) {
        this.verantwoordelijke = verantwoordelijke;
    }

    private void setGeopend(boolean geopend) {
        this.geopend = geopend;
    }

    public void setLokaal(Lokaal lokaal) {
        if (lokaal == null) {
            throw new SessieException("SessieException.lokaalNull");
        }
        this.lokaal = lokaal;
    }

    public void setVerwijderd(boolean verwijderd) {
        this.verwijderd = verwijderd;
    }

    private void setAcademiejaar() {
        int jaar = startSessie.getYear() - 2000;
        if (startSessie.getDayOfYear() < 243) {
            academiejaar = Integer.parseInt(String.format("%d%d", jaar - 1, jaar));
        } else {
            academiejaar = Integer.parseInt(String.format("%d%d", jaar, jaar + 1));
        }
    }

    //endregion

    //region Getters
    @Override
    public String getTitel() {
        return titel;
    }

    @Override
    public String getNaamGastspreker() {

        if (naamGastspreker == null || naamGastspreker.isBlank()) return "Geen gastspreker";
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
    public ObservableList<IMedia> getIMediaBijSessie() {
        return FXCollections.observableArrayList(media);
    }

    @Override
    public ObservableList<IInschrijving> getIIngeschrevenGebruikers() {
        return FXCollections.observableArrayList(inschrijvingen);
    }

    @Override
    public ObservableList<IAankondiging> getIAankondigingenSessie() {
        return FXCollections.observableArrayList(aankondigingen);
    }

    @Override
    public ObservableList<IFeedback> getIFeedbackSessie() {
        return FXCollections.observableArrayList(feedback);
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

    public List<Media> getMedia() {
        return media;
    }

    public List<Inschrijving> getInschrijvingen() {
        return inschrijvingen;
    }

    public List<Aankondiging> getAankondigingen() {
        return aankondigingen;
    }

    public List<Feedback> getFeedback() {
        return feedback;
    }

    private int getAcademiejaar() {
        return academiejaar;
    }

    private boolean isVerwijderd() {
        return verwijderd;
    }
    //endregion

    //region toString

    @Override
    public String toString() {
        return String.format("%s - %s", startSessie.toLocalDate(), titel);
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
        for (Inschrijving i : inschrijvingen) {
            sb.append(String.format("%s: %s%n",/* i.getGebruiker().getNaam(), */i.getInschrijvingsdatum().toString()));
        }
        return sb.toString();
    }

    public String toString_OverzichtAankondigingen() {
        StringBuilder sb = new StringBuilder();
        for (Aankondiging a : aankondigingen) {
            sb.append(String.format("%s - %s%n\t%s%n", a.getPublicatiedatum().toString(), a.getPublicist().getNaam(), a.getInhoud()));
        }
        return sb.toString();
    }

    public String toString_OverzichtInschrijvingenGeopend() {
        StringBuilder sb = new StringBuilder();
        for (Inschrijving i : inschrijvingen) {
            sb.append(String.format("%s: %s%n",/* i.getGebruiker().getNaam(),*/ i.isStatusAanwezigheid() ? "aanwezig" : "afwezig"));
        }
        return sb.toString();
    }

    public String toString_OverzichtFeedback() {
        StringBuilder sb = new StringBuilder();
        for (Feedback f : feedback) {
            sb.append(String.format("%s%n\t%s%n",/* f.getGebruiker().getNaam(),*/ f.getTekst()));
        }
        return sb.toString();
    }
    //endregion

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sessie sessie = (Sessie) o;
        return sessieId.equals(sessie.sessieId) &&
                Objects.equals(startSessie, sessie.startSessie);
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
        this.media.add(media);
    }

    public void addInschrijving(Inschrijving inschrijving) {
        if (inschrijving == null) {
            throw new SessieException();
        }
        this.inschrijvingen.add(inschrijving);
    }

    public void addAankondiging(Aankondiging aankondiging) {
        if (aankondiging == null) {
            throw new SessieException();
        }
        this.aankondigingen.add(aankondiging);
    }

    public void addFeedback(Feedback feedback) {
        if (feedback == null) {
            throw new SessieException();
        }
        this.feedback.add(feedback);
    }

    public int aantalVrijePlaatsen() {
        return maximumAantalPlaatsen - inschrijvingen.size();
    }

    public int aantalAanwezigenNaSessie() {
        int aantal = 0;
        for (Inschrijving i : inschrijvingen) {
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
        /*
        if (!startSessie.isAfter(LocalDateTime.now(ZoneId.of("Europe/Brussels")))) {
            throw new SessieException("SessieException.startSessie1Dag");
        }

        if (LocalDateTime.now().isAfter(eindeSessie)) {
            throw new SessieException();
        }
        */
    }

    //endregion
    @Override
    public Map<String, Object> gegevensDetails() {
        Map<String, Object> gegevens = new HashMap<>();
        gegevens.put("Titel", getTitel());
        gegevens.put("Gasspreker", getNaamGastspreker());
        gegevens.put("Start sessie", getStartSessie());
        gegevens.put("Einde sessie", getEindeSessie());
        gegevens.put("Maximaal aantal plaatsen", getMaximumAantalPlaatsen());
        gegevens.put("Sessie open?", isGeopend());
        gegevens.put("Verantwoordelijke", getVerantwoordelijke().getGebruikersnaam());
        /*gegevens.put("Aankondigingen", (List<IAankondiging>) getIAankondigingenSessie());
        gegevens.put("Media", (List<IMedia>) getIMediaBijSessie());
        gegevens.put("Feedback", (List<IFeedback>)getIFeedbackSessie());
        gegevens.put("Inschrijvingen", (List<IInschrijving>) getIIngeschrevenGebruikers());*/
        return gegevens;
    }

    @Override
    public int getAantalAanwezigen() {
        return (int) inschrijvingen.stream().filter(i -> i.isStatusAanwezigheid()).collect(Collectors.toList()).size();
    }

    @Override
    public int getBeschikbarePlaatsen() {
        return maximumAantalPlaatsen - inschrijvingen.size();
    }

    @Override
    public String toString_Kalender() {
        return String.format("%02d:%02d: %s", startSessie.getHour(), startSessie.getMinute(), getTitel());
    }

    public void updateSessie(Map<String, String> veranderingenMap, List<ILokaal> lokaal){
        veranderingenMap.forEach((key, value) -> {
            switch(key){
                case "naamverantwoordelijke":
                    setNaamGastspreker(value);
                    break;
                case "titel":
                    setTitel(value);
                    break;
                case "naamGastspreker":
                    setNaamGastspreker(value);
                    break;
                case "lokaal":
                    String[] str = value.split(",");
                    setLokaal((Lokaal)lokaal.stream().filter(e -> e.getLokaalCode().equals(str[0])).findFirst().orElse(null));
                    break;
                case "start":
                    //s.setStartSessie(LocalDateTime.parse(value));
                    break;
                case "eind":
                    //s.setEindeSessie(LocalDateTime.parse(value));
                    break;
                case "maxPlaatsen":
                    setMaximumAantalPlaatsen(Integer.parseInt(value));
                    break;
            }

        });
    }

    public void verwijderMedia(Media mediaOud) {
        media.remove(mediaOud);
    }

    public void verwijderFeedback(Feedback feedbackOud) {
        feedback.remove(feedbackOud);
    }

    public void verwijderAankondiging(Aankondiging aankondigingOud) {
        aankondigingen.remove(aankondigingOud);
    }

    public void verwijderInschrijving(Inschrijving inschrijvingOud) {
        inschrijvingen.remove(inschrijvingOud);
    }

    public String[] toArray (){
          String [] arr = new String []{titel, startSessie.toString(), eindeSessie.toString(),Integer.toString(maximumAantalPlaatsen), Integer.toString(academiejaar), Boolean.toString(geopend)};
        return arr;
    }
}
