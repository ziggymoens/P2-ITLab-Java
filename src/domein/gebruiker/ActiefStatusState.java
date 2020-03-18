package domein.gebruiker;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@DiscriminatorValue("ACTIEF")
public class ActiefStatusState extends GebruikerStatusState {
    private static final long serialVersionUID = 603309081014144108L;

    protected ActiefStatusState() {
    }

    public ActiefStatusState(Gebruiker gebruiker) {
        super(gebruiker);
    }

    @Override
    public String getStatus() {
        return "ACTIEF";
    }


}
