package persistentie.repositories;

import domein.Herinnering;
import exceptions.persistentie.repositories.SessieRepositoryException;
import persistentie.PersistentieController;
import persistentie.mappercontrollers.HerinneringMapperController;

import java.util.List;

public class HerinneringRepository {
    private HerinneringMapperController mapper;

    public HerinneringRepository() {
        this.mapper = new HerinneringMapperController();
    }

    public void setPersistenieController(PersistentieController pc){
        mapper.setPersistentieController(pc);
    }

    public List<Herinnering> getHerinneringen() {
        return mapper.getHerinneringen();
    }

    public void beheerHerinnering(String optie, Herinnering herinnering) {
        switch (optie) {
            case "CREATE":
                mapper.voegHerinneringToe(herinnering);
                break;
            case "READ":
                geefHerinneringMetId(herinnering.getHerinneringsId());
                break;
            case "UPDATE":
                mapper.updateHerinnering(herinnering);
                break;
            case "DELETE":
                mapper.verwijderHerinnering(herinnering);
                break;
            default:
                throw new SessieRepositoryException();
        }
    }

    private Herinnering geefHerinneringMetId(String id) {
        return mapper.getHerinneringen().stream().filter(a -> a.getHerinneringsId().equals(id)).findFirst().orElse(null);
    }

    public void schrijfWeg() {
        mapper.schrijfHerinneringen();
    }

    private void update() {
        throw new UnsupportedOperationException();
    }

    public void initData() {
        mapper.initData();
    }
}
