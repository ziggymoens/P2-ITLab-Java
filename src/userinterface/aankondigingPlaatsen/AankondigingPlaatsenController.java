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

public class AankondigingPlaatsenController extends BorderPane {
    private DomeinController domeinController;
    private ISessie sessie;
    private Stage stage;
    @FXML
    private TextArea aankondigingTekst;

    @FXML
    private ChoiceBox herinneringKeuze;

    @FXML
    private Button voegToe;

    public AankondigingPlaatsenController(DomeinController domeinController, ISessie sessie){
        this.domeinController = domeinController;
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
        int keuze2 = 0;
        boolean automatischeHerinnering = false;
        switch (keuze){
            case "geen":
                keuze2 = 0;
                automatischeHerinnering = false;
                break;
            case "1 dag":
                keuze2 = 1;
                automatischeHerinnering = true;
                break;
            case "2 dagen":
                keuze2 = 2;
                automatischeHerinnering = true;
                break;
            case "3 dagen":
                keuze2 = 3;
                automatischeHerinnering = true;
                break;
            case "7 dagen":
                keuze2 = 7;
                automatischeHerinnering = true;
        }
        IGebruiker gebruiker = domeinController.geefIGebruiker();
        domeinController.addAankondigingSessie(sessie.getSessieId(), gebruiker.getGebruikersnaam(), aankondigingTekst.getText(), automatischeHerinnering, keuze2);
        stage.close();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}
