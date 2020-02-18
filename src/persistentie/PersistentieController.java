package persistentie;

import domein.*;
import persistentie.repositories.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class PersistentieController {
    //region Variabelen
    private SessieRepository sessieRepository;
    private GebruikerRepository gebruikerRepository;
    private LokaalRepository lokaalRepository;
    private AankondigingRepository aankondigingRepository;
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
        sessieRepository.setPersistenieController(this);
        aankondigingRepository.setPersistenieController(this);
        initData();
    }

    private void initData() {
        gebruikerRepository.initData();
        lokaalRepository.initData();
        aankondigingRepository.initData();
        sessieRepository.initData();
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
        aankondigingRepository.beheerSessie(optie, a);
    }

    public Aankondiging geefAankondigingMetId(String aankondigingsId) {
        return aankondigingRepository.getAankondigingen().stream().filter(a -> a.getAankondigingsId().equals(aankondigingsId)).findFirst().orElse(null);
    }
    //endregion


    public void schrijfAllesWeg() {
        gebruikerRepository.schrijfWeg();
        lokaalRepository.schrijfWeg();
        sessieRepository.schrijfWeg();
        aankondigingRepository.schrijfWeg();
    }
}
