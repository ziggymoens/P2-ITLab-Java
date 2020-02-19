package persistentie.repositories;

import domein.Lokaal;
import exceptions.persistentie.LokaalPersistentieException;
import persistentie.mappercontrollers.LokaalMapperController;

import java.util.Set;

public class LokaalRepository {
    private LokaalMapperController mapper;
    private Set<Lokaal> lokalenSet;

    public LokaalRepository() {
        mapper = new LokaalMapperController();
        haalLokalenOp();
    }

    private void haalLokalenOp() {
        lokalenSet = mapper.getLokalen();
    }

    public Set<Lokaal> getLokalenSet() {
        return lokalenSet;
    }

    public void update() {
        lokalenSet = mapper.getLokalen();
    }

    public void schrijfWeg() {
        update();
        mapper.schrijfLokalen();
    }

    public void beheerLokaal(String optie, Lokaal l) {
        switch (optie.toUpperCase()){
            case "CREATE":
                mapper.voegLokaalToe(l);
                break;
            case "READ":
                mapper.geefLokaal(l.getLokaalCode());
                break;
            case "UPDATE":
                mapper.updateLokaal(l);
                break;
            case "DELETE":
                mapper.verwijderLokaal(l);
                break;
            default:
                throw new LokaalPersistentieException("LokaalRepository");
        }
    }

    public void initData() {
        mapper.initData();
    }
}
