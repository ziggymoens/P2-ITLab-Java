package ongebruikt.persistentie.mappercontrollers;

import domein.Herinnering;
import ongebruikt.persistentie.Connection;
import ongebruikt.persistentie.PersistentieController;
import ongebruikt.persistentie.mappers.HerinneringMapper;
import ongebruikt.persistentie.mappers.mappersOffline.HerinneringOfflineMapper;
import ongebruikt.persistentie.mappers.mappersOnline.HerinneringOnlineMapper;

import java.util.List;

public class HerinneringMapperController {
    private HerinneringMapper mapper;

    public HerinneringMapperController() {
        if(Connection.isONLINE()){
            mapper = new HerinneringOnlineMapper();
        } else{
            mapper = new HerinneringOfflineMapper();
        }
    }

    public void setPersistentieController(PersistentieController persistentieController){
        mapper.setPersistentieController(persistentieController);
    }

    public List<Herinnering> getHerinneringen() {
        return mapper.getHerinneringen();
    }

    public void voegHerinneringToe(Herinnering herinnering) {
        mapper.voegHerinneringToe(herinnering);
    }

    public void updateHerinnering(Herinnering herinnering) {
        throw new UnsupportedOperationException();
    }

    public void verwijderHerinnering(Herinnering herinnering) {
        mapper.verwijderHerinnering(herinnering);
    }

    public void schrijfHerinneringen() {
        mapper.schrijfHerinneringen();
    }

    public void initData() {
        mapper.initData();
    }

}
