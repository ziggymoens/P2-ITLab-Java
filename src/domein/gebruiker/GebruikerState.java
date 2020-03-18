package domein.gebruiker;

import javax.persistence.*;

@Entity
@DiscriminatorValue("GEBRUIKER")
public class GebruikerState extends GebruikerProfielState {

    private static final long serialVersionUID = 5367848720610131072L;

    protected GebruikerState() {
    }

    public GebruikerState(Gebruiker gebruiker) {
        super(gebruiker);
    }

    @Override
    public String getProfiel() {
        return "GEBRUIKER";
    }

}
