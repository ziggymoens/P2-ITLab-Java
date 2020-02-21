package userinterface.cui;

import domein.*;

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
        String gebruikersCode = in.next();

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
        //geefOpties(gebruikersCode);

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
/*
    public void geefOpties(String gebruikersCode){
        int keuze = 0;
        switch(dc.geefGebruikerProfiel(gebruikersCode)){
            case "HOOFDVERANTWOORDELIJKE":
                geefOptiesHoofdverantwoordelijke();
                System.out.println("Geef het gewenste cijfer");
                keuze = in.nextInt();
                while(keuze > 5 || keuze < 1){
                    System.out.println("\nCijfer ligt niet tussen de mogelijke cijfers.");
                    System.out.println("\nGeef het gewenste cijfer");
                    keuze = in.nextInt();
                }
                break;
            case "VERANTWOORDELIJKE":
                geefOptiesVerantwoordelijke();
                System.out.println("Geef het gewenste cijfer");
                keuze = in.nextInt();
                while(keuze > 4 || keuze < 1){
                    System.out.println("\nCijfer ligt niet tussen de mogelijke cijfers.");
                    System.out.println("\nGeef het gewenste cijfer");
                    keuze = in.nextInt();
                }
                break;
            case "GEBRUIKER":
                geefOptiesgebruiker();
                break;
        }


        switch (keuze){
            case 1: new SessieBeheren(dc, this, gebruikersCode); break;
            case 2: new SessiekalenderBeheren(dc); break;
            case 3: new AankondigingOfHerinneringSturen(dc); break;
            case 4: new StatistiekenRaadplegen(dc); break;
            case 5: new GebruikerBeheren(dc); break;
        }

    }


 */
}
