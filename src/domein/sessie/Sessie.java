package domein.sessie;

import com.sun.istack.NotNull;
import domein.*;
import domein.gebruiker.Gebruiker;
import domein.interfacesDomein.*;
import exceptions.domein.SessieException;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import userinterface.main.IObserver;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@Entity
@Table(name = "sessie")
public class Sessie implements ISessie, Serializable, IObservable {
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
    @Transient
    private LocalDate datum;
    @Transient
    private String datumString;
    @NotNull
    private LocalDateTime startSessie;
    @Transient
    private LocalTime startUur;
    @NotNull
    private LocalDateTime eindeSessie;
    @Transient
    private LocalTime eindeUur;
    @NotNull
    private int maximumAantalPlaatsen;

    private String beschrijving;

    @Transient
    private int aantalAanwezigen;
    @Transient
    private String stad;
    @Transient
    private Set<IObserver> observers = new HashSet<>();
    @Transient
    private Map<String,String>fouten = new HashMap<>();

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
    public Sessie(String titel, String beschrijving, LocalDateTime startSessie, LocalDateTime eindeSessie, Lokaal lokaal, Gebruiker verantwoordelijke, Academiejaar academiejaar, String state) {
        setVerantwoordelijke(verantwoordelijke);
        setTitel(titel);
        setBeschrijving(beschrijving);
        setDatumUurSessie(startSessie, eindeSessie);
        setLokaal(lokaal);
        //AANPASSEN
        setMaximumAantalPlaatsen(this.lokaal.getAantalPlaatsen());
        setNaamGastspreker("Onbekend");
        initLijsten();
        setAcademiejaar(academiejaar);
        setState(state);
    }

    public Sessie(String titel, String beschrijving, LocalDateTime startSessie, LocalDateTime eindeSessie, Lokaal lokaal, Gebruiker verantwoordelijke, Academiejaar academiejaar) {
        this(titel, beschrijving, startSessie, eindeSessie, lokaal, verantwoordelijke, academiejaar, null);
    }

    //endregion

    //region Init
    private void initLijsten() {
        this.media = new ArrayList<>();
        this.inschrijvingen = new ArrayList<>();
        this.aankondigingen = new ArrayList<>();
        this.feedback = new ArrayList<>();
    }

    public void initData() {
        this.datum = startSessie.toLocalDate();
        this.startUur = startSessie.toLocalTime();
        this.eindeUur = eindeSessie.toLocalTime();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM");
        this.datumString = datum.format(dtf);
        this.stad = lokaal.getStad();
        this.aantalAanwezigen = getAantalAanwezigen();
    }
    //endregion

    //region Setters
    protected void setTitel(String titel) {
        if(titel == null || titel.isBlank() || titel.isEmpty()){
            throw new SessieException();
        }
        this.titel = titel;
    }

    public void setNaamGastspreker(String naamGastspreker) {
        this.naamGastspreker = naamGastspreker;
    }

    protected void setDatumUurSessie(LocalDateTime startSessie, LocalDateTime eindeSessie) {
        if(startSessie == null || eindeSessie == null){
            throw new SessieException();
        }
        if (startSessie.isBefore(LocalDateTime.now())) {
            fouten.put("datum", "Sessie moet in de toekomst zijn.");
            //throw new SessieException("start;Sessie kan niet in het verleden zijn.");
        } else {
            this.startSessie = startSessie;
            this.datum = startSessie.toLocalDate();
            this.startUur = startSessie.toLocalTime();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM");
            this.datumString = datum.format(dtf);
        }
        if (startSessie.toLocalDate().isAfter(eindeSessie.toLocalDate())) {
                fouten.put("uur", "Sessie kan niet eindigen voor hij begonnen is.");
                //throw new SessieException("eind;Sessie kan niet eindigen voor hij begonnen is.");
            }
            if (startSessie.plusMinutes(29).plusSeconds(59).plusNanos(99).isAfter(eindeSessie)) {
                fouten.put("uur", "Sessie duurt minimaal 30 minuten");
                //throw new SessieException("eind;Sessie duurt minimaal 30 minuten.");
            } else if(!fouten.containsKey("uur")){
                this.eindeSessie = eindeSessie;
                this.eindeUur = startSessie.toLocalTime();
            }
        }

    protected void setMaximumAantalPlaatsen(int maximumAantalPlaatsen) {
        if (lokaal.getAantalPlaatsen() < maximumAantalPlaatsen) {
            fouten.put("maxPlaatsen","Aantal plaatsen is overschrijd limiet lokaal.");
            //throw new SessieException("maxPlaatsen;Aantal plaatsen is overschrijd limiet lokaal.");
        }
        if (maximumAantalPlaatsen <= 0) {
            fouten.put("maxPlaatsen","Maximaal aantal plaatsen moet groter zijn als 0.");
            //throw new SessieException("maxPlaatsen;Maximaal aantal plaatsen moet groter zijn als 0.");
        }
        if(!fouten.containsKey("maxPlaatsen")){
        this.maximumAantalPlaatsen = maximumAantalPlaatsen;
        }
    }

    protected void setVerantwoordelijke(Gebruiker verantwoordelijke) {
            this.verantwoordelijke = verantwoordelijke;
    }

    protected void setLokaal(Lokaal lokaal) {
        this.lokaal = lokaal;
        stad = lokaal.getStad();
    }

    protected void setVerwijderd(boolean verwijderd) {
        this.verwijderd = verwijderd;
    }

    protected void setAcademiejaar(Academiejaar academiejaar) {
        if (academiejaar == null) {
            throw new SessieException("Academiejaar");
        }
        this.academiejaar = academiejaar;
    }

    protected void setBeschrijving(String beschrijving) {
        if(beschrijving == null){
            throw new SessieException();
        }
        this.beschrijving = beschrijving;
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
    public LocalDate getDatum() {
        return datum;
    }

    @Override
    public String getDatumString() {
        return datumString;
    }

    @Override
    public LocalTime getStartUur() {
        return startUur;
    }

    @Override
    public LocalTime getEindeUur() {
        return eindeUur;
    }

    @Override
    public String getStad() {
        return lokaal.getStad();
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

    public Map<String,String> getFouten(){
        return this.fouten;
    }

    @Override
    public IAcademiejaar getAcademiejaar() {
        return (IAcademiejaar) academiejaar;
    }

    private boolean isVerwijderd() {
        return verwijderd;
    }

    @Override
    public String getBeschrijving() {
        return beschrijving;
    }

    @Override
    public SessieState getCurrentState() {
        return currentState;
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
    public void update(List<Object> gegevens) {
        this.currentState.update(gegevens);
        if (!(Boolean) gegevens.get(7)) {
            setState("niet zichtbaar");
        } else if ((Boolean) gegevens.get(7)){
            if(!getCurrentState().getStatus().toLowerCase().equals("open") && !getCurrentState().getStatus().toLowerCase().equals("gesloten")){
                setState("zichtbaar");
            }
        }
        if(fouten.isEmpty()) {
            verwittig();
        } else{
            throw new SessieException("Sessie;Update");
        }
    }

    public void verwijder(boolean verwijder) {
        this.currentState.verwijder(verwijder);
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

    public String[] toArray() {
        String[] arr = new String[]{titel, startSessie.toString(), eindeSessie.toString(), Integer.toString(maximumAantalPlaatsen), String.valueOf(academiejaar.getAcademiejaar())};
        return arr;
    }

    //region State
    private void setState(String status) {
        if (status == null || status.isBlank()) {
            throw new SessieException("State;State mag niet leeg zijn.");
        }
        switch (status.toLowerCase()) {
            case "open":
                toState(new OpenState(this));
                break;
            case "gesloten":
                toState(new GeslotenState(this));
                break;
            case "zichtbaar":
                toState(new ZichtbaarState(this));
                break;
            default:
            case "niet zichtbaar":
                toState(new NietZichtbaarState(this));
                break;
        }

    }

    public void toOpenState(){
        setState("open");
        verwittig();
    }
    public void toClosedState(){
        setState("gesloten");
        verwittig();
    }

    private void toState(SessieState state) {
        currentState = state;
    }
    //endregion

    //region observer
    //notify
    public void verwittig() {
        for (IObserver obs : observers) {
            obs.update();
        }
    }

    public void addObserver(IObserver iObserver) {
        this.observers.add(iObserver);
    }

    public void removeObserver(IObserver iObserver) {
        this.observers.remove(iObserver);
    }
    //endregion
}
