package domein;

import domein.Gebruiker;
import domein.Lokaal;
import domein.Media;
import domein.Sessie;
import exceptions.domein.MediaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

public class MediaTest {
    private static Gebruiker gebruiker;
    private static Lokaal lokaal;
    private static Sessie sessie;

    private static Stream<Arguments> opsommingGeldigeWaarden(){
        return Stream.of(Arguments.of("001", sessie, gebruiker, "https://www.Youtube.com/", "URL"),
                Arguments.of("001", sessie, gebruiker, "test.png", "FOTO"),
                Arguments.of("45", sessie, gebruiker, "test.mp3", "AUDIO"));
    }

    @ParameterizedTest
    @MethodSource("opsommingGeldigeWaarden")
    public void maakMediaGeldigeGegevens_Slaagt(String mediaId, Sessie sessie, Gebruiker gebruiker, String locatie, String type){
        Media media = new Media(locatie, type);
        Assertions.assertEquals(locatie, media.getLocatie());
        Assertions.assertEquals(type, media.getType());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    public void maakMediaOngeldigeGegevensId_GooitException(String id){
        Assertions.assertThrows(MediaException.class, () ->  new Media("test.png", "FOTO"));
    }

    @Test
    public void maakMediaOngeldigeGegevensSessie_GooitException(){
        Assertions.assertThrows(MediaException.class, () ->  new Media( "test.png", "FOTO"));
    }

    @Test
    public void maakMediaOngeldigeGegevensGebruiker_GooitException(){
        Assertions.assertThrows(MediaException.class, () ->  new Media("test.png", "FOTO"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    public void maakMediaOngeldigeGegevensLocatie_GooitException(String locatie){
        Assertions.assertThrows(MediaException.class, () ->  new Media( locatie, "FOTO"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    public void maakMediaOngeldigeGegevensType_GooitException(String type){
        Assertions.assertThrows(MediaException.class, () ->  new Media("test.png", type));
    }

}