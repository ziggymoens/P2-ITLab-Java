package domein;

import domein.enums.Campussen;
import domein.interfacesDomein.IGebruiker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.stream.Collectors;

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

    //region Academiejaar
    public List<String> geefAlleAcademieJaren() {
        return (List<String>) em.createQuery("select DISTINCT academiejaar from Sessie").getResultList();
    }

    //endregion

    //region Sessie
    public void voegSessieToe(Sessie sessie) {
        em.getTransaction().begin();
        em.persist(sessie);
        em.getTransaction().commit();
    }

    public void updateSessie(Sessie sessie) {

    }

    public void verwijderSessie(Sessie sessie) {
        em.getTransaction().begin();
        sessie.setVerwijderd(true);
        em.getTransaction().commit();
    }

    public Sessie geefSessieById(String sessieId) {
        return em.find(Sessie.class, sessieId);
        //return (Sessie) em.createQuery("select s from Sessie s where sessieId = ?1").setParameter(1, sessieId).getResultList().get(0);
    }

    public List<Sessie> geefAlleSessiesKalender(int academiejaar) {
        return (List<Sessie>) em.createQuery("select s from Sessie s where s.verwijderd = false and academiejaar = ?1").setParameter(1, academiejaar).getResultList();
    }

    public List<Sessie> geefAlleSessiesKalenderVanGebruiker(int academiejaar, Gebruiker gebruiker) {
        return (List<Sessie>) em.createQuery("select s from Sessie s where s.verwijderd = false and academiejaar = ?1 and verantwoordelijke = ?2").setParameter(1, academiejaar).setParameter(2, gebruiker).getResultList();
    }
    //endregion

    //region Lokaal
    public void voegLokaalToe(Lokaal lokaal) {
        em.getTransaction().begin();
        em.persist(lokaal);
        em.getTransaction().commit();
    }

    public void verwijderLokaal(Lokaal lokaal) {
        em.getTransaction().begin();
        lokaal.setVerwijderd(true);
        em.getTransaction().commit();
    }

    public List<Lokaal> geefAlleLokalen() {
        return (List<Lokaal>) em.createQuery("select l from Lokaal l where l.verwijderd = false").getResultList();
    }

    public Lokaal geefLokaalById(String lokaalCode) {
        return em.find(Lokaal.class, lokaalCode);
        //return (Lokaal) em.createQuery("select l from Lokaal l where lokaalCode = ?1").setParameter(1, lokaalCode).getResultList().get(0);
    }

    public List<Lokaal> geefLokaalByCampus(String campus) {
        String afkorting = Campussen.valueOf(campus).getAfkorting();
        return geefAlleLokalen().stream().filter(l -> l.getLokaalCode().matches(String.format("G%s.*", afkorting))).collect(Collectors.toList());
    }
    //endregion

    //region Gebruiker
    public void voegGebruikerToe(Gebruiker gebruiker) {
        em.getTransaction().begin();
        em.persist(gebruiker);
        em.getTransaction().commit();
    }

    public void updateGebruiker(Gebruiker gebruiker) {
        em.getTransaction().begin();
        em.remove(gebruiker);
        em.getTransaction().commit();
    }

    public void verwijderGebruiker(Gebruiker gebruiker) {
        em.getTransaction().begin();
        gebruiker.setVerwijderd(true);
        em.getTransaction().commit();
    }

    public Gebruiker geefGebruikerById(String gebruikerId) {
        return em.find(Gebruiker.class, gebruikerId);
        //return (Gebruiker) em.createQuery("select g from Gebruiker g where gebruikersnaam = ?1").setParameter(1, gebruikerId).getResultList().get(0);
    }

    public List<IGebruiker> geefAlleGebruikers() {
        return (List<IGebruiker>) em.createQuery("select g from Gebruiker g where g.verwijderd = false order by g.gebruikersprofiel DESC, g.naam, g.gebruikersnaam").getResultList();
    }

//    public List<Gebruiker> geefAlleActieveGebruikers(){
//        return (List<Gebruiker>) em.createQuery("select g from Gebruiker g where g.status = 'actief'").getResultList();
//    }
    //endregion

    //region Aankondiging
    public void voegAankondigingToe(Aankondiging aankondiging, Sessie sessie) {
        em.getTransaction().begin();
        em.persist(aankondiging);
        sessie.addAankondiging(aankondiging);
        em.getTransaction().commit();
    }

    public void verwijderAankondiging(Aankondiging aankondiging) {
        em.getTransaction().begin();
        aankondiging.setVerwijderd(true);
        em.getTransaction().commit();
    }

    public Aankondiging geefAankondigingById(String aankondigingsId) {
        return em.find(Aankondiging.class, aankondigingsId);
        //return (Aankondiging) em.createQuery("select a from Aankondiging a where aankondigingsId = ?1").setParameter(1, aankondigingsId).getResultList().get(0);
    }

    public List<Gebruiker> geefAlleAankondigingen() {
        return (List<Gebruiker>) em.createQuery("select a from Aankondiging a where a.verwijderd = false").getResultList();
    }

    public List<Aankondiging> geefAlleAankondigingenVanSessie(String sessie) {
        return geefSessieById(sessie).getAankondigingen();
    }
    //endregion

    //region Feedback
    public void voegFeedbackToe(Feedback feedback, Sessie sessie) {
        em.getTransaction().begin();
        em.persist(feedback);
        sessie.addFeedback(feedback);
        em.getTransaction().commit();
    }

    public void verwijderFeedback(Feedback feedback) {
        em.getTransaction().begin();
        feedback.setVerwijderd(true);
        em.getTransaction().commit();
    }

    public Feedback geefFeedbackById(String feedbackId) {
        return em.find(Feedback.class, feedbackId);
        //return (Feedback) em.createQuery("select f from Feedback f where feedbackId = ?1").setParameter(1, feedbackId).getResultList().get(0);
    }

    public List<Feedback> geefAlleFeedback() {
        return (List<Feedback>) em.createQuery("select a from Feedback a where a.verwijderd = false").getResultList();
    }

    public List<Feedback> geefAlleFeedbackVanSessie(String sessie) {
        return geefSessieById(sessie).getFeedback();
    }
    //endregion

    //region Inschrijving
    public void voegInschrijvingToe(Inschrijving inschrijving, Sessie sessie) {
        em.getTransaction().begin();
        em.persist(inschrijving);
        sessie.addInschrijving(inschrijving);
        em.getTransaction().commit();
    }

    public void verwijderInschrijving(Inschrijving inschrijving) {
        em.getTransaction().begin();
        inschrijving.setVerwijderd(true);
        em.getTransaction().commit();
    }

    public Inschrijving geefInschrijvingById(String inschrijvingsId) {
        return em.find(Inschrijving.class, inschrijvingsId);
        //return (Inschrijving) em.createQuery("select i from Inschrijving i where inschrijvingsId = ?1").setParameter(1, inschrijvingsId).getResultList().get(0);
    }

    public List<Inschrijving> geefAlleInschrijvingen() {
        return (List<Inschrijving>) em.createQuery("select f from Feedback f where f.verwijderd = false").getResultList();
    }

    public List<Inschrijving> geefAlleInschrijvingenVanSessie(String sessie) {
        return geefSessieById(sessie).getInschrijvingen();
    }
    //endregion

    //region Media
    public void voegMediaToe(Media media, Sessie sessie) {
        em.getTransaction().begin();
        em.persist(media);
        sessie.addMedia(media);
        em.getTransaction().commit();
    }

    public void verwijderMedia(Media media) {
        em.getTransaction().begin();
        media.setVerwijderd(true);
        em.getTransaction().commit();
    }

    public Media geefMediaById(String mediaId) {
        return em.find(Media.class, mediaId);
        //return (Media) em.createQuery("select m from Media m where mediaId = ?1").setParameter(1, mediaId).getResultList().get(0);
    }

    public List<Media> geefAlleMedia() {
        return (List<Media>) em.createQuery("select m from Media m where m.verwijderd = false").getResultList();
    }

    public List<Media> geefAlleMediaVanSessie(String sessie) {
        return geefSessieById(sessie).getMedia();
    }
    //endregion

    //region Herinnering
    public void voegHerinneringToe(Herinnering herinnering, Aankondiging aankondiging) {
        em.getTransaction().begin();
        em.persist(herinnering);
        aankondiging.setHerinnering(herinnering);
        em.getTransaction().commit();
    }

    public void verwijderHerinnering(Herinnering herinnering) {
        em.getTransaction().begin();
        herinnering.setVerwijderd(true);
        em.getTransaction().commit();
    }

    public Herinnering geefHerinneringById(String herinneringsId) {
        return em.find(Herinnering.class, herinneringsId);
        //return (Media) em.createQuery("select h from Herinnering h where herinneringsId = ?1").setParameter(1, herinneringsId).getResultList().get(0);
    }

    public List<Herinnering> geefAlleHerinneringen() {
        return (List<Herinnering>) em.createQuery("select h from Herinnering h where h.verwijderd = false").getResultList();
    }

    public List<Herinnering> geefAlleHerinneringenVanSessie(String sessie) {
        return geefSessieById(sessie).getAankondigingen().stream().filter(Aankondiging::isAutomatischeHerinnering).map(Aankondiging::getHerinnering).collect(Collectors.toList());
    }
    //endregion
}