package domein;

import com.sun.istack.NotNull;
import domein.gebruiker.Gebruiker;
import domein.interfacesDomein.IFeedback;
import domein.interfacesDomein.IGebruiker;
import domein.sessie.Sessie;
import exceptions.domein.FeedbackException;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "feedback")
public class Feedback implements IFeedback {

    //region Variabelen
    //Primairy key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedbackKey")
    @GenericGenerator(
            name = "feedbackKey",
            strategy = "domein.JPAIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = JPAIdGenerator.VALUE_PREFIX_PARAMETER, value = "F1920-"),
                    @org.hibernate.annotations.Parameter(name = JPAIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%06d")})
    @NotNull
    private String feedbackId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Sessie sessie;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Gebruiker gebruiker;
    @NotNull
    private String tekst;
    private LocalDate date;
    private boolean verwijderd = false;

    //endregion

    //region Constructor

    /**
     * Constructor voor JPA
     */
    protected Feedback() {
    }

    /**
     * Default constructor voor feedback
     *
     * @param gebruiker (Gebruiker) ==> de gebruiker die de feedback plaatst
     * @param tekst     (String) ==> de boodschap van de feedback
     */
    public Feedback(Sessie sessie, Gebruiker gebruiker, String tekst, LocalDate date) {
        setSessie(sessie);
        setTekst(tekst);
        setGebruiker(gebruiker);
        setDate(date);
    }
    //endregion

    //region Setters

    public void setSessie(Sessie sessie) {
        if (sessie == null) {
            throw new FeedbackException();
        }
        this.sessie = sessie;
    }

    private void setTekst(String tekst) {
        if (tekst == null || tekst.isBlank()) {
            throw new FeedbackException("Inhoud van feedback mag niet leeg zijn");
        }
        this.tekst = tekst;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        if (gebruiker == null) {
            throw new FeedbackException();
        }
        this.gebruiker = gebruiker;
    }

    public void setVerwijderd(boolean verwijderd) {
        this.verwijderd = verwijderd;
    }

    public void setDate(LocalDate date) {
        /*if(date.isBefore(LocalDate.now()) || !sessie.getCurrentState().getStatus().equalsIgnoreCase("gesloten")){
            throw new FeedbackException("Feedback; Feedback kan niet gegeven worden in het verleden");
        }*/
        this.date = date;
    }

    //endregion

    //region Getters
    @Override
    public String getTekst() {
        return tekst;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    @Override
    public IGebruiker getIGebruiker() {
        return gebruiker;
    }

    @Override
    public String getFeedbackId() {
        return feedbackId;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean getVerwijderd(){ return verwijderd;}

    //endregion

    //region Equals & Hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(feedbackId, feedback.feedbackId);
    }

    @Override
    public int hashCode() { return Objects.hash(feedbackId); }
    //endregion

    //region toString
    @Override
    public String toString() {
        return String.format("%s - %s", feedbackId, gebruiker.getNaam());
    }

    @Override
    public String toString_Compleet() {
        return String.format("Feedback: %s%nGeplaatst door: %s%nInhoud: %s%n", feedbackId, gebruiker.getNaam(), tekst);
    }

    /**
     * @param gegevens (String inhoud, Gebruiker gebruiker)
     */
    public void update(List<Object> gegevens) {
        try {
            if (gegevens.get(0) != null && !((String) gegevens.get(0)).isBlank()) {
                setTekst((String) gegevens.get(0));
            }
        } catch (Exception e) {
            throw new FeedbackException("Update");
        }
    }
    //endregion
}
