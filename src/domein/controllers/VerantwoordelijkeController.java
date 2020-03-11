package domein.controllers;

import domein.SessieKalender;
import domein.interfacesDomein.IGebruiker;

import java.util.List;

public class VerantwoordelijkeController implements IVerantwoordelijkeController{
    private SessieKalender huidigeSessie;

    public VerantwoordelijkeController(){
    }

    public List<IGebruiker> geefIGebruikers(){
        return (List<IGebruiker>) (Object) huidigeSessie.geefAlleGebruikers();
    }
}
