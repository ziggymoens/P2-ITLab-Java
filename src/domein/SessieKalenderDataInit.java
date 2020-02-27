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
    private final File gebruikers = new File("src/csv/Gebruikers");
    private final File sessies = new File("src/csv/Sessies");
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

        try {
            BufferedReader br = new BufferedReader(new FileReader(gebruikers));
            String line;
            while ((line = br.readLine()) != null) {
                String[] gebruiker = line.split(";");
                sessieKalender.voegGebruikerToe(new Gebruiker(gebruiker[1], gebruiker[0], gebruiker[2], gebruiker[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("gebruikers lezen");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(sessies));
            String line;
            while ((line = br.readLine()) != null) {
                String[] sessie = line.split(";");
                sessieKalender.voegSessieToe(new Sessie(sessie[0], LocalDateTime.parse(sessie[1]), LocalDateTime.parse(sessie[2]), sessieKalender.geefLokaalById(sessie[3]), sessieKalender.geefGebruikerById(sessie[4])));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("sessie lezen");
        }
    }
}
