package persistentie;

import domein.Gebruiker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GebruikerMapper {
    private Set<Gebruiker> gebruikers;

    public GebruikerMapper() {
        gebruikers = new HashSet<>();
    }

    public Set<Gebruiker> getGebruikers() {
        return gebruikers;
    }
}
