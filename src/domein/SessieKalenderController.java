package domein;

import domein.domeinklassen.Sessie;
import domein.interfacesDomein.ISessie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class SessieKalenderController {
    private final SessieKalenderBeheerder sessieKalenderBeheerder = new SessieKalenderBeheerder();

    public SessieKalenderController() {

    }

    public List<ISessie> getISessies() {
        return ((List<ISessie>) (Object) sessieKalenderBeheerder.geefAlleSessies());
    }

    public void close() {
        sessieKalenderBeheerder.closePersistentie();
    }

    public List<Sessie> geefAlleSessies() {
        return sessieKalenderBeheerder.geefAlleSessies();
    }
}
