package test.domein;

import domein.*;
import exceptions.domein.AankondigingException;
import exceptions.domein.FeedbackException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static domein.Gebruikersprofielen.GEBRUIKER;
import static domein.Gebruikersstatus.ACTIEF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FeedbackTest {

    Gebruiker gebruiker;
    Sessie sessie;
    Lokaal lokaal;

    //region beforeEach
    @BeforeEach
    public void before(){
        gebruiker = new Gebruiker("862361jv", "Jonathan Vanden Eynden", GEBRUIKER, ACTIEF);
        lokaal = new Lokaal("GSCB.3.049", 50);
        sessie = new Sessie("Titel sessie", LocalDateTime.now().plusSeconds(1), LocalDateTime.now().plusMinutes(30), lokaal, gebruiker);
    }
    //endregion

    //region maakFeedbackMetJuisteWaardenTestenOpGebruiker
    @Test
    public void maakFeedbackMetJuisteWaardenTestenOpGebruiker(){
        Feedback feedback = new Feedback("F001", sessie, gebruiker,"teskt");
        assertEquals(feedback.getGebruiker(), gebruiker);
    }
    //endregion

    //region maakFeedbackMetJuisteWaardenTestenOpTekst
    @Test
    public void maakFeedbackMetJuisteWaardenTestenOpTekst(){
        Feedback feedback = new Feedback("F0001", sessie, gebruiker, "tekst");
        assertEquals("tekst", feedback.getTekst());
    }
    //endregion

    //region maakFeedbackFouteWaardenTekst_werptException
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    public void maakFeedbackFouteWaardenTekst_werptException(String tekst){
        assertThrows(FeedbackException.class, () -> new Feedback("F001", sessie, gebruiker, tekst));
    }
    //endregion

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    public void maakAankondigingOngeldigeGegevensId_GooitException(String id){
        Assertions.assertThrows(AankondigingException.class, () ->  new Feedback(id, sessie,gebruiker, "Tekst"));
    }

    @Test
    public void maakAankondigingOngeldigeGegevensSessie_GooitException(){
        Assertions.assertThrows(AankondigingException.class, () ->  new Feedback("F001", null, gebruiker, "Tekst"));
    }
}