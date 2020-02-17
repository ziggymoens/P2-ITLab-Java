package domein;

import exceptions.FeedbackException;

import java.util.Objects;

public class Feedback {
    //Primairy key
    private String feedbackId;

    private Gebruiker gebruiker;
    private String tekst;

    public Feedback(Gebruiker gebruiker, String tekst) {
        setGebruiker(gebruiker);
        setTekst(tekst);
}

    public void setGebruiker(Gebruiker gebruiker) {
        if(gebruiker == null){
            throw new FeedbackException();
        }
        this.gebruiker = gebruiker;
    }

    public void setTekst(String tekst) {
        if(tekst == null || tekst.isBlank()){
            throw new FeedbackException();
        }
        this.tekst = tekst;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public String getTekst() {
        return tekst;
    }

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
}
