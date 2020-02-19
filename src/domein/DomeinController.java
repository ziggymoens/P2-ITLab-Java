package domein;

import persistentie.PersistentieController;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DomeinController {
    //region Variabelen
    private PersistentieController pc;
    //endregion

    //region Constructor
    public DomeinController() {
        initData();
    }
    //endregion

    //region Init
    private void initData() {
        pc = new PersistentieController();
    }
    //endregion

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
     */
    public void maakNieuweSessieAan(String titel, CharSequence startSessie, CharSequence eindeSessie, String lokaalCode, String verantwoordelijkeCode) {
        Sessie s = new Sessie(titel, LocalDateTime.parse(startSessie), LocalDateTime.parse(eindeSessie), pc.geefLokaalMetCode(lokaalCode), pc.geefGebruikerMetCode(verantwoordelijkeCode));
        pc.beheerSessie("CREATE", s);
    }

    /**
     * Voegt sessie toe
     * @param sessie ==> sessie object
     */
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

    //endregion
}
