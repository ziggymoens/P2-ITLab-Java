package domein.controllers;

import domein.Aankondiging;
import domein.gebruiker.Gebruiker;
import domein.sessie.Sessie;
import domein.SessieKalender;

import java.time.LocalDate;
import java.util.List;

public class VerantwoordelijkeStrategy implements TypeStrategy {
    private SessieKalender huidigeSessieKalender;
    private Gebruiker gebruiker;
    private int aj;

    public VerantwoordelijkeStrategy(SessieKalender huidigeSessieKalender, Gebruiker gebruiker) {
        this.huidigeSessieKalender = huidigeSessieKalender;
        this.gebruiker = gebruiker;
    }

    @Override
    public Sessie geefSessieId(String id) {
        return geefAlleSessiesKalender(aj)
                .stream()
                .filter(e -> e.getVerantwoordelijke().getGebruikersnaam().equals(gebruiker.getGebruikersnaam()))
                .filter(e -> e.getSessieId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<Sessie> geefAlleSessiesKalender(Integer jaar) {
        aj = jaar;
        return huidigeSessieKalender.geefAlleSessiesKalenderVanGebruiker(jaar,gebruiker);
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
    public void bewerkSessie(Sessie sessie, List<Object> veranderingen) {

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

    @Override
    public void maakAankondigingAan(Aankondiging aankondiging, Sessie sessie) {

    }

    @Override
    public void bewerkAankondiging(Aankondiging aankondiging) {

    }

    @Override
    public void verwijderAankondiging(Aankondiging aankondiging) {

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
