package domein.controllers;

import domein.Aankondiging;
import domein.gebruiker.Gebruiker;
import domein.sessie.Sessie;

import java.time.LocalDate;
import java.util.List;

public abstract class ITypeController {


    public abstract Sessie geefSessieId(String id);

    public abstract List<Sessie> geefAlleSessiesKalender(Integer jaar);

    public abstract List<Sessie> geefAlleSessiesTitel(String titel);

    public abstract List<Sessie> geefAlleSessiesDatum(LocalDate datum);

    public abstract List<Sessie> geefAlleSessiesLocatie(String locatie);

    public abstract void bewerkSessie(Sessie sessie, List<Object> veranderingen);

    public abstract void verwijderSessie(Sessie sessie);

    public void maakSessieAan(Sessie sessie) {

    }

    public List<Gebruiker> geefAlleGebruikersSessie(String id) // geef alle gebruikers bij bepaalde sessie
    {
        return null;
    }

    public List<Gebruiker> geefAlleGerbuikersNaamSessie(String id, String naam) //filter bij sessie op naam gebruiker
    {
        return null;
    }

    public List<Gebruiker> geefAlleGebuikersTypeSessie(String id, String type) //filter bij sessie op type gebruiker
    {
        return null;
    }

    public void maakGebruikerAan(Gebruiker gebruiker) {

    }

    public void bewerkGebruiker(Gebruiker gebruiker) {

    }

    public void verwijderGebruiker(Gebruiker gebruiker) {

    }

    public void maakAankondigingAan(Aankondiging aankondiging, Sessie sessie) {

    }

    public void bewerkAankondiging(Aankondiging aankondiging) {

    }

    public void verwijderAankondiging(Aankondiging aankondiging) {

    }

    public Gebruiker geefGebruikerId(String id) {
        return null;
    }

    public List<Gebruiker> geefAlleGebruikers() {
        return null;
    }

    public List<Gebruiker> geefAlleGerbuikersNaam(String naam) {
        return null;
    }

    public List<Gebruiker> geefAlleGebuikersType(String type) {
        return null;
    }

    public List<Gebruiker> geefAlleGebruikersSessie() // geef alle gebruikers bij bepaalde sessie
    {
        return null;
    }

    public List<Gebruiker> geefAlleGerbuikersNaamSessie() //filter bij sessie op naam gebruiker
    {
        return null;
    }

    public List<Gebruiker> geefAlleGebuikersTypeSessie() //filter bij sessie op type gebruiker
    {
        return null;
    }
}