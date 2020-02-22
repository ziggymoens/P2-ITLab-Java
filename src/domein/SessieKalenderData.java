package domein;

import domein.domeinklassen.*;
import domein.enums.Gebruikersprofielen;
import domein.enums.Gebruikersstatus;

import java.time.LocalDateTime;

public class SessieKalenderData {
    private SessieKalenderBeheerder sessieKalenderBeheerder;

    public SessieKalenderData(SessieKalenderBeheerder sessieKalenderBeheerder) {
        this.sessieKalenderBeheerder = sessieKalenderBeheerder;
    }

    public void populeerDataLokalen(){
        sessieKalenderBeheerder.addLokaal(new Lokaal("GSCHB1.017", 50));
        sessieKalenderBeheerder.addLokaal(new Lokaal("GSCHB3.019", 35));
        sessieKalenderBeheerder.addLokaal(new Lokaal("GSCHB4.036", 24));
        sessieKalenderBeheerder.addLokaal(new Lokaal("GSCHB1.012", 53));
    }

    public void populeerDataGebruikers() {
        sessieKalenderBeheerder.addGebruiker(new Gebruiker("Ziggy Moens", "758095zm", Gebruikersprofielen.HOOFDVERANTWOORDELIJKE, Gebruikersstatus.ACTIEF));
        sessieKalenderBeheerder.addGebruiker(new Gebruiker("Kilian Hoefman", "757932kh", Gebruikersprofielen.GEBRUIKER, Gebruikersstatus.ACTIEF));
        sessieKalenderBeheerder.addGebruiker(new Gebruiker("Jonathan Vanden Eynden", "862361jv", Gebruikersprofielen.GEBRUIKER, Gebruikersstatus.ACTIEF));
        sessieKalenderBeheerder.addGebruiker(new Gebruiker("Sébastien De Pauw", "755223sd", Gebruikersprofielen.VERANTWOORDELIJKE, Gebruikersstatus.ACTIEF));
        sessieKalenderBeheerder.addGebruiker(new Gebruiker("Sven Wyseur", "860570ea", Gebruikersprofielen.GEBRUIKER, Gebruikersstatus.NIET_ACTIEF));
        sessieKalenderBeheerder.addGebruiker(new Gebruiker("Elias Ameye", "751158sw", Gebruikersprofielen.GEBRUIKER, Gebruikersstatus.GEBLOKKEERD));
    }

    public void populeerDataSessie() {
        sessieKalenderBeheerder.addSessie(new Sessie("Sessie 1", LocalDateTime.now().plusMinutes(6000), LocalDateTime.now().plusMinutes(6200), sessieKalenderBeheerder.geefAlleLokalen().stream().filter(l -> l.getLokaalCode().equals("GSCHB1.017")).findFirst().orElse(null), sessieKalenderBeheerder.geefAlleGebruikers().stream().filter(g -> g.getGebruikersnaam().equals("758095zm")).findFirst().orElse(null)));
    }
}
