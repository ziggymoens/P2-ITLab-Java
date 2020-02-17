package persistentie.repositories;

import domein.Lokaal;
import domein.Sessie;
import persistentie.mappers.LokaalMapper;
import persistentie.offline.LokalenOfflineMapper;

import java.util.Set;

public class LokaalRepository {
    private LokaalMapper lm;
    private Set<Lokaal> lokalenSet;

    public LokaalRepository() {
        lm = new LokaalMapper();
        haalLokalenOp();
    }

    private void haalLokalenOp() {
        lokalenSet = lm.getLokalenSet();
    }

    public Set<Lokaal> getLokalenSet() {
        return lokalenSet;
    }

    public void update() {
        lokalenSet = lm.getLokalenSet();
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
        lm.schrijfLokalen(lokalenSet);
    }

    public void beheerLokaal(String optie, Sessie s) {
        switch (optie){
            case "CREATE":
                //lom.voeg

        }
    }
}
