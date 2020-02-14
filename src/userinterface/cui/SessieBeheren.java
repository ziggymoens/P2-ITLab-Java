package userinterface.cui;

import domein.DomeinController;
import domein.Gebruiker;

public class SessieBeheren {
    DomeinController dc;
    Gebruiker gebruiker;

    public SessieBeheren(DomeinController dc, Gebruiker gebruiker){
        this.dc = dc;
        this.gebruiker = gebruiker;
        sessiebeheren();
    }

    private void sessiebeheren() {
        dc.geefOverzichtVerantwoordelijke(gebruiker);

    }


}
