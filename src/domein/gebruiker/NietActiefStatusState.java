package domein.gebruiker;

import javax.persistence.*;

@Entity
@Table(name = "nietActiefStatusState")
public class NietActiefStatusState extends GebruikerStatusState {
    public NietActiefStatusState() {
    }

    protected NietActiefStatusState(Gebruiker gebruiker) {
        super("NIET ACTIEF", gebruiker);
    }

    @Override
    public String getStatus() {
        return "NIET ACTIEF";
    }
}