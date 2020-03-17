package domein.gebruiker;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "actiefStatusState")
public class ActiefStatusState extends GebruikerStatusState {

    private int inlogPogingen;

    public ActiefStatusState() {
    }

    protected ActiefStatusState(Gebruiker gebruiker) {
        super("ACTIEF", gebruiker);
        this.inlogPogingen = 0;
    }

    @Override
    public String getStatus() {
        return "ACTIEF";
    }

}
