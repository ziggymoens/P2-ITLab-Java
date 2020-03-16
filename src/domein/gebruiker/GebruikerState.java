package domein.gebruiker;

import javax.persistence.*;

@Entity
@Table(name = "gebruikerState")
public class GebruikerState extends GebruikerProfielState {
    public GebruikerState() {
    }

    public GebruikerState(Gebruiker gebruiker) {
        super("GEBRUIKER", gebruiker);
    }

    @Override
    public String getProfiel() {
        return "GEBRUIKER";
    }
}
