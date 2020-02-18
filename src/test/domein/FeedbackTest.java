package test.domein;

import domein.Feedback;
import domein.Gebruiker;
import exceptions.domein.FeedbackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static domein.Gebruikersprofielen.GEBRUIKER;
import static domein.Gebruikersstatus.ACTIEF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FeedbackTest {

    Gebruiker gebruiker;

    @BeforeEach
    public void before(){
        gebruiker = new Gebruiker("862361jv", "Jonathan Vanden Eynden", GEBRUIKER, ACTIEF);
    }
    @Test
    public void maakFeedbackMetJuisteWaardenTestenOpGebruiker(){
        Feedback feedback = new Feedback(gebruiker, "teskt");
        assertEquals(feedback.getGebruiker(), gebruiker);
    }

    @Test
    public void maakFeedbackMetJuisteWaardenTestenOpTekst(){
        Feedback feedback = new Feedback(gebruiker, "tekst");
        assertEquals("tekst", feedback.getTekst());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    public void maakFeedbackFouteWaardenTekst_werptException(String tekst){
        assertThrows(FeedbackException.class, () -> new Feedback(gebruiker, tekst));
    }
}