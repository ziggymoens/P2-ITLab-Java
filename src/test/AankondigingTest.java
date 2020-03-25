package test;

import domein.*;
import domein.gebruiker.Gebruiker;
import domein.Lokaal;
import domein.sessie.OpenState;
import domein.sessie.Sessie;
import exceptions.domein.AankondigingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public class AankondigingTest {
    private static Gebruiker gebruiker;
    private static Lokaal lokaal;
    private static Sessie sessie;
    private static Herinnering herinnering;
    private static Academiejaar academiejaar;
    private static OpenState openState;

    @BeforeAll
    public static void before() {
        academiejaar = new Academiejaar(2021, LocalDate.now(), LocalDate.now().plusMonths(5));
        gebruiker = new Gebruiker("Test Persoon", "123456tp", 0,"gebruiker", "actief");
        lokaal = new Lokaal("GSCHB.3.049", "AUDITORIUM", 50);
        herinnering = new Herinnering(1);
        openState = new OpenState();
        sessie = new Sessie("Titel sessie", "beschrijving", LocalDateTime.now().plusSeconds(1), LocalDateTime.now().plusMinutes(60), lokaal, gebruiker, academiejaar, openState.getStatus());
    }


    @ParameterizedTest
    @MethodSource("opsommingGeldigeWaarden")
    public void maakAankondigingGeldigeGegevens_Slaagt(Sessie sessie, Gebruiker gebruiker, LocalDateTime publicatiedatum, String inhoud, boolean automatischeHerinnering, int dagenVooraf) {
        Aankondiging aankondiging = new Aankondiging(sessie, gebruiker, publicatiedatum, inhoud, automatischeHerinnering, dagenVooraf);
        Assertions.assertEquals(gebruiker, aankondiging.getPublicist());
        Assertions.assertEquals(publicatiedatum, aankondiging.getPublicatiedatum());
        Assertions.assertEquals(inhoud, aankondiging.getInhoud());
        Assertions.assertEquals(automatischeHerinnering, aankondiging.isAutomatischeHerinnering());
        Assertions.assertEquals(dagenVooraf, herinnering.getDagenVoorafInt());
    }

    //Sessie sessie, Gebruiker gebruiker, LocalDateTime publicatiedatum, String inhoud, boolean automatischeHerinnering, int dagenVooraf

    private static Stream<Arguments> opsommingOngeldigeWaarden() {
        return Stream.of(Arguments.of(null, gebruiker, LocalDateTime.now().minusDays(2), "AankondigingTest", true, 1),
                Arguments.of(null, gebruiker, LocalDateTime.now().minusDays(2), "AankondigingTest", true, 1),
                Arguments.of(sessie, gebruiker, null, "AankondigingTest", true, 1),
                Arguments.of(sessie, gebruiker, LocalDateTime.now().minusDays(2), null, true, 1),
                Arguments.of(sessie, gebruiker, LocalDateTime.now().minusDays(2), "", true, 1)
        );
    }


    private static Stream<Arguments> opsommingGeldigeWaarden() {
        return Stream.of(Arguments.of(sessie, gebruiker, LocalDateTime.now(), "AankondigingTest", true, herinnering.getDagenVoorafInt()),
                Arguments.of(sessie, gebruiker, LocalDateTime.now().minusDays(4), "Test aankondiging", true, herinnering.getDagenVoorafInt()),
                Arguments.of(sessie, gebruiker, LocalDateTime.now().minusHours(2), "tekst", true, herinnering.getDagenVoorafInt()),
                Arguments.of(sessie, gebruiker, LocalDateTime.now().minusDays(2), "inhoud", false, herinnering.getDagenVoorafInt())
        );
    }

    @ParameterizedTest
    @MethodSource("opsommingOngeldigeWaarden")
    public void maakAankondigingOngeldigeGegevens_GooitException(Sessie sessie, Gebruiker gebruiker, LocalDateTime publicatiedatum, String inhoud, boolean automatischeHerinnering, int dagenVooraf) {
        Assertions.assertThrows(AankondigingException.class, () -> {
            new Aankondiging(sessie, gebruiker, publicatiedatum, inhoud, automatischeHerinnering, dagenVooraf);
        });
    }
}