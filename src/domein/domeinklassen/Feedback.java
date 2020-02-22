package domein.domeinklassen;

import domein.interfacesDomein.IFeedback;
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

    private String tekst;
    //endregion

    //region Constructor
    /**
     * Constructor voor JPA
     */
    protected Feedback() {
    }

    public Feedback(String tekst) {
        setTekst(tekst);
    }
    //endregion

    //region Setters
    private void setTekst(String tekst) {
        if (tekst == null || tekst.isBlank()) {
            throw new FeedbackException();
        }
        this.tekst = tekst;
    }
    //endregion

    //region Getters
    @Override
    public String getTekst() {
        return tekst;
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
