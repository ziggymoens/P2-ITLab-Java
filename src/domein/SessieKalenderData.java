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
        sessieKalenderBeheerder.addSessie(new Sessie("Sessie 2", LocalDateTime.now().plusMinutes(8000), LocalDateTime.now().plusMinutes(8100), sessieKalenderBeheerder.geefAlleLokalen().stream().filter(l -> l.getLokaalCode().equals("GSCHB4.036")).findFirst().orElse(null), sessieKalenderBeheerder.geefAlleGebruikers().stream().filter(g -> g.getGebruikersnaam().equals("755223sd")).findFirst().orElse(null)));
        sessieKalenderBeheerder.addSessie(new Sessie("Sessie 3", LocalDateTime.now().plusMinutes(8500), LocalDateTime.now().plusMinutes(8550), sessieKalenderBeheerder.geefAlleLokalen().stream().filter(l -> l.getLokaalCode().equals("GSCHB1.012")).findFirst().orElse(null), sessieKalenderBeheerder.geefAlleGebruikers().stream().filter(g -> g.getGebruikersnaam().equals("758095zm")).findFirst().orElse(null)));
        sessieKalenderBeheerder.addSessie(new Sessie("Sessie 4", LocalDateTime.now().plusMinutes(12500), LocalDateTime.now().plusMinutes(12590), sessieKalenderBeheerder.geefAlleLokalen().stream().filter(l -> l.getLokaalCode().equals("GSCHB1.017")).findFirst().orElse(null), sessieKalenderBeheerder.geefAlleGebruikers().stream().filter(g -> g.getGebruikersnaam().equals("755223sd")).findFirst().orElse(null)));
    }

    public void populeerDataAankondigingen(){
        sessieKalenderBeheerder.addAankondigingSessie(1, "758095zm", "Aankondiging 1" );
        sessieKalenderBeheerder.addAankondigingSessie(2, "862361jv", "Aankondiging 2" );
        sessieKalenderBeheerder.addAankondigingSessie(1, "860570ea", "Aankondiging 3" );
        sessieKalenderBeheerder.addAankondigingSessie(4, "862361jv", "Aankondiging 4" );
    }

    public void populeerDataHerinneringen(){
        sessieKalenderBeheerder.addAankondigingHerhalingSessie(1, "758095zm", "Herinnering 1", 1);
        sessieKalenderBeheerder.addAankondigingHerhalingSessie(4, "860570ea", "Herinnering 2", 7);
    }

    public void populeerDataFeedback(){
        sessieKalenderBeheerder.addFeedbackSessie(1, "758095zm", "Feedback 1" );
        sessieKalenderBeheerder.addFeedbackSessie(2, "862361jv", "Feedback 2" );
        sessieKalenderBeheerder.addFeedbackSessie(1, "860570ea", "Feedback 3" );
        sessieKalenderBeheerder.addFeedbackSessie(4, "862361jv", "Feedback 4" );
    }

    public void populeerDataInschrijvingen(){
        sessieKalenderBeheerder.addInschrijvingSessie(1, "758095zm", LocalDateTime.now() );
        sessieKalenderBeheerder.addInschrijvingSessie(2, "862361jv", LocalDateTime.now().plusMinutes(50));
        sessieKalenderBeheerder.addInschrijvingSessie(1, "860570ea", LocalDateTime.now().plusMinutes(345));
        sessieKalenderBeheerder.addInschrijvingSessie(4, "862361jv", LocalDateTime.now().plusMinutes(527));
    }

    public void populeerDataMedia(){
        sessieKalenderBeheerder.addMediaSessie(1, "758095zm", "Media 1" );
        sessieKalenderBeheerder.addMediaSessie(2, "862361jv", "Media 2" );
        sessieKalenderBeheerder.addMediaSessie(1, "860570ea", "Media 3" );
        sessieKalenderBeheerder.addMediaSessie(4, "862361jv", "Media 4" );
    }
}
