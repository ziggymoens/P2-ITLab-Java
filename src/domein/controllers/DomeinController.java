package domein.controllers;


import domein.*;
import domein.enums.HerinneringTijdstip;
import domein.enums.MediaType;
import domein.gebruiker.Gebruiker;
import domein.interfacesDomein.*;
import domein.sessie.Sessie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class DomeinController {

    //region Variabelen
    private ITypeController typeController;
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
            case "VERANTWOORDELIJKE":
                typeController = new VerantwoordelijkeController(huidigeSessieKalender, huidigeGebruiker);
                break;
            case "HOOFDVERANTWOORDELIJKE":
                typeController = new HoofdverantwoordelijkeController(huidigeSessieKalender, huidigeGebruiker);
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
        Gebruiker g = huidigeSessieKalender.geefGebruikerById(veranderingen.get(0));
        objVeranderingen.add(g);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        LocalDateTime start = LocalDateTime.parse(veranderingen.get(2), formatter);
        objVeranderingen.add(start);
        LocalDateTime eind = LocalDateTime.parse(veranderingen.get(3), formatter);
        objVeranderingen.add(eind);
        Lokaal l = huidigeSessieKalender.geefLokaalById(veranderingen.get(4));
        objVeranderingen.add(l);
        Integer i = Integer.parseInt(veranderingen.get(6));
        typeController.bewerkSessie(huidigeSessie, objVeranderingen);
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

    public List<ISessie> geefISessiesGebruiker(Gebruiker gebruiker, String gebruikersnaam){
        return (List<ISessie>) (Object) huidigeSessieKalender.geefAlleInschrijvingen()
                .stream()
                .filter(sessie -> sessie.getGebruiker().equals(gebruiker))
                .collect(Collectors.toList());
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
                huidigeSessieKalender.geefLokaalById(sessie.get(3)), huidigeSessieKalender.geefGebruikerById(sessie.get(4)), huidigeSessieKalender.getAcademiejaarByDate(LocalDateTime.parse(sessie.get(1))));
        typeController.maakSessieAan(s);
    }

    public void verwijderSessie(ISessie sessie) {
        typeController.verwijderSessie((Sessie) sessie);
    }

    public void setHuidigeISessie(ISessie sessie) {
        this.huidigeSessie = typeController.geefSessieId(sessie.getSessieId());
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
        typeController.verwijderGebruiker((Gebruiker) gebruiker);
    }

    public void bewerkGebruiker(IGebruiker gebruiker) {
        typeController.verwijderGebruiker((Gebruiker) gebruiker);
    }

    public void maakGebruikerAan(IGebruiker gebruiker) {
        typeController.verwijderGebruiker((Gebruiker) gebruiker);
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
        huidigeSessieKalender.geefAlleLokalen().stream().forEach(e -> set.add(e.getStad()));
        return set;
    }

    public Set<String> geefGebouwen(){
        Set<String> set = new HashSet<>();
        huidigeSessieKalender.geefAlleLokalen().stream().forEach(e -> set.add(e.getGebouw()));
        return set;
    }

    public Set<Integer> geefVerdiepingen(){
        return huidigeSessieKalender.geefAlleLokalen()
                .stream()
                .map(l -> l.getVerdieping())
                .collect(Collectors.toSet());
    }

    //2 inputvelden voor minimum en maximum cap.

    public List<String> geefMinCapaciteiten(){
        List <String> capaciteiten = new ArrayList<>();
        capaciteiten.add("0");
        capaciteiten.add("50");
        capaciteiten.add("100");
        capaciteiten.add("150");
        capaciteiten.add("200+");
        return capaciteiten;
    }

        public List<String> geefMaxCapaciteiten(){
        List <String> capaciteiten = new ArrayList<>();
        capaciteiten.add("50");
        capaciteiten.add("100");
        capaciteiten.add("150");
        capaciteiten.add("200+");
        return capaciteiten;
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
        typeController.maakAankondigingAan(aankondiging, sessie);
    }

    public void bewerkAankondiging(Aankondiging aankondiging){
        typeController.bewerkAankondiging(aankondiging);
    }

    public void verwijderAankondiging(IAankondiging aankondiging){
        typeController.verwijderAankondiging((Aankondiging) aankondiging);
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