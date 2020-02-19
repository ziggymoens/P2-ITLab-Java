package persistentie.mappersOnline;

import domein.Gebruiker;
import persistentie.mappersAbs.GebruikerMapper;

import java.util.Set;

public class GebruikerOnlineMapper extends GebruikerMapper {

    public GebruikerOnlineMapper() {
        super();
    }

    @Override
    public void initData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Gebruiker> getGebruikers() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void voegGebruikerToe(Gebruiker g) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void verwijderGebruiker(Gebruiker g) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateGebruiker(Gebruiker g) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void schrijfGebruikers() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException();
    }
}
