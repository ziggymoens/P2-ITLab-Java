package domein.controllers;

import domein.SessieKalenderDataInit;
import domein.gebruiker.Gebruiker;
import domein.SessieKalender;
import domein.interfacesDomein.IGebruiker;

import java.time.LocalDate;
import java.util.List;

public class StartController {
    private SessieKalender sessieKalender;
    private Gebruiker gebruiker;

    public StartController() {
        sessieKalender = new SessieKalender();
        //SessieKalenderDataInit sessieKalenderDataInit = new SessieKalenderDataInit(sessieKalender);
    }

    public List<IGebruiker> geefIGebruikers() {
        return (List<IGebruiker>) (Object) sessieKalender.geefAlleGebruikers();
    }

    public void setHuidigeGebruiker(String gebruiker) {
        this.gebruiker = (Gebruiker) geefIGebruikers().stream().filter(g -> g.getGebruikersnaam().equals(gebruiker)).findFirst().orElse(null);
    }

    public DomeinController initDomeinController() {
        return new DomeinController(gebruiker, sessieKalender, LocalDate.now());
    }
}
