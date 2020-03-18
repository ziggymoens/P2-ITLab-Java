package domein.gebruiker;

import javax.persistence.*;

@Entity
@DiscriminatorValue("NIET_ACTIEF")
public class NietActiefStatusState extends GebruikerStatusState {

    private static final long serialVersionUID = -2297467614703375098L;

    protected NietActiefStatusState() {
    }

    public NietActiefStatusState(Gebruiker gebruiker) {
        super(gebruiker);
    }

    @Override
    public String getStatus() {
        return "NIET ACTIEF";
    }
}