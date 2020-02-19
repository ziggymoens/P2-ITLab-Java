package persistentie.mappersAbs;

import domein.Lokaal;

import java.util.HashSet;
import java.util.Set;

public abstract class LokaalMapper {
    protected Set<Lokaal> lokaalSet;

    public LokaalMapper() {
        this.lokaalSet = new HashSet<>();
    }

    public abstract Set<Lokaal> getLokalen();

    public abstract void voegLokaalToe(Lokaal l);

    public abstract void verwijderLokaal(Lokaal l);

    public abstract void updateLokaal(Lokaal l);

    public abstract void schrijfLokalen();

    public abstract void initData();
}
