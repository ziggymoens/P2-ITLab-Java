package persistentie.mappercontrollers;

import domein.Inschrijving;
import persistentie.Connection;
import persistentie.PersistentieController;
import persistentie.mappers.InschrijvingMapper;
import persistentie.mappers.mappersOffline.InschrijvingOfflineMapper;
import persistentie.mappers.mappersOnline.InschrijvingOnlineMapper;

import java.util.List;

public class InschrijvingMapperController {
    private InschrijvingMapper mapper;

    public InschrijvingMapperController() {
        if(Connection.isONLINE()){
            mapper = new InschrijvingOnlineMapper();
        } else{
            mapper = new InschrijvingOfflineMapper();
        }
    }

    public void setPersistentieController(PersistentieController persistentieController){
        mapper.setPersistentieController(persistentieController);
    }

    public List<Inschrijving> getInschrijvingen() {
        return mapper.getAankodigingen();
    }

    public void voegInschrijvingToe(Inschrijving inschrijving) {
        mapper.voegInschrijvingToe(inschrijving);
    }

    public void updateInschrijving(Inschrijving inschrijving) {
        throw new UnsupportedOperationException();
    }

    public void verwijderInschrijving(Inschrijving inschrijving) {
        mapper.verwijderInschrijving(inschrijving);
    }

    public void schrijfInschrijvingen() {
        mapper.schrijfInschrijvingen();
    }

    public void initData() {
        mapper.initData();
    }
}
