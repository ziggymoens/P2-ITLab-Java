package userinterface.cui;

import domein.DomeinController;
import domein.Gebruiker;
import domein.Sessie;

import java.util.Scanner;

public class StartUpInterface {
    Scanner in = new Scanner(System.in);
    DomeinController dc;

    public StartUpInterface(DomeinController dc){
        this.dc = dc;
        welkomKeuzeScherm();
    }

    private void welkomKeuzeScherm() {

        System.out.print("Gebruikersnaam: ");
        String gebruikersnaam = in.next();
        System.out.print("Wachtwoord: ");
        in.next();

        Gebruiker gebruiker = dc.geefGebruikerMetGebruikersnaam(gebruikersnaam);
        int keuze = 0;
        switch(gebruiker.getType()){
            case HOOFDVERANTWOORDELIJKE:
                geefOptiesHoofdverantwoordelijke();
                System.out.println("Geef het gewenste cijfer");
                keuze = in.nextInt();
                while(keuze > 5 || keuze < 1){
                    keuze = in.nextInt();
                }
                break;
            case VERANTWOORDELIJKE:
                geefOptiesVerantwoordelijke();
                System.out.println("Geef het gewenste cijfer");
                keuze = in.nextInt();
                while(keuze > 4 || keuze < 1){
                    keuze = in.nextInt();
                }
                break;
            case GEBRUIKER:
                geefOptiesgebruiker();
                break;
        }

        switch (keuze){
            case 1: new SessieBeheren(dc, gebruiker); break;
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
