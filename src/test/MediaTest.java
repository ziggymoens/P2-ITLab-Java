package test;

import domein.gebruiker.Gebruiker;
import domein.Media;
import domein.sessie.Sessie;
import domein.enums.MediaType;
import exceptions.domein.MediaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

//TODO --> nakijken leeg type bij aanmaken media
public class MediaTest {
    private static Gebruiker gebruiker;
    private static Sessie sessie;

    @BeforeAll
    static void before() {
        gebruiker = new Gebruiker("Test Persoon", "123456tp", "gebruiker", "actief");
    }

    private static Stream<Arguments> opsommingGeldigeWaarden() {
        return Stream.of(Arguments.of(gebruiker, "https://www.Youtube.com/", "URL"),
                Arguments.of(gebruiker, "test.png", "FOTO"),
                Arguments.of(gebruiker, "test.mp3", "AUDIO"));
    }

    @ParameterizedTest
    @MethodSource("opsommingGeldigeWaarden")
    public void maakMediaGeldigeGegevens_Slaagt(Gebruiker gebruiker, String locatie, String type) {
        Media media = new Media(sessie, gebruiker, locatie, type);
        Assertions.assertEquals(locatie, media.getLocatie());
        String[] waarden = new String[]{type, "ONBEKEND"};
        //Assertions.assertTrue();
    }

    private static Stream<Arguments> opsommingOngeldigeWaarden() {
        return Stream.of(Arguments.of(null, "locatie", "URL"),
                Arguments.of(gebruiker, "", "URL"),
                Arguments.of(gebruiker, null, "URL"),
                Arguments.of(gebruiker, "test.png", null)
                //,Arguments.of(gebruiker, "test.png", "")
        );
    }

    @ParameterizedTest
    @MethodSource("opsommingOngeldigeWaarden")
    public void maakMediaOngeldigeWaarden_GooitException(Gebruiker gebruiker, String locatie, MediaType type) {
        Assertions.assertThrows(MediaException.class, () -> {
            new Media(sessie, gebruiker, locatie, type);
        });
    }
}