package persistentie;

import domein.Gebruiker;
import domein.Gebruikersprofielen;
import domein.Gebruikersstatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GebruikerMapper {

    private Set<Gebruiker> gebruikers;
    public GebruikerMapper() {
        gebruikers = new HashSet<>();
    }

    public Set<Gebruiker> getGebruikers() {
        return gebruikers;
    }

    public void voegGebruikerToe(Gebruiker g){
        gebruikers.add(g);
    }
    public void verwijderGebruiker(Gebruiker g) {
        gebruikers.remove(g);
    }
    public void updateGebruiker(Gebruiker g){}
}
