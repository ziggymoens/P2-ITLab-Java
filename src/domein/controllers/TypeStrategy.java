package domein.controllers;

import domein.Aankondiging;
import domein.gebruiker.Gebruiker;
import domein.sessie.Sessie;

import java.time.LocalDate;
import java.util.List;

public interface TypeStrategy {

    Sessie geefSessieId(String id);

    List<Sessie> geefAlleSessiesKalender(Integer jaar);

    List<Sessie> geefAlleNietGeopendeSessiesKalender(Integer jaar);

    List<Sessie> geefAlleSessiesTitel(String titel);

    List<Sessie> geefAlleSessiesDatum(LocalDate datum);

    List<Sessie> geefAlleSessiesLocatie(String locatie);

    void bewerkSessie(Sessie sessie, List<Object> veranderingen);

    void verwijderSessie(Sessie sessie);

    void maakSessieAan(Sessie sessie);

    List<Gebruiker> geefAlleGebruikersSessie(String id); // geef alle gebruikers bij bepaalde sessie

    List<Gebruiker> geefAlleGerbuikersNaamSessie(String id, String naam); //filter bij sessie op naam gebruiker

    List<Gebruiker> geefAlleGebuikersTypeSessie(String id, String type); //filter bij sessie op type gebruiker

    void maakGebruikerAan(Gebruiker gebruiker);

    void bewerkGebruiker(Gebruiker gebruiker);

    void verwijderGebruiker(Gebruiker gebruiker);

    void maakAankondigingAan(Aankondiging aankondiging, Sessie sessie);

    void bewerkAankondiging(Aankondiging aankondiging);

    void verwijderAankondiging(Aankondiging aankondiging);

    Gebruiker geefGebruikerId(String id);

    List<Gebruiker> geefAlleGebruikers();

    List<Gebruiker> geefAlleGerbuikersNaam(String naam);

    List<Gebruiker> geefAlleGebuikersType(String type);

    List<Gebruiker> geefAlleGebruikersSessie(); // geef alle gebruikers bij bepaalde sessie

    List<Gebruiker> geefAlleGerbuikersNaamSessie(); //filter bij sessie op naam gebruiker

    List<Gebruiker> geefAlleGebuikersTypeSessie(); //filter bij sessie op type gebruiker
}