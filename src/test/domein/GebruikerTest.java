package test.domein;

import domein.Gebruiker;
import domein.Gebruikersprofielen;
import domein.Gebruikersstatus;
import exceptions.domein.GebruikerException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static domein.Gebruikersprofielen.GEBRUIKER;
import static domein.Gebruikersstatus.GEBLOKKEERD;

public class GebruikerTest {

    private static Stream<Arguments> opsommingOngeldigeWaarden() {
        return Stream.of(Arguments.of(null, "123456tp", "GEBRUIKER", "ACTIEF"),
                Arguments.of("Test Persoon", null, "GEBRUIKER", "ACTIEF"),
                Arguments.of("Test Persoon", "123456tp", null, "ACTIEF"),
                Arguments.of("Test Persoon", "123456tp", "GEBRUIKER", null),
                Arguments.of("", "123456tp", "GEBRUIKER", "ACTIEF"),
                Arguments.of("Test Persoon", "", "GEBRUIKER", "ACTIEF"));
    }

    @ParameterizedTest
    @MethodSource("opsommingOngeldigeWaarden")
    public void maakGebruikerAanZonderProfielFotoFouteWaarden_GooitException(String naam, String gebruikersnaam, Gebruikersprofielen type, Gebruikersstatus status){
        Assertions.assertThrows(GebruikerException.class, () -> {
            Gebruiker geb = new Gebruiker(naam, gebruikersnaam, type, status);
        });
    }
}
