package domein.gebruiker;

import javax.persistence.Entity;

public class GeblokkeerdStatusState extends GebruikerStatusState {


    protected GeblokkeerdStatusState(Gebruiker gebruiker) {
        super("GEBLOKKEERD", gebruiker);
    }

}
