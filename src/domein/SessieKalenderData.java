package domein;

import domein.domeinklassen.*;
import domein.enums.Gebruikersprofielen;
import domein.enums.Gebruikersstatus;

import java.time.LocalDateTime;

public class SessieKalenderData {
    private final SessieKalenderBeheerder sessieKalenderBeheerder;

    public SessieKalenderData(SessieKalenderBeheerder sessieKalenderBeheerder) {
        this.sessieKalenderBeheerder = sessieKalenderBeheerder;
    }

    public void populeerDataLokalen() {
        sessieKalenderBeheerder.addLokaal(new Lokaal("GSCHB1.017", 50));
        sessieKalenderBeheerder.addLokaal(new Lokaal("GSCHB3.019", 35));
        sessieKalenderBeheerder.addLokaal(new Lokaal("GSCHB4.036", 24));
        sessieKalenderBeheerder.addLokaal(new Lokaal("GSCHB1.012", 53));
        sessieKalenderBeheerder.addLokaal(new Lokaal("GSCHB0.010", 200));
        sessieKalenderBeheerder.addLokaal(new Lokaal("GSCHB4.026", 30));
        sessieKalenderBeheerder.addLokaal(new Lokaal("GSCHC0.125", 175));
    }

    public void populeerDataGebruikers() {
        sessieKalenderBeheerder.addGebruiker(new Gebruiker("Ziggy Moens", "758095zm", Gebruikersprofielen.HOOFDVERANTWOORDELIJKE, Gebruikersstatus.ACTIEF));
        sessieKalenderBeheerder.addGebruiker(new Gebruiker("Kilian Hoefman", "757932kh", Gebruikersprofielen.GEBRUIKER, Gebruikersstatus.ACTIEF));
        sessieKalenderBeheerder.addGebruiker(new Gebruiker("Jonathan Vanden Eynden", "862361jv", Gebruikersprofielen.GEBRUIKER, Gebruikersstatus.ACTIEF));
        sessieKalenderBeheerder.addGebruiker(new Gebruiker("Sébastien De Pauw", "755223sd", Gebruikersprofielen.VERANTWOORDELIJKE, Gebruikersstatus.ACTIEF));
        sessieKalenderBeheerder.addGebruiker(new Gebruiker("Sven Wyseur", "751158sw", Gebruikersprofielen.GEBRUIKER, Gebruikersstatus.NIET_ACTIEF));
        sessieKalenderBeheerder.addGebruiker(new Gebruiker("Elias Ameye", "860570ea", Gebruikersprofielen.GEBRUIKER, Gebruikersstatus.GEBLOKKEERD));
        sessieKalenderBeheerder.addGebruiker(new Gebruiker("Yorgo baeyens", "755230yb", Gebruikersprofielen.GEBRUIKER, Gebruikersstatus.GEBLOKKEERD));
    }

    public void populeerDataSessie() {
        Sessie sessie1 = new Sessie("Wat de recruteringsafdeling van jou verwacht", LocalDateTime.now().plusMinutes(1000), LocalDateTime.now().plusMinutes(1200), sessieKalenderBeheerder.geefAlleLokalen().stream().filter(l -> l.getLokaalCode().equals("GSCHB1.017")).findFirst().orElse(null), sessieKalenderBeheerder.geefAlleGebruikers().stream().filter(g -> g.getGebruikersnaam().equals("758095zm")).findFirst().orElse(null));
        Sessie sessie2 = new Sessie("Internationaal samenwerken", LocalDateTime.now().plusMinutes(2000), LocalDateTime.now().plusMinutes(2100), sessieKalenderBeheerder.geefAlleLokalen().stream().filter(l -> l.getLokaalCode().equals("GSCHB4.036")).findFirst().orElse(null), sessieKalenderBeheerder.geefAlleGebruikers().stream().filter(g -> g.getGebruikersnaam().equals("755223sd")).findFirst().orElse(null));
        Sessie sessie3 = new Sessie("Inleiding tot Trello", LocalDateTime.now().plusMinutes(3500), LocalDateTime.now().plusMinutes(3550), sessieKalenderBeheerder.geefAlleLokalen().stream().filter(l -> l.getLokaalCode().equals("GSCHB1.012")).findFirst().orElse(null), sessieKalenderBeheerder.geefAlleGebruikers().stream().filter(g -> g.getGebruikersnaam().equals("758095zm")).findFirst().orElse(null));
        Sessie sessie4 = new Sessie("Inleiding tot GIT", LocalDateTime.now().plusMinutes(4500), LocalDateTime.now().plusMinutes(4590), sessieKalenderBeheerder.geefAlleLokalen().stream().filter(l -> l.getLokaalCode().equals("GSCHB1.017")).findFirst().orElse(null), sessieKalenderBeheerder.geefAlleGebruikers().stream().filter(g -> g.getGebruikersnaam().equals("755223sd")).findFirst().orElse(null));
        Sessie sessie5 = new Sessie("inleiding tot UNIX", LocalDateTime.now().plusMinutes(3000), LocalDateTime.now().plusMinutes(3120), sessieKalenderBeheerder.geefAlleLokalen().stream().filter(l -> l.getLokaalCode().equals("GSCHB4.026")).findFirst().orElse(null), sessieKalenderBeheerder.geefAlleGebruikers().stream().filter(g -> g.getGebruikersnaam().equals("758095zm")).findFirst().orElse(null));
        Sessie sessie6 = new Sessie("IT in het leger", LocalDateTime.now().plusMinutes(1000), LocalDateTime.now().plusMinutes(1060), sessieKalenderBeheerder.geefAlleLokalen().stream().filter(l -> l.getLokaalCode().equals("GSCHC0.125")).findFirst().orElse(null), sessieKalenderBeheerder.geefAlleGebruikers().stream().filter(g -> g.getGebruikersnaam().equals("755223sd")).findFirst().orElse(null));
        sessie1.setNaamGastspreker("Sukrit Bhatthacharya");
        sessie2.setNaamGastspreker("Hilde Janssens");
        sessie4.setNaamGastspreker("Johannes Verstraeten");

        sessieKalenderBeheerder.addSessie(sessie1);
        sessieKalenderBeheerder.addSessie(sessie2);
        sessieKalenderBeheerder.addSessie(sessie3);
        sessieKalenderBeheerder.addSessie(sessie4);
        sessieKalenderBeheerder.addSessie(sessie5);
        sessieKalenderBeheerder.addSessie(sessie6);
    }

    public void populeerDataAankondigingen() {
        sessieKalenderBeheerder.addAankondigingSessie("S1920-000001", "758095zm", "Aankondiging 1", false, 0);
        sessieKalenderBeheerder.addAankondigingSessie("S1920-000002", "862361jv", "Aankondiging 2", false, 0);
        sessieKalenderBeheerder.addAankondigingSessie("S1920-000001", "860570ea", "Aankondiging 3", false, 0);
        sessieKalenderBeheerder.addAankondigingSessie("S1920-000004", "862361jv", "Aankondiging 4", false, 0);
    }

    public void populeerDataHerinneringen() {
        sessieKalenderBeheerder.addAankondigingSessie("S1920-000001", "758095zm", "Herinnering 1", true, 1);
        sessieKalenderBeheerder.addAankondigingSessie("S1920-000004", "860570ea", "Herinnering 2", true, 7);
    }

    public void populeerDataFeedback() {
        sessieKalenderBeheerder.addFeedbackSessie("S1920-000001", "758095zm", "Feedback 1");
        sessieKalenderBeheerder.addFeedbackSessie("S1920-000002", "862361jv", "Feedback 2");
        sessieKalenderBeheerder.addFeedbackSessie("S1920-000001", "860570ea", "Feedback 3");
        sessieKalenderBeheerder.addFeedbackSessie("S1920-000004", "862361jv", "Feedback 4");
    }

    public void populeerDataInschrijvingen() {
        sessieKalenderBeheerder.addInschrijvingSessie("S1920-000001", "758095zm", LocalDateTime.now());
        sessieKalenderBeheerder.addInschrijvingSessie("S1920-000002", "862361jv", LocalDateTime.now().plusMinutes(50));
        sessieKalenderBeheerder.addInschrijvingSessie("S1920-000001", "860570ea", LocalDateTime.now().plusMinutes(345));
        sessieKalenderBeheerder.addInschrijvingSessie("S1920-000004", "862361jv", LocalDateTime.now().plusMinutes(527));
        sessieKalenderBeheerder.addInschrijvingSessie("S1920-000005", "757932kh", LocalDateTime.now().plusMinutes(28));
    }

    public void populeerDataMedia() {
        sessieKalenderBeheerder.addMediaSessie("S1920-000001", "758095zm", "Media 1");
        sessieKalenderBeheerder.addMediaSessie("S1920-000002", "862361jv", "Media 2");
        sessieKalenderBeheerder.addMediaSessie("S1920-000001", "860570ea", "Media 3");
        sessieKalenderBeheerder.addMediaSessie("S1920-000004", "862361jv", "Media 4");
    }

    public void populeerDataSessieKalender(){
        sessieKalenderBeheerder.addSessieKalender(new SessieKalender(1920));
    }
}
