package userinterface.aankondigingPlaatsen;

import domein.DomeinController;
import domein.interfacesDomein.ISessie;
import domein.interfacesDomein.ISessieKalender;
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

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class AankondigingPlaatsenController extends BorderPane {
    //private DomeinController domeinController;

    @FXML
    private TextArea aankondigingTekst;

    @FXML
    private ChoiceBox herinneringKeuze;

    @FXML
    private Button voegToe;

    public AankondigingPlaatsenController(){
        //this.domeinController = domeinController;

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

    }


}
