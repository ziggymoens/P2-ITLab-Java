package persistentie;

import domein.Lokaal;

import java.util.HashSet;
import java.util.Set;

public class LokaalMapper {
    private Set<Lokaal> lokalen;

    public LokaalMapper() {
        lokalen = new HashSet<>();
    }

    public Set<Lokaal> getLokalen() {
        return lokalen;
    }
}
