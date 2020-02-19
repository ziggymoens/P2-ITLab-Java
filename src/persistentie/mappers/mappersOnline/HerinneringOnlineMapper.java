package persistentie.mappers.mappersOnline;

import domein.Herinnering;
import persistentie.PersistentieController;
import persistentie.mappers.HerinneringMapper;

import java.util.List;

public class HerinneringOnlineMapper extends HerinneringMapper {

    public HerinneringOnlineMapper() {
        super();
    }

    @Override
    public void initData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Herinnering> getHerinneringen() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void voegHerinneringToe(Herinnering herinnering) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void verwijderHerinnering(Herinnering herinnering) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateHerinnering(Herinnering herinnering) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void schrijfHerinneringen() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPersistentieController(PersistentieController persistentieController) {
        throw new UnsupportedOperationException();
    }
}
