package persistentie.mappers.mappersOnline;

import domein.Aankondiging;
import persistentie.PersistentieController;
import persistentie.mappers.AankondigingMapper;

import java.util.List;

public class AankondigingOnlineMapper extends AankondigingMapper {

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
