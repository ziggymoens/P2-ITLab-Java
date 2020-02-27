package domein;


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

    public List<Sessie> geefSessieHuidigeKalender(){
        return huidigeSessieKalender.geefAlleSessiesKalender();
    }

}


/*
    public List<ISessie> getISessies() {
        return ((List<ISessie>)(Object)sessieKalenderController.getSessies());
    }

    public List<ISessie> getSessieObservableList(ISessieKalender selectedItem) {
        return (List<ISessie>)(Object)sessieKalenderController.geefSessiesVanJaar(selectedItem.getAcademiajaar());
    }

    public void setActieveGebruiker(String username){
        gebruiker = sessieKalenderController.getGebruikerByID(username);
    }

    public IGebruiker geefIGebruiker(){
        setActieveGebruiker("758095zm");
        return (IGebruiker) gebruiker;
    }

    public List<ISessieKalender> getISessieKalenders() {
        return (List<ISessieKalender>) (Object) sessieKalenderController.getSessieKalenders();
    }

    public void pasSessieAan(){

    }

    public List<ISessie> geefSessiesOpDag(LocalDate date) {
        return sessieKalenderController.geefSessiesOpDag(date);
    }

    public List<IGebruiker> geefIGebruikers(){
        return (List<IGebruiker>) (Object)sessieKalenderController.geefGebruikers();
    }

    public List<String> geefNamenGebruikers(){
        return sessieKalenderController.geefNamenGebruikers();
    }

    public void verwijderSessie (ISessie sessie){
        sessieKalenderController.verwijderSessie(sessie);
    }

    public void addAankondigingSessie(String sessieid, String gebruikersnaam, String tekst, boolean herinnering, int dagen){
        sessieKalenderController.addAankondigingSessie(sessieid, gebruikersnaam, tekst, herinnering, dagen);
    }

    public List<String> geefMediaTypes() {
        return Arrays.stream(MediaTypes.values()).map(Enum::toString).collect(Collectors.toList());
    }

    public String geefProfielGebruiker() {
        return gebruiker.getGebruikersprofiel().toString();
    }

    public List<ISessie> geefSessiesVanGebruiker() {
        return (List<ISessie>)(Object)sessieKalenderController.geefSessiesVanGebruiker(gebruiker);
    }


    public void pasSessieAan(ISessie sessie, Map<String, String> veranderingenMap){
        Sessie s = sessieKalenderController.getSessies().stream().filter(e -> e.getSessieId().equals(sessie.getSessieId())).findFirst().orElse(null);
        veranderingenMap.forEach((key, value) -> {
            switch(key){
                case "naamverantwoordelijke":
                    s.setNaamGastspreker(value);
                    break;
                case "titel":
                    s.setTitel(value);
                    break;
                case "naamGastspreker":
                    s.setNaamGastspreker(value);
                    break;
                case "lokaal":
                    String[] str = value.split(",");
                    s.setLokaal(sessieKalenderController.getLokalen().stream().filter(e -> e.getLokaalCode().equals(str[0])).findFirst().orElse(null));
                    break;
                case "start":
                    //s.setStartSessie(LocalDateTime.parse(value));
                    break;
                case "eind":
                    //s.setEindeSessie(LocalDateTime.parse(value));
                    break;
                case "maxPlaatsen":
                    s.setMaximumAantalPlaatsen(Integer.parseInt(value));
                    break;
            }

        });
            sessieKalenderController.updateSessie((Sessie)sessie, s);
/*         veranderingenMap.entrySet().stream().forEach( (k,v) -> {
            switch(k){
                case "naamverantwoordelijke":

                    break;
                case "titel":
                    break;
                case "naamGastspreker":
                    s.setNaamGastspreker(v);
                    break;
                case "lokaal":
                    break;
                case "start":
                    break;
                case "eind":
                    break;
                case "maxPlaatsen":
                    break;
            }

        });
       HashMap<String, String> map = veranderingenMap;
        for (HashMap.Entry<String,String> entry : map.entrySet()) {
            switch (entry.getKey()){
                case "naamverantwoordelijke":
                    break;
                case "titel":
                    break;
                case "naamGastspreker":
                    s.setNaamGastspreker(entry.getValue());
                    break;
                case "lokaal":
                    break;
                case "start":
                    break;
                case "eind":
                    break;
                case "maxPlaatsen":
                    break;
            }
        }
    }

    public void maakNieuweMedia(ISessie sessie, IGebruiker gebruiker, String type, String name) {
        Sessie s = sessieKalenderController.getSessieById(sessie.getSessieId());
        Gebruiker g = sessieKalenderController.getGebruikerByID(gebruiker.getGebruikersnaam());
        Media m = new Media(g, name, type);
        sessieKalenderController.addMediaSessie(s, m);
    }

    public void verwijderGebruiker(IGebruiker gebruiker) {
        sessieKalenderController.verwijderGebruiker(gebruiker);
    }
    public List<ILokaal> getLokalen() {
        return (List<ILokaal>)(Object) sessieKalenderController.getLokalen().stream().collect(Collectors.toList());
    }
}
//endregion
*/