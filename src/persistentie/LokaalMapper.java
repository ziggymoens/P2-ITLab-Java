package persistentie;

import domein.Gebruiker;
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

    public void voegLokaalToe(Lokaal l){
        lokalen.add(l);
    }
    public void verwijderLokaal(Lokaal l) {
        lokalen.remove(l);
    }
    public void updateLokaal(Lokaal l){}
}
