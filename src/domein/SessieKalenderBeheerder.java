package domein;

import domein.domeinklassen.*;
import domein.interfacesDomein.IMedia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SessieKalenderBeheerder {
    public final String PERSISTENCE_UNIT_NAME = "ITLab";
    private EntityManager em;
    private EntityManagerFactory emf;
    private final List<SessieKalender> sessieKalenders = new ArrayList<>();
    private final List<Sessie> sessies = new ArrayList<>();
    private final Set<Gebruiker> gebruikers = new HashSet<>();
    private final Set<Lokaal> lokalen = new HashSet<>();

    public SessieKalenderBeheerder() {
        initializePersistentie();
    }

    private void initializePersistentie() {
        openPersistentie();
        SessieKalenderData sessieKalenderData = new SessieKalenderData(this);
        sessieKalenderData.populeerDataGebruikers();
        sessieKalenderData.populeerDataLokalen();
        sessieKalenderData.populeerDataSessie();
        sessieKalenderData.populeerDataAankondigingen();
        sessieKalenderData.populeerDataFeedback();
        sessieKalenderData.populeerDataInschrijvingen();
        sessieKalenderData.populeerDataMedia();
        sessieKalenderData.populeerDataHerinneringen();
        sessieKalenderData.populeerDataSessieKalender();
        populeer1920Agenda();
    }

    public void populeer1920Agenda() {
        SessieKalender sessieKalender = sessieKalenders.stream().filter(s -> s.getAcademiajaar() == 1920).findFirst().orElse(null);
        sessieKalender.addSessies(sessies.stream().filter(sessie -> sessie.getSessieId().matches("^S1920-.*$")).collect(Collectors.toList()));
        em.getTransaction().begin();
        em.merge(sessieKalender);
        em.getTransaction().commit();
    }

    private void openPersistentie() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
    }

    public void closePersistentie() {
        em.close();
        emf.close();
    }

    //region Sessie
    public List<Sessie> geefAlleSessies() {
        return sessies;
    }

    public void addSessie(Sessie sessie) {
        sessies.add(sessie);
        em.getTransaction().begin();
        em.persist(sessie);
        em.getTransaction().commit();
    }

    public void updateSessie(Sessie sessieOud, Sessie sessieNieuw) {
        sessies.remove(sessieOud);
        sessies.add(sessieNieuw);
        em.getTransaction().begin();
        em.merge(sessieNieuw);
        em.getTransaction().commit();
    }

    public void verwijderSessie(Sessie sessie) {
        sessies.remove(sessie);
        em.getTransaction().begin();
        em.remove(sessie);
        em.getTransaction().commit();
    }
    //endregion

    //region Media
    public void addMediaSessie(String sessieid, String gebruikersnaam, String locatie, String type) {
        Gebruiker gebruiker = gebruikers.stream().filter(g -> g.getGebruikersnaam().equals(gebruikersnaam)).findFirst().orElse(null);
        Sessie s = sessies.stream().filter(ss -> ss.getSessieId().equals(sessieid)).findFirst().orElse(null);
        Media m = new Media(gebruiker, locatie, type);
        s.addMedia(m);
        em.getTransaction().begin();
        em.persist(m);
        em.persist(s);
        em.getTransaction().commit();
    }

    public void addMediaSessie(Sessie s, Media m) {
        s.addMedia(m);
        em.getTransaction().begin();
        em.persist(m);
        em.persist(s);
        em.getTransaction().commit();
    }

    public void updateMedia(Media mediaOud, Media mediaNieuw) {
        Sessie sessie = sessies.stream().filter(s -> s.getMedia().contains(mediaOud)).findFirst().orElse(null);
        sessie.verwijderMedia(mediaOud);
        sessie.addMedia(mediaNieuw);
        em.getTransaction().begin();
        em.merge(mediaNieuw);
        em.merge(sessie);
        em.getTransaction().commit();
    }

    public void verwijderMedia(Media media) {
        Sessie sessie = sessies.stream().filter(s -> s.getMedia().contains(media)).findFirst().orElse(null);
        sessie.verwijderMedia(media);
        em.getTransaction().begin();
        em.remove(media);
        em.merge(sessie);
        em.getTransaction().commit();
    }
    //endregion

    //region Feedback
    public void addFeedbackSessie(String sessieid, String gebruikersnaam, String tekst) {
        Gebruiker gebruiker = gebruikers.stream().filter(g -> g.getGebruikersnaam().equals(gebruikersnaam)).findFirst().orElse(null);
        Sessie s = sessies.stream().filter(ss -> ss.getSessieId().equals(sessieid)).findFirst().orElse(null);
        Feedback f = new Feedback(gebruiker, tekst);
        s.addFeedback(f);
        em.getTransaction().begin();
        em.persist(f);
        em.persist(s);
        em.getTransaction().commit();
    }

    public void updateFeedback(Feedback feedbackOud, Feedback feedbackNieuw) {
        Sessie sessie = sessies.stream().filter(s -> s.getFeedback().contains(feedbackOud)).findFirst().orElse(null);
        sessie.verwijderFeedback(feedbackOud);
        sessie.addFeedback(feedbackNieuw);
        em.getTransaction().begin();
        em.merge(feedbackNieuw);
        em.merge(sessie);
        em.getTransaction().commit();
    }

    public void verwijderFeedback(Feedback feedback) {
        Sessie sessie = sessies.stream().filter(s -> s.getFeedback().contains(feedback)).findFirst().orElse(null);
        sessie.verwijderFeedback(feedback);
        em.getTransaction().begin();
        em.remove(feedback);
        em.merge(sessie);
        em.getTransaction().commit();
    }
    //endregion

    //region Aankondiging
    public void addAankondigingSessie(String sessieid, String gebruikersnaam, String tekst, boolean herinnering, int dagen) {
        Gebruiker gebruiker = gebruikers.stream().filter(g -> g.getGebruikersnaam().equals(gebruikersnaam)).findFirst().orElse(null);
        Sessie s = sessies.stream().filter(ss -> ss.getSessieId().equals(sessieid)).findFirst().orElse(null);
        Aankondiging a = new Aankondiging(gebruiker, LocalDateTime.now(), tekst, herinnering, dagen);
        s.addAankondiging(a);
        em.getTransaction().begin();
        em.persist(a);
        em.persist(s);
        em.getTransaction().commit();
    }

    public void updateAankondiging(Aankondiging aankondigingOud, Aankondiging aankondigingNieuw) {
        Sessie sessie = sessies.stream().filter(s -> s.getAankondigingen().contains(aankondigingOud)).findFirst().orElse(null);
        sessie.verwijderAankondiging(aankondigingOud);
        sessie.addAankondiging(aankondigingNieuw);
        em.getTransaction().begin();
        em.merge(aankondigingNieuw);
        em.merge(sessie);
        em.getTransaction().commit();
    }

    public void verwijderAankondiging(Aankondiging aankondiging) {
        Sessie sessie = sessies.stream().filter(s -> s.getAankondigingen().contains(aankondiging)).findFirst().orElse(null);
        sessie.verwijderAankondiging(aankondiging);
        em.getTransaction().begin();
        em.remove(aankondiging);
        em.merge(sessie);
        em.getTransaction().commit();
    }
    //endregion

    //region Inschrijving
    public void addInschrijvingSessie(String sessieid, String gebruikersnaam, LocalDateTime inschrijving) {
        Gebruiker gebruiker = gebruikers.stream().filter(g -> g.getGebruikersnaam().equals(gebruikersnaam)).findFirst().orElse(null);
        Sessie s = sessies.stream().filter(ss -> ss.getSessieId().equals(sessieid)).findFirst().orElse(null);
        Inschrijving i = new Inschrijving(gebruiker, inschrijving);
        s.addInschrijving(i);
        em.getTransaction().begin();
        em.persist(i);
        em.persist(s);
        em.getTransaction().commit();
    }

    public void updateInschrijving(Inschrijving inschrijvingOud, Inschrijving InschrijvingNieuw) {
        Sessie sessie = sessies.stream().filter(s -> s.getFeedback().contains(inschrijvingOud)).findFirst().orElse(null);
        sessie.verwijderInschrijving(inschrijvingOud);
        sessie.addInschrijving(InschrijvingNieuw);
        em.getTransaction().begin();
        em.merge(InschrijvingNieuw);
        em.merge(sessie);
        em.getTransaction().commit();
    }

    public void verwijderInschrijving(Inschrijving inschrijving) {
        Sessie sessie = sessies.stream().filter(s -> s.getInschrijvingen().contains(inschrijving)).findFirst().orElse(null);
        sessie.verwijderInschrijving(inschrijving);
        em.getTransaction().begin();
        em.remove(inschrijving);
        em.merge(sessie);
        em.getTransaction().commit();
    }
    //endregion

    //region Gebruiker
    public Set<Gebruiker> geefAlleGebruikers() {
        return gebruikers;
    }

    public void addGebruiker(Gebruiker gebruiker) {
        gebruikers.add(gebruiker);
        em.getTransaction().begin();
        em.persist(gebruiker);
        em.getTransaction().commit();
    }

    public void updateGebruiker(Gebruiker gebruikerOud, Gebruiker gebruikerNieuw) {
        gebruikers.remove(gebruikerOud);
        gebruikers.add(gebruikerNieuw);
        em.getTransaction().begin();
        em.merge(gebruikerNieuw);
        em.getTransaction().commit();
    }

    public void verwijderGebruiker(Gebruiker gebruiker) {
        gebruikers.remove(gebruiker);
        em.getTransaction().begin();
        em.remove(gebruiker);
        em.getTransaction().commit();
    }

    private Set<Gebruiker> getGebruikers() {
        return gebruikers;
    }

    //endregion
    //region Lokaal
    public Set<Lokaal> geefAlleLokalen() {
        return lokalen;
    }

    public void addLokaal(Lokaal lokaal) {
        lokalen.add(lokaal);
        em.getTransaction().begin();
        em.persist(lokaal);
        em.getTransaction().commit();
    }

    public void updateLokaal(Lokaal lokaalOud, Lokaal lokaalNieuw) {
        lokalen.remove(lokaalOud);
        lokalen.add(lokaalNieuw);
        em.getTransaction().begin();
        em.merge(lokaalNieuw);
        em.getTransaction().commit();
    }

    public void verwijderLokaal(Lokaal lokaal) {
        lokalen.remove(lokaal);
        em.getTransaction().begin();
        em.remove(lokaal);
        em.getTransaction().commit();
    }

    //endregion
    //region SessieKalender
    public void addSessieKalender(SessieKalender sessieKalender) {
        sessieKalenders.add(sessieKalender);
        em.getTransaction().begin();
        em.persist(sessieKalender);
        em.getTransaction().commit();
    }

    public List<SessieKalender> geefAlleSessieKalenders() {
        return sessieKalenders;
    }


    //endregion

}
