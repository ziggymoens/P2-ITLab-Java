package persistentie.mappersAbs;

import domein.Sessie;
import persistentie.mappers.SessieMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class SessieMapperAb {
    protected List<Sessie> sessieList;

    public SessieMapperAb() {
        this.sessieList = new ArrayList<>();
    }

    public abstract List<Sessie> getSessies();

    public abstract void voegSessieToe(Sessie s);

    public abstract void verwijderSessie(Sessie s);

    public abstract void updateSessie(Sessie s);

    public abstract void schrijfSessies();
}
