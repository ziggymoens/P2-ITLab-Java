package ongebruikt.persistentie.repositories;

import domein.Aankondiging;
import exceptions.persistentie.AankondigingPersistentieException;
import ongebruikt.persistentie.PersistentieController;
import ongebruikt.persistentie.mappercontrollers.AankondigingMapperController;

import java.util.List;

public class AankondigingRepository {
    private AankondigingMapperController mapper;

    public AankondigingRepository() {
        this.mapper = new AankondigingMapperController();
    }

    public void setPersistenieController(PersistentieController pc){
        mapper.setPersistentieController(pc);
    }

    public List<Aankondiging> getAankondigingen() {
        return mapper.getAankondigingen();
    }

    public void beheerAankondiging(String optie, Aankondiging aankondiging) {
        switch (optie) {
            case "CREATE":
                mapper.voegAankondigingToe(aankondiging);
                break;
            case "READ":
                geefAankondigingMetId(aankondiging.getAankondigingsId());
                break;
            case "UPDATE":
                mapper.updateAankondiging(aankondiging);
                break;
            case "DELETE":
                mapper.verwijderAankondiging(aankondiging);
                break;
            default:
                throw new AankondigingPersistentieException("AankondigingRepository");
        }
    }

    private Aankondiging geefAankondigingMetId(String id) {
        return mapper.getAankondigingen().stream().filter(a -> a.getAankondigingsId().equals(id)).findFirst().orElse(null);
    }

    public void schrijfWeg() {
        mapper.schrijfAankondigingen();
    }

    private void update() {
        throw new UnsupportedOperationException();
    }

    public void initData() {
        mapper.initData();
    }
}
