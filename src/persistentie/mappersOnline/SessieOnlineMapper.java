package persistentie.mappersOnline;

import domein.Sessie;
import persistentie.mappersAbs.SessieMapperAb;

import java.util.List;

public class SessieOnlineMapper extends SessieMapperAb {

    public SessieOnlineMapper() {
        super();
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
}
