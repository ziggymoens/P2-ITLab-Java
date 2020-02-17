package testbestanden;

import domein.*;
import persistentie.PersistentieController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class testOffline {

    public static void main(String[] args) {

/*
        GebruikerOfflineMapper gom = new GebruikerOfflineMapper();
        //gom.leesGebruikers();
        Set<Gebruiker> gebruikers = gom.getGebruikerSet();
        String out = gebruikers.stream().map(g -> g.toString()).collect(Collectors.joining("\n"));
        gom.schrijfGebruikers(gebruikers);
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

        /*
        Gebruiker g = new Gebruiker("naam", "gebruikersnaam", Gebruikersprofielen.valueOf("test"), Gebruikersstatus.NIET_ACTIEF);
        System.out.println(g.toString());
         */

        PersistentieController pc = new PersistentieController();
        Set<Gebruiker> gebruikers = pc.getGebruikers();
        String out1 = gebruikers.stream().map(Gebruiker::toString).collect(Collectors.joining("\n"));
        System.out.println(out1);
        Set<Lokaal> lokaalSet = pc.getLokalen();
        String out2 = lokaalSet.stream().map(Lokaal::toString).collect(Collectors.joining("\n"));
        System.out.println(out2);
        System.out.println();
        List<Sessie> sessieList = pc.getSessies();
        String out = sessieList.stream().map(Sessie::toString).collect(Collectors.joining("\n"));
        System.out.println(out);
        pc.schrijfAllesWeg();

    }

}
