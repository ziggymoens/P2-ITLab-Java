package domein.gebruiker;

import javax.persistence.*;

public class GebruikerState extends GebruikerProfielState {

    public GebruikerState(Gebruiker gebruiker) {
        super("GEBRUIKER", gebruiker);
    }

}
