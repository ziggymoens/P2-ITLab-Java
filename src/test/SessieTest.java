package test;

import domein.Academiejaar;
import domein.gebruiker.Gebruiker;
import domein.Lokaal;
import domein.sessie.OpenState;
import domein.sessie.Sessie;
import domein.sessie.SessieState;
import exceptions.domein.SessieException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

class SessieTest {

    private static Gebruiker gebruiker;
    private static Lokaal lokaal;
    private static Academiejaar academiejaar;
    private static SessieState openState;

    @BeforeAll
    private static void before() {
        academiejaar = new Academiejaar(2021, LocalDate.now(), LocalDate.now().plusMonths(5));
        gebruiker = new Gebruiker("Test Persoon", "123456tp", 0, "verantwoordelijke", "actief");
        lokaal = new Lokaal("GSCHB.3.049", "AUDITORIUM", 50);
        openState = new OpenState();
    }

    private static Stream<Arguments> opsommingGeldigeWaarden() {
        return Stream.of(Arguments.of("Titel sessie", "beschrijving", LocalDateTime.now().plusSeconds(1), LocalDateTime.now().plusMinutes(30).plusSeconds(1), lokaal, gebruiker, academiejaar, openState.getStatus()),
                Arguments.of("Titel sessie", "beschrijving", LocalDateTime.now().plusSeconds(1), LocalDateTime.now().plusMinutes(60), lokaal, gebruiker, academiejaar, openState.getStatus()),
                Arguments.of("Titel sessie", "beschrijving", LocalDateTime.now().plusSeconds(1), LocalDateTime.now().plusMinutes(31), lokaal, gebruiker, academiejaar, openState.getStatus()));
    }

//String titel, String beschrijving, LocalDateTime startSessie, LocalDateTime eindeSessie, Lokaal lokaal, Gebruiker verantwoordelijke, Academiejaar academiejaar


    @ParameterizedTest
    @MethodSource("opsommingGeldigeWaarden")
    public void maakSessieGeldigeWaarden_Slaagt(String titel, String beschrijving, LocalDateTime startSessie, LocalDateTime eindSessie, Lokaal lokaal, Gebruiker gebruiker, Academiejaar academiejaar, String state) {
        Sessie sessie = new Sessie(titel, beschrijving, startSessie, eindSessie, lokaal, gebruiker, academiejaar, state);
        Assertions.assertEquals(titel, sessie.getTitel());
        Assertions.assertEquals(beschrijving, sessie.getBeschrijving());
        Assertions.assertEquals(startSessie, sessie.getStartSessie());
        Assertions.assertEquals(eindSessie, sessie.getEindeSessie());
        Assertions.assertEquals(lokaal, sessie.getLokaal());
        Assertions.assertEquals(academiejaar, sessie.getAcademiejaar());
    }

    private static Stream<Arguments> opsommingOngeldigeWaarden() {
        return Stream.of(Arguments.of("", "",LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), lokaal, gebruiker, academiejaar, openState.getStatus()),
                Arguments.of("Titel sessie", "", LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), lokaal, gebruiker, academiejaar, openState.getStatus()),
                Arguments.of(null, "", LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), lokaal, gebruiker, academiejaar, openState.getStatus()),
                Arguments.of("", "Beschrijving", LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), lokaal, gebruiker, academiejaar, openState.getStatus()),
                Arguments.of("Titel sessie", null, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), lokaal, gebruiker, academiejaar, openState.getStatus()),
                Arguments.of("Titel sessie", "Beschrijving", LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), lokaal, gebruiker, academiejaar, ""),
                Arguments.of("", "Beschrijving", LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), lokaal, gebruiker, academiejaar, null)
        );
    }

    @ParameterizedTest
    @MethodSource("opsommingOngeldigeWaarden")
    public void maakSessieOngeldigeWaarden_GooitException(String titel, String beschrijving, LocalDateTime startSessie, LocalDateTime eindSessie, Lokaal lokaal, Gebruiker gebruiker, Academiejaar academiejaar, String state) {
        Assertions.assertThrows(SessieException.class, () -> new Sessie(titel, beschrijving, startSessie, eindSessie, lokaal, gebruiker, academiejaar, state));
    }

}