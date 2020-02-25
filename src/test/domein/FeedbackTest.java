package test.domein;

import domein.domeinklassen.Feedback;
import domein.domeinklassen.Gebruiker;
import domein.domeinklassen.Lokaal;
import domein.domeinklassen.Sessie;
import exceptions.domein.AankondigingException;
import exceptions.domein.FeedbackException;
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

import static domein.enums.Gebruikersprofielen.GEBRUIKER;
import static domein.enums.Gebruikersstatus.ACTIEF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FeedbackTest {

    private static Gebruiker gebruiker;
    private static Sessie sessie;
    private static Lokaal lokaal;

    //region beforeAll
    @BeforeAll
    public static void before(){
        gebruiker = new Gebruiker("862361jv", "Jonathan Vanden Eynden", GEBRUIKER, ACTIEF);
        lokaal = new Lokaal("GSCB.3.049", 50);
        sessie = new Sessie("Titel sessie", LocalDateTime.now().plusSeconds(1), LocalDateTime.now().plusMinutes(30), lokaal, gebruiker);
    }
    //endregion

    private static Stream<Arguments> opsommingGeldigeWaarden(){
        return Stream.of(Arguments.of(gebruiker, "inhoud"),
                Arguments.of(gebruiker, "geldige inhoud"));
    }

    private static Stream<Arguments> opsommingOngeldigeWaarden(){
        return Stream.of(Arguments.of(gebruiker, ""),
                Arguments.of(gebruiker, null));
    }

    @ParameterizedTest
    @MethodSource("opsommingGeldigeWaarden")
    public void maakFeedbackGeldigeWaarden_Slaagt(Gebruiker gebruiker, String tekst){
        Feedback feedback = new Feedback(gebruiker, tekst);
        assertEquals(gebruiker, feedback.getGebruiker());
        assertEquals(tekst, feedback.getTekst());
    }

    @ParameterizedTest
    @MethodSource("opsommingOngeldigeWaarden")
    public void maakFeedbackOngeldigeWaarden_GooitException(Gebruiker gebruiker, String tekst){
        Assertions.assertThrows(FeedbackException.class, () -> {
           new Feedback(gebruiker, tekst);
        });
    }
}