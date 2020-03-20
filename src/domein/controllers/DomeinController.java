package domein.controllers;


import domein.*;
import domein.enums.HerinneringTijdstip;
import domein.enums.MediaType;
import domein.gebruiker.Gebruiker;
import domein.interfacesDomein.*;
import domein.sessie.Sessie;
import exceptions.domein.LokaalException;
import exceptions.domein.SessieException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class DomeinController {

    //region Variabelen
    private TypeStrategy typeStrategy;
    private SessieKalender huidigeSessieKalender;
    private Sessie huidigeSessie;
    private Gebruiker huidigeGebruiker;
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
        switch (huidigeGebruiker.getGebruikersprofiel()) {
            case "verantwoordelijke":
                typeStrategy = new VerantwoordelijkeStrategy(huidigeSessieKalender, huidigeGebruiker);
                break;
            case "hoofdverantwoordelijke":
                typeStrategy = new HoofdverantwoordelijkeStrategy(huidigeSessieKalender, huidigeGebruiker);
                break;
            default:
                throw new RuntimeException();
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

    public void updateSessie(List<String> veranderingen){
        List<Object> objVeranderingen = new ArrayList<>();

        Gebruiker verantwoordelijke = huidigeSessieKalender.geefGebruikerById(veranderingen.get(0));

        String titel = veranderingen.get(1);

/*        String[] startarr = veranderingen.get(2).split(" ");
        String[] startdatarr = startarr[0].split("/");
        String[] startuurarr = startarr[1].split(":");
        int jaar = LocalDate.now().getYear();
        if(Integer.parseInt(startdatarr[1])<8){
            jaar = LocalDate.now().getYear()+1;
        }
        LocalDate startDate = LocalDate.of(jaar, Integer.parseInt(startdatarr[1]), Integer.parseInt(startdatarr[0]));
        LocalTime startUur = LocalTime.of(Integer.parseInt(startuurarr[0]), Integer.parseInt(startuurarr[1]));
        LocalDateTime start = LocalDateTime.of(startDate, startUur);


        String[] eindarr = veranderingen.get(3).split(" ");
        String[] eindatarr = eindarr[0].split("/");
        String[] einuurarr = eindarr[1].split(":");
        int jaar1 = LocalDate.now().getYear();
        if(Integer.parseInt(eindatarr[1])<8){
            jaar1 = LocalDate.now().getYear()+1;
        }
        LocalDate eindDate = LocalDate.of(jaar, Integer.parseInt(eindatarr[1]), Integer.parseInt(eindatarr[0]));
        LocalTime eindUur = LocalTime.of(Integer.parseInt(einuurarr[0]), Integer.parseInt(einuurarr[1]));
        LocalDateTime eind = LocalDateTime.of(eindDate, eindUur);*/
        LocalDate startdat = LocalDate.parse(veranderingen.get(2));
        String[] startuurarr = veranderingen.get(3).split(":");
        LocalTime startUur = LocalTime.of(Integer.parseInt(startuurarr[0]), Integer.parseInt(startuurarr[1]));
        LocalDateTime start = LocalDateTime.of(startdat,startUur);

        LocalDate einddat = LocalDate.parse(veranderingen.get(4));
        String[] einduurarr = veranderingen.get(5).split(":");
        LocalTime einduur = LocalTime.of(Integer.parseInt(startuurarr[0]), Integer.parseInt(startuurarr[1]));
        LocalDateTime eind = LocalDateTime.of(einddat,einduur);

        Lokaal l = huidigeSessieKalender.geefLokaalById(veranderingen.get(6));

        Integer i = Integer.parseInt(veranderingen.get(8));

        objVeranderingen.add(0, verantwoordelijke);
        objVeranderingen.add(1, titel);
        objVeranderingen.add(2, start);
        objVeranderingen.add(3, eind);
        objVeranderingen.add(4, l);
        objVeranderingen.add(5, veranderingen.get(7));
        objVeranderingen.add(6, i);
        typeStrategy.bewerkSessie(huidigeSessie, objVeranderingen);
    }

    public Map<String, String> controleerDataSessie (List<String> data) {
        Map<String, String> map = new HashMap<>();
        try{

        } catch(LokaalException lokaalEx){
            map.put("lokaal", lokaalEx.getMessage());
        } catch(SessieException sessieEx){
            map.put("lokaal", sessieEx.getMessage());
        }
        return map;
    }

    public List<ISessie> geefISessiesHuidigeKalender() {
        return (List<ISessie>) (Object) typeStrategy.geefAlleSessiesKalender(academiejaar);
    }

    public List<ISessie> geefISessiesOpAcademiejaar(Integer academiejaar) {
        return (List<ISessie>) (Object) typeStrategy.geefAlleSessiesKalender(academiejaar);
    }

    public List<ISessie> geefISessiesOpDag(LocalDate date) {
        return (List<ISessie>) (Object) typeStrategy.geefAlleSessiesDatum(date);
    }

    public List<ISessie> geefISessiesGebruiker(Gebruiker gebruiker, String gebruikersnaam){
        return (List<ISessie>) (Object) huidigeSessieKalender.geefAlleInschrijvingen()
                .stream()
                .filter(sessie -> sessie.getGebruiker().equals(gebruiker))
                .collect(Collectors.toList());
    }

    public ISessie geefISessieOpId(String id) {
        return typeStrategy.geefSessieId(id);
    }

    public ISessie geefHuidigeISessie() {
        return huidigeSessie;
    }

    public List<ISessie> geefISessieOpLocatie(String locatie) {
        return (List<ISessie>) (Object) typeStrategy.geefAlleSessiesLocatie(locatie);
    }

    public List<ISessie> geefAlleSessiesKalenderVanGebruiker(Gebruiker gebruiker){
        return (List<ISessie>) (Object) huidigeSessieKalender.geefAlleSessiesKalenderVanGebruiker(academiejaar, gebruiker);
    }

    public void maakSessieAan(List<String> sessie) {
        //iterator
        Sessie s = new Sessie(sessie.get(0), sessie.get(1),LocalDateTime.parse(sessie.get(2)), LocalDateTime.parse(sessie.get(3)),
                huidigeSessieKalender.geefLokaalById(sessie.get(4)), huidigeSessieKalender.geefGebruikerById(sessie.get(5)), huidigeSessieKalender.getAcademiejaarByDate(LocalDateTime.parse(sessie.get(6))));
        typeStrategy.maakSessieAan(s);
    }

    public void verwijderSessie(ISessie verwijderen, ISessie vorige) {
        setHuidigeISessie(vorige);
        typeStrategy.verwijderSessie((Sessie) verwijderen);
    }

    public void setHuidigeISessie(ISessie sessie) {
        this.huidigeSessie = typeStrategy.geefSessieId(sessie.getSessieId());
    }


    /* public List<ISessie> geefISessiesVanGebruiker() {
        return (List<ISessie>) (Object) huidigeSessieKalender.geefSessiesVanGebruiker(huidigeGebruiker);
    }

    public List<ISessie> geefISessiesVanGebruiker(List<ISessie> sessies) {
        return sessies.stream().filter(e -> e.getVerantwoordelijke().equals(huidigeGebruiker)).collect(Collectors.toList());
    }*/
    //endregion

    //region Gebruiker
    public void updateGebruiker(String naam, String gebruikersnaam, String status, String profiel){
        List<String> gegevens = new ArrayList<>();
        gegevens.addAll(Arrays.asList(naam, gebruikersnaam, status, profiel));
        huidigeGebruiker.update(gegevens);
    }

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
        typeStrategy.verwijderGebruiker((Gebruiker) gebruiker);
    }

    public void bewerkGebruiker(IGebruiker gebruiker) {
        typeStrategy.verwijderGebruiker((Gebruiker) gebruiker);
    }

    public void maakGebruikerAan(IGebruiker gebruiker) {
        typeStrategy.verwijderGebruiker((Gebruiker) gebruiker);
    }

    public void maakNieuweGebruiker(String naam, String gebruikersnaam, String gebruikersprofiel, String gebruikersstatus){
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
        set.add("--Stad--");
        huidigeSessieKalender.geefAlleLokalen().stream().forEach(e -> set.add(e.getStad()));
        return set;
    }

    public Set<String> geefGebouwen(){
        Set<String> set = new HashSet<>();
        set.add("--Gebouw--");
        huidigeSessieKalender.geefAlleLokalen().stream().forEach(e -> set.add(e.getGebouw()));
        return set;
    }

    public Set<String> geefVerdiepingen(){
        Set<String> set = new HashSet<>();
        set.add("--Verdieping--");
        huidigeSessieKalender.geefAlleLokalen().stream().forEach(e -> set.add(((Integer)e.getVerdieping()).toString()));
        return set;
    }

    public boolean controleerMaxCapaciteitLokaal(int getal, ILokaal lokaal){
        return lokaal.getAantalPlaatsen() < getal;
    }

    public List<ILokaal> filterLokaal(Map<String,String> filter){
        List<List<ILokaal>> gefiltered = new ArrayList<>();
        filter.keySet().stream().forEach(e -> {
            switch(e){
                case "stad":
                    gefiltered.add(filterOpStad(filter.get(e)));
                    break;
                case "gebouw":
                    gefiltered.add(filterOpGebouw(filter.get(e)));
                    break;
                case "verdieping":
                    gefiltered.add(filterOpVerdieping(Integer.parseInt(filter.get(e))));
                    break;
                case "minCapaciteit":
                    gefiltered.add(filterOpMinCapaciteit(filter.get(e)));
                    break;
                case "maxCapaciteit":
                    gefiltered.add(filterOpMaxCapaciteit(filter.get(e)));
                    break;
                default:
                    break;
            }
        });
        Collection<ILokaal> temp = new ArrayList<>();
        for(int i = gefiltered.size()-1; i >= 1;i--){
            gefiltered.get(i-1).retainAll(gefiltered.get(i));
        }
        return gefiltered.get(0);
    }

    private List<ILokaal> filterOpStad(String filter){
        return geefILokalen().stream().filter(lokaal -> lokaal.getStad().equals(filter)).collect(Collectors.toList());
    }

    private List<ILokaal> filterOpGebouw(String filter){
        return geefILokalen().stream().filter(lokaal -> lokaal.getGebouw().equals(filter)).collect(Collectors.toList());
    }

    private List<ILokaal> filterOpVerdieping(int filter){
        return geefILokalen().stream().filter(lokaal -> lokaal.getVerdieping() == filter).collect(Collectors.toList());
    }

    private List<ILokaal> filterOpMinCapaciteit(String filter){
        return geefILokalen()
                .stream()
                .filter(e -> e.getAantalPlaatsen() >= Integer.parseInt(filter))
                .collect(Collectors.toList());
    }

    private List<ILokaal> filterOpMaxCapaciteit(String filter){
        return geefILokalen()
                .stream()
                .filter(e -> e.getAantalPlaatsen() < Integer.parseInt(filter))
                .collect(Collectors.toList());
    }

    /*
    2 velden met min en max waarde

    private List<ILokaal> filterOpCapaciteit(String filter){ //#SmallBrainCode
        int min = 0, max = 0;
        switch(filter){
            default:
            case "0 - 50":
                min = 0;
                max = 49;
                break;
            case "50 - 100":
                min = 50;
                max = 99;
                break;
            case "100 - 150":
                min = 100;
                max = 149;
                break;
            case "150 - 200":
                min = 150;
                max = 199;
                break;
            case "200+":
                min = 200;
                max = Integer.MAX_VALUE;
                break;
        }
        final int minFinal = min;
        final int maxFinal = max;
        return geefILokalen()
                .stream()
                .filter(lokaal -> lokaal.getAantalPlaatsen() > minFinal && lokaal.getAantalPlaatsen() < maxFinal)
                .collect(Collectors.toList());
    }
     */

    private void updateSessieLokaal (ILokaal lokaal) {

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
        return Arrays.stream(MediaType.values()).map(Enum::toString).collect(Collectors.toList());
    }

    public void huidigeGebruikerIngelogd() {
        throw new UnsupportedOperationException();
    }

    public List<String> geefHerinneringsTijdstippen(){
        return Arrays.stream(HerinneringTijdstip.values()).map(Enum::toString).collect(Collectors.toList());
    }

    public void maakAankondigingAan(Aankondiging aankondiging, Sessie sessie){
        typeStrategy.maakAankondigingAan(aankondiging, sessie);
    }

    public void bewerkAankondiging(Aankondiging aankondiging){
        typeStrategy.bewerkAankondiging(aankondiging);
    }

    public void verwijderAankondiging(IAankondiging aankondiging){
        typeStrategy.verwijderAankondiging((Aankondiging) aankondiging);
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