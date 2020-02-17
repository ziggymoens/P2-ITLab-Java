package persistentie.repositories;

import domein.Lokaal;
import domein.Sessie;
import persistentie.mappers.LokaalMapper;
import persistentie.mappersOffline.LokalenOfflineMapper;

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
