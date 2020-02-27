package main;

import domein.DomeinController;
import domein.interfacesDomein.ISessie;

import java.util.stream.Collectors;

public class StartUpDatabank {
    public static void main(String[] args) {
        DomeinController domeinController = new DomeinController();

        System.out.println(domeinController.geefSessieHuidigeKalender().stream().map(ISessie::toString).collect(Collectors.joining("\n")));
    }
}
