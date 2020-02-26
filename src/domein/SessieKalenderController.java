package domein;

import domein.domeinklassen.*;
import domein.interfacesDomein.ISessie;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SessieKalenderController {
    private final SessieKalenderBeheerder sessieKalenderBeheerder = new SessieKalenderBeheerder();

    public SessieKalenderController() {

    }

    public Gebruiker getGebruikerByID(String username) {
        return sessieKalenderBeheerder.geefAlleGebruikers().stream().filter(g -> g.getGebruikersnaam().equals(username)).findFirst().orElse(null);
    }


    //Sessie
    public List<Sessie> getSessies() {
        return sessieKalenderBeheerder.geefAlleSessies();
    }

    public List<Sessie> geefSessiesVanJaar(int jaar){
        return sessieKalenderBeheerder.geefAlleSessies().stream().filter(s -> s.getSessieId().matches(String.format("S%d-.*", jaar))).collect(Collectors.toList());
    }

    public void updateSessie(Sessie sessieOud, Sessie sessieNieuw){
        sessieKalenderBeheerder.updateSessie(sessieOud,sessieNieuw);
    }

    //Einde Sessie

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
        return sessieKalenderBeheerder.geefAlleGebruikers();
    }

    public List<ISessie> geefSessiesOpDag(LocalDate date) {
        return geefAlleSessies().stream().filter(s -> s.getStartSessie().getDayOfYear() == date.getDayOfYear() && s.getStartSessie().getYear() == date.getYear()).sorted(Comparator.comparing(Sessie::getStartSessie)).collect(Collectors.toList());
    }

    public void verwijderSessie(ISessie sessie) {
        Sessie s = getSessieById(sessie.getSessieId());
        sessieKalenderBeheerder.verwijderSessie(s);
    }

    public Sessie getSessieById(String sessieId) {
        return sessieKalenderBeheerder.geefAlleSessies().stream().filter(e -> e.getSessieId().equals(sessieId)).findFirst().orElse(null);
    }

    public void addAankondigingSessie(String sessieid, String gebruikersnaam, String tekst, boolean herinnering, int dagen){
        sessieKalenderBeheerder.addAankondigingSessie(sessieid, gebruikersnaam, tekst, herinnering, dagen);
    }

    public List<Sessie> geefSessiesVanGebruiker(Gebruiker gebruiker) {
        return sessieKalenderBeheerder.geefAlleSessies().stream().filter(s -> s.getVerantwoordelijke().equals(gebruiker)).collect(Collectors.toList());
    }

    public void addMediaSessie(Sessie s, Media m) {
        sessieKalenderBeheerder.addMediaSessie(s, m);
    }

    public Set<Lokaal> getLokalen (){
        return sessieKalenderBeheerder.geefAlleLokalen();
    }
}
