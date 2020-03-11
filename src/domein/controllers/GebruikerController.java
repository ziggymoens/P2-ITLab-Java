package domein.controllers;

import domein.Gebruiker;
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
    public Gebruiker geefGebruikerId(String id) {
        return null;
    }

    @Override
    public List<Gebruiker> geefAlleGebruikers() {
        return null;
    }

    @Override
    public List<Gebruiker> geefAlleGerbuikersNaam(String naam) {
        return null;
    }

    @Override
    public List<Gebruiker> geefAlleGebuikersType(String type) {
        return null;
    }

    @Override
    public List<Gebruiker> geefAlleGebruikersSessie() {
        return null;
    }

    @Override
    public List<Gebruiker> geefAlleGerbuikersNaamSessie() {
        return null;
    }

    @Override
    public List<Gebruiker> geefAlleGebuikersTypeSessie() {
        return null;
    }
}
