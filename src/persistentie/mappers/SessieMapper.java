package persistentie.mappers;

import domein.DomeinController;
import domein.Gebruiker;
import domein.Lokaal;
import domein.Sessie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
