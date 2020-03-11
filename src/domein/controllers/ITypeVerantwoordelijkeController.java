package domein.controllers;

import domein.Gebruiker;
import domein.Sessie;

import java.util.List;

public interface ITypeVerantwoordelijkeController extends ITypeController {
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
