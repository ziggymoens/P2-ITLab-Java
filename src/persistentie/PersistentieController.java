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
    public void voegGebruikerToeMetProfielfoto(String profielfoto, String naam, String gebruikersnaam, Gebruikersprofielen type, Gebruikersstatus status){
        Gebruiker g = new Gebruiker(profielfoto, naam, gebruikersnaam, type, status);
        gebruikerMapper.voegGebruikerToe(g);
    }

    public void voegGebruikerToeZonderProfielfoto(String naam, String gebruikersnaam, Gebruikersprofielen type, Gebruikersstatus status){
        Gebruiker g = new Gebruiker(naam, gebruikersnaam, type, status);
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

    public void verwijderSessie(Sessie s) {
        sessieMapper.verwijderSessie(s);
    }
}
