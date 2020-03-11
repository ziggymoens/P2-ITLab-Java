package domein.controllers;

public interface ITypeController {

    Sessie geefSessieId(String id);

    List<Sessie> geefAlleSessiesKalender(Integer jaar);

    List<Sessie> geefAlleSessiesTitel(String titel);

    List<Sessie> geefAlleSessiesDatum(LocalDate datum);

    List<Sessie> geefAlleSessiesLocatie(String locatie);

    void bewerkSessie(Sessie sessie);

    void verwijderSessie(Sessie sessie);

    void maakSessieAan(Sessie sessie);

    List<Gebruiker> geefAlleGebruikersSessie(String id); // geef alle gebruikers bij bepaalde sessie

    List<Gebruiker> geefAlleGerbuikersNaamSessie(String id, String naam); //filter bij sessie op naam gebruiker

    List<Gebruiker> geefAlleGebuikersTypeSessie(String id, String type); //filter bij sessie op type gebruiker

    void maakGebruikerAan(Gebruiker gebruiker);

    void bewerkGebruiker(Gebruiker gebruiker);

    void verwijderGebruiker(Gebruiker gebruiker);
}