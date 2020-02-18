package persistentie.mappersOnline;

import domein.Lokaal;
import persistentie.mappersAbs.LokaalMapperAb;

import java.util.Set;

public class LokaalOnlineMapper extends LokaalMapperAb {

    public LokaalOnlineMapper() {
        super();
    }

    @Override
    public Set<Lokaal> getLokalen() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void voegLokaalToe(Lokaal l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void verwijderLokaal(Lokaal l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateLokaal(Lokaal l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void schrijfLokalen() {
        throw new UnsupportedOperationException();
    }
}
