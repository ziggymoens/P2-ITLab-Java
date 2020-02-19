package persistentie.mappersOnline;

import domein.Sessie;
import persistentie.PersistentieController;
import persistentie.mappersAbs.SessieMapper;

import java.util.List;

public class SessieOnlineMapper extends SessieMapper {

    public SessieOnlineMapper() {
        super();
    }

    @Override
    public void initData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Sessie> getSessies() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void voegSessieToe(Sessie s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void verwijderSessie(Sessie s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateSessie(Sessie s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void schrijfSessies() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPersistentieController(PersistentieController persistentieController) {
        throw new UnsupportedOperationException();
    }
}
