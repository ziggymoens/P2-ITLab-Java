package domein;

import persistentie.repositories.GebruikerRepository;
import persistentie.repositories.LokaalRepository;
import persistentie.PersistentieController;

import java.time.LocalDateTime;
import java.util.*;

public class DomeinController {
    private GebruikerRepository gebruikerRepository;
    private LokaalRepository lokalenRepository;
    private List<Sessie> sessies;
    private PersistentieController pc;
    private Gebruiker gebruiker;

    public DomeinController() {
        pc = new PersistentieController();
        initData();
    }

    private void initData() {
        gebruikerRepository = new GebruikerRepository();
        lokalenRepository = new LokaalRepository();
        sessies = pc.getSessies();
    }

    private void updateData() {
        gebruikerRepository.update();
    }

    public String geefOverzichtVerantwoordelijke(Gebruiker gebruiker) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sessies.size(); i++) {
            Sessie s = sessies.get(i);
            //for(Sessie s : sessies){
            if (!s.isGeopend() && s.getVerantwoordelijke() == gebruiker) {
                sb.append(String.format("%d. %s - %s - %s -> %s: %s", i + 1, s.getVerantwoordelijke().getNaam(), s.getTitel(), s.getStartSessie().toString(),
                        s.getEindeSessie().toString(), s.isGeopend() ? String.format("%d", s.aantalVrijePlaatsen()) : String.format("%d", s.aantalAanwezigenNaSessie())));
            }
        }
        return sb.toString();
    }

    public String geefOverzichtHoofdverantwoordelijke() {
        StringBuilder sb = new StringBuilder();
        sessies.sort(Comparator.comparing(Sessie::getStartSessie));
        for (int i = 0; i < sessies.size(); i++) {
            Sessie s = sessies.get(i);
            //for (Sessie s: sessies) {
            if (!s.isGeopend()) {
                sb.append(String.format("%d. %s - %s - %s -> %s: %s", i + 1, s.getVerantwoordelijke().getNaam(), s.getTitel(), s.getStartSessie().toString(),
                        s.getEindeSessie().toString(), s.isGeopend() ? String.format("%d", s.aantalVrijePlaatsen()) : String.format("%d", s.aantalAanwezigenNaSessie())));
            }
        }
        return sb.toString();
    }

    public String geefDetailVanSessie(int volgnummer) {
        Sessie s = sessies.get(volgnummer - 1);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s%n%s%n%s%n%s%n%s%n%s%n%d%n%s: %s%n%s%n%s%n", s.getVerantwoordelijke().getNaam(), s.getTitel(), s.getNaamGastspreker(), s.getLokaal().getLokaalCode(), s.getStartSessie().toString(),
                s.getEindeSessie().toString(), s.getMaximumAantalPlaatsen(), "Automatische herninnering", s.isAutomatischeHerinnering() ? "ja" : "nee",
                s.getMediaBijSessie().toString(), s.isGeopend() ? String.format("Inschrijvingen:%n%s%nAankondigingen:%n%s", s.toString_OverzichtInschrijvingenNietGeopend(), s.toString_OverzichtAankondigingen())
                        : String.format("Inschrijvingen:%n%s%nFeedback:%n%s", s.toString_OverzichtInschrijvingenGeopend(), s.toString_OverzichtFeedback())));

        return sb.toString();
    }


    //krijgt een Sessieobject mee dat mogelijke aanpassigen bevat
    public void pasSessieAan(String verantwoordelijkeNaam, String titel, String naamGastSpreker, int lokaalcode, String startSessie, String eindSessie, String maxAantalPlaatsen, String autoHerinnering, String tijdstipAutoHerinnering,
                             String inhoudautoHerinnering, String media, String gebruikers, String aankondigingen, String feedback) {

    }

    /**
     *
     * @param titel
     * @param startSessie ==> vb. "2007-12-03T10:15:30"
     * @param eindeSessie ==> vb. "2007-12-03T10:15:30"
     * @param maximumAantalPlaatsen
     * @param lokaal
     * @param verantwoordelijke
     */
    public void maakNieuweSessieAan(String titel, CharSequence startSessie, CharSequence eindeSessie, int maximumAantalPlaatsen, String lokaal, String verantwoordelijke) {
        pc.maakNieuweSessieAan(titel, LocalDateTime.parse(startSessie), LocalDateTime.parse(eindeSessie), maximumAantalPlaatsen, geefLokaalMetCode(lokaal), geefGebruikerMetGebruikersnaam(verantwoordelijke));
        sessies = pc.getSessies();
    }

    public String geefOverzichtAlleGebruikers() {
        Set<Gebruiker> gebruikers = pc.getGebruikers();
        StringBuilder sb = new StringBuilder();
        gebruikers.stream().forEach(gebruiker -> sb.append(gebruiker.toString()));
        return sb.toString();
    }

    public Gebruiker geefGebruikerMetGebruikersnaam(String verantwoordelijke) {
        for (Gebruiker g : gebruikerRepository.getGebruikerSet()) {
            if (g.getGebruikersnaam().equals(verantwoordelijke)) {
                return g;
            }
        }
        return null;
    }

    public Gebruiker getGebruiker() {
        return this.gebruiker;
    }

    public void setGebruiker(Gebruiker g) {
        this.gebruiker = g;
    }

    private Lokaal geefLokaalMetCode(String lokaal) {
        for (Lokaal l : lokalenRepository.getLokalenSet()) {
            if (l.getLokaalCode().equals(lokaal)) {
                return l;
            }
        }
        return null;
    }

    //Gebruiker Beheren
    public void voegGebruikerToe(Gebruiker gebruiker) {
        pc.voegGebruikerToe(gebruiker);
    }

    //moet herzien worden, nu even geen zin om hard na te denken
    public void verwijderGebruiker(Gebruiker g) {
        pc.verwijderGebruiker(g);
    }
    //Einde Gebruiker Beheren

    public void verwijderSessie(int volgnummer) {
        Sessie s = sessies.get(volgnummer - 1);
        pc.verwijderSessie(s);
        sessies = pc.getSessies();
    }

    public void voegLokaalToe(Lokaal lokaal) {
        pc.voegLokaalToe(lokaal);
    }

    public void voegSessieToe(Sessie sessie) {
        pc.voegSessieToe(sessie);
    }

}
