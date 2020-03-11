package domein.controllers;

import domein.Gebruiker;
import domein.Sessie;
import domein.SessieKalender;

import java.time.LocalDate;
import java.util.List;

public class GebruikerController implements ITypeController {
    private SessieKalender huidigeSessieKalender;
    private Gebruiker gebruiker;

    public GebruikerController(SessieKalender huidigeSessieKalender, Gebruiker gebruiker) {
        this.huidigeSessieKalender = huidigeSessieKalender;
        this.gebruiker = gebruiker;
    }

    @Override
    public Sessie geefSessieId(String id) {
        return null;
    }

    @Override
    public List<Sessie> geefAlleSessiesKalender(Integer jaar) {
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
    public void bewerkSessie(Sessie sessie) {

    }

    @Override
    public void verwijderSessie(Sessie sessie) {

    }

    @Override
    public void maakSessieAan(Sessie sessie) {

    }

    @Override
    public List<Gebruiker> geefAlleGebruikersSessie(String id) {
        return null;
    }

    @Override
    public List<Gebruiker> geefAlleGerbuikersNaamSessie(String id, String naam) {
        return null;
    }

    @Override
    public List<Gebruiker> geefAlleGebuikersTypeSessie(String id, String type) {
        return null;
    }

    @Override
    public void maakGebruikerAan(Gebruiker gebruiker) {

    }

    @Override
    public void bewerkGebruiker(Gebruiker gebruiker) {

    }

    @Override
    public void verwijderGebruiker(Gebruiker gebruiker) {

    }
}
