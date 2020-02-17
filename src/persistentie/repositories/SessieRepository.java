package persistentie.repositories;

import domein.Sessie;
import exceptions.persistentie.repositories.SessieRepositoryException;
import persistentie.PersistentieController;
import persistentie.mappers.SessieMapper;
import persistentie.offline.SessieOfflineMapper;

import java.util.List;

public class SessieRepository {

    private SessieMapper sm;
    private SessieOfflineMapper som;
    private List<Sessie> sessies;

    public SessieRepository() {
        this.sm = new SessieMapper();
        this.som = new SessieOfflineMapper();
        haalSessiesOp();
    }

    public void setPersistenieController(PersistentieController pc){
        som.setPersistentieController(pc);
    }

    public void haalSessiesOp() {
        sessies = som.getSessieList();
    }

    public List<Sessie> getSessies() {
        return sessies;
    }

    public void beheerSessie(String optie, Sessie s) {
        switch (optie) {
            case "CREATE":
                sm.addSessie(s);
                break;
            case "READ":
                geefSessie(s.getSessieId());
                break;
            case "UPDATE":
                sm.updateSessie(s.getSessieId(), s);
                break;
            case "DELETE":
                sm.verwijderSessie(s);
                break;
            default:
                throw new SessieRepositoryException();
        }
    }

    private Sessie geefSessie(String id) {
        return sm.getSessies().stream().filter(s -> s.getSessieId().equals(id)).findFirst().orElse(null);
    }

    public void maakSessies() {
        som.maakSessies();
    }

    public void schrijfWeg() {
        update();
        som.schrijfSessies(sessies);
    }

    private void update() {
        sessies = som.getSessieList();
    }
}
