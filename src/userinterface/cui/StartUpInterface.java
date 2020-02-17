package userinterface.cui;

import domein.*;
import userinterface.cui.ucs.*;

import java.util.Scanner;

public class StartUpInterface {
    Scanner in = new Scanner(System.in);
    DomeinController dc;

    public StartUpInterface(DomeinController dc){
        this.dc = dc;
        welkomKeuzeScherm();
    }

    private void welkomKeuzeScherm() {
        Gebruiker gebruiker = new Gebruiker("naam", "a", Gebruikersprofielen.HOOFDVERANTWOORDELIJKE, Gebruikersstatus.ACTIEF);
        Lokaal lokaal = new Lokaal("123", 100);
        //Sessie sessie = new Sessie("titel", new Date(121, 1, 17,12,0,0),new Date(121, 1, 17,12,45,0),  100, lokaal, gebruiker);
        dc.voegGebruikerToe(gebruiker);
        //dc.voegSessieToe(sessie);
        dc.voegLokaalToe(lokaal);

        System.out.print("Gebruikersnaam: ");
        String gebruikersnaam = in.next();
        System.out.print("Wachtwoord: ");
        in.next();
/*
        dc.setGebruiker(dc.geefGebruikerMetGebruikersnaam(gebruikersnaam));
        while(dc.getGebruiker() == null){
            System.out.println("Gebruiker bestaat niet\n");
            System.out.print("Gebruikersnaam: ");
            gebruikersnaam = in.next();
            System.out.print("Wachtwoord: ");
            in.next();
            dc.setGebruiker(dc.geefGebruikerMetGebruikersnaam(gebruikersnaam));
        }
*/
        int keuze = 0;
        switch(gebruiker.getType()){
            case HOOFDVERANTWOORDELIJKE:
                geefOptiesHoofdverantwoordelijke();
                System.out.println("Geef het gewenste cijfer");
                keuze = in.nextInt();
                while(keuze > 5 || keuze < 1){
                    System.out.println("Cijfer ligt niet tussen de mogelijke cijfers.");
                    System.out.println("\nGeef het gewenste cijfer");
                    keuze = in.nextInt();
                }
                break;
            case VERANTWOORDELIJKE:
                geefOptiesVerantwoordelijke();
                System.out.println("Geef het gewenste cijfer");
                keuze = in.nextInt();
                while(keuze > 4 || keuze < 1){
                    System.out.println("\nCijfer ligt niet tussen de mogelijke cijfers.");
                    System.out.println("\nGeef het gewenste cijfer");
                    keuze = in.nextInt();
                }
                break;
            case GEBRUIKER:
                geefOptiesgebruiker();
                break;
        }

        switch (keuze){
            case 1: new SessieBeheren(dc); break;
            case 2: new SessiekalenderBeheren(dc); break;
            case 3: new AankondigingOfHerinneringSturen(dc); break;
            case 4: new StatistiekenRaadplegen(dc); break;
            case 5: new GebruikerBeheren(dc); break;
        }

    }

    private void geefOptiesHoofdverantwoordelijke() {
        geefOptiesVerantwoordelijke();
        System.out.println("5. Beheren gebruiker");

    }

    private void geefOptiesgebruiker() {
        System.out.println("Momenteel zijn er nog geen opties");
    }

    private void geefOptiesVerantwoordelijke() {
        System.out.println("\n\n1. Beheren sessie");
        System.out.println("2. Beheren sessiekalender");
        System.out.println("3. Aankondiging plaatsen");
        System.out.println("4. Raadplegen statistieken");
    }

}
