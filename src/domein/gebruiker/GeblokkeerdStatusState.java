package domein.gebruiker;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("GEBLOKKEERD")
public class GeblokkeerdStatusState extends GebruikerStatusState {

    private static final long serialVersionUID = -7546130762513143650L;

    protected GeblokkeerdStatusState() {
    }

    public GeblokkeerdStatusState(Gebruiker gebruiker) {
        super(gebruiker);
    }

    @Override
    public String getStatus() {
        return "GEBLOKKEERD";
    }

}
