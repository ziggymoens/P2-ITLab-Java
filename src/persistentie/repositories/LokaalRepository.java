package persistentie.repositories;

import domein.Lokaal;
import persistentie.mappers.LokaalMapper;
import persistentie.mappersOffline.LokaalOfflineMapper;

import java.util.Set;

public class LokaalRepository {
    private LokaalMapper lm;
    private Set<Lokaal> lokalenSet;

    public LokaalRepository() {
        lm = new LokaalMapper();
        haalLokalenOp();
    }

    private void haalLokalenOp() {
        lokalenSet = lm.getLokalen();
    }

    public Set<Lokaal> getLokalenSet() {
        return lokalenSet;
    }

    public void update() {
        lokalenSet = lm.getLokalen();
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
        lm.schrijfLokalen();
    }

    public void beheerLokaal(String optie, Lokaal l) {
        switch (optie.toUpperCase()){
            case "CREATE":
                lm.voegLokaalToe(l);
                break;
            case "READ":
                lm.geefLokaal(l.getLokaalCode());
                break;
            case "UPDATE":
                lm.updateLokaal(l);
                break;
            case "DELETE":
                lm.verwijderLokaal(l);
                break;
            default:
                break;

        }
    }
}
