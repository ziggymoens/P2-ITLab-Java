package databank;

import domein.Aankondiging;
import domein.SessieKalenderData;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DatabankVerbinding {

    public static final String PERSISTENCE_UNIT_NAME = "ITLab";
    private static EntityManager em;
    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        initializePersistentie();
    }

    private static void initializePersistentie() {
        openPersistentie();

        //SessieKalenderData od = new SessieKalenderData();
        //od.populeerData();
    }

    private static void openPersistentie() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
    }

    public void closePersistentie() {
        em.close();
        emf.close();
    }

    public void addObject(Object o){
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
    }
}