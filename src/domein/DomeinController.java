package domein;


import domein.interfacesDomein.IMedia;
import domein.interfacesDomein.ISessie;

import java.time.LocalDate;
import java.util.List;

public class DomeinController {
    //region Variabelen
    private SessieKalender huidigeSessieKalender;

    private Sessie huidigeSessie;
    private Gebruiker huidigeGebruiker;
    //endregion

    //region Constructor
    public DomeinController() {
        int academiejaar = 0;
        int jaar = LocalDate.now().getYear() - 2000;
        if (LocalDate.now().getMonthValue() < 9) {
            academiejaar = Integer.parseInt(String.format("%d%d", jaar - 1, jaar));
        } else {
            academiejaar = Integer.parseInt(String.format("%d%d", jaar, jaar + 1));
        }
        huidigeSessieKalender = new SessieKalender(academiejaar);
        SessieKalenderDataInit sessieKalenderDataInit = new SessieKalenderDataInit(huidigeSessieKalender);
    }

    private DomeinController(int academiejaar) {
        huidigeSessieKalender = new SessieKalender(academiejaar);
    }
    //endregion

    //region Sessie
    public List<ISessie> geefSessieHuidigeKalender(){
        return (List<ISessie>)(Object)huidigeSessieKalender.geefAlleSessiesKalender();
    }
    //endregion

    //region Media
    public List<IMedia> geefMediaVanSessie(String sessie) {
        return (List<IMedia>)(Object) huidigeSessieKalender.geefAlleMediaVanSessie(sessie);
    }
    //endregion
}