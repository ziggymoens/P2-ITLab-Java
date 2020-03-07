package userinterface.ongebruikt.aankondiging;

import domein.controllers.DomeinController;
import domein.interfacesDomein.IAankondiging;
import domein.interfacesDomein.IGebruiker;
import domein.interfacesDomein.ISessie;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import userinterface.ongebruikt.main.MainScreenController;

import java.io.IOException;

public class AankondigingBewerkenController extends AnchorPane {
    private DomeinController domeinController;
    private MainScreenController mainScreenController;
    private IAankondiging aankondiging;
    private ISessie sessie;
    @FXML
    private TextArea aankondigingTekst;

    @FXML
    private CheckBox automatischeHerinnering;

    @FXML
    private ChoiceBox herinneringKeuze;
    @FXML
    private ChoiceBox<ISessie> sessieList;
    @FXML
    private ChoiceBox<IGebruiker> gebruikersList;

    @FXML
    private Button voegToe;

    public AankondigingBewerkenController(DomeinController domeinController, MainScreenController mainScreenController, ISessie sessie, IAankondiging aankondiging){
        this.domeinController = domeinController;
        this.mainScreenController = mainScreenController;
        this.aankondiging = aankondiging;
        this.sessie = sessie;
        startFXML();
        sessiesInit();
        gebruikersInit();
        aankondigingTekst.setText(aankondiging.getInhoud());
        if(aankondiging.isAutomatischeHerinnering()){
            automatischeHerinnering.setSelected(true);
            //herinneringKeuze.setValue(aankondiging.getIHerinnering().getDagenVoorafInt());
        }
    }


    public void startFXML(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AankondigingPlaatsen.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void sessiesInit(){
        sessieList.setItems(FXCollections.observableList(domeinController.geefISessiesHuidigeKalender()));
        sessieList.setValue(sessie);
        sessieList.setDisable(true);
    }

    public void gebruikersInit(){
        gebruikersList.setItems(FXCollections.observableList(domeinController.geefIGebruikers()));
        gebruikersList.setValue(sessie.getVerantwoordelijke());
        gebruikersList.setDisable(true);
    }
}
