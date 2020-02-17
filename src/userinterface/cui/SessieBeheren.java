package userinterface.cui;

import domein.DomeinController;

import java.util.List;
import java.util.Scanner;

public class SessieBeheren {
    Scanner in = new Scanner(System.in);
    DomeinController dc;

    public SessieBeheren(DomeinController dc){
        this.dc = dc;
        sessieBeheren();
    }

    private void sessieBeheren() {
        switch (dc.getGebruiker().getType()) {
            case HOOFDVERANTWOORDELIJKE:
                sessieBeherenAlsHoofdVerantwoordelijke();
                break;
            case VERANTWOORDELIJKE:
                sessieBeherenAlsVerantwoordelijke();
                break;
        }
        System.out.println("\nKies een sessie: ");
        int keuzeSessie = in.nextInt();

        String verantwoordelijkeNaam, titel, naamGastSpreker, startSessie, eindSessie, inhoudautoHerinnering, autoHerinnering;
        String tijdstipAutoHerinnering, maxAantalPlaatsen, lokaalcode,media, gebruikers, aankondigingen,feedback;
        int keuzeVoorAanpassen = -1;
        while (keuzeVoorAanpassen != 0) {
            System.out.println("Gekozen sessie ziet er momenteel als volgt uit.");
            System.out.println(dc.geefDetailVanSessie(keuzeSessie) + "\n");
            System.out.println("Geef het nummer van het sessiedetail dat u wenst u aan te passen, geef 0 om te stoppen. ");
            keuzeVoorAanpassen = in.nextInt();
            while (keuzeVoorAanpassen < 0 && keuzeVoorAanpassen > 11) {
                System.out.println("\n" + "Geen geldig nummer, probeer opnieuw");
                System.out.println("Geef het nummer van het sessiedetail dat u wenst u aan te passen, geef 0 om te stoppen. ");
                keuzeVoorAanpassen = in.nextInt();
            }
            switch(keuzeVoorAanpassen){
                case 1:
                    System.out.print("Geef een nieuwe verantwoordelijke: ");
                        verantwoordelijkeNaam = in.next();
                        /*dc.pasSessieAan(verantwoordelijkeNaam,dc.getSessie().getTitel(),
                                dc.getSessie().getNaamGastspreker(), dc.getSessie().getLokaal().getLokaalCode(),dc.getSessie().getStartSessie().toString(),
                                dc.getSessie().getEindeSessie().toString(), dc.getSessie().getMaximumAantalPlaatsen(),
                                dc.getSessie().isAutomatischeHerinnering()?"ja":"nee",dc.getSessie().getHerinnering().getDagenVooraf(),
                                dc.getSessie().getHerinnering().getInhoud(), dc.getSessie().getMediaBijSessie().toString(),
                                dc.getSessie().toString_OverzichtInschrijvingenNietGeopend(), dc.getSessie().toString_OverzichtAankondigingen());*/
                        // verkeerd
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
                    break;

            }
        }
    }

    private void sessieBeherenAlsVerantwoordelijke() {
        System.out.println(dc.geefOverzichtVerantwoordelijke(dc.getGebruiker()));
    }

    private void sessieBeherenAlsHoofdVerantwoordelijke() {
        System.out.println(dc.geefOverzichtHoofdverantwoordelijke());
    }


}