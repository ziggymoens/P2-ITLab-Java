package domein.controllers;

import domein.Gebruiker;
import domein.Sessie;

import java.time.LocalDate;
import java.util.List;

public interface ITypeController {

    Sessie geefSessieId(String id);
    List<Sessie> geefAlleSessiesKalender(Integer jaar);
    List<Sessie> geefAlleSessiesTitel(String titel);
    List<Sessie> geefAlleSessiesDatum(LocalDate datum);
    List<Sessie> geefAlleSessiesLocatie(String locatie);

    List<String> filterOpties();

    void bewerkSessie(Sessie sessie);
    void verwijderSessie(Sessie sessie);
    void maakSessieAan(Sessie sessie);

    Gebruiker geefGebruikerId(String id);
    List<Gebruiker> geefAlleGebruikers();
    List<Gebruiker> geefAlleGerbuikersNaam(String naam);
    List<Gebruiker> geefAlleGebuikersType(String type);
    List<Gebruiker> geefAlleGebruikersSessie(); // geef alle gebruikers bij bepaalde sessie
    List<Gebruiker> geefAlleGerbuikersNaamSessie(); //filter bij sessie op naam gebruiker
    List<Gebruiker> geefAlleGebuikersTypeSessie(); //filter bij sessie op type gebruiker
}
