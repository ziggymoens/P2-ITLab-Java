package domein;

import domein.enums.Gebruikersprofielen;
import domein.enums.Gebruikersstatus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SessieKalenderDataInit {
    private final File lokalen = new File("src/csv/Lokalen");
    private SessieKalender sessieKalender;

    public SessieKalenderDataInit(SessieKalender sessieKalender){
        this.sessieKalender = sessieKalender;

        try {
            BufferedReader br = new BufferedReader(new FileReader(lokalen));
            String line;
            while ((line = br.readLine()) != null) {
                String[] lokaal = line.split(";");
                sessieKalender.voegLokaalToe(new Lokaal(lokaal[0], Integer.parseInt(lokaal[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("lokalen lezen");
        }


        Gebruiker gebruiker1 = new Gebruiker("Ziggy Moens", "758095zm", Gebruikersprofielen.HOOFDVERANTWOORDELIJKE, Gebruikersstatus.ACTIEF);
        Gebruiker gebruiker2 = new Gebruiker("Kilian Hoefman", "757932kh", Gebruikersprofielen.GEBRUIKER, Gebruikersstatus.ACTIEF);
        Gebruiker gebruiker3 = new Gebruiker("Jonathan Vanden Eynden", "862361jv", Gebruikersprofielen.GEBRUIKER, Gebruikersstatus.ACTIEF);
        Gebruiker gebruiker4 = new Gebruiker("Sébastien De Pauw", "755223sd", Gebruikersprofielen.VERANTWOORDELIJKE, Gebruikersstatus.ACTIEF);
        Gebruiker gebruiker5 = new Gebruiker("Sven Wyseur", "751158sw", Gebruikersprofielen.GEBRUIKER, Gebruikersstatus.NIET_ACTIEF);
        Gebruiker gebruiker6 = new Gebruiker("Elias Ameye", "860570ea", Gebruikersprofielen.GEBRUIKER, Gebruikersstatus.GEBLOKKEERD);
        Gebruiker gebruiker7 = new Gebruiker("Yorgo baeyens", "755230yb", Gebruikersprofielen.GEBRUIKER, Gebruikersstatus.GEBLOKKEERD);


        Sessie sessie1 = new Sessie("Wat de recruteringsafdeling van jou verwacht", LocalDateTime.now().plusMinutes(1000), LocalDateTime.now().plusMinutes(1200), sessieKalender.geefLokaalById("GSCHB1.017"), gebruiker1);
        Sessie sessie2 = new Sessie("Internationaal samenwerken", LocalDateTime.now().plusMinutes(2000), LocalDateTime.now().plusMinutes(2100), sessieKalender.geefLokaalById("GSCHB3.019"), gebruiker2);
        Sessie sessie3 = new Sessie("Inleiding tot Trello", LocalDateTime.now().plusMinutes(3500), LocalDateTime.now().plusMinutes(3550),sessieKalender.geefLokaalById("GSCHB4.036"), gebruiker4);
        Sessie sessie4 = new Sessie("Inleiding tot GIT", LocalDateTime.now().plusMinutes(4500), LocalDateTime.now().plusMinutes(4590), sessieKalender.geefLokaalById("GSCHB1.017"), gebruiker1);
        Sessie sessie5 = new Sessie("inleiding tot UNIX", LocalDateTime.now().plusMinutes(3000), LocalDateTime.now().plusMinutes(3120), sessieKalender.geefLokaalById("GSCHC0.125"), gebruiker5);
        Sessie sessie6 = new Sessie("IT in het leger", LocalDateTime.now().plusMinutes(1000), LocalDateTime.now().plusMinutes(1060), sessieKalender.geefLokaalById("GSCHB0.010"), gebruiker1);

        sessieKalender.voegSessieToe(sessie1);
        sessieKalender.voegSessieToe(sessie2);
        sessieKalender.voegSessieToe(sessie3);
        sessieKalender.voegSessieToe(sessie4);
        sessieKalender.voegSessieToe(sessie5);
        sessieKalender.voegSessieToe(sessie6);

    }
}
