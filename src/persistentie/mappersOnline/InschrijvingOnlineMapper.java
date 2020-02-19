package persistentie.mappersOnline;

import domein.Inschrijving;
import persistentie.PersistentieController;
import persistentie.mappers.InschrijvingMapper;

import java.util.List;

public class InschrijvingOnlineMapper extends InschrijvingMapper {

    public InschrijvingOnlineMapper() {
        super();
    }

    @Override
    public void initData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Inschrijving> getAankodigingen() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void voegInschrijvingToe(Inschrijving inschrijving) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void verwijderInschrijving(Inschrijving inschrijving) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateInschrijving(Inschrijving inschrijving) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void schrijfInschrijvingen() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPersistentieController(PersistentieController persistentieController) {
        throw new UnsupportedOperationException();
    }
}
