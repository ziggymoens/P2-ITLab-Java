package test.domein;

import domein.domeinklassen.Gebruiker;
import domein.domeinklassen.Herinnering;
import domein.domeinklassen.Lokaal;
import domein.domeinklassen.Sessie;
import exceptions.domein.AankondigingException;
import exceptions.domein.HerinneringException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;


//TODO
public class HerinneringTest {


    private static Stream<Arguments> opsommingGeldigeWaarden(){
        return Stream.of(Arguments.of(0),
                Arguments.of(1),
                Arguments.of(2),
                Arguments.of(3),
                Arguments.of(7));
    }

    //region maakHerinneringGeldigeGegevens
    @ParameterizedTest
    @MethodSource("opsommingGeldigeWaarden")
    public void maakHerinneringGeldigeGegevens_Slaagt(int dagenVooraf){
        Herinnering herinnering = new Herinnering(dagenVooraf);
        Assertions.assertEquals(dagenVooraf, herinnering.getDagenVoorafInt());
    }
    //endregion

    private static Stream<Arguments> opsommingOngeldigeWaarden(){
        return Stream.of(Arguments.of(-3),
                Arguments.of(5),
                Arguments.of(6),
                Arguments.of(12),
                Arguments.of(Integer.MAX_VALUE),
                Arguments.of(Integer.MIN_VALUE));
    }

    @ParameterizedTest
    @MethodSource("opsommingOngeldigeWaarden")
    public void maakHerinneringOngeldigeGegevens_GooitException(int dagenVooraf){
        Assertions.assertThrows(HerinneringException.class, () -> new Herinnering(dagenVooraf));
    }

}