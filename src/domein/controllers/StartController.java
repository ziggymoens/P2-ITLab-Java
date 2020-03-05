package domein.controllers;

import domein.*;
import domein.interfacesDomein.IGebruiker;

import java.time.LocalDate;
import java.util.List;

public class StartController {
    private int academiejaar;
    private SessieKalender sessieKalender;
    private Gebruiker gebruiker;

    public StartController() {
        this.academiejaar = geefAcademiejaar(LocalDate.now());
        sessieKalender = new SessieKalender();
        //SessieKalenderDataInit sessieKalenderDataInit = new SessieKalenderDataInit(sessieKalender);
    }

    private int geefAcademiejaar(LocalDate date) {
        int jaar = LocalDate.now().getYear() - 2000;
        int aj = 0;
        if (LocalDate.now().getMonthValue() < 9) {
            aj = Integer.parseInt(String.format("%d%d", jaar - 1, jaar));
        } else {
            aj = Integer.parseInt(String.format("%d%d", jaar, jaar + 1));
        }
        return aj;
    }

    public List<IGebruiker> geefIGebruikers() {
        return (List<IGebruiker>)(Object) sessieKalender.geefAlleGebruikers();
    }

    public void setHuidigeGebruiker(String gebruiker){
        this.gebruiker = (Gebruiker) geefIGebruikers().stream().filter(g -> g.getGebruikersnaam().equals("758095zm")).findFirst().orElse(null);
    }

    public DomeinController initDomeinController(){
        return new DomeinController(gebruiker, sessieKalender);
    }
}
