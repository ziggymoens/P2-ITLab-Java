package test.domein;

import domein.Lokaal;
import exceptions.domein.LokaalException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LokaalTest {

    private static Stream<Arguments> geldigeWaarden() {
        return Stream.of(Arguments.of("GSCB.3.049", 50),
                Arguments.of("GSCB.1.017", 38));
    }

    @ParameterizedTest
    @MethodSource("geldigeWaarden")
    void maakLokaalAanMetJuisteWaarden(String lokaalCode, int aantalPlaatsen){
        Lokaal lokaal = new Lokaal(lokaalCode, aantalPlaatsen);
        Assertions.assertEquals(lokaalCode, lokaal.getLokaalCode());
        Assertions.assertEquals(aantalPlaatsen, lokaal.getAantalPlaatsen());
    }

    private static Stream<Arguments> ongeldigeWaardenAantalPlaatsen() {
        return Stream.of(Arguments.of("GSCB.3.049", -1)
                //Eventueel testen met 1000+ , implementeren na vragen
                );
    }

    @ParameterizedTest
    @MethodSource("ongeldigeWaardenAantalPlaatsen")
    void maakLokaalZonderAantalPlaatsen_GooitException(String lokaalCode, int aantalPlaatsen){
        Assertions.assertThrows(LokaalException.class, () -> new Lokaal(lokaalCode, aantalPlaatsen));
    }

    private static Stream<Arguments> ongeldigeWaardenlokaalCode() {
        return Stream.of(Arguments.of("", 50),
                Arguments.of(null, 50),
                Arguments.of(" ", 50));
    }

    @ParameterizedTest
    @MethodSource("ongeldigeWaardenlokaalCode")
    void maakLokaalOngeldigeWaardenAantalPlaatsen_GooitException(String lokaalCode, int aantalPlaatsen){
        Assertions.assertThrows(LokaalException.class, () -> new Lokaal(lokaalCode, aantalPlaatsen));
    }
}