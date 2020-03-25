package test;

import domein.gebruiker.Gebruiker;
import exceptions.domein.GebruikerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class GebruikerTest {

    private static Stream<Arguments> opsommingOngeldigeWaarden() {
        return Stream.of(Arguments.of(null, "123456tp", 0,"gebruiker", "actief"),
                Arguments.of("Test Persoon", null, 0,"gebruiker", "actief"),
                Arguments.of("Test Persoon", "123456tp", 0,null, "actief"),
                Arguments.of("Test Persoon", "123456tp", 0,"gebruiker", null),
                Arguments.of("", "123456tp", 0,"gebruiker", "actief"),
                Arguments.of("Test Persoon", "", 0,"gebruiker", "actief"),
                Arguments.of("Test Persoon", "", 0,"nietsnut", "actief"),
                Arguments.of("Test Persoon", "123456tp",0, "", "actief"),
                Arguments.of("Test Persoon", "123456tp",0, "NIETSNUT", ""));
    }

    private static Stream<Arguments> opsommingGeldigeWaarden() {
        return Stream.of(Arguments.of("Test Persoon", "123456tp", 0,"gebruiker", "actief"),
                Arguments.of("Test Persoon", "123456tp",0, "verantwoordelijke", "actief"),
                Arguments.of("Test Persoon", "123456tp",0, "hoofdverantwoordelijke", "actief"),
                Arguments.of("Test Persoon", "123456tp",0, "gebruiker", "geblokkeerd"),
                Arguments.of("Test Persoon", "123456tp",0, "gebruiker", "niet actief"));
    }

    @ParameterizedTest
    @MethodSource("opsommingOngeldigeWaarden")
    public void maakGebruikerAanZonderProfielFotoFouteWaarden_GooitException(String naam, String gebruikersnaam, long barcode, String type, String status) {
        Assertions.assertThrows(GebruikerException.class, () -> new Gebruiker(naam, gebruikersnaam, barcode, type, status));
    }

    @ParameterizedTest
    @MethodSource("opsommingGeldigeWaarden")
    public void maakGebruikerAanZonderProfielFotoCorrecteWaarden_Slaagt(String naam, String gebruikersnaam, long barcode, String type, String status) {
        Gebruiker gebruiker = new Gebruiker(naam, gebruikersnaam, barcode, type, status);
        Assertions.assertEquals(naam, gebruiker.getNaam());
        Assertions.assertEquals(gebruikersnaam, gebruiker.getGebruikersnaam());
        Assertions.assertEquals(type, gebruiker.getGebruikersprofiel().toString());
        Assertions.assertEquals(status, gebruiker.getStatus().toString());
    }
}
