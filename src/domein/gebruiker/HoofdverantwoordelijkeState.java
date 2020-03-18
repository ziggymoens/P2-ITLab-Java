package domein.gebruiker;

import javax.persistence.*;

public class HoofdverantwoordelijkeState extends GebruikerProfielState {

    public HoofdverantwoordelijkeState(Gebruiker gebruiker) {
        super("HOOFDVERANTWOORDELIJKE", gebruiker);
    }

}