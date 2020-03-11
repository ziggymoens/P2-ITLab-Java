package domein.controllers;

import domein.Gebruiker;
import domein.SessieKalender;
import domein.interfacesDomein.IGebruiker;

import java.util.List;

public class StartController {
    private int academiejaar;
    private SessieKalender sessieKalender;
    private Gebruiker gebruiker;

    public StartController() {
  /*      this.academiejaar = geefAcademiejaar(); //why?*/
        sessieKalender = new SessieKalender();
        //SessieKalenderDataInit sessieKalenderDataInit = new SessieKalenderDataInit(sessieKalender);
    }

/*    private int geefAcademiejaar() { //why?
        int jaar = LocalDate.now().getYear() - 2000;
        int aj = 0;
        if (LocalDate.now().getMonthValue() < 9) {
            aj = Integer.parseInt(String.format("%d%d", jaar - 1, jaar));
        } else {
            aj = Integer.parseInt(String.format("%d%d", jaar, jaar + 1));
        }
        return aj;
    }*/

    public List<IGebruiker> geefIGebruikers() {
        return (List<IGebruiker>)(Object) sessieKalender.geefAlleGebruikers();
    }

    public void setHuidigeGebruiker(String gebruiker){
        this.gebruiker = (Gebruiker) geefIGebruikers().stream().filter(g -> g.getGebruikersnaam().equals(gebruiker)).findFirst().orElse(null);
    }

    public DomeinController initDomeinController(){
        return new DomeinController(gebruiker, sessieKalender);
    }
}
