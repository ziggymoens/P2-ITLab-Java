package main;

import domein.DomeinController;
import domein.PasswordUtils;
import domein.interfacesDomein.ILokaal;
import domein.interfacesDomein.ISessie;

import java.util.stream.Collectors;

public class StartUpDatabank {
    public static void main(String[] args) {
        DomeinController domeinController = new DomeinController();

        System.out.println(domeinController.geefISessiesHuidigeKalender().stream().map(ISessie::toString).collect(Collectors.joining("\n")));



        domeinController.setHuidigeGebruiker("758095zm");
        System.out.println(domeinController.geefISessiesHuidigeGebruiker().stream().map(ISessie::toString_Kalender).collect(Collectors.joining("\n")));
        System.out.println(domeinController.geefLokalenVanCampus("schoonmeersen").stream().map(ILokaal::getLokaalCode).collect(Collectors.joining("\n")));
        System.out.println(PasswordUtils.getSalt(60));
    }
}
