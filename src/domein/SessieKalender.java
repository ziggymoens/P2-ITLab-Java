package domein;

import domein.enums.Campus;
import domein.gebruiker.Gebruiker;
import domein.interfacesDomein.IGebruiker;
import domein.sessie.Sessie;
import exceptions.domein.SessieException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SessieKalender {

    public final String PERSISTENCE_UNIT_NAME = "ITLab_JAVA";
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
    public List<Academiejaar> geefAlleAcademieJaren() {
        List<Academiejaar> aj = (List<Academiejaar>) em.createQuery("select aj  from Academiejaar aj").getResultList();
        for (Academiejaar academiejaar : aj){
            academiejaar.initTable();
        }
        return aj;
    }

    public void voegAcademieJaarToe(Academiejaar academiejaar) {
        em.getTransaction().begin();
        em.persist(academiejaar);
        em.getTransaction().commit();
    }

    public Academiejaar geefAcademiejaarById(int jaar){
        return em.find(Academiejaar.class, jaar);
    }

    public Academiejaar getAcademiejaarByDate(LocalDateTime datum) {
        LocalDate d = LocalDate.parse(datum.toString().substring(0,10));
        return (Academiejaar) em.createQuery("select a from Academiejaar a where ?1 between start and eind").setParameter(1, d).getResultList().get(0);
    }

    //endregion

    //region Sessie
    public void voegSessieToe(Sessie sessie) {
        try {
            if(!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            em.persist(sessie);
            em.getTransaction().commit();
        } catch (SessieException se){
            em.getTransaction().rollback();
            throw se;
        }
    }

    public void verwijderSessie(Sessie sessie) {
        em.getTransaction().begin();
        sessie.verwijder(true);
        em.getTransaction().commit();
    }

    public void setState(Sessie sessie, String state){
        em.getTransaction().begin();
        em.remove(sessie.getCurrentState());
        if(state.toLowerCase().equals("open")) {
            sessie.toOpenState();
        } else {
            sessie.toClosedState();
        }
        em.getTransaction().commit();
    }

    public Sessie geefSessieById(String sessieId) {
        return em.find(Sessie.class, sessieId);
    }

    public List<Sessie> geefAlleSessiesKalender(int academiejaar) {
        List<Sessie> sessies = em.createQuery("select s from Sessie s where s.verwijderd = false and academiejaar = ?1").setParameter(1, geefAcademiejaarById(academiejaar)).getResultList();
        for (Sessie sessie : sessies){
            sessie.initData();
        }
        return sessies;
    }

    public List<Sessie> geefAlleSessiesKalenderVanGebruiker(int academiejaar, Gebruiker gebruiker) {
        List<Sessie> sessies = em.createQuery("select s from Sessie s where s.verwijderd = false and academiejaar = ?1 and verantwoordelijke = ?2").setParameter(1, geefAcademiejaarById(academiejaar)).setParameter(2, gebruiker).getResultList();
        for (Sessie sessie : sessies){
            sessie.initData();
        }
        return sessies;
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
    }

    public List<Lokaal> geefLokaalByCampus(String campus) {
        String afkorting = Campus.valueOf(campus).getCode();
        return geefAlleLokalen().stream().filter(l -> l.getLokaalCode().matches(String.format("G%s.*", afkorting))).collect(Collectors.toList());
    }
    //endregion

    //region Gebruiker
    public void voegGebruikerToe(Gebruiker gebruiker) {
        em.getTransaction().begin();
        em.persist(gebruiker);
        em.getTransaction().commit();
    }

    public void verwijderGebruiker(Gebruiker gebruiker) {
        em.getTransaction().begin();
        gebruiker.setVerwijderd(true);
        em.getTransaction().commit();
    }

    public Gebruiker geefGebruikerById(String gebruikerId) {
        return em.find(Gebruiker.class, gebruikerId);
    }

    /***
     * Methode die alle gebruikers teruggeeft zonder parameters
     * @return List van IGebruiker
     */
    public List<IGebruiker> geefAlleGebruikers() {
        return (List<IGebruiker>) em.createQuery("select g from Gebruiker g where g.verwijderd = false order by g.naam, g.gebruikersnaam").getResultList();
    }
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
    }

    public List<Gebruiker> geefAlleAankondigingen() {
        return (List<Gebruiker>) em.createQuery("select a from Aankondiging a where a.verwijderd = false").getResultList();
    }

    public List<Aankondiging> geefAlleAankondigingenVanSessie(String sessie) {
        return geefSessieById(sessie).getAankondigingen().stream().filter(a -> !a.getVerwijderd()).collect(Collectors.toList());
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
    }

    public List<Feedback> geefAlleFeedback() {
        return (List<Feedback>) em.createQuery("select a from Feedback a where a.verwijderd = false").getResultList();
    }

    public List<Feedback> geefAlleFeedbackVanSessie(String sessie) {
        return geefSessieById(sessie).getFeedback().stream().filter(a -> !a.getVerwijderd()).collect(Collectors.toList());
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
    }

    public List<Inschrijving> geefAlleInschrijvingen() {
        return (List<Inschrijving>) em.createQuery("select i from Inschrijving i where i.verwijderd = false").getResultList();
    }

    public List<Inschrijving> geefAlleInschrijvingenVanSessie(String sessie) {
        return geefSessieById(sessie).getInschrijvingen().stream().filter(a -> !a.getVerwijderd()).collect(Collectors.toList());
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
    }

    public List<Herinnering> geefAlleHerinneringen() {
        return (List<Herinnering>) em.createQuery("select h from Herinnering h where h.verwijderd = false").getResultList();
    }

    public List<Herinnering> geefAlleHerinneringenVanSessie(String sessie) {
        return geefSessieById(sessie).getAankondigingen().stream().filter(Aankondiging::isAutomatischeHerinnering).map(Aankondiging::getHerinnering).collect(Collectors.toList());
    }
    //endregion

    //region UPDATE
    public void updateSessie(Sessie sessie, List<Object> gegevens) {
        try {
            if(!em.getTransaction().isActive()) {
                em.getTransaction().begin();
                try {
                    em.remove(sessie.getCurrentState());
                } catch (Exception e){
                }
            }
            sessie.update(gegevens);
            em.getTransaction().commit();
        } catch (SessieException se){
            em.getTransaction().rollback();
            throw se;
        }
    }

    public void updateGebruiker(Gebruiker gebruiker, List<String> gegevens) {
        em.getTransaction().begin();
        em.remove(gebruiker.getGebruikerProfielState());
        em.remove(gebruiker.getGebruikerStatusState());
        gebruiker.update(gegevens);
        em.getTransaction().commit();
    }

    public void updateAankondiging(Aankondiging aankondiging, List<Object> gegevens) {
        em.getTransaction().begin();
        aankondiging.update(gegevens);
        em.getTransaction().commit();
    }

    public void updateFeedback(Feedback feedback, List<Object> gegevens) {
        em.getTransaction().begin();
        feedback.update(gegevens);
        em.getTransaction().commit();
    }

    public void updateHerinnering(Herinnering herinnering, int gegevens) {
        herinnering.update(gegevens);
        em.getTransaction().commit();
    }

    public void updateInschrijving(Inschrijving inschrijving, List<Object> gegevens) {
        em.getTransaction().begin();
        inschrijving.update(gegevens);
        em.getTransaction().commit();
    }
    public void updateLokaal(Lokaal lokaal, List<Object> gegevens) {
        lokaal.update(gegevens);
        em.getTransaction().commit();
    }

    public void updateMedia(Media media, List<Object> gegevens) {
        em.getTransaction().begin();
        media.update(gegevens);
        em.getTransaction().commit();
    }

    public void voegMediaToeLob(Media media, Sessie sessie) {
        em.getTransaction().begin();
        em.persist(media);
        sessie.addMedia(media);
        em.getTransaction().commit();
    }

    public void profielfotoGebruikerWijzigen(BufferedImage image, Gebruiker selected) {
        em.getTransaction().begin();
        selected.setProfielfoto(image);
        em.getTransaction().commit();
    }

    public void mediaAfbeeldingWijzigen(BufferedImage image, Media huidigeMedia) {
        em.getTransaction().begin();
        huidigeMedia.setAfbeelding(image);
        em.getTransaction().commit();
    }
    //endregion
}