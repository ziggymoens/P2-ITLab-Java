package domein.gebruiker;

import javax.persistence.*;

@Entity
@Table(name = "hoofdverantwoordelijkeState")
public class HoofdverantwoordelijkeState extends GebruikerProfielState {
    public HoofdverantwoordelijkeState() {

    }

    public HoofdverantwoordelijkeState(Gebruiker gebruiker) {
        super("HOOFDVERANTWOORDELIJKE", gebruiker);
    }

    @Override
    public String getProfiel() {
        return "HOOFDVERANTWOORDELIJKE";
    }
}
