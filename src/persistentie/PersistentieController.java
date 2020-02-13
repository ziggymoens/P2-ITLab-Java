package persistentie;

import domein.Gebruiker;
import domein.Lokaal;
import domein.Sessie;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class PersistentieController {

    private SessieMapper sessieMapper;
    private GebruikerMapper gebruikerMapper;
    private LokaalMapper lokaalMapper;

    public PersistentieController() {
        sessieMapper = new SessieMapper();
    }

    public Set<Gebruiker> getGebruikers() {
        return gebruikerMapper.getGebruikers();
    }

    public Set<Lokaal> getLokalen() {
        return lokaalMapper.getLokalen();
    }

    public List<Sessie> getSessies() {
        return sessieMapper.getSessies();
    }

    public void maakNieuweSessieAan(String titel, Date startSessie, Date eindeSessie, int maximumAantalPlaatsen, Lokaal lokaal, Gebruiker verantwoordelijke) {
        Sessie s = new Sessie(titel, startSessie, eindeSessie, maximumAantalPlaatsen, lokaal, verantwoordelijke);
        sessieMapper.voegSessieToe(s);
    }

    public void verwijderSessie(Sessie s) {
        sessieMapper.verwijderSessie(s);
    }
}
