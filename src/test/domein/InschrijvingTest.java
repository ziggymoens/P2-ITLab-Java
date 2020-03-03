package domein;
import exceptions.domein.InschrijvingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class InschrijvingTest {
    private static Gebruiker gebruiker;
    private static Lokaal lokaal;
    private static Sessie sessie;

    @BeforeAll
    public static void before(){
        gebruiker = new Gebruiker("TestGebruiker", "123456tp", "GEBRUIKER", "ACTIEF");
    }

    private static Stream<Arguments> opsommingGeldigeWaarden(){
        return Stream.of(Arguments.of(gebruiker, LocalDateTime.now(), true),
                Arguments.of(gebruiker, LocalDateTime.now(), false),
                Arguments.of(gebruiker, LocalDateTime.now(), true),
                Arguments.of(gebruiker, LocalDateTime.now().minusHours(20), false));
    }

    @ParameterizedTest
    @MethodSource("opsommingGeldigeWaarden")
    public void maakInschrijvingGeldigeGegevens_Slaagt(Gebruiker gebruiker, LocalDateTime inschrijvingsdatum, boolean statusAanwezigheid){
        Inschrijving inschrijving = new Inschrijving(gebruiker, inschrijvingsdatum, statusAanwezigheid);
        Assertions.assertEquals(inschrijvingsdatum, inschrijving.getInschrijvingsdatum());
        Assertions.assertEquals(statusAanwezigheid, inschrijving.isStatusAanwezigheid());
    }

    private static Stream<Arguments> opsommingOngeldigeWaarden(){
        return Stream.of(Arguments.of(null, LocalDateTime.now(), true),
                Arguments.of(gebruiker, null, true));
    }

    @ParameterizedTest
    @MethodSource("opsommingOngeldigeWaarden")
    public void maakInschrijvingOngeldigeGegevens_GooitException(Gebruiker gebruiker, LocalDateTime inschrijvingsdatum, boolean statusAanwezigheid){
        Assertions.assertThrows(InschrijvingException.class, () -> {
            new Inschrijving(gebruiker, inschrijvingsdatum, statusAanwezigheid);
        });
    }

}