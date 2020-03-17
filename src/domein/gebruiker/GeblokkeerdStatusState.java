package domein.gebruiker;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "geblokkeersStatusState")
public class GeblokkeerdStatusState extends GebruikerStatusState {


    public GeblokkeerdStatusState() {
    }

    protected GeblokkeerdStatusState(Gebruiker gebruiker) {
        super("GEBLOKKEERD", gebruiker);
    }

    @Override
    public String getStatus() {
        return "GEBLOKKEERD";
    }
}
