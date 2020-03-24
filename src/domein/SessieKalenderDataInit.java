package domein;

import domein.gebruiker.Gebruiker;
import domein.sessie.Sessie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SessieKalenderDataInit {
    private final File lokalen = new File("src/csv/Lokalen.csv");
    private final File gebruikers = new File("src/csv/Gebruikers.csv");
    private final File sessies = new File("src/csv/Sessies.csv");
    private final File aankondiging = new File("src/csv/Aankondigingen.csv");
    private final File feedback = new File("src/csv/Feedback.csv");
    private final File herinnering = new File("src/csv/Herinneringen.csv");
    private final File inschrijving = new File("src/csv/Inschrijvingen.csv");
    private final File media = new File("src/csv/Media.csv");
    private final File academiejaar = new File("src/csv/Academiejaren.csv");

    private SessieKalender sessieKalender;

    public SessieKalenderDataInit(SessieKalender sessieKalender) {
        this.sessieKalender = sessieKalender;

        try {
            BufferedReader br = new BufferedReader(new FileReader(academiejaar));
            String line;
            while ((line = br.readLine()) != null) {
                String[] jaar = line.split(",");
                sessieKalender.voegAcademieJaarToe(new Academiejaar(Integer.parseInt(jaar[0]), LocalDate.parse(jaar[1]), LocalDate.parse(jaar[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("academiejaren lezen");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(lokalen));
            String line;
            while ((line = br.readLine()) != null) {
                String[] lokaal = line.split(",");
                sessieKalender.voegLokaalToe(new Lokaal(lokaal[0], lokaal[1], Integer.parseInt(lokaal[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("lokalen lezen");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(gebruikers));
            String line;
            while ((line = br.readLine()) != null) {
                String[] gebruiker = line.split(",");
                sessieKalender.voegGebruikerToe(new Gebruiker(gebruiker[1], gebruiker[0], gebruiker[2], gebruiker[3], "storage/profielfotos/profielfoto.png", gebruiker[4]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("gebruikers lezen");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(sessies));
            String line;
            while ((line = br.readLine()) != null) {
                String[] sessie = line.split(",");
                Sessie s = new Sessie(sessie[0], sessie[1],LocalDateTime.parse(sessie[2]), LocalDateTime.parse(sessie[3]), sessieKalender.geefLokaalById(sessie[4]), sessieKalender.geefGebruikerById(sessie[5]), sessieKalender.getAcademiejaarByDate(LocalDateTime.parse(sessie[2])), sessie[6]);
                sessieKalender.voegSessieToe(s);

            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("sessie lezen");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(feedback));
            String line;
            while ((line = br.readLine()) != null) {
                String[] feedback = line.split(",");
                sessieKalender.voegFeedbackToe(new Feedback(sessieKalender.geefSessieById(feedback[3]), sessieKalender.geefGebruikerById(feedback[0]), feedback[1], LocalDate.parse(feedback[2])), sessieKalender.geefSessieById(feedback[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("feedback lezen");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(aankondiging));
            String line;
            while ((line = br.readLine()) != null) {
                String[] aankondiging = line.split(",");
                sessieKalender.voegAankondigingToe(new Aankondiging(sessieKalender.geefSessieById(aankondiging[3]), sessieKalender.geefGebruikerById(aankondiging[0]), LocalDateTime.parse(aankondiging[1]), aankondiging[2]), sessieKalender.geefSessieById(aankondiging[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("aankondiging lezen");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(herinnering));
            String line;
            while ((line = br.readLine()) != null) {
                String[] herinnering = line.split(",");
                sessieKalender.voegHerinneringToe(new Herinnering(Integer.parseInt(herinnering[0])), sessieKalender.geefAankondigingById(herinnering[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("herinnering lezen");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(inschrijving));
            String line;
            while ((line = br.readLine()) != null) {
                String[] inschrijving = line.split(",");
                Inschrijving i = new Inschrijving(sessieKalender.geefSessieById(inschrijving[2]), sessieKalender.geefGebruikerById(inschrijving[0]), LocalDateTime.parse(inschrijving[1]));
                i.setStatusAanwezigheid(inschrijving[3].equals("true"));
                sessieKalender.voegInschrijvingToe(i, sessieKalender.geefSessieById(inschrijving[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("inschrijving lezen");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(media));
            String line;
            while ((line = br.readLine()) != null) {
                String[] media = line.split(",");
                sessieKalender.voegMediaToe(new Media(sessieKalender.geefSessieById(media[3]), sessieKalender.geefGebruikerById(media[0]), media[1], media[2]), sessieKalender.geefSessieById(media[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("media lezen");
        }
    }
}
