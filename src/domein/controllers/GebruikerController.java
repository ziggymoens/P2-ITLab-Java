package domein.controllers;

import domein.Sessie;
import domein.SessieKalender;

import java.time.LocalDate;
import java.util.List;

public class GebruikerController implements ITypeController {
    private SessieKalender huidigeSessieKalender;

    public GebruikerController(SessieKalender huidigeSessieKalender) {
       this.huidigeSessieKalender = huidigeSessieKalender;
    }


    @Override
    public Sessie geefSessieId(String id) {
        return null;
    }

    @Override
    public List<Sessie> geefAlleSessiesHuidigeKalender() {
        return null;
    }

    @Override
    public List<Sessie> geefAlleSessiesTitel(String titel) {
        return null;
    }

    @Override
    public List<Sessie> geefAlleSessiesDatum(LocalDate datum) {
        return null;
    }

    @Override
    public List<Sessie> geefAlleSessiesLocatie(String locatie) {
        return null;
    }

    @Override
    public List<Sessie> geefAlleSessiesKalender(Integer jaar) {
        return null;
    }

    @Override
    public List<String> filterOpties() {
        return null;
    }
}
