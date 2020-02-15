package userinterface.cui;

import domein.DomeinController;
import domein.Gebruiker;

import java.util.Scanner;

public class SessieBeheren {
    Scanner in = new Scanner(System.in);
    DomeinController dc;
    Gebruiker gebruiker;

    public SessieBeheren(DomeinController dc, Gebruiker gebruiker){
        this.dc = dc;
        this.gebruiker = gebruiker;
        sessieBeheren();
    }

    private void sessieBeheren() {
        switch (gebruiker.getType()) {
            case HOOFDVERANTWOORDELIJKE:
                sessieBeherenAlsHoofdVerantwoordelijke();
                break;
            case VERANTWOORDELIJKE:
                sessieBeherenAlsVerantwoordelijke();
                break;
        }
        System.out.println("Kies een sessie: ");
        int keuzeSessie = in.nextInt();

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
        System.out.println(dc.geefOverzichtVerantwoordelijke(gebruiker));
    }

    private void sessieBeherenAlsHoofdVerantwoordelijke() {
        System.out.println(dc.geefOverzichtHoofdverantwoordelijke());
    }


}
