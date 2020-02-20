package ongebruikt.persistentie.mappercontrollers;

import domein.Lokaal;
import ongebruikt.persistentie.Connection;
import ongebruikt.persistentie.mappers.LokaalMapper;
import ongebruikt.persistentie.mappers.mappersOffline.LokaalOfflineMapper;
import ongebruikt.persistentie.mappers.mappersOnline.LokaalOnlineMapper;

import java.util.Set;

public class LokaalMapperController {
    LokaalMapper mapper;

    public LokaalMapperController() {
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

    public void initData() {
        mapper.initData();
    }
}
