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
        sessieRepository.setPersistenieController(this);
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
    //endregion

    //region Gebruiker CRUD
    public void beheerGebruiker(String optie, Gebruiker g){
        gebruikerRepository.beheerGebruiker(optie, g);
    }

    public void voegGebruikerToe(Gebruiker g) {
        gebruikerRepository.voegGebruikerToe(g);
    }

    public void verwijderGebruiker(Gebruiker g) {
        gebruikerRepository.verwijderGebruiker(g);
    }

    public Gebruiker geefGebruikerMetCode(String s) {
        return gebruikerRepository.getGebruikerSet().stream().filter(g -> g.getGebruikersnaam().equals(s)).findFirst().orElse(null);
    }

    //endregion

    //region Sessie CRUD
    public void beheerSessie(String optie, Sessie s) {
        sessieRepository.beheerSessie(optie, s);
    }

    public Sessie geefSessieMetId(String sessieId) {
        return sessieRepository.getSessies().stream().filter(s -> s.getSessieId().equals(sessieId)).findFirst().orElse(null);
    }
    //endregion

    //region Lokaal CRUD
    public void beheerLokaal(String optie, Lokaal l) {
        lokaalRepository.beheerLokaal(optie, l);
    }

    public Lokaal geefLokaalMetCode(String s) {
        return lokaalRepository.getLokalenSet().stream().filter(l -> l.getLokaalCode().equals(s)).findFirst().orElse(null);
    }
    //endregion

    public void schrijfAllesWeg() {
        gebruikerRepository.schrijfWeg();
        lokaalRepository.schrijfWeg();
        sessieRepository.schrijfWeg();
    }

    public void voegLokaalToe(Lokaal lokaal) {

    }

    public Set<Lokaal> getLokalenSet() {
        return lokaalRepository.getLokalenSet();
    }


}
