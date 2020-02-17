package persistentie.mappers;

import domein.Gebruiker;

import java.util.*;

public class GebruikerMapper {

    private Set<Gebruiker> gebruikers;

    public GebruikerMapper() {
        gebruikers = new HashSet<>();
    }

    public Set<Gebruiker> getGebruikers() {
        return gebruikers;
    }

    public void voegGebruikerToe(Gebruiker g) {
        gebruikers.add(g);
    }

    public void verwijderGebruiker(Gebruiker g) {
        gebruikers.remove(g);
    }

    public void updateGebruiker(Gebruiker g) {
        throw new UnsupportedOperationException();
    }

}
