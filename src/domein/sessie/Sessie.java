package domein.sessie;

import com.sun.istack.NotNull;
import domein.*;
import domein.enums.SessieStatus;
import domein.gebruiker.Gebruiker;
import domein.interfacesDomein.*;
import domein.Lokaal;
import exceptions.domein.SessieException;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@Entity
@Table(name = "sessie")
public class Sessie implements ISessie, Serializable {
    private static final long serialVersionUID = -1929570926684580330L;

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
    @NotNull
    private String titel;
    private String naamGastspreker;
    @NotNull
    private LocalDateTime startSessie;
    @NotNull
    private LocalDateTime eindeSessie;
    @NotNull
    private int maximumAantalPlaatsen;

    @ManyToOne(fetch = FetchType.LAZY)
    private Academiejaar academiejaar;

    private boolean verwijderd = false;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sessie", fetch = FetchType.LAZY)
    private List<Media> media;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sessie", fetch = FetchType.LAZY)
    private List<Inschrijving> inschrijvingen;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sessie", fetch = FetchType.LAZY)
    private List<Aankondiging> aankondigingen;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sessie", fetch = FetchType.LAZY)
    private List<Feedback> feedback;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotNull
    private Lokaal lokaal;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotNull
    private Gebruiker verantwoordelijke;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private SessieState currentState;

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
    public Sessie(String titel, LocalDateTime startSessie, LocalDateTime eindeSessie, Lokaal lokaal, Gebruiker verantwoordelijke, Academiejaar academiejaar) {
        setVerantwoordelijke(verantwoordelijke);
        setTitel(titel);
        setStartSessie(startSessie);
        setEindeSessie(eindeSessie);
        controleData();
        setLokaal(lokaal);
        setMaximumAantalPlaatsen(this.lokaal.getAantalPlaatsen());
        setNaamGastspreker("Onbekend");
        initLijsten();
        setAcademiejaar(academiejaar);
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
            throw new SessieException("Titel;Titel mag niet leeg zijn.");
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
            throw new SessieException("MaximumPlaatsen.Aantal plaatsen is overschrijd limiet lokaal.");
        }
        this.maximumAantalPlaatsen = maximumAantalPlaatsen;

    }

    public void setVerantwoordelijke(Gebruiker verantwoordelijke) {
        this.verantwoordelijke = verantwoordelijke;

    }

    public void setLokaal(Lokaal lokaal) {
        if (lokaal == null) {
            throw new SessieException("Lokaal;Lokaal mag niet null zijn.");
        }
        this.lokaal = lokaal;

    }

    public void setVerwijderd(boolean verwijderd) {
        this.verwijderd = verwijderd;

    }

    private void setAcademiejaar(Academiejaar academiejaar) {
        if (academiejaar == null){
            throw new SessieException("Academiejaar");
        }
        this.academiejaar = academiejaar;
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
    public List<IMedia> getIMediaBijSessie() {

        return (List<IMedia>) (Object) this.media;
    }

    @Override
    public List<IInschrijving> getIIngeschrevenGebruikers() {
        return (List<IInschrijving>) (Object) this.inschrijvingen;
    }

    @Override
    public List<IAankondiging> getIAankondigingenSessie() {
        return (List<IAankondiging>) (Object) this.aankondigingen;
    }

    @Override
    public List<IFeedback> getIFeedbackSessie() {

        return (List<IFeedback>) (Object) this.feedback;
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
        return academiejaar.getCode();
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

    //HARDCODE
    public String toString_Overzicht() {
        return String.format("%s. %s - %s - %s -> %s - %s", sessieId, verantwoordelijke.getNaam(), titel, startSessie.toString(),
                eindeSessie.toString(), true ? String.format("%s%d", "aantal vrije plaatsen: ", aantalVrijePlaatsen()) : String.format("%s%d", "aantal aanwezigheden: ", aantalAanwezigenNaSessie()));
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
        gegevens.put("Verantwoordelijke", getVerantwoordelijke().getGebruikersnaam());
        /*gegevens.put("Aankondigingen", (List<IAankondiging>) getIAankondigingenSessie());
        gegevens.put("Media", (List<IMedia>) getIMediaBijSessie());
        gegevens.put("Feedback", (List<IFeedback>)getIFeedbackSessie());
        gegevens.put("Inschrijvingen", (List<IInschrijving>) getIIngeschrevenGebruikers());*/
        return gegevens;
    }

    @Override
    public int getAantalAanwezigen() {
        return inschrijvingen.stream().filter(i -> i.isStatusAanwezigheid()).collect(Collectors.toList()).size();
    }

    @Override
    public int getBeschikbarePlaatsen() {
        return maximumAantalPlaatsen - inschrijvingen.size();
    }

    @Override
    public String toString_Kalender() {
        return String.format("%02d:%02d: %s", startSessie.getHour(), startSessie.getMinute(), getTitel());
    }

    /**
     * @param gegevens (Gebruiker gebruiker, String titel, LocalDateTime start, LocalDateTime einde, Lokaal lokaal, String gastspreker)
     */
    public void update(List<Object> gegevens) { //aantal plaatsen moet nog veranderen
        try {
            if (gegevens.get(0) != null) {
                setVerantwoordelijke((Gebruiker) gegevens.get(0));
            }
            if (gegevens.get(1) != null && !((String) gegevens.get(1)).isBlank()) {
                setTitel((String) gegevens.get(1));
            }
            if (gegevens.get(2) != null) {
                setStartSessie((LocalDateTime) gegevens.get(2));
            }
            if (gegevens.get(3) != null) {
                setEindeSessie((LocalDateTime) gegevens.get(3));
            }
            if (gegevens.get(4) != null) {
                setLokaal((Lokaal) gegevens.get(4));
            }
            if (gegevens.get(5) != null && !((String) gegevens.get(5)).isBlank()) {
                setNaamGastspreker((String) gegevens.get(5));
            }
            if (gegevens.get(6) != null && !((String) gegevens.get(6)).isBlank()) {
                setMaximumAantalPlaatsen((Integer) gegevens.get(6));
            }
            controleData();
        } catch (Exception e) {
            throw new SessieException("Update");
        }
    }
/*
    public void updateSessie(Map<String, String> veranderingenMap, List<ILokaal> lokaal) {
        veranderingenMap.forEach((key, value) -> {
            switch (key) {
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
                    setLokaal((Lokaal) lokaal.stream().filter(e -> e.getLokaalCode().equals(str[0])).findFirst().orElse(null));
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
 */

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

    public String[] toArray() {
        String[] arr = new String[]{titel, startSessie.toString(), eindeSessie.toString(), Integer.toString(maximumAantalPlaatsen), String.valueOf(academiejaar.getCode())};
        return arr;
    }

    //region State
    public void setState(String state) {
        setState(Arrays.stream(SessieStatus.values()).filter(s -> s.toString().equals(state)).findFirst().orElse(null));
    }

    private void setState(SessieStatus status) {
        if (status == null) {
            status = SessieStatus.NIET_ZICHTBAAR;
        }
        switch (status) {
            case OPEN:
                toState(new OpenState(this));
                break;
            case GESLOTEN:
                toState(new GeslotenState(this));
                break;
            case ZICHTBAAR:
                toState(new ZichtbaarState(this));
                break;
            default:
            case NIET_ZICHTBAAR:
                toState(new NietZichtbaarState(this));
                break;
        }
    }

    public void sessieZichtBaar() {
        if (currentState.getStatus().equals("NIET ZICHTBAAR")) {
            toState(new ZichtbaarState(this));
        } else {
            throw new SessieException();
        }
    }

    public void sessieNietZichtBaar() {
        if (currentState.getStatus().equals("ZICHTBAAR")) {
            toState(new NietZichtbaarState(this));
        } else {
            throw new SessieException();
        }
    }

    public void sessieOpen() {
        if (currentState.getStatus().equals("ZICHTBAAR") || currentState.getStatus().equals("GESLOTEN")) {
            toState(new OpenState(this));
        } else {
            throw new SessieException();
        }
    }

    public void sessieGesloten() {
        if (currentState.getStatus().equals("OPEN") && startSessie.isBefore(LocalDateTime.now())) {
            toState(new GeslotenState(this));
        } else {
            throw new SessieException();
        }
    }

    private void toState(SessieState state) {
        currentState = state;
    }
    //endregion
}
