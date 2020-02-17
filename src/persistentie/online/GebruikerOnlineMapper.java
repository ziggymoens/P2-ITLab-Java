package persistentie.online;

import domein.Gebruiker;

import java.util.HashSet;
import java.util.Set;

public class GebruikerOnlineMapper {
    private Set<Gebruiker> gebruikers;

    public GebruikerOnlineMapper() {
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
