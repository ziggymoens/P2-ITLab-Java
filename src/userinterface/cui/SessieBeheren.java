package userinterface.cui;

import domein.DomeinController;
import domein.Gebruiker;

public class SessieBeheren {
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
        dc.geefOverzichtVerantwoordelijke(gebruiker);

    }

    private void sessieBeherenAlsVerantwoordelijke() {
        System.out.println(dc.geefOverzichtVerantwoordelijke(gebruiker));
    }

    private void sessieBeherenAlsHoofdVerantwoordelijke() {
        System.out.println(dc.geefOverzichtHoofdverantwoordelijke());
    }


}
