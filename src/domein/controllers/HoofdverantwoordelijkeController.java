package domein.controllers;

import domein.Aankondiging;
import domein.gebruiker.Gebruiker;
import domein.sessie.Sessie;
import domein.SessieKalender;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class HoofdverantwoordelijkeController extends ITypeController {
    private SessieKalender huidigeSessieKalender;
    private Gebruiker gebruiker;
    private int aj;

    public HoofdverantwoordelijkeController(SessieKalender huidigeSessieKalender, Gebruiker gebruiker) {
        this.huidigeSessieKalender = huidigeSessieKalender;
        this.gebruiker = gebruiker;
    }
/*
    @Override
    public void bewerkSessie(Sessie sessie) {
        huidigeSessieKalender.updateSessie(sessie);
    }
 */

    @Override
    public void verwijderSessie(Sessie sessie) {
        huidigeSessieKalender.verwijderSessie(sessie);
    }

    @Override
    public void maakSessieAan(Sessie sessie) {
        huidigeSessieKalender.voegSessieToe(sessie);
    }

    @Override
    public List<Gebruiker> geefAlleGebruikersSessie(String id) {
        return geefSessieId(id).getInschrijvingen().stream().map(e -> e.getGebruiker()).collect(Collectors.toList());
    }

    @Override
    public List<Gebruiker> geefAlleGerbuikersNaamSessie(String id, String naam) {
        return geefSessieId(id).getInschrijvingen().stream().filter(e -> e.getGebruiker().getNaam().matches("(\\.*)" + naam + "(\\.*)")).map(e -> e.getGebruiker()).collect(Collectors.toList());
    }

    @Override
    public List<Gebruiker> geefAlleGebuikersTypeSessie(String id, String type) {
        return geefSessieId(id).getInschrijvingen().stream().filter(e -> e.getGebruiker().getGebruikersprofiel().toString().equals(type)).map(e -> e.getGebruiker()).collect(Collectors.toList());
    }

    @Override
    public void maakGebruikerAan(Gebruiker gebruiker) {
        huidigeSessieKalender.voegGebruikerToe(gebruiker);
    }

    @Override
    public void bewerkGebruiker(Gebruiker gebruiker) {

    }
/*
    @Override
    public void bewerkGebruiker(Gebruiker gebruiker) {
        huidigeSessieKalender.updateGebruiker(gebruiker);
    }

 */

    @Override
    public void verwijderGebruiker(Gebruiker gebruiker) {
        huidigeSessieKalender.verwijderGebruiker(gebruiker);
    }

    @Override
    public void maakAankondigingAan(Aankondiging aankondiging, Sessie sessie) {
        huidigeSessieKalender.voegAankondigingToe(aankondiging, sessie);
    }

    @Override
    public void bewerkAankondiging(Aankondiging aankondiging) {

    }

    @Override
    public void verwijderAankondiging(Aankondiging aankondiging) {
        huidigeSessieKalender.verwijderAankondiging(aankondiging);
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

    @Override
    public Sessie geefSessieId(String id) {
        return huidigeSessieKalender.geefSessieById(id);
    }

    @Override
    public List<Sessie> geefAlleSessiesTitel(String titel) {
        return geefAlleSessiesKalender(aj).stream().filter(e -> e.getTitel().matches("(\\.*)" + titel + "(\\.*)")).collect(Collectors.toList());
    }

    @Override
    public List<Sessie> geefAlleSessiesDatum(LocalDate datum) {
        return geefAlleSessiesKalender(aj).stream().filter(s -> s.getStartSessie().getDayOfYear() == datum.getDayOfYear() && s.getStartSessie().getYear() == datum.getYear()).collect(Collectors.toList());
    }

    @Override
    public List<Sessie> geefAlleSessiesLocatie(String locatie) {
        return geefAlleSessiesKalender(aj)
                .stream()
                .filter(s -> s.getLokaal().getStad().equals(locatie.toUpperCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void bewerkSessie(Sessie sessie) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Sessie> geefAlleSessiesKalender(Integer jaar) {
        aj = jaar;
        return huidigeSessieKalender.geefAlleSessiesKalender(jaar);
    }
}
