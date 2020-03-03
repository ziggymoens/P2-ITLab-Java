package main;

import domeintje.DomeinController;
import domeintje.PasswordUtils;
import domeintje.interfacesDomein.ILokaal;
import domeintje.interfacesDomein.ISessie;

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
