package persistentie.repositories;

import domein.Aankondiging;
import domein.Sessie;
import exceptions.persistentie.repositories.SessieRepositoryException;
import persistentie.PersistentieController;
import persistentie.mappers.AankondigingMapper;
import persistentie.mappers.SessieMapper;

import java.util.List;

public class AankondigingRepository {
    private AankondigingMapper mapper;

    public AankondigingRepository() {
        this.mapper = new AankondigingMapper();
    }

    public void setPersistenieController(PersistentieController pc){
        mapper.setPersistentieController(pc);
    }

    public List<Aankondiging> getAankondigingen() {
        return mapper.getAankondigingen();
    }

    public void beheerSessie(String optie, Aankondiging aankondiging) {
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
                throw new SessieRepositoryException();
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
