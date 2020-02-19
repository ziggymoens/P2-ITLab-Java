package persistentie.mappers;

import domein.Sessie;
import persistentie.PersistentieController;

import java.util.ArrayList;
import java.util.List;

public abstract class SessieMapper {
    protected List<Sessie> sessieList;
    protected PersistentieController persistentieController;

    public SessieMapper() {
        this.sessieList = new ArrayList<>();
    }

    public abstract List<Sessie> getSessies();

    public abstract void voegSessieToe(Sessie s);

    public abstract void verwijderSessie(Sessie s);

    public abstract void updateSessie(Sessie s);

    public abstract void schrijfSessies();

    public abstract void setPersistentieController(PersistentieController persistentieController);

    public abstract void initData();
}
