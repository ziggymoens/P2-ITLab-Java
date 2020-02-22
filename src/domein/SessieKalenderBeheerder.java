package domein;

import domein.domeinklassen.Gebruiker;
import domein.domeinklassen.Lokaal;
import domein.domeinklassen.Sessie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SessieKalenderBeheerder {
    public final String PERSISTENCE_UNIT_NAME = "ITLab";
    private EntityManager em;
    private EntityManagerFactory emf;
    private List<Sessie> sessies = new ArrayList<>();
    private Set<Gebruiker> gebruikers = new HashSet<>();
    private Set<Lokaal> lokalen = new HashSet<>();

    public SessieKalenderBeheerder() {
        initializePersistentie();
    }

    private void initializePersistentie() {
        openPersistentie();
        SessieKalenderData sessieKalenderData = new SessieKalenderData(this);
        sessieKalenderData.populeerDataGebruikers();
        sessieKalenderData.populeerDataLokalen();
        sessieKalenderData.populeerDataSessie();
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
    public Set<Lokaal> geefAlleLokalen(){return lokalen;}

    public void addLokaal(Lokaal lokaal) {
        lokalen.add(lokaal);
        em.getTransaction().begin();
        em.persist(lokaal);
        em.getTransaction().commit();
    }
    //endregion
}
