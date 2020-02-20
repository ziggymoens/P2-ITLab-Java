package domein;

import databank.DatabankVerbinding;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SessieKalenderData {
    private static DatabankVerbinding gb;

    public SessieKalenderData() {
    }

    public static void init(DatabankVerbinding garageBeheerder){
        gb = garageBeheerder;
    }

    public void populeerData() {
        gb.addObject(new Aankondiging(LocalDateTime.now(), "hallo"));

        gb.addObject(new Feedback("test"));

        gb.addObject(new Gebruiker("naam", "13461gb", Gebruikersprofielen.GEBRUIKER, Gebruikersstatus.NIET_ACTIEF));

        //gb.addObject(new Herinnering(HerinneringTijdstippen.DRIE.getDagen()));

        gb.addObject(new Inschrijving(LocalDateTime.now(), false));
    }
}
