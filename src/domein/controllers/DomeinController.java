package domein.controllers;


import domein.*;
import domein.enums.Gebruikersprofielen;
import domein.enums.Gebruikersstatus;
import domein.enums.MediaTypes;
import domein.interfacesDomein.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class DomeinController {

    //region Variabelen
    private ITypeController typeController;
    private SessieKalender huidigeSessieKalender;
    private Sessie huidigeSessie;
    public Gebruiker huidigeGebruiker;
    public int academiejaar;
    //endregion

    //region Constructor
    public DomeinController(Gebruiker gebruiker, SessieKalender sessieKalender, LocalDate academiejaar) {
        this.huidigeGebruiker = gebruiker;
        this.huidigeSessieKalender = sessieKalender;
        this.academiejaar = geefAcademiejaar(academiejaar);
        setTypeController();
    }

    private void setTypeController() {
        switch (huidigeGebruiker.getGebruikersprofiel().toString()) {
            default:
            case "GEBRUIKER":
                typeController = new GebruikerController(huidigeSessieKalender, huidigeGebruiker);
                break;
            case "VERANTWOORDELIJKE":
                typeController = new VerantwoordelijkeController(huidigeSessieKalender, huidigeGebruiker);
                break;
            case "HOOFDVERANTWOORDELIJKE":
                typeController = new HoofdverantwoordelijkeController(huidigeSessieKalender, huidigeGebruiker);
                break;
        }
    }
    //endregion

    //region Academiejaar
    public List<String> geefAcademiejaren() {
        return huidigeSessieKalender.geefAlleAcademieJaren();
    }

    public void setAcademiejaar(LocalDate jaar) {
        new DomeinController(huidigeGebruiker, huidigeSessieKalender, jaar);
        System.gc();
    }

    public List<String> geefMaanden() {
        String[] maanden = {"Januari", "Februari", "Maart", "April", "Mei", "Juni", "Juli", "Augustus", "September", "Oktober", "November", "December"};
        // {"January","February","March","April","May","June","July","August","September","October","November","December"};
        return Arrays.asList(maanden);
    }

    public String vergelijkMaanden (){
        String[] maanden = {"Januari", "Februari", "Maart", "April", "Mei", "Juni", "Juli", "Augustus", "September", "Oktober", "November", "December"};
        String [] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        String huidigeMaand = huidigeSessie.getStartSessie().getMonth().toString();
        for(int i = 0; i < months.length; i++){
            if(months[i].toLowerCase().equals(huidigeMaand.toLowerCase()))
                huidigeMaand = maanden[i];
        }
        return huidigeMaand;
    }

    private static int geefAcademiejaar(LocalDate date) {
        int jaar = LocalDate.now().getYear() - 2000;
        int aj = 0;
        if (LocalDate.now().getMonthValue() < 9) {
            aj = Integer.parseInt(String.format("%d%d", jaar - 1, jaar));
        } else {
            aj = Integer.parseInt(String.format("%d%d", jaar, jaar + 1));
        }
        return aj;
    }
    //endregion

    //region Sessie
    public List<String> geefFilterOpties() {
        String[] keuzeVoorZoeken = {"Titel", "Stad", "Status"};
        return Arrays.asList(keuzeVoorZoeken);

    }

    public List<ISessie> geefISessiesHuidigeKalender() {
        return (List<ISessie>) (Object) typeController.geefAlleSessiesKalender(academiejaar);
    }

    public List<ISessie> geefISessiesOpAcademiejaar(Integer academiejaar) {
        return (List<ISessie>) (Object) typeController.geefAlleSessiesKalender(academiejaar);
    }

    public List<ISessie> geefISessiesOpDag(LocalDate date) {
        return (List<ISessie>) (Object) typeController.geefAlleSessiesDatum(date);
    }

    public ISessie geefISessieOpId(String id) {
        return typeController.geefSessieId(id);
    }

    public ISessie geefHuidigeISessie() {
        return huidigeSessie;
    }

    public List<ISessie> geefISessieOpLocatie(String locatie) {
        return (List<ISessie>) (Object) typeController.geefAlleSessiesLocatie(locatie);
    }

    public void maakSessieAan(List<String> sessie) {
        //iterator
        Sessie s = new Sessie(sessie.get(0), LocalDateTime.parse(sessie.get(1)), LocalDateTime.parse(sessie.get(2)),
                huidigeSessieKalender.geefLokaalById(sessie.get(3)), huidigeSessieKalender.geefGebruikerById(sessie.get(4)));
        typeController.maakSessieAan(s);
    }

    public void pasSessieAan(Map<String, String> nieuweMap) {
        huidigeSessie.updateSessie(nieuweMap, (List<ILokaal>) (Object) huidigeSessieKalender.geefAlleLokalen());
        typeController.bewerkSessie(huidigeSessie);
    }

    public void verwijderSessie(ISessie sessie) {
        typeController.verwijderSessie((Sessie) sessie);
    }

    public void setHuidigeISessie(ISessie sessie) {
        if (sessie == null) {
            this.huidigeSessie = null;
        } else {
            this.huidigeSessie = typeController.geefSessieId(sessie.getSessieId());
        }
    }


    /* public List<ISessie> geefISessiesVanGebruiker() {
        return (List<ISessie>) (Object) huidigeSessieKalender.geefSessiesVanGebruiker(huidigeGebruiker);
    }

    public List<ISessie> geefISessiesVanGebruiker(List<ISessie> sessies) {
        return sessies.stream().filter(e -> e.getVerantwoordelijke().equals(huidigeGebruiker)).collect(Collectors.toList());
    }*/
    //endregion

    //region Gebruiker
    public IGebruiker geefIGebruikerOpId(String id) {
        return huidigeSessieKalender.geefGebruikerById(id);
    }

    public List<IGebruiker> geefAlleIGebruikers() {
        return (List<IGebruiker>) (Object) huidigeSessieKalender.geefAlleGebruikers();
    }

    public List<IGebruiker> geefIGebruikersOpNaam(String naam) {
        return (List<IGebruiker>) (Object) huidigeSessieKalender.geefAlleGebruikers()
                .stream()
                .filter(e -> e.getNaam().matches("(\\.*)" + naam + "(\\.*)"))
                .collect(Collectors.toList());
    }

    public List<IGebruiker> geefIGebruikersOpType(String type) {
        return (List<IGebruiker>) (Object) huidigeSessieKalender.geefAlleGebruikers()
                .stream()
                .filter(e -> e.getGebruikersprofiel().toString().equals(type))
                .collect(Collectors.toList());
    }

    public List<IGebruiker> geefIGebruikersOpStatus(String status) {
        return (List<IGebruiker>) (Object) huidigeSessieKalender.geefAlleGebruikers()
                .stream()
                .filter(e -> e.getStatus().toString().equals(status))
                .collect(Collectors.toList());
    }

    public List<IGebruiker> geefIGebruikersOpTypeEnStatus(String type, String status) {
        return (List<IGebruiker>) (Object) huidigeSessieKalender.geefAlleGebruikers()
                .stream()
                .filter(e -> e.getStatus().toString().equals(status) && e.getGebruikersprofiel().toString().equals(type))
                .collect(Collectors.toList());
    }

    public IGebruiker geefHuidigeIGebruiker() {
        return huidigeGebruiker;
    }

    public void setHuidigeGebruiker(String gebruikersnaam) {
        huidigeGebruiker = huidigeSessieKalender.geefGebruikerById(gebruikersnaam);
    }

    public void verwijderGebruiker(IGebruiker gebruiker) {
        typeController.verwijderGebruiker((Gebruiker) gebruiker);
    }

    public void bewerkGebruiker(IGebruiker gebruiker) {
        typeController.verwijderGebruiker((Gebruiker) gebruiker);
    }

    public void maakGebruikerAan(IGebruiker gebruiker) {
        typeController.verwijderGebruiker((Gebruiker) gebruiker);
    }

    public void maakNieuweGebruiker(String naam, String gebruikersnaam, Gebruikersprofielen gebruikersprofiel, Gebruikersstatus gebruikersstatus){
        Gebruiker gebruiker = new Gebruiker(naam, gebruikersnaam, gebruikersprofiel, gebruikersstatus);
        huidigeSessieKalender.voegGebruikerToe(gebruiker);
    }
    //endregion

    //region Lokaal
    public List<ILokaal> geefILokalen() {
        return (List<ILokaal>) (Object) huidigeSessieKalender.geefAlleLokalen();
    }

    public ILokaal geefLokaalSessie() {
        return huidigeSessie.getLokaal();
    }

    public List<ILokaal> geefLokalenVanCampus(String campus) {
        return (List<ILokaal>) (Object) huidigeSessieKalender.geefLokaalByCampus(campus.toUpperCase());
    }

    public Set<String> geefSteden(){
        Set<String> set = new HashSet<>();
        huidigeSessieKalender.geefAlleLokalen().stream().forEach(e -> set.add(e.getStad()));
        return set;
    }

    public Set<String> geefGebouwen(){
        Set<String> set = new HashSet<>();
        huidigeSessieKalender.geefAlleLokalen().stream().forEach(e -> set.add(e.getGebouw()));
        return set;
    }

    public Set<String> geefVerdiepingen(){
        Set<String> set = new HashSet<>();
        huidigeSessieKalender.geefAlleLokalen().stream().forEach(e -> set.add(e.getVerdieping()));
        return set;
    }

    public List<String> geefCapaciteiten(){
        List <String> capaciteiten = new ArrayList<>();
        capaciteiten.add("0 - 50");
        capaciteiten.add("50 - 100");
        capaciteiten.add("100 - 150");
        capaciteiten.add("150 - 200");
        capaciteiten.add("200+");
        return capaciteiten;
    }
    //endregion

    //region Media
    public List<IMedia> geefMediaVanHuidigeSessie() {
        return (List<IMedia>) (Object) huidigeSessieKalender.geefAlleMediaVanSessie(huidigeSessie.getSessieId());
    }

    public List<IMedia> geefIMedia() {
        return (List<IMedia>) (Object) huidigeSessieKalender.geefAlleMedia();
    }

    public void maakNieuweMedia(ISessie sessie, IGebruiker gebruiker, String type, String locatie) {
        huidigeSessieKalender.voegMediaToe(new Media(huidigeSessie, (Gebruiker) gebruiker, locatie, type), (Sessie) sessie);
    }
    //endregion

    //region Aankondiging
    public List<IAankondiging> geefAlleAankondigingenVanHuidigeSessie() {
        return (List<IAankondiging>) (Object) huidigeSessieKalender.geefAlleAankondigingenVanSessie(huidigeSessie.getSessieId());
    }

    public void addAankondigingSessie(String sessieId, String gebruikersnaam, String text, boolean automatischeHerinnering, int dagenHerinnering) {
        Aankondiging aankondiging = new Aankondiging(huidigeSessie, huidigeSessieKalender.geefGebruikerById(gebruikersnaam), LocalDateTime.now(), text);
        huidigeSessieKalender.voegAankondigingToe(aankondiging, huidigeSessieKalender.geefSessieById(sessieId));
        if (automatischeHerinnering) {
            huidigeSessieKalender.voegHerinneringToe(new Herinnering(dagenHerinnering), aankondiging);
        }
    }

    public List<String> geefMediaTypes() {
        return Arrays.stream(MediaTypes.values()).map(Enum::toString).collect(Collectors.toList());
    }

    public void huidigeGebruikerIngelogd() {
        huidigeGebruiker.setIngelogd();
    }
    //endregion

    //region Inschrijving
    public List<IInschrijving> geefAlleInschrijvingenVanHuidigeSessie() {
        return (List<IInschrijving>) (Object) huidigeSessieKalender.geefAlleInschrijvingenVanSessie(huidigeSessie.getSessieId());
    }

    public List<IInschrijving> geefAlleInschrijvingen() {
        return (List<IInschrijving>) (Object) huidigeSessieKalender.geefAlleInschrijvingen();
    }
    //endregion

    //region Feedback
    public List<IFeedback> geefAlleFeedbackVanHuidigeSessie() {
        return (List<IFeedback>) (Object) huidigeSessieKalender.geefAlleFeedbackVanSessie(huidigeSessie.getSessieId());
    }
    //endregion
}


    //region extras
/* vorige dc
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
 */
//endregion