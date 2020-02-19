package test.domein;

import domein.Gebruiker;
import domein.Herinnering;
import domein.Lokaal;
import domein.Sessie;
import exceptions.domein.HerinneringException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

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
        return Stream.of(Arguments.of("12345", 1, gebruiker, sessie, LocalDateTime.now().minusDays(2), "inhoud"),
                Arguments.of("1", 5, gebruiker, sessie, LocalDateTime.now().minusMonths(1), "inhoud"));
    }

    //region maakHerinneringGeldigeGegevens
    @ParameterizedTest
    @MethodSource("opsommingGeldigeWaarden")
    public void maakHerinneringGeldigeGegevens_Slaagt(String herinneringsId, int dagenVooraf, Gebruiker gebruiker, Sessie sessie, LocalDateTime aangemaakt, String inhoud){
        Herinnering herinnering = new Herinnering(herinneringsId, dagenVooraf, gebruiker, sessie, aangemaakt, inhoud);
        Assertions.assertEquals(inhoud, herinnering.getInhoud());
        Assertions.assertEquals(dagenVooraf, herinnering.getDagenVooraf());
    }
    //endregion

}