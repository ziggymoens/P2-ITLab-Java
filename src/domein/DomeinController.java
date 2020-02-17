package domein;

import persistentie.repositories.GebruikerRepository;
import persistentie.repositories.LokaalRepository;
import persistentie.PersistentieController;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class DomeinController {
    //region Variabelen
    private PersistentieController pc;
    private Gebruiker gebruiker;
    //endregion

    //region Constructor
    public DomeinController() {
        initData();
    }
    //endregion

    //region Init
    private void initData() {
        pc = new PersistentieController();
    }
    //endregion

    //region Overzicht
    public List<String> geefOverzichtVerantwoordelijke(String gebruikersCode) {
        return pc.getSessies().stream().filter(s -> s.isGeopend() && s.getVerantwoordelijke().getGebruikersnaam().equals(gebruikersCode)).map(Sessie::toString_Overzicht).collect(Collectors.toList());
    }

    public List<String> geefOverzichtHoofdverantwoordelijke() {
        return pc.getSessies().stream().sorted(Comparator.comparing(Sessie::getStartSessie)).map(Sessie::toString_Overzicht).collect(Collectors.toList());
    }

    public List<String> geefOverzichtAlleGebruikers() {
        return pc.getGebruikers().stream().map(Gebruiker::toString).collect(Collectors.toList());
    }
    //endregion

    //region Gebruiker
    public void voegGebruikerToe(Gebruiker gebruiker) {
        pc.beheerGebruiker("CREATE", gebruiker);
    }

    public void verwijderGebruiker(Gebruiker g) {
        pc.beheerGebruiker("DELETE", gebruiker);
    }

    //endregion

    //region Lokaal
    private Lokaal geefLokaalMetCode(String lokaalCode) {
        return pc.geefLokaalMetCode(lokaalCode);
    }

    public void voegLokaalToe(Lokaal lokaal) {
        pc.beheerLokaal("CREATE", lokaal);
    }
    //endregion

    //region Sessie

    /**
     * @param titel                 ==> titel van de sessie
     * @param startSessie           ==> vb. "2007-12-03T10:15:30"
     * @param eindeSessie           ==> vb. "2007-12-03T10:15:30"
     * @param lokaalCode            ==> de code van het lokaal
     * @param verantwoordelijkeCode ==> code van de veranwoordelijke
     */
    public void maakNieuweSessieAan(String titel, CharSequence startSessie, CharSequence eindeSessie, String lokaalCode, String verantwoordelijkeCode) {
        Sessie s = new Sessie(titel, LocalDateTime.parse(startSessie), LocalDateTime.parse(eindeSessie), pc.geefLokaalMetCode(lokaalCode), pc.geefGebruikerMetCode(verantwoordelijkeCode));
        pc.beheerSessie("CREATE", s);
    }

    public void voegSessieToe(Sessie sessie) {
        pc.beheerSessie("CREATE", sessie);
    }

    public void verwijderSessie(String SessieId) {
        Sessie s = pc.geefSessieMetId(SessieId);
        pc.beheerSessie("DELETE", s);
    }

    public String geefDetailVanSessie(String sessieId) {
        Sessie sessie = pc.geefSessieMetId(sessieId);
        return sessie.toString();
    }
    //endregion
}
