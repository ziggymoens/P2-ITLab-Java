package test;

import domein.Herinnering;
import exceptions.domein.HerinneringException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


public class HerinneringTest {

    //region opsommingGeldigeWaarden
    private static Stream<Arguments> opsommingGeldigeWaarden() {
        return Stream.of(Arguments.of(0),
                Arguments.of(1),
                Arguments.of(2),
                Arguments.of(3),
                Arguments.of(7));
    }
    //endregion

    //region maakHerinneringGeldigeGegevens
    @ParameterizedTest
    @MethodSource("opsommingGeldigeWaarden")
    public void maakHerinneringGeldigeGegevens_Slaagt(int dagenVooraf) {
        Herinnering herinnering = new Herinnering(dagenVooraf);
        Assertions.assertEquals(dagenVooraf, herinnering.getDagenVoorafInt());
    }
    //endregion

    //region opsommingOngeldigeWaarden
    private static Stream<Arguments> opsommingOngeldigeWaarden() {
        return Stream.of(Arguments.of(-3),
                Arguments.of(5),
                Arguments.of(6),
                Arguments.of(12),
                Arguments.of(Integer.MAX_VALUE),
                Arguments.of(Integer.MIN_VALUE));
    }
    //endregion

    //region maak herinnering ongeldige gegevens
    @ParameterizedTest
    @MethodSource("opsommingOngeldigeWaarden")
    public void maakHerinneringOngeldigeGegevens_GooitException(int dagenVooraf) {
        Assertions.assertThrows(HerinneringException.class, () -> new Herinnering(dagenVooraf));
    }
    //endregion

}