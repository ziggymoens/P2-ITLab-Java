package persistentie.repositories;

import domein.Gebruiker;
import persistentie.mappers.GebruikerMapper;
import persistentie.offline.GebruikerOfflineMapper;

import java.util.Set;

public class GebruikerRepository {
    private GebruikerMapper gm;
    private GebruikerOfflineMapper gom;

    private Set<Gebruiker> gebruikerSet;

    public GebruikerRepository() {
        gm = new GebruikerMapper();
        gom = new GebruikerOfflineMapper();
        haalGebruikersOp();
    }

    private void haalGebruikersOp() {
        gebruikerSet = gom.getGebruikerSet();
    }

    public Set<Gebruiker> getGebruikerSet() {
        return gebruikerSet;
    }

    public void update() {
        gebruikerSet = gom.getGebruikerSet();
    }

    public void voegGebruikerToe(Gebruiker g) {
        gm.voegGebruikerToe(g);
    }

    public void verwijderGebruiker(Gebruiker g) {

    }
}
