package domein;

import exceptions.domein.FeedbackException;

import java.io.Serializable;
import java.util.Objects;

public class Feedback implements Serializable {
    private static final long serialVersionUID = -7572591287217890928L;

    //region Variabelen
    //Primairy key
    private String feedbackId;

    private Gebruiker gebruiker;
    private String tekst;
    //endregion

    //region Constructor
    public Feedback(Gebruiker gebruiker, String tekst) {
        setGebruiker(gebruiker);
        setTekst(tekst);
    }
    //endregion

    //region Setters
    private void setGebruiker(Gebruiker gebruiker) {
        if (gebruiker == null) {
            throw new FeedbackException();
        }
        this.gebruiker = gebruiker;
    }

    private void setTekst(String tekst) {
        if (tekst == null || tekst.isBlank()) {
            throw new FeedbackException();
        }
        this.tekst = tekst;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }
    //endregion

    //region Getters
    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public String getTekst() {
        return tekst;
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
}
