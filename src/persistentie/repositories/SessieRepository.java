package persistentie.repositories;

import domein.Sessie;
import exceptions.persistentie.repositories.SessieRepositoryException;
import persistentie.mappers.SessieMapper;

import java.util.List;

public class SessieRepository {

    private SessieMapper sm;

    public SessieRepository() {
        this.sm = new SessieMapper();
    }

    public List<Sessie> getSessies() {
        return sm.getSessies();
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
}
