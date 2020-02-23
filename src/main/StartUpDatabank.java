package main;

import domein.SessieKalenderController;
import net.connectcode.Code128Auto;

public class StartUpDatabank {
    public static void main(String[] args) {
        SessieKalenderController gebruikerController = new SessieKalenderController();

        System.out.println(gebruikerController.geefSessiesVanJaar(2020));


    }
}
