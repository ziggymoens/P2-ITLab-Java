package persistentie;

import domein.*;
import persistentie.repositories.*;

import java.util.List;
import java.util.Set;

public class PersistentieController {
    //region Variabelen
    private SessieRepository sessieRepository;
    private GebruikerRepository gebruikerRepository;
    private LokaalRepository lokaalRepository;
    private AankondigingRepository aankondigingRepository;
    private FeedbackRepository feedbackRepository;
    //endregion

    //region Constructor
    public PersistentieController() {
        Connection.setONLINE(false);
        initRepos();
    }
    //endregion

    public void initRepos() {
        gebruikerRepository = new GebruikerRepository();
        lokaalRepository = new LokaalRepository();
        sessieRepository = new SessieRepository();
        aankondigingRepository = new AankondigingRepository();
        feedbackRepository = new FeedbackRepository();
        sessieRepository.setPersistenieController(this);
        aankondigingRepository.setPersistenieController(this);
        feedbackRepository.setPersistenieController(this);
        initData();
    }

    private void initData() {
        gebruikerRepository.initData();
        lokaalRepository.initData();
        sessieRepository.initData();
        aankondigingRepository.initData();
        feedbackRepository.initData();
    }

    //region Getters
    public Set<Gebruiker> getGebruikers() {
        return gebruikerRepository.getGebruikerSet();
    }

    public Set<Lokaal> getLokalen() {
        return lokaalRepository.getLokalenSet();
    }

    public List<Sessie> getSessies() {
        return sessieRepository.getSessies();
    }

    public List<Aankondiging> getAankondigingen() {
        return aankondigingRepository.getAankondigingen();
    }

    public List<Feedback> getFeedback() {
        return feedbackRepository.getFeedback();
    }
    //endregion

    //region Gebruiker
    public void beheerGebruiker(String optie, Gebruiker g) {
        gebruikerRepository.beheerGebruiker(optie, g);
    }

    public Gebruiker geefGebruikerMetCode(String s) {
        return gebruikerRepository.getGebruikerSet().stream().filter(g -> g.getGebruikersnaam().equals(s)).findFirst().orElse(null);
    }
    //endregion

    //region Sessie
    public void beheerSessie(String optie, Sessie s) {
        sessieRepository.beheerSessie(optie, s);
    }

    public Sessie geefSessieMetId(String sessieId) {
        return sessieRepository.getSessies().stream().filter(s -> s.getSessieId().equals(sessieId)).findFirst().orElse(null);
    }
    //endregion

    //region Lokaal
    public void beheerLokaal(String optie, Lokaal l) {
        lokaalRepository.beheerLokaal(optie, l);
    }

    public Lokaal geefLokaalMetCode(String s) {
        return lokaalRepository.getLokalenSet().stream().filter(l -> l.getLokaalCode().equals(s)).findFirst().orElse(null);
    }
    //endregion

    //region Aankondiging
    public void beheerAankondiging(String optie, Aankondiging a) {
        aankondigingRepository.beheerAankondiging(optie, a);
    }

    public Aankondiging geefAankondigingMetId(String aankondigingsId) {
        return aankondigingRepository.getAankondigingen().stream().filter(a -> a.getAankondigingsId().equals(aankondigingsId)).findFirst().orElse(null);
    }
    //endregion

    //region Feedback
    public void beheerFeedback(String optie, Feedback feedback) {
        feedbackRepository.beheerFeedback(optie, feedback);
    }

    public Feedback geefFeedbackMetId(String feedbackId) {
        return feedbackRepository.getFeedback().stream().filter(a -> a.getFeedbackId().equals(feedbackId)).findFirst().orElse(null);
    }

    //endregion

    public void schrijfAllesWeg() {
        gebruikerRepository.schrijfWeg();
        lokaalRepository.schrijfWeg();
        sessieRepository.schrijfWeg();
        aankondigingRepository.schrijfWeg();
        feedbackRepository.schrijfWeg();
    }
}
