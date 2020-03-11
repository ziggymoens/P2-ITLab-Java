package test;

import domein.Lokaal;
import exceptions.domein.LokaalException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LokaalTest {

    //region maakLokaalAanMetJuisteWaardenTest
    private static Stream<Arguments> geldigeWaarden() {
        return Stream.of(Arguments.of("GSCB.3.049", "AUDITORIUM", 50),
                Arguments.of("GSCB.1.017", "PC_KLAS", 38));
    }

    @ParameterizedTest
    @MethodSource("geldigeWaarden")
    public void maakLokaalAanMetJuisteWaarden(String lokaalCode, String type, int aantalPlaatsen) {
        Lokaal lokaal = new Lokaal(lokaalCode, type, aantalPlaatsen);
        assertEquals(lokaalCode, lokaal.getLokaalCode());
        assertEquals(aantalPlaatsen, lokaal.getAantalPlaatsen());
    }
    //endregion

    //region maakLokaalZonderAantalPlaatsen_GooitExcepionTest
    private static Stream<Arguments> ongeldigeWaardenAantalPlaatsen() {
        return Stream.of(Arguments.of("GSCB.3.049", "AUDITORIUM", -1)
                //Eventueel testen met 1000+ , implementeren na vragen
        );
    }

    @ParameterizedTest
    @MethodSource("ongeldigeWaardenAantalPlaatsen")
    public void maakLokaalZonderAantalPlaatsen_GooitException(String lokaalCode, String type, int aantalPlaatsen) {
        assertThrows(LokaalException.class, () -> new Lokaal(lokaalCode, type, aantalPlaatsen));
    }
    //endregion

    //region maakLokaalOngeldigeWaardenAantalPlaatsen_GooitExceptionTest
    private static Stream<Arguments> ongeldigeWaardenlokaalCode() {
        return Stream.of(Arguments.of("", "AUDITORIUM", 50),
                Arguments.of(null, "AUDITORIUM", 50),
                Arguments.of(" ", "AUDITORIUM", 50));
    }

    @ParameterizedTest
    @MethodSource("ongeldigeWaardenlokaalCode")
    public void maakLokaalOngeldigeWaardenAantalPlaatsen_GooitException(String lokaalCode, String type, int aantalPlaatsen) {
        assertThrows(LokaalException.class, () -> new Lokaal(lokaalCode, type, aantalPlaatsen));
    }
    //endregion
}