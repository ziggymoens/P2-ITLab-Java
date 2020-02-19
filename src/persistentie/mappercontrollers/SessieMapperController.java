package persistentie.mappercontrollers;

import domein.Sessie;
import persistentie.Connection;
import persistentie.PersistentieController;
import persistentie.mappers.SessieMapper;
import persistentie.mappers.mappersOffline.SessieOfflineMapper;
import persistentie.mappers.mappersOnline.SessieOnlineMapper;

import java.util.List;

public class SessieMapperController {
    protected SessieMapper mapper;
    protected PersistentieController persistentieController;

    public SessieMapperController() {
        if (Connection.isONLINE()){
            mapper = new SessieOnlineMapper();
        }else{
            mapper = new SessieOfflineMapper();
        }
    }

    public List<Sessie> getSessies() {
        return mapper.getSessies();
    }


    public void addSessie(Sessie s) {
        mapper.voegSessieToe(s);
    }

    public void verwijderSessie(Sessie s) {
        mapper.verwijderSessie(s);
    }

    public void updateSessie(Sessie s) {
        mapper.updateSessie(s);
    }

    public void schrijfSessies() {
        mapper.schrijfSessies();
    }

    public void setPersistentieController(PersistentieController persistentieController) {
        mapper.setPersistentieController(persistentieController);
    }

    public void initData() {
        mapper.initData();
    }
}
