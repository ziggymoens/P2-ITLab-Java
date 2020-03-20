package domein.gebruiker;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("VERANTWOORDELIJKE")
public class VerantwoordelijkeState extends GebruikerProfielState {

    private static final long serialVersionUID = 7397529294386849587L;

    public VerantwoordelijkeState() {
    }

    public VerantwoordelijkeState(Gebruiker gebruiker) {
        super(gebruiker);
    }

    @Override
    public String getProfiel() {
        return "verantwoordelijke";
    }

}
