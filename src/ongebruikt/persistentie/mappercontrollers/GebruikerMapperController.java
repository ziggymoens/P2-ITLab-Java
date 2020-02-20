package ongebruikt.persistentie.mappercontrollers;

import domein.Gebruiker;
import ongebruikt.persistentie.Connection;
import ongebruikt.persistentie.mappers.GebruikerMapper;
import ongebruikt.persistentie.mappers.mappersOffline.GebruikerOfflineMapper;
import ongebruikt.persistentie.mappers.mappersOnline.GebruikerOnlineMapper;

import java.util.*;

public class GebruikerMapperController {
    private GebruikerMapper mapper;

    public GebruikerMapperController() {
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

    public void initData() {
        mapper.initData();
    }
}
