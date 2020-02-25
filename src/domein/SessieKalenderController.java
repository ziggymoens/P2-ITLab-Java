package domein;

import domein.domeinklassen.Gebruiker;
import domein.domeinklassen.Sessie;
import domein.domeinklassen.SessieKalender;

import java.util.List;
import java.util.stream.Collectors;

public class SessieKalenderController {
    private final SessieKalenderBeheerder sessieKalenderBeheerder = new SessieKalenderBeheerder();

    public SessieKalenderController() {

    }

    public Gebruiker getGebruikerByID(String username) {
        return sessieKalenderBeheerder.geefAlleGebruikers().stream().filter(g -> g.getGebruikersnaam().equals(username)).findFirst().orElse(null);
    }

    public List<Sessie> getSessies() {
        return sessieKalenderBeheerder.geefAlleSessies();
    }

    public List<Sessie> geefSessiesVanJaar(int jaar){
        return sessieKalenderBeheerder.geefAlleSessies().stream().filter(s -> s.getSessieId().matches(String.format("S%d-.*", jaar-2000))).collect(Collectors.toList());
    }

    public void close() {
        sessieKalenderBeheerder.closePersistentie();
    }

    public List<Sessie> geefAlleSessies() {
        return sessieKalenderBeheerder.geefAlleSessies();
    }

    public List<SessieKalender> getSessieKalenders() {
        return sessieKalenderBeheerder.geefAlleSessieKalenders();
    }
}
