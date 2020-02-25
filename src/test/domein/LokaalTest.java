package test.domein;
import domein.domeinklassen.Lokaal;
import exceptions.domein.LokaalException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LokaalTest {

    //region maakLokaalAanMetJuisteWaardenTest
    private static Stream<Arguments> geldigeWaarden() {
        return Stream.of(Arguments.of("GSCB.3.049", 50),
                Arguments.of("GSCB.1.017", 38));
    }

    @ParameterizedTest
    @MethodSource("geldigeWaarden")
    public void maakLokaalAanMetJuisteWaarden(String lokaalCode, int aantalPlaatsen){
        Lokaal lokaal = new Lokaal(lokaalCode, aantalPlaatsen);
        assertEquals(lokaalCode, lokaal.getLokaalCode());
        assertEquals(aantalPlaatsen, lokaal.getAantalPlaatsen());
    }
    //endregion

    //region maakLokaalZonderAantalPlaatsen_GooitExcepionTest
    private static Stream<Arguments> ongeldigeWaardenAantalPlaatsen() {
        return Stream.of(Arguments.of("GSCB.3.049", -1)
                //Eventueel testen met 1000+ , implementeren na vragen
                );
    }

    @ParameterizedTest
    @MethodSource("ongeldigeWaardenAantalPlaatsen")
    public void maakLokaalZonderAantalPlaatsen_GooitException(String lokaalCode, int aantalPlaatsen){
        assertThrows(LokaalException.class, () -> new Lokaal(lokaalCode, aantalPlaatsen));
    }
    //endregion

    //region maakLokaalOngeldigeWaardenAantalPlaatsen_GooitExceptionTest
    private static Stream<Arguments> ongeldigeWaardenlokaalCode() {
        return Stream.of(Arguments.of("", 50),
                Arguments.of(null, 50),
                Arguments.of(" ", 50));
    }

    @ParameterizedTest
    @MethodSource("ongeldigeWaardenlokaalCode")
    public void maakLokaalOngeldigeWaardenAantalPlaatsen_GooitException(String lokaalCode, int aantalPlaatsen){
        assertThrows(LokaalException.class, () -> new Lokaal(lokaalCode, aantalPlaatsen));
    }
    //endregion
}