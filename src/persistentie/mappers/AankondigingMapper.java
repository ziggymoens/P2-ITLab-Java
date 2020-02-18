package persistentie.mappers;

import domein.Aankondiging;
import domein.Sessie;
import persistentie.Connection;
import persistentie.PersistentieController;
import persistentie.mappersAbs.AankondigingMapperAb;
import persistentie.mappersOffline.AankondigingOfflineMapper;
import persistentie.mappersOnline.AankondigingOnlineMapper;

import java.util.List;

public class AankondigingMapper {
    private AankondigingMapperAb mapper;

    public AankondigingMapper() {
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
