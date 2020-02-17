package persistentie.mappersoOnline;

import domein.Gebruiker;
import persistentie.mappersAbs.GebruikerMapperAb;

import java.util.Set;

public class GebruikerOnlineMapper extends GebruikerMapperAb {

    public GebruikerOnlineMapper() {
        super();
    }

    @Override
    public Set<Gebruiker> getGebruikers() {
        return gebruikerSet;
    }

    @Override
    public void voegGebruikerToe(Gebruiker g) {
        gebruikerSet.add(g);
    }

    @Override
    public void verwijderGebruiker(Gebruiker g) {
        gebruikerSet.remove(g);
    }

    @Override
    public void updateGebruiker(Gebruiker g) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void schrijfGebruikers() {
        throw new UnsupportedOperationException();
    }
}
