package persistentie.mappercontrollers;

import domein.Aankondiging;
import persistentie.Connection;
import persistentie.PersistentieController;
import persistentie.mappers.AankondigingMapper;
import persistentie.mappers.mappersOffline.AankondigingOfflineMapper;
import persistentie.mappers.mappersOnline.AankondigingOnlineMapper;

import java.util.List;

public class AankondigingMapperController {
    private AankondigingMapper mapper;

    public AankondigingMapperController() {
        if(Connection.isONLINE()){
            mapper = new AankondigingOnlineMapper();
        } else{
            mapper = new AankondigingOfflineMapper();
        }
    }

    public void setPersistentieController(PersistentieController persistentieController){
        mapper.setPersistentieController(persistentieController);
    }

    public List<Aankondiging> getAankondigingen() {
        return mapper.getAankodigingen();
    }

    public void voegAankondigingToe(Aankondiging aankondiging) {
        mapper.voegAankondigingToe(aankondiging);
    }

    public void updateAankondiging(Aankondiging aankondiging) {
        throw new UnsupportedOperationException();
    }

    public void verwijderAankondiging(Aankondiging aankondiging) {
        mapper.verwijderAankondiging(aankondiging);
    }

    public void schrijfAankondigingen() {
        mapper.schrijfAankondigingen();
    }

    public void initData() {
        mapper.initData();
    }
}
