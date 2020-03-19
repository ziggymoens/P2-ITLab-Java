package domein.controllers;

import domein.Aankondiging;
import domein.gebruiker.Gebruiker;
import domein.sessie.Sessie;

import java.time.LocalDate;
import java.util.List;

public abstract class TypeController {

    public abstract Sessie geefSessieId(String id);

    public abstract List<Sessie> geefAlleSessiesKalender(Integer jaar);

    public abstract List<Sessie> geefAlleSessiesTitel(String titel);

    public abstract List<Sessie> geefAlleSessiesDatum(LocalDate datum);

    public abstract List<Sessie> geefAlleSessiesLocatie(String locatie);

    public abstract void bewerkSessie(Sessie sessie, List<Object> veranderingen);

    public abstract void verwijderSessie(Sessie sessie);

    public abstract void maakSessieAan(Sessie sessie);

    public abstract List<Gebruiker> geefAlleGebruikersSessie(String id); // geef alle gebruikers bij bepaalde sessie

    public abstract List<Gebruiker> geefAlleGerbuikersNaamSessie(String id, String naam); //filter bij sessie op naam gebruiker

    public abstract List<Gebruiker> geefAlleGebuikersTypeSessie(String id, String type); //filter bij sessie op type gebruiker

    public abstract void maakGebruikerAan(Gebruiker gebruiker);

    public abstract void bewerkGebruiker(Gebruiker gebruiker);

    public abstract void verwijderGebruiker(Gebruiker gebruiker);

    public abstract void maakAankondigingAan(Aankondiging aankondiging, Sessie sessie);

    public abstract void bewerkAankondiging(Aankondiging aankondiging);

    public abstract void verwijderAankondiging(Aankondiging aankondiging);

    public abstract Gebruiker geefGebruikerId(String id);

    public abstract List<Gebruiker> geefAlleGebruikers();

    public abstract List<Gebruiker> geefAlleGerbuikersNaam(String naam);

    public abstract List<Gebruiker> geefAlleGebuikersType(String type);

    public abstract List<Gebruiker> geefAlleGebruikersSessie(); // geef alle gebruikers bij bepaalde sessie

    public abstract List<Gebruiker> geefAlleGerbuikersNaamSessie(); //filter bij sessie op naam gebruiker

    public abstract List<Gebruiker> geefAlleGebuikersTypeSessie(); //filter bij sessie op type gebruiker
}