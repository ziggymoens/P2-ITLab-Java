package domein.gebruiker;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "actiefStatusState")
public class ActiefStatusState extends GebruikerStatusState {

    private int inlogPogingen;
    private LocalDate laaststeLogin;

    public ActiefStatusState() {
    }

    protected ActiefStatusState(Gebruiker gebruiker) {
        super("ACTIEF", gebruiker);
    }

    @Override
    public String getStatus() {
        return "ACTIEF";
    }
}
