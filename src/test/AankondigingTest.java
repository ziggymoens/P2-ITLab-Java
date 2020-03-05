package test;

import domein.*;
import exceptions.domein.AankondigingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class AankondigingTest {
    private static Gebruiker gebruiker;
    private static Lokaal lokaal;
    private static Sessie sessie;
    private static Herinnering herinnering;
    @BeforeAll
    public static void before(){
        gebruiker = new Gebruiker("Test Persoon", "123456tp", "GEBRUIKER", "ACTIEF");
        lokaal = new Lokaal("GSCB.3.049","AUDITORIUM", 50);
        sessie = new Sessie("Titel sessie", LocalDateTime.now().plusSeconds(1), LocalDateTime.now().plusMinutes(30), lokaal, gebruiker);
        herinnering = new Herinnering(1);
    }

    private static Stream<Arguments> opsommingGeldigeWaarden(){
        return Stream.of(Arguments.of("A001", sessie, LocalDateTime.now(), gebruiker, "AankondigingTest", true, herinnering),
                Arguments.of("A1245", sessie, LocalDateTime.now().minusDays(4), gebruiker, "Test aankondiging", true, herinnering),
                Arguments.of("A005", sessie, LocalDateTime.now().minusHours(2), gebruiker, "tekst", true, herinnering));
    }

    @ParameterizedTest
    @MethodSource("opsommingGeldigeWaarden")
    public void maakAankondigingGeldigeGegevens_Slaagt(String id, Sessie sessie, LocalDateTime publicatiedatum, Gebruiker publicist, String inhoud, boolean automatischeHerinnering){
        Aankondiging aankondiging = new Aankondiging(sessie, publicist, publicatiedatum, inhoud, automatischeHerinnering, herinnering);
        Assertions.assertEquals(publicist, aankondiging.getPublicist());
        Assertions.assertEquals(publicatiedatum, aankondiging.getPublicatiedatum());
        Assertions.assertEquals(inhoud, aankondiging.getInhoud());
        Assertions.assertEquals(automatischeHerinnering, aankondiging.isAutomatischeHerinnering());
        Assertions.assertEquals(herinnering, aankondiging.getHerinnering());
    }

    private static Stream<Arguments> opsommingOngeldigeWaarden(){
        return Stream.of(Arguments.of(null, LocalDateTime.now().minusDays(2), "AankondigingTest", true, 1),
                Arguments.of(gebruiker, null, "AankondigingTest", true, 1),
                Arguments.of(gebruiker, LocalDateTime.now().minusDays(2), null, true, 1),
                Arguments.of(gebruiker, LocalDateTime.now().minusDays(2), "", true, 1),
                Arguments.of(gebruiker, LocalDateTime.now().minusDays(2), "inhoud", true, -2));
    }

    @ParameterizedTest
    @MethodSource("opsommingOngeldigeWaarden")
    public void maakAankondigingOngeldigeGegevens_GooitException(Gebruiker gebruiker, LocalDateTime publicatiedatum, String inhoud, boolean automatischeHerinnering, int dagenVooraf){
        Assertions.assertThrows(AankondigingException.class, () -> {
            new Aankondiging(sessie, gebruiker, publicatiedatum, inhoud, automatischeHerinnering, dagenVooraf);
        });
    }
}