package test.domein;

import domein.Gebruiker;
import domein.Lokaal;
import domein.Sessie;
import exceptions.domein.SessieException;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDateTime;
import java.util.stream.Stream;
class SessieTest {

    private static Stream<Arguments> opsommingGeldigeWaarden(){
        return Stream.of(Arguments.of("Titel", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30),
                new Lokaal("GSCB.3.049", 50),
                new Gebruiker("Test Persoon", "123456tp", "VERANTWOORDELIJKE", "ACTIEF")));
    }

    @ParameterizedTest
    @MethodSource("opsommingGeldigeWaarden")
    public void maakSessieGeldigeWaarden_Slaagt(String titel, LocalDateTime startSessie, LocalDateTime eindSessie, Lokaal lokaal, Gebruiker verantwoordelijke){
        Assertions.assertThrows(SessieException.class, () -> {
            new Sessie(titel, startSessie, eindSessie,lokaal, verantwoordelijke);
        });
    }

    /*private static Stream<Arguments> opsommingOngeldigeWaarden(){
        return Stream.of(Arguments.of("Titel", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30),
                        new Lokaal("GSCB.3.049", 50),
                        new Gebruiker("Test Persoon", "123456tp", "VERANTWOORDELIJKE", "ACTIEF")),
                (Arguments.of("Titel", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30),
                        new Lokaal("GSCB.3.049", 50),
                        new Gebruiker("Test Persoon", "123456tp", "VERANTWOORDELIJKE", "ACTIEF"))));
    }*/

}