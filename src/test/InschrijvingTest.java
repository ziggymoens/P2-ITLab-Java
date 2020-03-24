package test;

import domein.Academiejaar;
import domein.gebruiker.Gebruiker;
import domein.Inschrijving;
import domein.Lokaal;
import domein.sessie.OpenState;
import domein.sessie.Sessie;
import exceptions.domein.InschrijvingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public class InschrijvingTest {
    private static Sessie sessie;
    private static Gebruiker gebruiker;
    private static Lokaal lokaal;
    private static Academiejaar academiejaar;
    private static OpenState openState;

    @BeforeAll
    public static void before() {
        academiejaar = new Academiejaar(2021, LocalDate.now(), LocalDate.now().plusMonths(5));
        lokaal = new Lokaal("GSCHB.3.049", "AUDITORIUM", 50);
        gebruiker = new Gebruiker("TestGebruiker", "123456tp", "gebruiker", "actief");
        openState = new OpenState();
        sessie = new Sessie("Titel sessie", "beschrijving", LocalDateTime.now().plusSeconds(1), LocalDateTime.now().plusMinutes(60), lokaal, gebruiker, academiejaar, openState.getStatus());
    }

    private static Stream<Arguments> opsommingGeldigeWaarden() {
        return Stream.of(Arguments.of(sessie, gebruiker, LocalDateTime.now(), true),
                Arguments.of(sessie, gebruiker, LocalDateTime.now(), false),
                Arguments.of(sessie, gebruiker, LocalDateTime.now(), true),
                Arguments.of(sessie, gebruiker, LocalDateTime.now().minusHours(20), false));
    }

    @ParameterizedTest
    @MethodSource("opsommingGeldigeWaarden")
    public void maakInschrijvingGeldigeGegevens_Slaagt(Sessie sessie, Gebruiker gebruiker, LocalDateTime inschrijvingsdatum, boolean statusAanwezigheid) {
        Inschrijving inschrijving = new Inschrijving(sessie, gebruiker, inschrijvingsdatum, statusAanwezigheid);
        Assertions.assertEquals(inschrijvingsdatum, inschrijving.getInschrijvingsdatum());
        Assertions.assertEquals(statusAanwezigheid, inschrijving.isStatusAanwezigheid());
    }

    private static Stream<Arguments> opsommingOngeldigeWaarden() {
        return Stream.of(Arguments.of(sessie, null, LocalDateTime.now(), true),
                Arguments.of(null, gebruiker, LocalDateTime.now(), true),
                Arguments.of(sessie, null, LocalDateTime.now(), false),
                Arguments.of(sessie, gebruiker, null, true));
    }

    @ParameterizedTest
    @MethodSource("opsommingOngeldigeWaarden")
    public void maakInschrijvingOngeldigeGegevens_GooitException(Sessie sessie, Gebruiker gebruiker, LocalDateTime inschrijvingsdatum, boolean statusAanwezigheid) {
        Assertions.assertThrows(InschrijvingException.class, () -> {
            new Inschrijving(sessie, gebruiker, inschrijvingsdatum, statusAanwezigheid);
        });
    }

}