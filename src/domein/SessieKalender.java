package domein;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.util.List;

public class SessieKalender {

    public final String PERSISTENCE_UNIT_NAME = "ITLab";
    private EntityManager em;
    private EntityManagerFactory emf;

    public SessieKalender() {
        initPersistentie();
    }

    private void initPersistentie() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
    }

    //region Sessie
    public void voegSessieToe(Sessie sessie){
        em.getTransaction().begin();
        em.persist(sessie);
        em.getTransaction().commit();
    }

    public void verwijderSessie(Sessie sessie){
        em.getTransaction().begin();
        sessie.setVerwijderd(true);
        em.getTransaction().commit();
    }

    public List<Sessie> geefAlleSessiesKalender(){
        return em.createQuery("select s from Sessie s").getResultList();
    }
    //endregion

    //region Lokaal
    public void voegLokaalToe(Lokaal lokaal){
        em.getTransaction().begin();
        em.persist(lokaal);
        em.getTransaction().commit();
    }

    public void verwijderLokaal(Lokaal lokaal){
        em.getTransaction().begin();
        lokaal.setVerwijderd(true);
        em.getTransaction().commit();
    }

    public List<Lokaal> geefAlleLokalen(){
        return em.createQuery("select l from Lokaal l").getResultList();
    }

    public Lokaal geefLokaalById(String lokaalCode) {
        return (Lokaal) em.createQuery("select l from Lokaal l where lokaalCode = ?1").setParameter(1, lokaalCode).getResultList().get(0);
    }
    //endregion
}
