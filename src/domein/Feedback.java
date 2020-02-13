package domein;

import exceptions.FeedbackException;

public class Feedback {
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
}
