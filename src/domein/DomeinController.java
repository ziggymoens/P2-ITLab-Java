package domein;

import persistentie.PersistentieController;

import java.util.*;

public class DomeinController {
    private Set<Gebruiker> gebruikers;
    private Set<Lokaal> lokalen;
    private List<Sessie> sessies;
    private PersistentieController pc;

    public DomeinController() {
        pc = new PersistentieController();
        initData();
    }

    private void initData() {
        gebruikers = pc.getGebruikers();
        lokalen = pc.getLokalen();
        sessies = pc.getSessies();
    }

    public String geefOverzichtVerantwoordelijke(Gebruiker gebruiker){
        StringBuilder sb = new StringBuilder();
        for (Sessie s: sessies){
            if (!s.isGeopend() && s.getVerantwoordelijke() == gebruiker){
                sb.append(String.format("%s - %s - %s -> %s: %s", s.getVerantwoordelijke().getNaam(), s.getTitel(), s.getStartSessie().toString(),
                        s.getEindeSessie().toString(), s.isGeopend() ? String.format("%d", s.aantalVrijePlaatsen()) : String.format("%d", s.aantalAanwezigenNaSessie())));
            }
        }
        return sb.toString();
    }

    public String geefOverzichtHoofdverantwoordelijke(){
        StringBuilder sb = new StringBuilder();
        sessies.sort(Comparator.comparing(Sessie::getStartSessie));
        for (Sessie s: sessies) {
            if (!s.isGeopend()) {
                sb.append(String.format("%s - %s - %s -> %s: %s", s.getVerantwoordelijke().getNaam(), s.getTitel(), s.getStartSessie().toString(),
                        s.getEindeSessie().toString(), s.isGeopend() ? String.format("%d", s.aantalVrijePlaatsen()) : String.format("%d", s.aantalAanwezigenNaSessie())));
            }
        }
        return sb.toString();
    }

    public String geefDetailVanSessie(int volgnummer){
        Sessie s = sessies.get(volgnummer-1);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s%n%s%n%s%n%s%n%s%n%s%n%d%n%s: %s%n%s%n%s%n", s.getVerantwoordelijke().getNaam(), s.getTitel(),s.getNaamGastspreker(), s.getLokaal().getLokaalCode(), s.getStartSessie().toString(),
                s.getEindeSessie().toString(), s.getMaximumAantalPlaatsen(), "Automatische herninnering", s.isAutomatischeHerinnering()?"ja":"nee",
                s.getMediaBijSessie().toString(), s.isGeopend()?String.format("Inschrijvingen:%n%s%nAankondigingen:%n%s", s.toString_OverzichtInschrijvingenNietGeopend(), s.toString_OverzichtAankondigingen())
                        :String.format("Inschrijvingen:%n%s%nFeedback:%n%s", s.toString_OverzichtInschrijvingenGeopend(), s.toString_OverzichtFeedback())));

        return sb.toString();
    }

    public void maakNieuweSessieAan(String titel, String startSessie, String eindeSessie, int maximumAantalPlaatsen, String lokaal, String verantwoordelijke){
        pc.maakNieuweSessieAan(titel, new Date(startSessie), new Date(eindeSessie), maximumAantalPlaatsen, geefLokaalMetCode(lokaal), geefGebruikerMetGebruikersnaam(verantwoordelijke));
        sessies = pc.getSessies();
    }

    private Gebruiker geefGebruikerMetGebruikersnaam(String verantwoordelijke) {
        for (Gebruiker g: gebruikers){
            if(g.getGebruikersnaam().equals(verantwoordelijke)){
                return g;
            }
        }
        return null;
    }

    private Lokaal geefLokaalMetCode(String lokaal) {
        for (Lokaal l : lokalen){
            if (l.getLokaalCode().equals(lokaal)){
                return l;
            }
        }
        return null;
    }

    //Gebruiker Beheren
    public void voegGebruikerToeZonderProfielfoto(String naam, String gebruikersnaam, Gebruikersprofielen type, Gebruikersstatus status){
        pc.voegGebruikerToeZonderProfielfoto(naam, gebruikersnaam, type, status);
    }

    public void voegGebruikerToeMetProfielfoto(String profielfoto, String naam, String gebruikersnaam, Gebruikersprofielen type, Gebruikersstatus status){
        pc.voegGebruikerToeMetProfielfoto(profielfoto, naam, gebruikersnaam, type, status);
    }

    //moet herzien worden, nu even geen zin om hard na te denken
    public void verwijderGebruiker(Gebruiker g){
        pc.verwijderGebruiker(g);
    }
    //Einde Gebruiker Beheren

    public void verwijderSessie(int volgnummer){
        Sessie s = sessies.get(volgnummer-1);
        pc.verwijderSessie(s);
        sessies = pc.getSessies();
    }
}
