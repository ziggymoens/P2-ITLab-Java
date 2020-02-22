package domein;

import domein.domeinklassen.Sessie;
import exceptions.domein.SessieKalenderException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SessieKalenderController {
    SessieKalenderBeheerder sessieKalenderBeheerder = new SessieKalenderBeheerder();

    public void close(){
        sessieKalenderBeheerder.closePersistentie();
    }
}
