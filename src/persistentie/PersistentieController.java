package persistentie;

import domein.*;
import persistentie.repositories.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class PersistentieController {

    private SessieRepository sessieRepository;
    private GebruikerRepository gebruikerRepository;
    private LokaalRepository lokaalRepository;

    public PersistentieController() {
        initRepos();
    }

    public void initRepos() {
        gebruikerRepository = new GebruikerRepository();
        lokaalRepository = new LokaalRepository();
        sessieRepository = new SessieRepository();
        sessieRepository.setPersistenieController(this);
        sessieRepository.maakSessies();
    }

    public Set<Gebruiker> getGebruikers() {
        return gebruikerRepository.getGebruikerSet();
    }

    public Set<Lokaal> getLokalen() {
        return lokaalRepository.getLokalenSet();
    }

    public List<Sessie> getSessies() {
        return sessieRepository.getSessies();
    }


    //region Gebruiker Beheren
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
    public void maakNieuweSessieAan(String titel, LocalDateTime startSessie, LocalDateTime eindeSessie, int maximumAantalPlaatsen, Lokaal lokaal, Gebruiker verantwoordelijke) {
        Sessie s = new Sessie(titel, startSessie, eindeSessie, maximumAantalPlaatsen, lokaal, verantwoordelijke);
        beheerSessie("CREATE", s);
    }

    public void beheerSessie(String optie, Sessie s) {
        sessieRepository.beheerSessie(optie, s);
    }
    //endregion

    //region Lokaal Beheren
    public void voegLokaalToe(Lokaal l) {
        lokaalRepository.voegLokaalToe(l);
    }

    public void verwijderLokaal(Lokaal l) {
        lokaalRepository.verwijderLokaal(l);
    }

    public void updateLokaal(Lokaal l) {
        lokaalRepository.updateLokaal(l);
    }

    public Lokaal geefLokaalMetCode(String s) {
        return lokaalRepository.getLokalenSet().stream().filter(l -> l.getLokaalCode().equals(s)).findFirst().orElse(null);
    }

    public void schrijfAllesWeg() {
        gebruikerRepository.schrijfWeg();
        lokaalRepository.schrijfWeg();
        sessieRepository.schrijfWeg();
    }

    //endregion
}
