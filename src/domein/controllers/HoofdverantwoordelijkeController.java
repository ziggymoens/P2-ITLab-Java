package domein.controllers;

import domein.Sessie;
import domein.SessieKalender;
import domein.interfacesDomein.IGebruiker;

import java.util.List;

public class HoofdverantwoordelijkeController implements IVerantwoordelijkeController {
    private SessieKalender huidigeSessieKalender;

    public HoofdverantwoordelijkeController(){
        this.huidigeSessieKalender = huidigeSessieKalender;
    }

    public List<IGebruiker> geefIGebruikers() {
        return (List<IGebruiker>)(Object) huidigeSessieKalender.geefAlleGebruikers();
    }
}
