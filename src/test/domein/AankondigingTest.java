package domein;

import domein.domeinklassen.Aankondiging;
import domein.domeinklassen.Gebruiker;
import domein.domeinklassen.Lokaal;
import domein.domeinklassen.Sessie;
import exceptions.domein.AankondigingException;
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

public class AankondigingTest {
    private static Gebruiker gebruiker;
    private static Lokaal lokaal;
    private static Sessie sessie;
    @BeforeAll
    public static void before(){
        gebruiker = new Gebruiker("Pulicist", "123456tp", "GEBRUIKER", "ACTIEF");
        lokaal = new Lokaal("GSCB.3.049", 50);
        sessie = new Sessie("Titel sessie", LocalDateTime.now().plusSeconds(1), LocalDateTime.now().plusMinutes(30), lokaal, gebruiker);
    }

    private static Stream<Arguments> opsommingGeldigeWaarden(){
        return Stream.of(Arguments.of("A001", sessie, LocalDateTime.now(), gebruiker, "AankondigingTest"),
                Arguments.of("A1245", sessie, LocalDateTime.now().minusDays(4), gebruiker, "Test aankondiging"),
                Arguments.of("A005", sessie, LocalDateTime.now().minusHours(2), gebruiker, "tekst"));
    }

    @ParameterizedTest
    @MethodSource("opsommingGeldigeWaarden")
    public void maakAankondigingGeldigeGegevens_Slaagt(String id, Sessie sessie, LocalDateTime publicatiedatum, Gebruiker publicist, String inhoud){
        Aankondiging aankondiging = new Aankondiging(gebruiker, publicatiedatum,inhoud);
        Assertions.assertEquals(id, aankondiging.getAankondigingsId());
        Assertions.assertEquals(publicatiedatum, aankondiging.getPublicatiedatum());
        Assertions.assertEquals(publicist, aankondiging.getPublicist());
        Assertions.assertEquals(inhoud, aankondiging.getInhoud());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    public void maakAankondigingOngeldigeGegevensId_GooitException(String id){
        Assertions.assertThrows(AankondigingException.class, () ->  new Aankondiging(gebruiker, LocalDateTime.now(), "AankondigingTest"));
    }

    @Test
    public void maakAankondigingOngeldigeGegevensSessie_GooitException(){
        Assertions.assertThrows(AankondigingException.class, () ->  new Aankondiging(gebruiker, LocalDateTime.now(), "AankondigingTest"));
    }

    @Test
    public void maakAankondigingOngeldigeGegevensLocalDateTime_GooitException(){
        Assertions.assertThrows(AankondigingException.class, () ->  new Aankondiging(gebruiker,  null, "AankondigingTest"));
    }

    @Test
    public void maakAankondigingOngeldigeGegevensPublicist_GooitException(){
        Assertions.assertThrows(AankondigingException.class, () ->  new Aankondiging(gebruiker, LocalDateTime.now(), "AankondigingTest"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    public void maakAankondigingOngeldigeGegevensInhoud_GooitException(String inhoud){
        Assertions.assertThrows(AankondigingException.class, () ->  new Aankondiging(gebruiker,LocalDateTime.now(), inhoud));
    }
}