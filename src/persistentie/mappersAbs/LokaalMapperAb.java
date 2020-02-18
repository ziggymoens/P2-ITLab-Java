package persistentie.mappersAbs;

import domein.Lokaal;

import java.util.HashSet;
import java.util.Set;

public abstract class LokaalMapperAb {
    protected Set<Lokaal> lokaalSet;

    public LokaalMapperAb() {
        this.lokaalSet = new HashSet<>();
    }


}
