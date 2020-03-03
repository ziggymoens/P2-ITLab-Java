package userinterface.aankondiging;

import domein.Aankondiging;
import domein.DomeinController;
import domein.interfacesDomein.IAankondiging;
import domein.interfacesDomein.IGebruiker;
import domein.interfacesDomein.ISessie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import userinterface.main.MainScreenController;

import java.io.IOException;

public class AankondigingBewerkenController extends AnchorPane {
    private DomeinController domeinController;
    private MainScreenController mainScreenController;
    private IAankondiging aankondiging;
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
        startFXML();
        aankondigingTekst.setText(aankondiging.getInhoud());
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
}
