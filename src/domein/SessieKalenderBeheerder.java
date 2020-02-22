package domein;

import domein.domeinklassen.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SessieKalenderBeheerder {
    public final String PERSISTENCE_UNIT_NAME = "ITLab";
    private EntityManager em;
    private EntityManagerFactory emf;
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

    //region add Object to sessie
    public void addMediaSessie(int sessieid, String gebruikersnaam, String locatie) {
        Gebruiker gebruiker = gebruikers.stream().filter(g -> g.getGebruikersnaam().equals(gebruikersnaam)).findFirst().orElse(null);
        Sessie s = sessies.get(sessieid - 1);
        Media m = new Media(gebruiker, locatie);
        s.addMedia(m);
        em.getTransaction().begin();
        em.persist(m);
        em.persist(s);
        em.getTransaction().commit();
    }

    public void addFeedbackSessie(int sessieid, String gebruikersnaam, String tekst) {
        Gebruiker gebruiker = gebruikers.stream().filter(g -> g.getGebruikersnaam().equals(gebruikersnaam)).findFirst().orElse(null);
        Sessie s = sessies.get(sessieid - 1);
        Feedback f = new Feedback(gebruiker, tekst);
        s.addFeedback(f);
        em.getTransaction().begin();
        em.persist(f);
        em.persist(s);
        em.getTransaction().commit();
    }

    public void addAankondigingSessie(int sessieid, String gebruikersnaam, String tekst, boolean herinnering ,int dagen) {
        Gebruiker gebruiker = gebruikers.stream().filter(g -> g.getGebruikersnaam().equals(gebruikersnaam)).findFirst().orElse(null);
        Sessie s = sessies.get(sessieid - 1);
        Aankondiging a = new Aankondiging(gebruiker, LocalDateTime.now(), tekst, herinnering, dagen);
        s.addAankondiging(a);
        em.getTransaction().begin();
        em.persist(a);
        em.persist(s);
        em.getTransaction().commit();
    }

    public void addInschrijvingSessie(int sessieid, String gebruikersnaam, LocalDateTime inschrijving) {
        Gebruiker gebruiker = gebruikers.stream().filter(g -> g.getGebruikersnaam().equals(gebruikersnaam)).findFirst().orElse(null);
        Sessie s = sessies.get(sessieid - 1);
        Inschrijving i = new Inschrijving(gebruiker, inschrijving);
        s.addInschrijving(i);
        em.getTransaction().begin();
        em.persist(i);
        em.persist(s);
        em.getTransaction().commit();
    }

    //endregion
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
    //endregion
}
