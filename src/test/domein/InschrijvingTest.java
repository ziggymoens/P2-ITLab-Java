package domein;

import domein.*;
import exceptions.domein.AankondigingException;
import exceptions.domein.InschrijvingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class InschrijvingTest {
    private static Gebruiker gebruiker;
    private static Lokaal lokaal;
    private static Sessie sessie;

    @BeforeAll
    public static void before(){
        gebruiker = new Gebruiker("TestGebruiker", "123456tp", "GEBRUIKER", "ACTIEF");
        lokaal = new Lokaal("GSCB.3.049", 50);
        sessie = new Sessie("Titel sessie", LocalDateTime.now().plusSeconds(1), LocalDateTime.now().plusMinutes(30), lokaal, gebruiker);
    }

    private static Stream<Arguments> opsommingGeldigeWaarden(){
        return Stream.of(Arguments.of("001", gebruiker,sessie, LocalDateTime.now(), true),
                Arguments.of("001", gebruiker, sessie, LocalDateTime.now(), false),
                Arguments.of("52", gebruiker, sessie, LocalDateTime.now(), true),
                Arguments.of("001", gebruiker, sessie, LocalDateTime.now().minusHours(20), false));
    }

    @ParameterizedTest
    @MethodSource("opsommingGeldigeWaarden")
    public void maakInschrijvingGeldigeGegevens_Slaagt(String id,  Gebruiker gebruiker, Sessie sessie, LocalDateTime inschrijvingsdatum, boolean statusAanwezigheid){
        Inschrijving inschrijving = new Inschrijving(inschrijvingsdatum, statusAanwezigheid);
        Assertions.assertEquals(id, inschrijving.getInschrijvingsId());
        Assertions.assertEquals(gebruiker, inschrijving.getGebruiker());
        Assertions.assertEquals(sessie, inschrijving.getSessie());
        Assertions.assertEquals(inschrijvingsdatum, inschrijving.getInschrijvingsdatum());
        Assertions.assertEquals(statusAanwezigheid, inschrijving.isStatusAanwezigheid());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    public void maakInschrijvingOngeldigeGegevensId_GooitException(String id){
        Assertions.assertThrows(InschrijvingException.class, () ->  new Inschrijving(LocalDateTime.now(), true));
    }

    @Test
    public void maakInschrijvingOngeldigeGegevensGebruiker_GooitException(){
        Assertions.assertThrows(InschrijvingException.class, () ->  new Inschrijving(LocalDateTime.now(), true));
    }

    @Test
    public void maakInschrijvingOngeldigeGegevensSessie_GooitException(){
        Assertions.assertThrows(InschrijvingException.class, () ->  new Inschrijving(LocalDateTime.now(), true));
    }

    @Test
    public void maakInschrijvingOngeldigeGegevensLocalDateTime_GooitException(){
        Assertions.assertThrows(InschrijvingException.class, () ->  new Inschrijving(null, true));
    }
}