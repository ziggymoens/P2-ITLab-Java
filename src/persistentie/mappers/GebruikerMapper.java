package persistentie.mappers;

import domein.Gebruiker;
import persistentie.Connection;
import persistentie.mappersAbs.GebruikerMapperAb;
import persistentie.mappersOffline.GebruikerOfflineMapper;
import persistentie.mappersOnline.GebruikerOnlineMapper;

import java.util.*;

public class GebruikerMapper {
    private GebruikerMapperAb mapper;

    public GebruikerMapper() {
        if (Connection.isONLINE()) {
            mapper = new GebruikerOnlineMapper();
        } else {
            mapper = new GebruikerOfflineMapper();
        }
    }

    public Set<Gebruiker> getGebruikers() {
        return mapper.getGebruikers();
    }

    public void voegGebruikerToe(Gebruiker g) {
        mapper.voegGebruikerToe(g);
    }

    public void verwijderGebruiker(Gebruiker g) {
        mapper.verwijderGebruiker(g);
    }

    public void updateGebruiker(Gebruiker g) {
        throw new UnsupportedOperationException();
    }

    public void schrijfGebruikers() {
        mapper.schrijfGebruikers();
    }

    public void update() {
        mapper.update();
    }


    public Gebruiker geefGebruiker(String gebruikersnaam) {
        return mapper.getGebruikers().stream().filter(g -> g.getGebruikersnaam().equals(gebruikersnaam)).findFirst().orElse(null);
    }
}
