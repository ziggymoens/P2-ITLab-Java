package main;

import domein.controllers.DomeinController;
import domein.controllers.StartController;

public class StartUpDatabank {
    public static void main(String[] args) {

        StartController startController = new StartController();
        startController.setHuidigeGebruiker("758095zm");
        DomeinController domeinController = startController.initDomeinController();
    }
}
