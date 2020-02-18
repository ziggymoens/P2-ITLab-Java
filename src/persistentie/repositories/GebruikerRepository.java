package persistentie.repositories;

import domein.Gebruiker;
import persistentie.mappers.GebruikerMapper;

import java.util.Set;

public class GebruikerRepository {
    private GebruikerMapper gm;

    public GebruikerRepository() {
        gm = new GebruikerMapper();
    }

    public Set<Gebruiker> getGebruikerSet() {
        return gm.getGebruikers();
    }

    public void update() {
        gm.update();
    }

    public void schrijfWeg() {
        update();
        gm.schrijfGebruikers();
    }

    public void beheerGebruiker(String optie, Gebruiker g) {
        switch (optie.toUpperCase()){
            case "CREATE":
                gm.voegGebruikerToe(g);
                break;
            case "READ":
                gm.geefGebruiker(g.getGebruikersnaam());
                break;
            case "UPDATE":
                gm.updateGebruiker(g);
                break;
            case "DELETE":
                gm.verwijderGebruiker(g);
                break;
            default:
                break;
        }
    }
}
