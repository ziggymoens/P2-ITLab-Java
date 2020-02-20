package test.domein;

import domein.Aankondiging;
import domein.Gebruiker;
import domein.Lokaal;
import domein.Sessie;
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
        Aankondiging aankondiging = new Aankondiging(id, sessie, publicatiedatum, publicist, inhoud);
        Assertions.assertEquals(id, aankondiging.getAankondigingsId());
        Assertions.assertEquals(sessie, aankondiging.getSessie());
        Assertions.assertEquals(publicatiedatum, aankondiging.getPublicatiedatum());
        Assertions.assertEquals(publicist, aankondiging.getPublicist());
        Assertions.assertEquals(inhoud, aankondiging.getInhoud());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    public void maakAankondigingOngeldigeGegevensId_GooitException(String id){
        Assertions.assertThrows(AankondigingException.class, () ->  new Aankondiging(id, sessie, LocalDateTime.now(), gebruiker, "AankondigingTest"));
    }

    @Test
    public void maakAankondigingOngeldigeGegevensSessie_GooitException(){
        Assertions.assertThrows(AankondigingException.class, () ->  new Aankondiging("A001", null, LocalDateTime.now(), gebruiker, "AankondigingTest"));
    }

    @Test
    public void maakAankondigingOngeldigeGegevensLocalDateTime_GooitException(){
        Assertions.assertThrows(AankondigingException.class, () ->  new Aankondiging("A001", sessie, null, gebruiker, "AankondigingTest"));
    }

    @Test
    public void maakAankondigingOngeldigeGegevensPublicist_GooitException(){
        Assertions.assertThrows(AankondigingException.class, () ->  new Aankondiging("A001", sessie, LocalDateTime.now(), null, "AankondigingTest"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    public void maakAankondigingOngeldigeGegevensInhoud_GooitException(String inhoud){
        Assertions.assertThrows(AankondigingException.class, () ->  new Aankondiging("A001", sessie, LocalDateTime.now(), gebruiker, inhoud));
    }
}