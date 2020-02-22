package domein;

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

public class HerinneringTest {

    private static Gebruiker gebruiker;
    private static Lokaal lokaal;
    private static Sessie sessie;

    @BeforeAll
    private static void before(){
        gebruiker = new Gebruiker("Test Persoon", "123456tp", "GEBRUIKER", "ACTIEF");
        lokaal = new Lokaal("GSCB.3.049", 50);
        sessie = new Sessie("Titel sessie", LocalDateTime.now().plusSeconds(1), LocalDateTime.now().plusMinutes(30), lokaal, gebruiker);
    }

    private static Stream<Arguments> opsommingGeldigeWaarden(){
        return Stream.of(Arguments.of("12345", 1, gebruiker, sessie, LocalDateTime.now().minusDays(2), "Test inhoud 123"),
                Arguments.of("1", 5, gebruiker, sessie, LocalDateTime.now().minusMonths(1), "Herinnering test!"),
                Arguments.of("87247", 9, gebruiker, sessie, LocalDateTime.now().minusDays(10), "inhoud"));
    }

    //region maakHerinneringGeldigeGegevens
    @ParameterizedTest
    @MethodSource("opsommingGeldigeWaarden")
    public void maakHerinneringGeldigeGegevens_Slaagt(String herinneringsId, int dagenVooraf, Gebruiker gebruiker, Sessie sessie, LocalDateTime aangemaakt, String inhoud){
        Herinnering herinnering = new Herinnering(dagenVooraf);
        Assertions.assertEquals(dagenVooraf, herinnering.getDagenVooraf());
    }
    //endregion

    private static Stream<Arguments> opsommingOngeldigeWaarden(){
        return Stream.of(Arguments.of("", 5, gebruiker, sessie, LocalDateTime.now().minusDays(2), "Test inhoud 123"),
                Arguments.of(null, 5, gebruiker, sessie, LocalDateTime.now().minusMonths(1), "Test inhoud 123"),
                Arguments.of("123456", -1, gebruiker, sessie, LocalDateTime.now().minusDays(10), "Test inhoud 123"),
                Arguments.of("123456", Integer.MIN_VALUE, gebruiker, sessie, LocalDateTime.now().minusDays(10), "Test inhoud 123"));
    }

    @ParameterizedTest
    @MethodSource("opsommingOngeldigeWaarden")
    public void maakHerinneringOngeldigeGegevens_GooitException(String herinneringsId, int dagenVooraf, Gebruiker gebruiker, Sessie sessie, LocalDateTime aangemaakt, String inhoud){
        Assertions.assertThrows(HerinneringException.class, () -> new Herinnering(dagenVooraf));
    }

    private static Stream<Arguments> ongeldigeInhoud(){
        return Stream.of(Arguments.of(null, 5, gebruiker, sessie, LocalDateTime.now().minusMonths(1), ""),
                Arguments.of(null, 5, gebruiker, sessie, LocalDateTime.now().minusMonths(1), null));
    }

    @ParameterizedTest
    @MethodSource("ongeldigeInhoud")
    public void maakHerinneringZonderInhoud_GooitException(String herinneringsId, int dagenVooraf, Gebruiker gebruiker, Sessie sessie, LocalDateTime aangemaakt, String inhoud){
        Assertions.assertThrows(AankondigingException.class, () -> new Herinnering( dagenVooraf));
    }
}