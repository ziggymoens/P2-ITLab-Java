package domein.controllers;


import domein.*;
import domein.enums.HerinneringTijdstip;
import domein.enums.MediaType;
import domein.gebruiker.Gebruiker;
import domein.interfacesDomein.*;
import domein.sessie.Sessie;
import exceptions.domein.SessieException;
import userinterface.main.IObserver;

import java.awt.image.BufferedImage;
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
    public Academiejaar huidigAcademiejaar;
    //endregion

    //region Constructor
    public DomeinController(Gebruiker gebruiker, SessieKalender sessieKalender, LocalDate academiejaar) {
        this.huidigeGebruiker = gebruiker;
        this.huidigeSessieKalender = sessieKalender;
        this.huidigAcademiejaar = geefAcademiejaarVanDate(academiejaar);
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
    public List<IAcademiejaar> geefAcademiejaren() {
        return (List<IAcademiejaar>) (Object) huidigeSessieKalender.geefAlleAcademieJaren();
    }

    public IAcademiejaar geefAcademiejaarVanString(String academiejaar) {
        String[] ajarr = academiejaar.split(" - ");
        String j1 = ajarr[0].substring(2);
        String j2 = ajarr[1].substring(2);
        int jaar = Integer.parseInt(j1 + j2);
        return huidigeSessieKalender.geefAcademiejaarById(jaar);
    }

    public IAcademiejaar geefAcademiejaarOpId(int academiejaar) {
        return this.huidigeSessieKalender.geefAcademiejaarById(academiejaar);
    }

    public IAcademiejaar geefHuidigIAcademiejaar() {
        return this.huidigAcademiejaar;
    }

    public List<String> geefMaanden() {
        String[] maanden = {"Januari", "Februari", "Maart", "April", "Mei", "Juni", "Juli", "Augustus", "September", "Oktober", "November", "December"};
        // {"January","February","March","April","May","June","July","August","September","October","November","December"};
        return Arrays.asList(maanden);
    }

    public String vergelijkMaanden() {
        //vergelijking tussen Nederlandse en Engelse maanden voor dropdown
        String[] maanden = {"Januari", "Februari", "Maart", "April", "Mei", "Juni", "Juli", "Augustus", "September", "Oktober", "November", "December"};
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String huidigeMaand = huidigeSessie.getStartSessie().getMonth().toString();
        for (int i = 0; i < months.length; i++) {
            if (months[i].toLowerCase().equals(huidigeMaand.toLowerCase()))
                huidigeMaand = maanden[i];
        }
        return huidigeMaand;
    }

    public String vergelijkMaanden(Sessie s) {
        String[] maanden = {"Januari", "Februari", "Maart", "April", "Mei", "Juni", "Juli", "Augustus", "September", "Oktober", "November", "December"};
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String huidigeMaand = s.getStartSessie().getMonth().toString();
        for (int i = 0; i < months.length; i++) {
            if (months[i].toLowerCase().equals(huidigeMaand.toLowerCase()))
                huidigeMaand = maanden[i];
        }
        return huidigeMaand;
    }

    private Academiejaar geefAcademiejaarVanDate(LocalDate date) {
        int jaar = LocalDate.now().getYear() - 2000;
        int aj = 0;
        if (LocalDate.now().getMonthValue() < 9) {
            aj = Integer.parseInt(String.format("%d%d", jaar - 1, jaar));
        } else {
            aj = Integer.parseInt(String.format("%d%d", jaar, jaar + 1));
        }
        return this.huidigeSessieKalender.geefAcademiejaarById(aj);
    }
    //endregion

    //region Sessie
    public List<String> geefFilterOpties() {
        String[] keuzeVoorZoeken = {"Titel", "Stad", "Status"};
        return Arrays.asList(keuzeVoorZoeken);
    }

    public boolean isSessieOpen() {
        if (huidigeSessie.getCurrentState().getStatus().toLowerCase().equals("open")) {
            return true;
        }
        return false;
    }

    public boolean isSessieGesloten() {
        if (huidigeSessie.getCurrentState().getStatus().toLowerCase().equals("gesloten")) {
            return true;
        }
        return false;
    }

    public boolean isZichtbaar() {
        if (huidigeSessie.getCurrentState().getStatus().toLowerCase().equals("zichtbaar") || huidigeSessie.getCurrentState().getStatus().toLowerCase().equals("open") || huidigeSessie.getCurrentState().getStatus().toLowerCase().equals("gesloten")) {
            return true;
        }
        return false;
    }

    public Map <String, String> errorVelden(List<String> veranderingen, boolean nieuweSessie){
        List<Object> objVeranderingen = new ArrayList<>();
        Map<String, String> fouten = new HashMap<>();
        try {
            Gebruiker verantwoordelijke = null;
            String titel = null;
            Lokaal lokaal = null;
            Integer maxLokaal = null;
            String gastspreker;
            Boolean zichtbaar;
            LocalDateTime eind = null;
            LocalDateTime start = null;
            LocalDate datum = null;
            String beschrijving;
            try{
                titel = veranderingen.get(1);
                if(titel.isBlank()) throw new SessieException();
            } catch(SessieException se){
                fouten.put("titel", "Sessie moet een titel hebben");
            }
            try {
                if(veranderingen.get(0) != null) {
                    verantwoordelijke = huidigeSessieKalender.geefGebruikerById(veranderingen.get(0));
                } else{
                    throw new SessieException();
                }
            } catch (SessieException se) {
                fouten.put("verantwoordelijke", "Sessie moet een verantwoordelijke hebben");
            }
            try {
                datum = LocalDate.parse(veranderingen.get(2));
            } catch (NullPointerException np) {
                fouten.put("datum", "Sessie moet een datum hebben");
            }
            try {
                String[] startuurarr = veranderingen.get(3).split(":");
                LocalTime startUur = LocalTime.of(Integer.parseInt(startuurarr[0]), Integer.parseInt(startuurarr[1]));
                start = LocalDateTime.of(datum, startUur);
            }catch (NumberFormatException np) {
                fouten.put("start", "Sessie moet een start uur hebben");
            }
            try {
                String[] einduurarr = veranderingen.get(4).split(":");
                LocalTime einduur = LocalTime.of(Integer.parseInt(einduurarr[0]), Integer.parseInt(einduurarr[1]));
                eind = LocalDateTime.of(datum, einduur);
            } catch (NumberFormatException np) {
                fouten.put("eind", "Sessie moet een eind uur hebben");
            }
            try {
                if(veranderingen.get(5) != null) {
                    lokaal = huidigeSessieKalender.geefLokaalById(veranderingen.get(5));
                } else {
                    throw new SessieException();
                }
            }  catch (SessieException se) {
                fouten.put("lokaal", "Sessie moet een lokaal hebben");
            }

            try {
                maxLokaal = Integer.parseInt(veranderingen.get(7));
            }   catch (NumberFormatException np) {
                fouten.put("maxPlaatsen", "Sessie moet een max hebben");
            }
            gastspreker = veranderingen.get(6);
            zichtbaar = Boolean.parseBoolean(veranderingen.get(9));
            beschrijving = veranderingen.get(8);


            objVeranderingen.add(0, verantwoordelijke);
            objVeranderingen.add(1, titel);
            objVeranderingen.add(2, start);
            objVeranderingen.add(3, eind);
            objVeranderingen.add(4, lokaal);
            objVeranderingen.add(5, gastspreker);
            objVeranderingen.add(6, maxLokaal);
            objVeranderingen.add(7, zichtbaar);
            objVeranderingen.add(8, beschrijving);

            if(fouten.isEmpty()) {
                if (nieuweSessie) {
                    fouten.putAll(maakSessieAan(objVeranderingen));
                } else {
                    updateSessie(objVeranderingen);
                    fouten.putAll(huidigeSessie.getFouten());
                }
            }
        } catch (SessieException se) {
            String[] fout = se.getMessage().split(";");
            fouten.put(fout[0], fout[1]);
        }

        return fouten;

    }

    public void updateSessie(List<Object> veranderingen) {
        typeStrategy.bewerkSessie(huidigeSessie, veranderingen);
    }

    public Map<String, String> maakSessieAan(List<Object> sessie) {
        Sessie s = new Sessie((String) sessie.get(1), (String) sessie.get(8), (LocalDateTime) sessie.get(2), (LocalDateTime) sessie.get(3), (Lokaal) sessie.get(4), (Gebruiker) sessie.get(0), this.huidigAcademiejaar, (Boolean) sessie.get(7) ? "zichtbaar" : "niet zichtbaar");
        if(s.getFouten().isEmpty()){typeStrategy.maakSessieAan(s);}
        return s.getFouten();
    }

    public String verwijderSessie(ISessie verwijderen, ISessie vorige) {
        String s = "";
        setHuidigeISessie(vorige);
        try {
            typeStrategy.verwijderSessie((Sessie) verwijderen);
        } catch (SessieException se){
            s = se.getMessage();
        }
        return s;
    }

    public void veranderState(String state){
        typeStrategy.setState(huidigeSessie, state);
    }

    public void setHuidigeISessie(ISessie sessie) {
        this.huidigeSessie = typeStrategy.geefSessieId(sessie.getSessieId());
    }

    public void addObserver(IObserver iObserver) {
        huidigeSessie.addObserver(iObserver);
    }

    public List<ISessie> geefISessiesHuidigeKalender() {
        return (List<ISessie>) (Object) typeStrategy.geefAlleSessiesKalender(huidigAcademiejaar.getAcademiejaar());
    }

    public List<ISessie> geefNietGeopendeISessiesHuidigeKalender() {
        return (List<ISessie>) (Object) typeStrategy.geefAlleNietGeopendeSessiesKalender(huidigAcademiejaar.getAcademiejaar());
    }

    public List<ISessie> geefISessiesOpAcademiejaar(Integer academiejaar) {
        return (List<ISessie>) (Object) typeStrategy.geefAlleSessiesKalender(academiejaar);
    }

    public List<ISessie> geefISessiesOpDag(LocalDate date) {
        return (List<ISessie>) (Object) typeStrategy.geefAlleSessiesDatum(date);
    }

    public List<ISessie> geefISessiesGebruiker(Gebruiker gebruiker, String gebruikersnaam) {
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

    public List<ISessie> geefAlleSessiesKalenderVanGebruiker(Gebruiker gebruiker) {
        return (List<ISessie>) (Object) huidigeSessieKalender.geefAlleSessiesKalenderVanGebruiker(huidigAcademiejaar.getAcademiejaar(), gebruiker);
    }


    /* public List<ISessie> geefISessiesVanGebruiker() {
        return (List<ISessie>) (Object) huidigeSessieKalender.geefSessiesVanGebruiker(huidigeGebruiker);
    }

    public List<ISessie> geefISessiesVanGebruiker(List<ISessie> sessies) {
        return sessies.stream().filter(e -> e.getVerantwoordelijke().equals(huidigeGebruiker)).collect(Collectors.toList());
    }*/
    //endregion

    //region Gebruiker
    public void updateGebruiker(String naam, String gebruikersnaam, String status, String profiel) {
        List<String> gegevens = new ArrayList<>(Arrays.asList(naam, gebruikersnaam, status.toLowerCase(), profiel.toLowerCase()));
        Gebruiker gebruiker = huidigeSessieKalender.geefGebruikerById(gebruikersnaam);
        System.out.println(gebruiker.toString());
        huidigeSessieKalender.updateGebruiker(gebruiker, gegevens);
    }

    public IGebruiker geefIGebruikerOpId(String id) {
        return huidigeSessieKalender.geefGebruikerById(id);
    }

    /***
     * Returntypes worden gecast naar een Object --> IGebruiker =/= Gebruiker (types zijn anders incompatible)
     * @return
     */
    public List<IGebruiker> geefAlleIGebruikers() {
        return (List<IGebruiker>) (Object) huidigeSessieKalender.geefAlleGebruikers();
    }

    /***
     * Returntypes worden gecast naar een Object --> IGebruiker =/= Gebruiker (types zijn anders incompatible)
     * @return
     */
    public List<IGebruiker> geefAlleVerantwoordelijken() {
        return (List<IGebruiker>) (Object) huidigeSessieKalender.geefAlleGebruikers().stream().filter(g -> g.getGebruikersprofiel().contains("verant")).collect(Collectors.toList());
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

    public void maakNieuweGebruiker(String naam, String gebruikersnaam, String gebruikersprofiel, String gebruikersstatus) {
        Gebruiker gebruiker = new Gebruiker(naam, gebruikersnaam, 0, gebruikersprofiel, gebruikersstatus);
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

    public Set<String> geefSteden() {
        Set<String> set = new HashSet<>();
        set.add("--Stad--");
        huidigeSessieKalender.geefAlleLokalen().stream().forEach(e -> set.add(e.getStad()));
        return set;
    }

    public Set<String> geefGebouwen() {
        Set<String> set = new HashSet<>();
        set.add("--Gebouw--");
        huidigeSessieKalender.geefAlleLokalen().stream().forEach(e -> set.add(e.getGebouw()));
        return set;
    }

    public Set<String> geefVerdiepingen() {
        Set<String> set = new HashSet<>();
        set.add("--Verdieping--");
        huidigeSessieKalender.geefAlleLokalen().stream().forEach(e -> set.add(((Integer) e.getVerdieping()).toString()));
        return set;
    }

    public boolean controleerMaxCapaciteitLokaal(int getal, ILokaal lokaal) {
        return lokaal.getAantalPlaatsen() < getal;
    }

    public List<ILokaal> filterLokaal(Map<String, String> filter) {
        List<List<ILokaal>> gefiltered = new ArrayList<>();
        filter.keySet().stream().forEach(e -> {
            switch (e) {
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
        for (int i = gefiltered.size() - 1; i >= 1; i--) {
            gefiltered.get(i - 1).retainAll(gefiltered.get(i));
        }
        return gefiltered.get(0);
    }

    private List<ILokaal> filterOpStad(String filter) {
        return geefILokalen().stream().filter(lokaal -> lokaal.getStad().equals(filter)).collect(Collectors.toList());
    }

    private List<ILokaal> filterOpGebouw(String filter) {
        return geefILokalen().stream().filter(lokaal -> lokaal.getGebouw().equals(filter)).collect(Collectors.toList());
    }

    private List<ILokaal> filterOpVerdieping(int filter) {
        return geefILokalen().stream().filter(lokaal -> lokaal.getVerdieping() == filter).collect(Collectors.toList());
    }

    private List<ILokaal> filterOpMinCapaciteit(String filter) {
        return geefILokalen()
                .stream()
                .filter(e -> e.getAantalPlaatsen() >= Integer.parseInt(filter))
                .collect(Collectors.toList());
    }

    private List<ILokaal> filterOpMaxCapaciteit(String filter) {
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

    private void updateSessieLokaal(ILokaal lokaal) {

    }
    //endregion

    //region Media
    public List<IMedia> geefMediaVanHuidigeSessie() {
        return (List<IMedia>) (Object) huidigeSessieKalender.geefAlleMediaVanSessie(huidigeSessie.getSessieId());
    }

    public List<IMedia> geefIMedia() {
        return (List<IMedia>) (Object) huidigeSessieKalender.geefAlleMedia();
    }


    public void maakNieuweMedia(BufferedImage image) {
        Media m = new Media(huidigeGebruiker, huidigeSessie, image);
        huidigeSessieKalender.voegMediaToe(m, huidigeSessie);
    }

    public void maakNieuweMedia(ISessie sessie, IGebruiker gebruiker, String type, String locatie) {
        huidigeSessieKalender.voegMediaToe(new Media(huidigeSessie, (Gebruiker) gebruiker, locatie, type), (Sessie) sessie);
    }

    public void voegMediaToe(ISessie sessie, IGebruiker gebruiker, String type, BufferedImage afbeelding, String fileName) {
        huidigeSessieKalender.voegMediaToeLob(new Media(huidigeSessie, (Gebruiker) gebruiker, type, afbeelding, fileName), (Sessie) sessie);
    }

    public void verwijderMedia(IMedia media) {
        huidigeSessieKalender.verwijderMedia((Media) media);
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

    public List<String> geefHerinneringsTijdstippen() {
        return Arrays.stream(HerinneringTijdstip.values()).map(Enum::toString).collect(Collectors.toList());
    }

    public void maakAankondigingAan(Aankondiging aankondiging, Sessie sessie) {
        typeStrategy.maakAankondigingAan(aankondiging, sessie);
    }

    public void bewerkAankondiging(Aankondiging aankondiging) {
        typeStrategy.bewerkAankondiging(aankondiging);
    }

    public void verwijderAankondiging(IAankondiging aankondiging) {
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

    public void addInschrijvingSessie(String sessieId, String gebruikersnaam, LocalDateTime inschrijvingsdatum, boolean statusAanwezigheid) {
        Inschrijving inschrijving = new Inschrijving(huidigeSessie, huidigeSessieKalender.geefGebruikerById(gebruikersnaam), inschrijvingsdatum, statusAanwezigheid);
        huidigeSessieKalender.voegInschrijvingToe(inschrijving, huidigeSessieKalender.geefSessieById(sessieId));
    }

    public void verwijderInschrijving(IInschrijving inschrijving) {
        typeStrategy.verwijderInschrijving((Inschrijving) inschrijving);
    }
    public void updateInschrijving(IInschrijving inschrijving, List<String> veranderingen){
        List<Object> objVeranderingen = new ArrayList<>();
        objVeranderingen.add(0 ,LocalDate.parse(veranderingen.get(0)));
        if(veranderingen.get(1) == "AANWEZIG")
            objVeranderingen.add(1, true);
        else
            objVeranderingen.add(1, false);
        huidigeSessieKalender.updateInschrijving((Inschrijving) inschrijving, objVeranderingen);
    }
    //endregion

    //region Feedback
    public List<IFeedback> geefAlleFeedbackVanHuidigeSessie() {
        return (List<IFeedback>) (Object) huidigeSessieKalender.geefAlleFeedbackVanSessie(huidigeSessie.getSessieId());
    }

    public void addFeedbackSessie(String sessieId, String gebruikersnaam, String text) {
        Feedback feedback = new Feedback(huidigeSessie, huidigeSessieKalender.geefGebruikerById(gebruikersnaam), text, LocalDate.now());
        huidigeSessieKalender.voegFeedbackToe(feedback, huidigeSessieKalender.geefSessieById(sessieId));
    }

    public void verwijderFeedback(IFeedback feedback) {
        typeStrategy.verwijderFeedback((Feedback) feedback);
    }

    public Map<String, String> controleerDataFeedback(List<String> data) {
        /*Map<String, String> map = new HashMap<>();
        try {
            updateFeedback(data);
        } catch (LokaalException lokaalEx) {
            map.put("lokaal", lokaalEx.getMessage());
        } catch (SessieException sessieEx) {
            String[] fout = sessieEx.getMessage().split(";");
            switch (fout[0]) {
                case "verantwoordelijke":
                    map.put("verantwoordelijke", fout[1]);
                    break;
                case "titel":
                    map.put("titel", fout[1]);
                    break;
                case "start":
                    map.put("start", fout[1]);
                    break;
                case "eind":
                    map.put("eind", fout[1]);
                    break;
                case "gastspreker":
                    map.put("gastspreker", fout[1]);
                    break;
                case "maxPlaatsen":
                    map.put("maxPlaatsen", fout[1]);
                    break;
                case "lokaal":
                    map.put("lokaal", fout[1]);
                    break;
            }
            map.put("sessie", sessieEx.getMessage());
        }
        return map;*/
        return null;
    }

    public void updateFeedback(IFeedback feedback, List<String> veranderingen){
        List<Object> objVeranderingen = new ArrayList<>();
        objVeranderingen.add(0, veranderingen.get(0));
        huidigeSessieKalender.updateFeedback((Feedback) feedback, objVeranderingen);
    }

    public void profielfotoGebruikerWijzigen(BufferedImage image, IGebruiker selected) {
        huidigeSessieKalender.profielfotoGebruikerWijzigen(image, (Gebruiker) selected);
    }

    public void mediaAfbeeldingWijzigen(BufferedImage image, IMedia huidigeMedia) {
        huidigeSessieKalender.mediaAfbeeldingWijzigen(image, (Media) huidigeMedia);
    }

    public IStatistiek geefStatistiek() {
        return new Statistiek();
    }


    //endregion
}