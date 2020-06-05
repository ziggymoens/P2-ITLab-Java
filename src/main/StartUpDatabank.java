package main;

import domein.Academiejaar;
import domein.PasswordUtils;
import domein.controllers.DomeinController;
import domein.controllers.StartController;
import domein.interfacesDomein.ILokaal;
import domein.interfacesDomein.ISessie;

import java.util.stream.Collectors;

public class StartUpDatabank {
    public static void main(String[] args) {

        StartController startController = new StartController();
        startController.setHuidigeGebruiker("758095zm");
        DomeinController domeinController = startController.initDomeinController();


        //System.out.println(domeinController.geefISessiesHuidigeKalender().stream().map(ISessie::toString).collect(Collectors.joining("\n")));

        //System.out.println(domeinController.geefISessiesHuidigeKalender().stream().map(ISessie::toString_Kalender).collect(Collectors.joining("\n")));
        System.out.println(domeinController.geefLokalenVanCampus("schoonmeersen").stream().map(ILokaal::getLokaalCode).collect(Collectors.joining("\n")));
        //System.out.println(PasswordUtils.getSalt(60));
    }
}
