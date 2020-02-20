package userinterface.cui.ucs;

import domein.DomeinController;
import userinterface.cui.StartUpInterface;

import java.util.Scanner;

public class SessieBeheren {
    private Scanner in = new Scanner(System.in);
    private DomeinController dc;
    private StartUpInterface startUpInterface;
    private String gebruikersCode;

    public SessieBeheren(DomeinController dc, StartUpInterface s, String gebruikersCode){
        this.dc = dc;
        this.startUpInterface = s;
        this.gebruikersCode = gebruikersCode;
        //sessieBeheren();
    }
/*
    private void sessieBeheren() {
        System.out.println("1. Toon alle open sessies");
        System.out.println("2. Toon alle gesloten sessies");
        System.out.println("3. Toon alle sessies");
        int toonSessies = Integer.parseInt(in.nextLine());

        switch (dc.geefGebruikerProfiel(gebruikersCode)) {
            case "HOOFDVERANTWOORDELIJKE":
                if(dc.geefAantalSessies() == 0){
                System.out.println("Er zijn geen sessies");
                } else{
                switch(toonSessies){
                    case 1:
                        if (dc.geefAantalOpenSessies()==0) {
                            System.out.println("Er zijn geen open sessies");
                        }
                        else {
                            sessieBeherenAlsHoofdVerantwoordelijke(true);//open
                        }
                        break;
                    case 2:
                        if (dc.geefAantalSessies() - dc.geefAantalOpenSessies()==0) {
                            System.out.println("Er zijn geen gesloten sessies");
                        }
                        else {
                            sessieBeherenAlsHoofdVerantwoordelijke(false);//closed
                        }
                        break;
                    case 3:
                    default:
                        sessieBeherenAlsHoofdVerantwoordelijke(true);
                        sessieBeherenAlsHoofdVerantwoordelijke(false);
                        break;
                }
                }

                break;
            case "VERANTWOORDELIJKE":
                if (dc.geefAantalSessies(gebruikersCode) == 0) {
                    System.out.println("Er zijn geen sessies");
                } else {
                switch(toonSessies) {
                        case 1:
                            if (dc.geefAantalOpenSessies(gebruikersCode) == 0) {
                                System.out.println("Er zijn geen open sessies");
                            } else {
                                sessieBeherenAlsVerantwoordelijke(gebruikersCode, true);//open
                            }
                            break;
                        case 2:
                            if (dc.geefAantalSessies(gebruikersCode) - dc.geefAantalOpenSessies(gebruikersCode) == 0) {
                                System.out.println("Er zijn geen gesloten sessies");
                            } else {
                                sessieBeherenAlsVerantwoordelijke(gebruikersCode, false);//closed
                            }
                            break;
                        case 3:
                        default:
                            sessieBeherenAlsVerantwoordelijke(gebruikersCode, true);
                            sessieBeherenAlsVerantwoordelijke(gebruikersCode, false);
                            break;
                    }
                }

                break;
        }
        kiesSessieNr();
    }

    private void sessieBeherenAlsVerantwoordelijke(String gebruikersCode, boolean open) {
        dc.geefOverzichtVerantwoordelijke(gebruikersCode, open).forEach(e -> System.out.println(e));

    }

    private void sessieBeherenAlsHoofdVerantwoordelijke(boolean open) {
        dc.geefOverzichtHoofdverantwoordelijke(open).forEach(e -> System.out.println(e));;

    }

    private void kiesSessieNr(){
        int teller = 0;
        String keuzeSessie = "";
        do {
            if (teller>0)System.out.println("Sessie id " + keuzeSessie + " bestaat niet, probeer opnieuw");
            System.out.println("Kies een sessie adhv het id");
            System.out.println("Kies -1 om een nieuwe sessie aan te maken");
            System.out.println("Kies 0 om terug te gaan: ");
            keuzeSessie = in.nextLine();
            if(keuzeSessie.equals("0") || ++teller >= 3){
                if(teller >= 3) System.out.println("Te veel foutieve pogingen, keer terug naar menu");
                this.startUpInterface.geefOpties(gebruikersCode);
            }
            if(keuzeSessie.equals("-1")) maakNieuweSessieAan();
        }while (!dc.bestaatSessie(keuzeSessie));
        kiesDetailNrSessie(keuzeSessie);
    }

    private void kiesDetailNrSessie(String gekozenSessieNr) {
        int detailNrSessie = -1;
        while (detailNrSessie != 0) {
            System.out.println("Gekozen sessie ziet er momenteel als volgt uit.");
            System.out.println(dc.geefDetailVanSessie(gekozenSessieNr));
            System.out.println("Geef het nummer van het sessiedetail dat u wenst u aan te passen, geef 0 om te stoppen. ");
            detailNrSessie = Integer.parseInt(in.nextLine());
            while (detailNrSessie < 0) {
                System.out.println("Geen geldig nummer, probeer opnieuw");
                System.out.println("Geef het nummer van het sessiedetail dat u wenst u aan te passen, geef 0 om terug te keren. ");
                detailNrSessie = Integer.parseInt(in.nextLine());
            }
            if(detailNrSessie == 0) this.startUpInterface.geefOpties(gebruikersCode);
            if(dc.isSessieOpen(gekozenSessieNr)){
                aanpasbareDetailsGeopendeSessie(gekozenSessieNr, detailNrSessie);
            }else{
                aanpasbareDetailsSessie(gekozenSessieNr, detailNrSessie);
            }
        }
    }

    private void aanpasbareDetailsGeopendeSessie(String gekozenSessieNr, int detailNrSessie) {
        switch(detailNrSessie){
            case 1:
                System.out.print("Pas titel aan: ");
                String titel = in.nextLine();
                break;

            case 2:
                System.out.println("Pas naam gastspreker aan: ");
                String naamGastspreker = in.nextLine();
                break;

            case 3:
                System.out.print("Pas lokaal aan: ");
                String lokaal = in.nextLine();
                break;

            case 4:
                System.out.print("Pas start uur aan: ");
                String startUur = in.nextLine();
                break;

            case 5:
                System.out.print("Pas eind uur aan: ");
                String eindUur = in.nextLine();
                break;

            case 6:
                System.out.print("Pas maximum aantal plaatsen aan: ");
                String maxPlaatsen = in.nextLine();
                break;
            case 7:
                System.out.print("Pas automatische herrinering aan: ");
                String automatischeHerinnering = in.nextLine();
                break;

            case 8:
                System.out.print("Pas tijdstip automatische herrinering aan: ");
                String tijdstipAutomatischeHerinnering = in.nextLine();
                break;
            case 9:
                System.out.print("Pas inhoud aankondiging aan: ");
                String inhoudAankondiging = in.nextLine();
                break;
            case 10:
                System.out.println("Pas media aan: ");
                //toon alle media en pas aan
            case 11:
                    System.out.print("Pas ingeschreven gebruikers aan");
                //toon alle gebruikers naam + status aanwezigheid
                break;
            case 12:
                switch (dc.geefGebruikerProfiel(gebruikersCode)){
                    case "HOOFDVERANTWOORDELIJKE":
                        int keuzeActieFeedback = 0, keuzeFeedback = 0;
                        dc.geefAlleFeedbackVanSessie(gekozenSessieNr);
                        do {
                            System.out.println("Kies welke feedback je wenst aan te passen, 0 om terug te gaan");
                            keuzeFeedback = Integer.parseInt(in.nextLine());
                            if(keuzeFeedback == 0) kiesDetailNrSessie(gekozenSessieNr);
                        } while(keuzeFeedback < 0 || dc.geefAantalFeedbackObjVanSessie(gekozenSessieNr) < keuzeFeedback);
                        do{
                            System.out.println("1. Pas feedback aan");
                            System.out.println("2. Verwijder feedback");
                            keuzeActieFeedback = Integer.parseInt(in.nextLine());
                        }while(keuzeActieFeedback < 1 && keuzeActieFeedback > 2);
                        if(keuzeActieFeedback == 1){
                            String feedback ="";
                            do {
                                System.out.println("Pas feedback aan: ");
                                feedback = in.nextLine();
                            }while (feedback.isBlank());
                            dc.pasFeedbackAanVanSessie(gekozenSessieNr, keuzeFeedback, feedback);
                        } else if(keuzeFeedback == 2){
                            dc.verwijderFeedbackVanSessie(gekozenSessieNr, keuzeFeedback);
                        }
                        break;
                    case "VERANTWOORDELIJKE":
                        System.out.println("Alleen hoofdverantwoordelijke mag feedback aanpassen");
                        break;
                }
                break;
            default:
                System.out.println("Geen geldige keuze, probeer opnieuw");
                break;
        }
    }

    private void aanpasbareDetailsSessie(String gekozenSessieNr, int detailNrSessie){
        switch(detailNrSessie){
            case 1:
                System.out.print("Pas titel aan: ");
                String titel = in.nextLine();
                break;

            case 2:
                System.out.println("Pas naam gastspreker aan: ");
                String naamGastspreker = in.nextLine();
                break;

            case 3:
                System.out.print("Pas lokaal aan: ");
                String lokaal = in.nextLine();
                break;

            case 4:
                System.out.print("Pas start uur aan: ");
                String startUur = in.nextLine();
                break;

            case 5:
                System.out.print("Pas eind uur aan: ");
                String eindUur = in.nextLine();
                break;

            case 6:
                System.out.print("Pas maximum aantal plaatsen aan: ");
                String maxPlaatsen = in.nextLine();
                break;
            case 7:
                System.out.print("Pas automatische herrinering aan: ");
                String automatischeHerinnering = in.nextLine();
                break;

            case 8:
                System.out.print("Pas tijdstip automatische herrinering aan: ");
                String tijdstipAutomatischeHerinnering = in.nextLine();
                break;
            case 9:
                System.out.print("Pas inhoud aankondiging aan: ");
                String inhoudAankondiging = in.nextLine();
                break;
            case 10:
                System.out.println("Pas media aan: ");
                //toon alle media en pas aan
            case 11:
                if(dc.isSessieOpen(gekozenSessieNr)){
                    System.out.print("Pas ingeschreven gebruikers aan");
                }
                break;
            case 12:
                if(dc.isSessieOpen(gekozenSessieNr)){
                    System.out.print("Pas ingeschreven gebruikers aan");
                }
                break;
            default:
                System.out.println("Geen geldige keuze, probeer opnieuw");
                kiesDetailNrSessie(gekozenSessieNr);
                break;
        }
    }

    private void maakNieuweSessieAan(){
        System.out.println("Bent u zeker dat u een nieuwe sessie wilt aanmaken?");
        System.out.println("Typ 0 om terug te gaan");
        int aanmakenKeuze = Integer.parseInt(in.nextLine());
        if(aanmakenKeuze == 0)
        this.startUpInterface.geefOpties(gebruikersCode);
    }



 */
}