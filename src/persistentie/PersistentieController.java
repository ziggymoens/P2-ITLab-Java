package persistentie;

import domein.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class PersistentieController {

    private SessieMapper sessieMapper;
    private GebruikerMapper gebruikerMapper;
    private LokaalMapper lokaalMapper;

    public PersistentieController() {
        sessieMapper = new SessieMapper();
        gebruikerMapper = new GebruikerMapper();
        lokaalMapper = new LokaalMapper();
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


    //Gebruiker Beheren
    public void voegGebruikerToe(Gebruiker g){
        gebruikerMapper.voegGebruikerToe(g);
    }

    public void verwijderGebruiker(Gebruiker g){
        gebruikerMapper.verwijderGebruiker(g);
    }

    public void maakNieuweSessieAan(String titel, Date startSessie, Date eindeSessie, int maximumAantalPlaatsen, Lokaal lokaal, Gebruiker verantwoordelijke) {
        Sessie s = new Sessie(titel, startSessie, eindeSessie, maximumAantalPlaatsen, lokaal, verantwoordelijke);
        sessieMapper.voegSessieToe(s);
    }
    //Einde Gebruiker Beheren

    //Sessie beheren
    public void voegSessieToe(Sessie s){sessieMapper.voegSessieToe(s);}
    public void verwijderSessie(Sessie s) {
        sessieMapper.verwijderSessie(s);
    }
    public void updateSessie(Sessie s){sessieMapper.updateSessie(s);}
    //Einde Sessie beheren

    //Lokaal beheren
    public void voegLokaalToe(Lokaal l){lokaalMapper.voegLokaalToe(l);}
    public void verwijderLokaal(Lokaal l){lokaalMapper.verwijderLokaal(l);}
    public void updateLokaal(Lokaal l){lokaalMapper.updateLokaal(l);}
    //Einde lokaal beheren
}
