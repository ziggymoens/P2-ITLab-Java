package persistentie.repositories;

import domein.Gebruiker;
import persistentie.mappers.GebruikerMapperController;

import java.util.Set;

public class GebruikerRepository {
    private GebruikerMapperController gm;

    public GebruikerRepository() {
        gm = new GebruikerMapperController();
    }

    public void initData() {
        gm.initData();
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
