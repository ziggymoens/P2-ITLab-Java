package main;

import domein.SessieKalenderController;

public class StartUpDatabank {
    public static void main(String[] args) {
        SessieKalenderController gebruikerController = new SessieKalenderController();

        System.out.println(gebruikerController.geefSessiesVanJaar(2020));


    }
}
