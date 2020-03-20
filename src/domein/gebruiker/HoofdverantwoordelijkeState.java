package domein.gebruiker;

import javax.persistence.*;

@Entity
@DiscriminatorValue("HOOFDVERANTWOORDELIJKE")
public class HoofdverantwoordelijkeState extends GebruikerProfielState {

    private static final long serialVersionUID = 370239301138816523L;

    public HoofdverantwoordelijkeState() {
    }

    public HoofdverantwoordelijkeState(Gebruiker gebruiker) {
        super(gebruiker);
    }

    @Override
    public String getProfiel() {
        return "hoofdverantwoordelijke";
    }

}