package domein;


import domein.domeinklassen.Gebruiker;
import domein.domeinklassen.Media;
import domein.domeinklassen.Sessie;
import domein.enums.MediaTypes;
import domein.interfacesDomein.IGebruiker;
import domein.interfacesDomein.ISessie;
import domein.interfacesDomein.ISessieKalender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DomeinController {
    private ObservableList<Sessie> sessieObservableList;
    //region Variabelen
    private SessieKalenderController sessieKalenderController;
    private Gebruiker gebruiker;
    //endregion

    //region Constructor
    public DomeinController() {
        sessieKalenderController = new SessieKalenderController();
        sessieObservableList = FXCollections.observableArrayList(sessieKalenderController.geefAlleSessies());
    }

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
                    s.setLokaal(sessieKalenderController.getLokalen().stream().filter(e -> e.getLokaalCode().equals(value)).findFirst().orElse(null));
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
        }*/
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
}

//endregion

//region Init

//endregion
/*
    //region Overzicht
    public List<String> geefOverzichtVerantwoordelijke(String gebruikersCode, boolean open) {
        //if(open) is voor als geruiker alleen geopende sessies wilt zien
        if(open) return pc.getSessies().stream().filter(s -> s.isGeopend() && s.getVerantwoordelijke().getGebruikersnaam().equals(gebruikersCode)).map(Sessie::toString_Overzicht).collect(Collectors.toList());
        return pc.getSessies().stream().filter(s -> !s.isGeopend() && s.getVerantwoordelijke().getGebruikersnaam().equals(gebruikersCode)).map(Sessie::toString_Overzicht).collect(Collectors.toList());
    }

    public List<String> geefOverzichtHoofdverantwoordelijke(boolean open) {
        if(open) return pc.getSessies().stream().filter(s -> s.isGeopend()).sorted(Comparator.comparing(Sessie::getStartSessie)).map(Sessie::toString_Overzicht).collect(Collectors.toList());
        return pc.getSessies().stream().filter(s -> !s.isGeopend()).sorted(Comparator.comparing(Sessie::getStartSessie)).map(Sessie::toString_Overzicht).collect(Collectors.toList());
    }

    public List<String> geefOverzichtAlleGebruikers() {
        return pc.getGebruikers().stream().map(Gebruiker::toString).collect(Collectors.toList());
    }
    //endregion

    //region Gebruiker
    public void voegGebruikerToe(Gebruiker gebruiker) {
        pc.beheerGebruiker("CREATE", gebruiker);
    }

    public String geefGebruiker(String gebruikersCode){
        pc.geefGebruikerMetCode(gebruikersCode);
        return null;
    }

    public String geefGebruikerProfiel(String gebruikersCode){
        return (pc.geefGebruikerMetCode(gebruikersCode)).getGebruikersprofielen().toString();
    }

    public void verwijderGebruiker(String gebruikersCode) {
        pc.beheerGebruiker("DELETE", pc.geefGebruikerMetCode(gebruikersCode));
    }
    //endregion

    //region Lokaal
    private Lokaal geefLokaalMetCode(String lokaalCode) {
        return pc.geefLokaalMetCode(lokaalCode);
    }

    public void voegLokaalToe(Lokaal lokaal) {
        pc.beheerLokaal("CREATE", lokaal);
    }
    //endregion

    //region Sessie

    /**
     * @param titel                 ==> titel van de sessie
     * @param startSessie           ==> vb. "2007-12-03T10:15:30"
     * @param eindeSessie           ==> vb. "2007-12-03T10:15:30"
     * @param lokaalCode            ==> de code van het lokaal
     * @param verantwoordelijkeCode ==> code van de veranwoordelijke
     *
    public void maakNieuweSessieAan(String titel, CharSequence startSessie, CharSequence eindeSessie, String lokaalCode, String verantwoordelijkeCode) {
        Sessie s = new Sessie(titel, LocalDateTime.parse(startSessie), LocalDateTime.parse(eindeSessie), pc.geefLokaalMetCode(lokaalCode), pc.geefGebruikerMetCode(verantwoordelijkeCode));
        pc.beheerSessie("CREATE", s);
    }

    /**
     * Voegt sessie toe
     * @param sessie ==> sessie object
     *
    public void voegSessieToe(Sessie sessie) {
        pc.beheerSessie("CREATE", sessie);
    }

    public void verwijderSessie(String SessieId) {
        Sessie s = pc.geefSessieMetId(SessieId);
        pc.beheerSessie("DELETE", s);
    }



    public String geefDetailVanSessie(String sessieId) {
        Sessie sessie = pc.geefSessieMetId(sessieId);
        return String.format("%s%n%s",sessie.toString(), sessie.isGeopend()?
                sessie.toString_OverzichtInschrijvingenGeopend()+sessie.toString_OverzichtFeedback()
                :sessie.toString_OverzichtInschrijvingenNietGeopend()+sessie.toString_OverzichtAankondigingen());
    }


    public boolean bestaatSessie(String sessieId){
        if(pc.geefSessieMetId(sessieId) == null) return false;
        return true;
    }

    public boolean isSessieOpen(String sessieId){
        return pc.geefSessieMetId(sessieId).isGeopend();
    }

    public int geefAantalAanwezigenSessie(String sessieId){
        return pc.geefSessieMetId(sessieId).aantalAanwezigenNaSessie();
    }

    //aantal sessies
    public int geefAantalSessies(){
        return pc.getSessies().size();
    }
    public int geefAantalSessies(String gebruikersCode){
        return (int)pc.getSessies().stream().filter(e -> e.getVerantwoordelijke().getGebruikersnaam().equals(gebruikersCode)).count();
    }
    public int geefAantalOpenSessies(){
        return (int)pc.getSessies().stream().map(Sessie::isGeopend).count();
    }
    public int geefAantalOpenSessies(String gebruikersCode){
        return (int)pc.getSessies().stream().filter(e -> e.getVerantwoordelijke().getGebruikersnaam().equals(gebruikersCode)).map(Sessie::isGeopend).count();
    }
    //Sessie feedback
    public List<String> geefAlleFeedbackVanSessie(String sessieId){
        Sessie sessie = pc.geefSessieMetId(sessieId);
        return sessie.getFeedbackSessie().stream().map(Feedback::toString).collect(Collectors.toList());
    }
    public void pasFeedbackAanVanSessie(String gekozenSessieNr, int feedBakcNr, String feedback){

    }
    public void verwijderFeedbackVanSessie(String sessieId, int feedBackNr){
        //Verwijder de feedback bij bepaalde sessie
    }
    public int geefAantalFeedbackObjVanSessie(String sessieId){
        Sessie sessie = pc.geefSessieMetId(sessieId);
        return sessie.getFeedbackSessie().size();
    }


 */
//endregion
