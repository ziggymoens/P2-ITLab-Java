package userinterface.cui.ucs;

import domein.DomeinController;

import java.util.Scanner;

public class SessieBeheren {
    private Scanner in = new Scanner(System.in);
    private DomeinController dc;
    private String gebruikersNaam;

    public SessieBeheren(DomeinController dc, String gebruikersNaam){
        this.dc = dc;
        this.gebruikersNaam = gebruikersNaam;
        sessieBeheren();
    }

    private void sessieBeheren() {
        switch (dc.geefProfielVanGebruiker(gebruikersNaam)) {
            case "HOOFDVERANTWOORDELIJKE":
                sessieBeherenAlsHoofdVerantwoordelijke();
                break;
            case "VERANTWOORDELIJKE":
                sessieBeherenAlsVerantwoordelijke(gebruikersNaam);
                break;
        }
        System.out.println("\nKies een sessie: ");
        int keuzeSessie = in.nextInt();


        int keuzeVoorAanpassen = -1;
        while (keuzeVoorAanpassen != 0) {
            System.out.println("Gekozen sessie ziet er momenteel als volgt uit.");
           // System.out.println(dc.geefDetailVanSessie(keuzeSessie) + "\n");
            System.out.println("Geef het nummer van het sessiedetail dat u wenst u aan te passen, geef 0 om te stoppen. ");
            keuzeVoorAanpassen = in.nextInt();
            while (keuzeVoorAanpassen < 0 && keuzeVoorAanpassen > 11) {
                System.out.println("\n" + "Geen geldig nummer, probeer opnieuw");
                System.out.println("Geef het nummer van het sessiedetail dat u wenst u aan te passen, geef 0 om te stoppen. ");
                keuzeVoorAanpassen = in.nextInt();
            }
            switch(keuzeVoorAanpassen){

                case 1:
                    System.out.print("Pas titel aan: ");
                        String titel = in.next();
                    break;

                case 2:
                    System.out.println("Pas naam gastspreker aan: ");
                    String naamGastspreker = in.next();
                    break;

                case 3:
                    System.out.print("Pas lokaal aan: ");
                    String lokaal = in.next();
                    break;

                case 4:
                    System.out.print("Pas start uur aan: ");
                    String startUur = in.next();
                    break;

                case 5:
                    System.out.print("Pas eind uur aan: ");
                    String eindUur = in.next();
                    break;

                case 6:
                    if(!dc.isSessieOpen(Integer.toString(keuzeSessie))){
                        System.out.print("Pas maximum aantal plaatsen aan: ");
                        String maxPlaatsen = in.next();
                    }
                    break;
                case 7:
                    System.out.print("Pas automatische herrinering aan: ");
                    String automatischeHerinnering = in.next();
                    break;

                case 8:
                    System.out.print("Pas tijdstip automatische herrinering aan: ");
                    String tijdstipAutomatischeHerinnering = in.next();
                    break;
                case 9:
                    System.out.print("Pas inhoud aankondiging aan: ");
                    String inhoudAankondiging = in.next();
                    break;
                case 10:
                    if(dc.isSessieOpen(Integer.toString(keuzeSessie))){
                        System.out.print("Pas ingeschreven gebruikers aan");
                    }
                    break;
                case 11:
                    if(dc.isSessieOpen(Integer.toString(keuzeSessie))){
                        System.out.print("Pas ingeschreven gebruikers aan");
                    }
                    break;


            }
        }
    }

    private void sessieBeherenAlsVerantwoordelijke(String gebruikersCode) {
        System.out.println(dc.geefOverzichtVerantwoordelijke(gebruikersCode));
    }

    private void sessieBeherenAlsHoofdVerantwoordelijke() {
        System.out.println(dc.geefOverzichtHoofdverantwoordelijke());
    }


}