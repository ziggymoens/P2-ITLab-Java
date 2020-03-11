package domein.controllers;

import domein.Gebruiker;
import domein.Sessie;
import domein.SessieKalender;

import java.util.Arrays;
import java.util.List;

public abstract class TypeVerantwoordelijkeController implements ITypeController {
    private SessieKalender huidigeSessieKalender;

    public TypeVerantwoordelijkeController(SessieKalender huidigeSessieKalender){
        this.huidigeSessieKalender = huidigeSessieKalender;
    }

    public SessieKalender getHuidigeSessieKalender(){
        return huidigeSessieKalender;
    }

    public void bewerkSessie(Sessie sessie){
        huidigeSessieKalender.updateSessie(sessie);
    }

    public void verwijderSessie(Sessie sessie){
        huidigeSessieKalender.verwijderSessie(sessie);
    }

    public void maakSessieAan(Sessie sessie){
        maakSessieAan(sessie);
    }

    public List<String> filterOpties(){
        String[] keuzeVoorZoeken = {"Titel", "Stad", "Status"};
        return Arrays.asList(keuzeVoorZoeken);
    }

    public abstract List<Gebruiker> geefAlleGebruikers();
    public abstract Gebruiker geefGebruikerId();
    public abstract List<Gebruiker> geefAlleGerbuikersNaam();
    public abstract List<Gebruiker> geefAlleGebuikersType();
    public abstract List<Gebruiker> geefAlleGebruikersSessie();
    public abstract List<Gebruiker> geefAlleGerbuikersNaamSessie();
    public abstract List<Gebruiker> geefAlleGebuikersTypeSessie();
}
