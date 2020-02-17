package testbestanden;

import domein.Gebruiker;
import domein.Gebruikersprofielen;
import domein.Gebruikersstatus;
import domein.Lokaal;
import persistentie.offline.GebruikerOfflineMapper;
import persistentie.offline.LokalenOfflineMapper;

import java.util.Set;
import java.util.stream.Collectors;

public class testOffline {

    public static void main(String[] args) {

        /*
        GebruikerOfflineMapper gom = new GebruikerOfflineMapper();
        gom.leesGebruikers();
        Set<Gebruiker> gebruikers = gom.getGebruikerSet();
        String out = gebruikers.stream().map(g -> g.toString()).collect(Collectors.joining("\n"));
        //gom.schrijfGebruikers(gebruikers);
        System.out.println(out);
         */

        /*
        LokalenOfflineMapper lom = new LokalenOfflineMapper();
        //Set<Lokaal> lokaalSet = lom.getLokalenSet();
        lom.leesLokalen();
        Set<Lokaal> lokaalSet = lom.getLokalenSet();
        String out = lokaalSet.stream().map(l -> l.toString()).collect(Collectors.joining("\n"));
        //lom.schrijfLokalen(lokaalSet);
        System.out.println(out);
        */

        Gebruiker g = new Gebruiker("naam", "gebruikersnaam", Gebruikersprofielen.valueOf("test"), Gebruikersstatus.NIET_ACTIEF);
        System.out.println(g.toString());
    }

}
