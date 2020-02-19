package test.domein;

import domein.Herinnering;
import exceptions.domein.HerinneringException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class HerinneringTest {
/*
    //region maakHerinneringGeldigeGegevens
    @Test
    public void maakHerinneringGeldigeGegevens(){
        Herinnering herinnering = new Herinnering(3, "tekst");
        assertEquals("tekst", herinnering.getInhoud());
        assertEquals(3, herinnering.getDagenVooraf());
    }
    //endregion

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    public void maakHerinneringOngeldigeGegevens_GooitExcetpion(String tekst){
        assertThrows(HerinneringException.class, () -> new Herinnering(3, tekst));
    }

 */
}