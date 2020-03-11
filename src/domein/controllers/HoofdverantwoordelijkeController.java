package domein.controllers;

import domein.Gebruiker;
import domein.Sessie;
import domein.SessieKalender;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HoofdverantwoordelijkeController implements ITypeController {
    private SessieKalender huidigeSessieKalender;

    public HoofdverantwoordelijkeController(SessieKalender huidigeSessieKalender){
        this.huidigeSessieKalender = huidigeSessieKalender;
    }


    @Override
    public void bewerkSessie(Sessie sessie) {
        huidigeSessieKalender.updateSessie(sessie);
    }

    @Override
    public void verwijderSessie(Sessie sessie) {
        huidigeSessieKalender.verwijderSessie(sessie);
    }

    @Override
    public void maakSessieAan(Sessie sessie) {
        huidigeSessieKalender.voegSessieToe(sessie);
    }

    @Override
    public Gebruiker geefGebruikerId(String id) {
        return huidigeSessieKalender.geefGebruikerById(id);
    }

    @Override
    public List<Gebruiker> geefAlleGebruikers() {
        return huidigeSessieKalender.geefAlleGebruikers();
    }

    @Override
    public List<Gebruiker> geefAlleGerbuikersNaam(String naam) {
        return huidigeSessieKalender.geefAlleGebruikers()
                .stream()
                .filter(e -> e.getNaam().matches("(\\.*)"+naam+"(\\.*)"))
                .collect(Collectors.toList());
    }

    @Override
    public List<Gebruiker> geefAlleGebuikersType(String type) {
        return huidigeSessieKalender.geefAlleGebruikers()
                .stream()
                .filter(e -> e.getGebruikersprofiel().toString().equals(type))
                .collect(Collectors.toList());
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
        return geefAlleSessiesKalender(DomeinController.academiejaar).stream().filter(e -> e.getTitel().matches("(\\.*)"+titel+"(\\.*)")).collect(Collectors.toList());
    }

    @Override
    public List<Sessie> geefAlleSessiesDatum(LocalDate datum) {
        return geefAlleSessiesKalender(DomeinController.academiejaar).stream().filter(s -> s.getStartSessie().getDayOfYear() == datum.getDayOfYear() && s.getStartSessie().getYear()==datum.getYear()).collect(Collectors.toList());
    }

    @Override
    public List<Sessie> geefAlleSessiesLocatie(String locatie) {
        List<Sessie> sessies = null;
        switch (locatie){
            case "Gent":
                sessies = geefAlleSessiesKalender(DomeinController.academiejaar)
                        .stream()
                        .filter(e -> e.getLokaal().getLokaalCode().matches("GS\\.+"))
                        .collect(Collectors.toList());

                break;
            case "Aalst":
                sessies = geefAlleSessiesKalender(DomeinController.academiejaar)
                        .stream()
                        .filter(e -> e.getLokaal().getLokaalCode().matches("GA\\.+"))
                        .collect(Collectors.toList());
                break;
        }
        return sessies;
    }

    @Override
    public List<Sessie> geefAlleSessiesKalender(Integer jaar) {
        return huidigeSessieKalender.geefAlleSessiesKalender(DomeinController.academiejaar);
    }

    @Override
    public List<String> filterOpties() {
        String[] keuzeVoorZoeken = {"Titel", "Stad", "Status"};
        return Arrays.asList(keuzeVoorZoeken);
    }
}
