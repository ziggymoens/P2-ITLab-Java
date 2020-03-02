package userinterface.aankondigingPlaatsen;

import domein.DomeinController;
import domein.interfacesDomein.IGebruiker;
import domein.interfacesDomein.ISessie;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import userinterface.MAIN.MainScreenController;
import userinterface.sessieBeheren.InfoSessieController;

import javax.swing.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class AankondigingPlaatsenController extends BorderPane {
    private DomeinController domeinController;
    private MainScreenController mainScreenController;
    private ISessie sessie;

    @FXML
    private TextArea aankondigingTekst;

    @FXML
    private ChoiceBox herinneringKeuze;

    @FXML
    private Button voegToe;

    public AankondigingPlaatsenController(DomeinController domeinController, MainScreenController mainScreenController, ISessie sessie){
        this.domeinController = domeinController;
        this.mainScreenController = mainScreenController;
        this.sessie = sessie;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AankondigingPlaatsen.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        aankondigingTekst.setPromptText("Schrijf hier je aankondiging");
        voegToe.setOnAction(this::voegAankondigingToe);
    }

    public void voegAankondigingToe(ActionEvent actionEvent){
        String keuze = herinneringKeuze.getSelectionModel().selectedItemProperty().getValue().toString();
        boolean automatischeHerinnering = false;
        Map<String, Integer> herinneringsMap = new HashMap<>();
        herinneringsMap.put("geen", 0);
        herinneringsMap.put("1 dag", 1);
        herinneringsMap.put("2 dagen", 2);
        herinneringsMap.put("3 dagen", 3);
        herinneringsMap.put("7 dagen", 7);
        int dagenHerinnering = herinneringsMap.get(keuze);
        if(herinneringsMap.get(keuze) > 0)
            automatischeHerinnering = true;

        IGebruiker gebruiker = domeinController.geefIGebruiker();
        domeinController.addAankondigingSessie(sessie.getSessieId(), gebruiker.getGebruikersnaam(), aankondigingTekst.getText(), automatischeHerinnering, dagenHerinnering);
    }
}
