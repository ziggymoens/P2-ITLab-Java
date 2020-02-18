package persistentie.repositories;

import domein.Lokaal;
import persistentie.mappers.LokaalMapper;
import persistentie.mappersOffline.LokaalOfflineMapper;

import java.util.Set;

public class LokaalRepository {
    private LokaalMapper mapper;
    private Set<Lokaal> lokalenSet;

    public LokaalRepository() {
        mapper = new LokaalMapper();
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
                break;

        }
    }

    public void initData() {
        mapper.initData();
    }
}
