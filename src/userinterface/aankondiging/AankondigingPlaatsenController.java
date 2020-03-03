package userinterface.aankondiging;

import domein.DomeinController;
import domein.interfacesDomein.IGebruiker;
import domein.interfacesDomein.ISessie;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import userinterface.main.MainScreenController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AankondigingPlaatsenController extends AnchorPane {
    private DomeinController domeinController;
    private MainScreenController mainScreenController;
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

    public AankondigingPlaatsenController(DomeinController domeinController, MainScreenController mainScreenController, ISessie sessie){
        this.domeinController = domeinController;
        this.mainScreenController = mainScreenController;
        this.sessie = sessie;
        startFXML();
        sessiesInit();
        gebruikersInit();
        herinneringKeuze.setVisible(false);
        automatischeHerinnering.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    herinneringKeuze.setVisible(true);
                } else {
                    herinneringKeuze.setVisible(false);
                }
            }
        });
        aankondigingTekst.setPromptText("Schrijf hier je aankondiging");
        voegToe.setOnAction(this::voegAankondigingToe);
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
        if(domeinController.geefIGebruiker().getGebruikersprofiel().toString().equals("HOOFDVERANTWOORDELIJKE"))
            sessieList.setItems(FXCollections.observableList(domeinController.geefISessiesHuidigeKalender()));
        if(domeinController.geefIGebruiker().getGebruikersprofiel().toString().equals("VERANTWOORDELIJKE"))
            sessieList.setItems(FXCollections.observableList(domeinController.geefISessiesVanGebruiker()));
        sessieList.setValue(sessie);
    }

    public void gebruikersInit(){
        if(domeinController.geefIGebruiker().getGebruikersprofiel().toString().equals("HOOFDVERANTWOORDELIJKE")){
            gebruikersList.setItems(FXCollections.observableList(domeinController.geefIGebruikers()));
            gebruikersList.setValue(domeinController.geefIGebruiker());
        }

        if(domeinController.geefIGebruiker().getGebruikersprofiel().toString().equals("VERANTWOORDELIJKE")){
            gebruikersList.setItems(FXCollections.observableList(domeinController.geefIGebruikers()));
            gebruikersList.setValue(domeinController.geefIGebruiker());
            gebruikersList.setDisable(true);
        }
    }

    public void voegAankondigingToe(ActionEvent actionEvent){
        String keuze = herinneringKeuze.getSelectionModel().selectedItemProperty().getValue().toString();
        boolean herinnering = false;
        Map<String, Integer> herinneringsMap = new HashMap<>();
        herinneringsMap.put("1 dag", 1);
        herinneringsMap.put("2 dagen", 2);
        herinneringsMap.put("3 dagen", 3);
        herinneringsMap.put("7 dagen", 7);
        int dagenHerinnering = herinneringsMap.get(keuze);
        if(automatischeHerinnering.isSelected())
            herinnering = true;
        domeinController.addAankondigingSessie(sessieList.getValue().getSessieId(), gebruikersList.getValue().getGebruikersnaam(), aankondigingTekst.getText(), herinnering, dagenHerinnering);
    }
}
