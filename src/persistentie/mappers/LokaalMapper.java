package persistentie.mappers;

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

    public void voegLokaalToe(Lokaal l) {
        lokalen.add(l);
    }

    public void verwijderLokaal(Lokaal l) {
        lokalen.remove(l);
    }

    public void updateLokaal(Lokaal l) {
    }

    public Set<Lokaal> getLokalenSet() {
        return lokalen;
    }

    public void schrijfLokalen(Set<Lokaal> lokalenSet) {
    }

    public Lokaal geefLokaal(String lokaalCode) {
        return lokalen.stream().filter(l -> l.getLokaalCode().equals(lokaalCode)).findFirst().orElse(null);
    }
}
