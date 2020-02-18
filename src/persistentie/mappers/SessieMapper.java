package persistentie.mappers;

import domein.Sessie;
import persistentie.Connection;
import persistentie.PersistentieController;
import persistentie.mappersAbs.SessieMapperAb;
import persistentie.mappersOffline.SessieOfflineMapper;
import persistentie.mappersOnline.SessieOnlineMapper;

import java.util.List;

public class SessieMapper {
    protected SessieMapperAb mapper;
    protected PersistentieController persistentieController;

    public SessieMapper() {
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
