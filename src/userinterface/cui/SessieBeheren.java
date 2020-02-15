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
        switch (gebruiker.getType()){
            case HOOFDVERANTWOORDELIJKE:
                sessieBeherenAlsHoofdVerantwoordelijke();
                break;
            case VERANTWOORDELIJKE:
                sessieBeherenAlsVerantwoordelijke();
                break;
        }
        System.out.println("Kies een sessie: ");
        int keuzeSessie = in.nextInt();

        int keuzeVoorAanpassen = 1;
        while(keuzeVoorAanpassen != 0){
            System.out.println("Gekozen sessie ziet er momenteel als volgt uit.");
            System.out.println(dc.geefDetailVanSessie(keuzeSessie));
            while(keuzeVoorAanpassen<0 && keuzeVoorAanpassen > 11) {
                System.out.println("Geef het nummer van het sessiedetail dat u wenst u aan te passen, geef 0 om te stoppen. ");
                keuzeVoorAanpassen = in.nextInt();
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
