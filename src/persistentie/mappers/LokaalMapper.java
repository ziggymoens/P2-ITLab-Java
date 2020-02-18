package persistentie.mappers;

import domein.Lokaal;
import persistentie.Connection;
import persistentie.mappersAbs.LokaalMapperAb;
import persistentie.mappersOffline.LokaalOfflineMapper;
import persistentie.mappersOnline.LokaalOnlineMapper;

import java.util.Set;

public class LokaalMapper {
    LokaalMapperAb mapper;

    public LokaalMapper() {
        if(Connection.isONLINE()){
            mapper = new LokaalOnlineMapper();
        }else{
            mapper = new LokaalOfflineMapper();
        }
    }

    public Set<Lokaal> getLokalen() {
        return mapper.getLokalen();
    }

    public void voegLokaalToe(Lokaal l) {
        mapper.voegLokaalToe(l);
    }

    public void verwijderLokaal(Lokaal l) {
        mapper.verwijderLokaal(l);
    }

    public void updateLokaal(Lokaal l) {
        mapper.updateLokaal(l);
    }

    public void schrijfLokalen() {
        mapper.schrijfLokalen();
    }

    public Lokaal geefLokaal(String lokaalCode) {
        return mapper.getLokalen().stream().filter(l -> l.getLokaalCode().equals(lokaalCode)).findFirst().orElse(null);
    }
}
