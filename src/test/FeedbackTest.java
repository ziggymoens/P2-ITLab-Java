package test;

import domein.Academiejaar;
import domein.Feedback;
import domein.gebruiker.Gebruiker;
import domein.Lokaal;
import domein.sessie.GeslotenState;
import domein.sessie.OpenState;
import domein.sessie.Sessie;
import exceptions.domein.FeedbackException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FeedbackTest {

    private static Gebruiker gebruiker;
    private static Sessie openSessie;
    private static Sessie geslotenSessie;
    private static Lokaal lokaal;
    private static OpenState openState;
    private static GeslotenState geslotenState;
    private static Academiejaar academiejaar;

    //region beforeAll
    @BeforeAll
    public static void before() {
        academiejaar = new Academiejaar(2021, LocalDate.now(), LocalDate.now().plusMonths(5));
        gebruiker = new Gebruiker("123456tp", "Test Persoon", "gebruiker", "actief");
        lokaal = new Lokaal("GSCHB.3.049", "AUDITORIUM", 50);
        openState = new OpenState();
        geslotenState = new GeslotenState();
        openSessie = new Sessie("Titel sessie", "beschrijving", LocalDateTime.now().plusSeconds(1), LocalDateTime.now().plusMinutes(60), lokaal, gebruiker, academiejaar, openState.getStatus());
        geslotenSessie = new Sessie("Titel sessie", "beschrijving", LocalDateTime.now().plusSeconds(1), LocalDateTime.now().plusMinutes(60), lokaal, gebruiker, academiejaar, geslotenState.getStatus());
    }
    //endregion

    private static Stream<Arguments> opsommingGeldigeWaarden() {
        return Stream.of(Arguments.of(geslotenSessie, gebruiker, "inhoud", LocalDate.now()),
                Arguments.of(geslotenSessie, gebruiker, "geldige inhoud", LocalDate.now())
        );
    }

    private static Stream<Arguments> opsommingOngeldigeWaarden() {
        return Stream.of(Arguments.of(openSessie, gebruiker, "", LocalDate.now()),
                Arguments.of(openSessie, gebruiker, null, LocalDate.now()),
                Arguments.of(openSessie, gebruiker, "Feedback", LocalDate.now().minusDays(2)),
                Arguments.of(null, gebruiker, "Feedback", LocalDate.now()),
                Arguments.of(openSessie, null, "Feedback", LocalDate.now())
        );
    }

    @ParameterizedTest
    @MethodSource("opsommingGeldigeWaarden")
    public void maakFeedbackGeldigeWaarden_Slaagt(Sessie sessie, Gebruiker gebruiker, String tekst, LocalDate date) {
        Feedback feedback = new Feedback(sessie, gebruiker, tekst, date);
        assertEquals(gebruiker, feedback.getGebruiker());
        assertEquals(tekst, feedback.getTekst());
    }

    @ParameterizedTest
    @MethodSource("opsommingOngeldigeWaarden")
    public void maakFeedbackOngeldigeWaarden_GooitException(Sessie sessie, Gebruiker gebruiker, String tekst, LocalDate date) {
        Assertions.assertThrows(FeedbackException.class, () -> {
            new Feedback(sessie, gebruiker, tekst, date);
        });
    }
}