package domein.domeinklassen;

import domein.interfacesDomein.IFeedback;
import domein.interfacesDomein.IGebruiker;
import exceptions.domein.FeedbackException;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "feedback")
public class Feedback implements IFeedback {

    //region Variabelen
    //Primairy key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;

    @OneToOne
    private Gebruiker gebruiker;
    private String tekst;
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
    public Feedback(Gebruiker gebruiker, String tekst) {
        setTekst(tekst);
        setGebruiker(gebruiker);
    }
    //endregion

    //region Setters
    private void setTekst(String tekst) {
        if (tekst == null || tekst.isBlank()) {
            throw new FeedbackException();
        }
        this.tekst = tekst;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        if (gebruiker == null) {
            throw new FeedbackException();
        }
        this.gebruiker = gebruiker;
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
    public int getFeedbackId() {
        return feedbackId;
    }
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
    public int hashCode() {
        return Objects.hash(feedbackId);
    }
    //endregion

    //region toString
    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId='" + feedbackId + '\'' +
                ", tekst='" + tekst + '\'' +
                '}';
    }
    //endregion
}
