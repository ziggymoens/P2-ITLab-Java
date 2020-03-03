package domein;
import exceptions.domein.SessieException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;
class SessieTest {

    private static Gebruiker gebruiker;
    private static Lokaal lokaal;

    @BeforeAll
    private static void before(){
        gebruiker = new Gebruiker("Test Persoon", "123456tp", "VERANTWOORDELIJKE", "ACTIEF");
        lokaal = new Lokaal("GSCB.3.049","AUDITORIUM", 50);
    }

    private static Stream<Arguments> opsommingGeldigeWaarden(){
        return Stream.of(Arguments.of("Titel sessie", LocalDateTime.now().plusSeconds(1), LocalDateTime.now().plusMinutes(30), lokaal, gebruiker),
                Arguments.of("Titel sessie", LocalDateTime.now().plusSeconds(1), LocalDateTime.now().plusMinutes(60), lokaal, gebruiker),
                Arguments.of("Titel sessie", LocalDateTime.now().plusSeconds(1), LocalDateTime.now().plusMinutes(31), lokaal, gebruiker));
    }


    @ParameterizedTest
    @MethodSource("opsommingGeldigeWaarden")
    public void maakSessieGeldigeWaarden_Slaagt(String titel, LocalDateTime startSessie, LocalDateTime eindSessie, Lokaal lokaal, Gebruiker gebruiker){
            Sessie sessie = new Sessie(titel, startSessie, eindSessie, lokaal, gebruiker);
            Assertions.assertEquals(titel, sessie.getTitel());
            Assertions.assertEquals(startSessie, sessie.getStartSessie());
            Assertions.assertEquals(eindSessie, sessie.getEindeSessie());
            Assertions.assertEquals(lokaal, sessie.getLokaal());
    }

    private static Stream<Arguments> opsommingOngeldigeWaarden(){
        return Stream.of(Arguments.of("", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), lokaal, gebruiker),
                Arguments.of("Titel sessie", LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), lokaal, gebruiker),
                Arguments.of("Titel sessie", LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), lokaal, gebruiker));
    }

    @ParameterizedTest
    @MethodSource("opsommingOngeldigeWaarden")
    public void maakSessieOngeldigeWaarden_GooitException(String titel, LocalDateTime startSessie, LocalDateTime eindSessie, Lokaal lokaal, Gebruiker gebruiker){
        Assertions.assertThrows(SessieException.class, () -> new Sessie(titel, startSessie, eindSessie, lokaal, gebruiker));
    }

}