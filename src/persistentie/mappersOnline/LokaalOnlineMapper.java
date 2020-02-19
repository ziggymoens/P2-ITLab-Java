package persistentie.mappersOnline;

import domein.Lokaal;
import persistentie.mappers.LokaalMapper;

import java.util.Set;

public class LokaalOnlineMapper extends LokaalMapper {

    public LokaalOnlineMapper() {
        super();
    }

    @Override
    public void initData() {
        throw new UnsupportedOperationException();
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
