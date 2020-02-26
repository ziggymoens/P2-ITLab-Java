package domein;

import domein.domeinklassen.Gebruiker;
import domein.domeinklassen.Sessie;
import domein.domeinklassen.SessieKalender;
import domein.interfacesDomein.ISessie;

import java.time.LocalDate;
import java.util.Comparator;
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
        return sessieKalenderBeheerder.geefAlleSessies().stream().filter(s -> s.getSessieId().matches(String.format("S%d-.*", jaar))).collect(Collectors.toList());
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

    public List<Gebruiker> geefGebruikers(){
        return sessieKalenderBeheerder.geefAlleGebruikers().stream().map(e -> e ).collect(Collectors.toList());
    }

    public List<ISessie> geefSessiesOpDag(LocalDate date) {
        return geefAlleSessies().stream().filter(s -> s.getStartSessie().getDayOfYear() == date.getDayOfYear() && s.getStartSessie().getYear() == date.getYear()).sorted(Comparator.comparing(Sessie::getStartSessie)).collect(Collectors.toList());
    }

    public void verwijderSessie(ISessie sessie) {
        Sessie s = getSessieById(sessie.getSessieId());
        sessieKalenderBeheerder.verwijderSessie(s);
    }

    private Sessie getSessieById(String sessieId) {
        return sessieKalenderBeheerder.geefAlleSessies().stream().filter(e -> e.getSessieId().equals(sessieId)).findFirst().orElse(null);
    }
}
