package ongebruikt.persistentie.repositories;

import domein.Inschrijving;
import exceptions.persistentie.InschrijvingPersistentieException;
import ongebruikt.persistentie.PersistentieController;
import ongebruikt.persistentie.mappercontrollers.InschrijvingMapperController;

import java.util.List;

public class InschrijvingRepository {
    private InschrijvingMapperController mapper;

    public InschrijvingRepository() {
        this.mapper = new InschrijvingMapperController();
    }

    public void setPersistenieController(PersistentieController pc){
        mapper.setPersistentieController(pc);
    }

    public List<Inschrijving> getInschrijvingen() {
        return mapper.getInschrijvingen();
    }

    public void beheerInschrijving(String optie, Inschrijving inschrijving) {
        switch (optie) {
            case "CREATE":
                mapper.voegInschrijvingToe(inschrijving);
                break;
            case "READ":
                geefInschrijvingMetId(inschrijving.getInschrijvingsId());
                break;
            case "UPDATE":
                mapper.updateInschrijving(inschrijving);
                break;
            case "DELETE":
                mapper.verwijderInschrijving(inschrijving);
                break;
            default:
                throw new InschrijvingPersistentieException("Inschrijvingrepository");
        }
    }

    private Inschrijving geefInschrijvingMetId(String id) {
        return mapper.getInschrijvingen().stream().filter(a -> a.getInschrijvingsId().equals(id)).findFirst().orElse(null);
    }

    public void schrijfWeg() {
        mapper.schrijfInschrijvingen();
    }

    private void update() {
        throw new UnsupportedOperationException();
    }

    public void initData() {
        mapper.initData();
    }
}