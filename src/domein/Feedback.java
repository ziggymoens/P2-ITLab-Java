package domein;

import domein.interfacesDomein.IFeedback;
import exceptions.domein.FeedbackException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "feedback")
public class Feedback implements IFeedback {

    //region Variabelen
    //Primairy key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String feedbackId;

    private String tekst;
    //endregion

    //region Constructor
    protected Feedback(){}

    public Feedback(String feedbackId, String tekst) {
        setFeedbackId(feedbackId);
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

    private void setFeedbackId(String feedbackId) {
        if(feedbackId == null || feedbackId.isBlank()){
            throw new FeedbackException();
        }
        this.feedbackId = feedbackId;
    }
    //endregion

    //region Getters
    public Gebruiker getGebruiker() {
        throw new UnsupportedOperationException();
    }

    public String getTekst() {
        return tekst;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public Sessie getSessie() {
        throw new UnsupportedOperationException();
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
