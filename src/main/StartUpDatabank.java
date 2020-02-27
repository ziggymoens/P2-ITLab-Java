package main;

import domein.DomeinController;
import domein.interfacesDomein.IMedia;
import domein.interfacesDomein.ISessie;

import java.util.stream.Collectors;

public class StartUpDatabank {
    public static void main(String[] args) {
        DomeinController domeinController = new DomeinController();

        System.out.println(domeinController.geefISessieHuidigeKalender().stream().map(ISessie::toString).collect(Collectors.joining("\n")));


        System.out.println(domeinController.geefMediaVanSessie("S1920-000001").stream().map(IMedia::toString_Compleet).collect(Collectors.joining("\n")));
    }
}
