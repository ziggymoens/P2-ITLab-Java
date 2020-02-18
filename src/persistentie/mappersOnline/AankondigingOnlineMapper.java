package persistentie.mappersOnline;

import domein.Aankondiging;
import persistentie.PersistentieController;
import persistentie.mappersAbs.AankondigingMapperAb;

import java.util.List;

public class AankondigingOnlineMapper extends AankondigingMapperAb {

    public AankondigingOnlineMapper() {
        super();
    }

    @Override
    public void initData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Aankondiging> getAankodigingen() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void voegAankondigingToe(Aankondiging aankondiging) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void verwijderAankondiging(Aankondiging aankondiging) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateAankondiging(Aankondiging aankondiging) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void schrijfAankondigingen() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPersistentieController(PersistentieController persistentieController) {
        throw new UnsupportedOperationException();
    }
}
