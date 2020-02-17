package persistentie.repositories;

import domein.Lokaal;
import persistentie.mappers.LokaalMapper;
import persistentie.offline.LokalenOfflineMapper;

import java.util.Set;

public class LokaalRepository {
    private LokaalMapper lm;
    private LokalenOfflineMapper lom;

    private Set<Lokaal> lokalenSet;

    public LokaalRepository() {
        lm = new LokaalMapper();
        lom = new LokalenOfflineMapper();
        haalLokalenOp();
    }

    private void haalLokalenOp() {
        lokalenSet = lom.getLokalenSet();
    }

    public Set<Lokaal> getLokalenSet() {
        return lokalenSet;
    }

    public void update() {
        lokalenSet = lom.getLokalenSet();
    }

    public void voegLokaalToe(Lokaal l) {
        lm.voegLokaalToe(l);
    }

    public void verwijderLokaal(Lokaal l) {
        lm.verwijderLokaal(l);
    }

    public void updateLokaal(Lokaal l) {
        lm.updateLokaal(l);
    }

    public void schrijfWeg() {
        update();
        lom.schrijfLokalen(lokalenSet);
    }
}
