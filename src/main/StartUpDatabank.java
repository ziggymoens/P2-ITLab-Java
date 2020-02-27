package main;

import domein.DomeinController;
import domein.Sessie;
import domein.SessieKalenderDataInit;

import java.util.stream.Collectors;

public class StartUpDatabank {
    public static void main(String[] args) {
        DomeinController domeinController = new DomeinController();

        System.out.println(domeinController.geefSessieHuidigeKalender().stream().map(Sessie::toString).collect(Collectors.joining("\n")));
    }
}
