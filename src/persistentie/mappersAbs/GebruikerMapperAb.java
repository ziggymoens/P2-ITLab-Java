package persistentie.mappersAbs;

import domein.Gebruiker;

import java.util.HashSet;
import java.util.Set;

public abstract class GebruikerMapperAb {
    protected Set<Gebruiker> gebruikerSet;

    public GebruikerMapperAb() {
        this.gebruikerSet = new HashSet<>();
    }

    public abstract Set<Gebruiker> getGebruikers();

    public abstract void voegGebruikerToe(Gebruiker g);

    public abstract void verwijderGebruiker(Gebruiker g);

    public abstract void updateGebruiker(Gebruiker g);

    public abstract void schrijfGebruikers();

    public abstract void update();
}