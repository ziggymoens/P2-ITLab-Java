package ongebruikt.persistentie.repositories;

import domein.Sessie;
import exceptions.persistentie.SessiePersistentieException;
import ongebruikt.persistentie.PersistentieController;
import ongebruikt.persistentie.mappercontrollers.SessieMapperController;

import java.util.List;

public class SessieRepository {

    private SessieMapperController mapper;

    public SessieRepository() {
        this.mapper = new SessieMapperController();
    }

    public void setPersistenieController(PersistentieController pc){
        mapper.setPersistentieController(pc);
    }

    public List<Sessie> getSessies() {
        return mapper.getSessies();
    }

    public void beheerSessie(String optie, Sessie s) {
        switch (optie) {
            case "CREATE":
                mapper.addSessie(s);
                break;
            case "READ":
                geefSessie(s.getSessieId());
                break;
            case "UPDATE":
                mapper.updateSessie(s);
                break;
            case "DELETE":
                mapper.verwijderSessie(s);
                break;
            default:
                throw new SessiePersistentieException("SessieRepository");
        }
    }

    private Sessie geefSessie(String id) {
        return mapper.getSessies().stream().filter(s -> s.getSessieId().equals(id)).findFirst().orElse(null);
    }

    public void schrijfWeg() {
        mapper.schrijfSessies();
    }

    private void update() {
        throw new UnsupportedOperationException();
    }

    public void initData() {
        mapper.initData();
    }
}
