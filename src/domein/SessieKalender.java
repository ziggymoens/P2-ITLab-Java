package domein;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.util.List;

public class SessieKalender {

    public final String PERSISTENCE_UNIT_NAME = "ITLab";
    private EntityManager em;
    private EntityManagerFactory emf;
    private int academiejaar;

    public SessieKalender(int academiejaar) {
        this.academiejaar = academiejaar;
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
        return (List<Sessie>) em.createQuery("select s from Sessie s where s.verwijderd = false and academiejaar = ?1").setParameter(1, academiejaar).getResultList();
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
        return (List<Lokaal>) em.createQuery("select l from Lokaal l where l.verwijderd = false").getResultList();
    }

    public Lokaal geefLokaalById(String lokaalCode) {
        return (Lokaal) em.createQuery("select l from Lokaal l where lokaalCode = ?1").setParameter(1, lokaalCode).getResultList().get(0);
    }
    //endregion

    //region Gebruiker
    public void voegGebruikerToe(Gebruiker gebruiker) {
        em.getTransaction().begin();
        em.persist(gebruiker);
        em.getTransaction().commit();
    }

    public void verwijderGebruiker(Gebruiker gebruiker){
        em.getTransaction().begin();
        gebruiker.setVerwijderd(true);
        em.getTransaction().commit();
    }

    public Gebruiker geefGebruikerById(String gebruikerId) {
        return (Gebruiker) em.createQuery("select g from Gebruiker g where gebruikersnaam = ?1").setParameter(1, gebruikerId).getResultList().get(0);
    }

    public List<Gebruiker> geefAlleGebruikers(){
        return (List<Gebruiker>) em.createQuery("select g from Gebruiker g where g.verwijderd = false").getResultList();
    }
    //endregion

    //region Aankondiging
    public void voegAankondigingToe(Aankondiging aankondiging) {
        em.getTransaction().begin();
        em.persist(aankondiging);
        em.getTransaction().commit();
    }

    public void verwijderGebruiker(Aankondiging aankondiging, Sessie sessie){
        em.getTransaction().begin();
        sessie.addAankondiging(aankondiging);
        em.getTransaction().commit();
    }

    public Aankondiging geefAankondigingById(String aankondigingsId) {
        return (Aankondiging) em.createQuery("select a from Aankondiging a where aankondigingsId = ?1").setParameter(1, aankondigingsId).getResultList().get(0);
    }

    public List<Gebruiker> geefAlleAankondigingen(){
        return (List<Gebruiker>) em.createQuery("select a from Aankondiging a where a.verwijderd = false").getResultList();
    }
    //endregion


    //region Feedback
    public void voegFeedbackToe(Feedback feedback, Sessie sessie) {
        em.getTransaction().begin();
        sessie.addFeedback(feedback);
        em.getTransaction().commit();
    }

    public void verwijderFeedback(Feedback feedback){
        em.getTransaction().begin();
        feedback.setVerwijderd(true);
        em.getTransaction().commit();
    }

    public Feedback geefFeedbackById(String feedbackId) {
        return (Feedback) em.createQuery("select f from Feedback f where feedbackId = ?1").setParameter(1, feedbackId).getResultList().get(0);
    }

    public List<Feedback> geefAlleFeedback(){
        return (List<Feedback>) em.createQuery("select a from Feedback a where a.verwijderd = false").getResultList();
    }
    //endregion

    //region Inschrijving
    public void voegInschrijvingToe(Inschrijving inschrijving, Sessie sessie) {
        em.getTransaction().begin();
        sessie.addInschrijving(inschrijving);
        em.getTransaction().commit();
    }

    public void verwijderInschrijving(Inschrijving inschrijving){
        em.getTransaction().begin();
        inschrijving.setVerwijderd(true);
        em.getTransaction().commit();
    }

    public Feedback geefInschrijvingById(String inschrijvingsId) {
        return (Feedback) em.createQuery("select i from Inschrijving i where inschrijvingsId = ?1").setParameter(1, inschrijvingsId).getResultList().get(0);
    }

    public List<Inschrijving> geefAlleInschrijvingen(){
        return (List<Inschrijving>) em.createQuery("select a from Feedback a where a.verwijderd = false").getResultList();
    }
    //endregion
}
