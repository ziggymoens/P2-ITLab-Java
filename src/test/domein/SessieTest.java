package test.domein;

import domein.Gebruiker;
import domein.Lokaal;
import exceptions.domein.SessieException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SessieTest {

    private static Stream<Arguments> opsomminsOngeldigeWaarden(){
        return Stream.of(Arguments.of("", ""));
    }

    @ParameterizedTest
    @MethodSource("opsomminsOngeldigeWaarden")
    public void maakSessieOngeldigeWaarden_GooitException(String titel, LocalDateTime startSessie, Lokaal lokaal, Gebruiker verantwoordelijke){
        Assertions.assertThrows(SessieException.class, () -> {

        });
    }

}