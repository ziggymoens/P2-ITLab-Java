package persistentie.mappersAbs;

import domein.Gebruiker;

import java.util.HashSet;
import java.util.Set;

public abstract class GebruikerMapperAb {
    protected Set<Gebruiker> gebruikerSet;

    public GebruikerMapperAb() {
        this.gebruikerSet = new HashSet<>();
    }

    public Set<Gebruiker> getGebruikers() {
        return null;
    }

    public void voegGebruikerToe(Gebruiker g) {
    }

    public void verwijderGebruiker(Gebruiker g) {
    }

    public void updateGebruiker(Gebruiker g) {
    }

    public void schrijfGebruikers() {
    }

    public void update(){
    }
}
