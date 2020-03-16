package domein.gebruiker;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "verantwoordelijkeState")
public class VerantwoordelijkeState extends GebruikerProfielState {
    public VerantwoordelijkeState() {
    }

    public VerantwoordelijkeState(Gebruiker gebruiker) {
        super("VERANTWOORDELIJKE", gebruiker);
    }

    @Override
    public String getProfiel() {
        return "VERANTWOORDELIJKE";
    }
}
