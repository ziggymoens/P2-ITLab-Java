package domein.gebruiker;

import javax.persistence.*;

public class NietActiefStatusState extends GebruikerStatusState {
    protected NietActiefStatusState(Gebruiker gebruiker) {
        super("NIET ACTIEF", gebruiker);
    }
}