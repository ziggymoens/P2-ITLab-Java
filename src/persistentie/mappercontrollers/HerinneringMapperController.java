package persistentie.mappercontrollers;

import domein.Herinnering;
import persistentie.Connection;
import persistentie.PersistentieController;
import persistentie.mappers.HerinneringMapper;
import persistentie.mappersOffline.HerinneringOfflineMapper;
import persistentie.mappersOnline.HerinneringOnlineMapper;

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
