package persistentie.mappers;

import domein.Sessie;

import java.util.ArrayList;
import java.util.List;

public class SessieMapper {
    private List<Sessie> sessies;

    public SessieMapper() {
        sessies = new ArrayList<>();
    }

    public List<Sessie> getSessies() {
        return sessies;
    }


    public void addSessie(Sessie s) {
        sessies.add(s);
    }

    public void verwijderSessie(Sessie s) {
        sessies.remove(s);
    }

    public void updateSessie(String id, Sessie s) {
    }
}
