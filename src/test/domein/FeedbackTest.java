package test.domein;

import domein.Feedback;
import domein.Gebruiker;
import domein.Lokaal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;
import static domein.Gebruikersprofielen.*;
import static domein.Gebruikersstatus.*;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FeedbackTest {

    @Mock
    private Gebruiker gebruikerDummy;

    /*private static Stream<Arguments> geldigeWaarden() {
        return Stream.of(Arguments.of("001", gebruiker, )
                );
    }*/
    @InjectMocks
    private Feedback feedback;

    /*@ParameterizedTest
    @MethodSource("geldigeWaarden")*/
    @Test
    void maakFeedbackGeldigeWaarden(){
        gebruikerDummy = new Gebruiker("862361jv", "Jonathan Vanden Eynden", VERANTWOORDELIJKE, ACTIEF);
        Mockito.when(feedback.getGebruiker()).thenReturn(gebruikerDummy);
        feedback = new Feedback(gebruikerDummy, "test");
        Assertions.assertEquals("test", feedback.getTekst());
    }

    @Test
    void test(){
        Gebruiker gebruiker = new Gebruiker("862361jv", "Jonathan Vanden Eynden", VERANTWOORDELIJKE, ACTIEF);
        Feedback feedback = new Feedback(gebruiker, "tekst");
        assertEquals("tekst", feedback.getTekst());
    }
}