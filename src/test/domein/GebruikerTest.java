package test.domein;

import domein.Gebruiker;
import domein.Gebruikersprofielen;
import domein.Gebruikersstatus;
import exceptions.domein.GebruikerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class GebruikerTest {

    private static Stream<Arguments> opsommingOngeldigeWaarden() {
        return Stream.of(Arguments.of(null, "123456tp", "GEBRUIKER", "ACTIEF"),
                Arguments.of("Test Persoon", null, "GEBRUIKER", "ACTIEF"),
                Arguments.of("Test Persoon", "123456tp", null, "ACTIEF"),
                Arguments.of("Test Persoon", "123456tp", "GEBRUIKER", null),
                Arguments.of("", "123456tp", "GEBRUIKER", "ACTIEF"),
                Arguments.of("Test Persoon", "", "GEBRUIKER", "ACTIEF"),
                Arguments.of("Test Persoon", "", "NIETSNUT", "ACTIEF"),
                Arguments.of("Test Persoon", "123456tp", "", "ACTIEF"),
                Arguments.of("Test Persoon", "123456tp", "NIETSNUT", ""));
    }

    @ParameterizedTest
    @MethodSource("opsommingOngeldigeWaarden")
    public void maakGebruikerAanZonderProfielFotoFouteWaarden_GooitException(String naam, String gebruikersnaam, String type, String status){
        Assertions.assertThrows(GebruikerException.class, () -> {
            Gebruiker geb = new Gebruiker(naam, gebruikersnaam, type, status);
        });
    }
}
