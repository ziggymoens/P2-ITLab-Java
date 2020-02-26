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
    private DomeinController domeinController;
    private MainScreenController mainScreenController;

    @FXML
    private TextField naamverantwoordelijke, titel, start, eind, plaatsen;

    @FXML
    private Button nieuw;

    @FXML
    private ListView<ISessie> listView;

    @FXML
    private ChoiceBox<ISessieKalender> choiceBoxSessie;

    private ObservableList<ISessie> sessies;

    private ISessie sessie;

    public AankondigingPlaatsenController(DomeinController domeinController, MainScreenController mainScreenController){
        this.domeinController = domeinController;
        this.mainScreenController = mainScreenController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AankondigingPlaatsen.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        choiceBoxSessie.setItems(FXCollections.observableArrayList(domeinController.getISessieKalenders()));
        choiceBoxSessie.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ISessieKalender>() {
            @Override
            public void changed(ObservableValue<? extends ISessieKalender> observableValue, ISessieKalender iSessieKalender, ISessieKalender t1) {
                sessies = FXCollections.observableArrayList(t1.getISessieList());
                listView.setItems(sessies);
                listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ISessie>() {
                    @Override
                    public void changed(ObservableValue<? extends ISessie> observableValue, ISessie iSessie, ISessie t1) {
                        sessie = t1;
                        geefDetails(t1);
                    }
                });
            }
        });
        choiceBoxSessie.setValue(choiceBoxSessie.getItems().get(0));
        nieuw.setOnAction(this::nieuw);
        mainScreenController.vulSchermIn(this);
    }

    private void geefDetails(ISessie sessie) {
        naamverantwoordelijke.setText(sessie.getVerantwoordelijke().getNaam());
        titel.setText(sessie.getTitel());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        start.setText(sessie.getStartSessie().format(formatter));
        eind.setText(sessie.getEindeSessie().format(formatter));
        plaatsen.setText(String.valueOf(sessie.isGeopend()?sessie.getAantalAanwezigen():sessie.getBeschikbarePlaatsen()));
    }

    private void nieuw(ActionEvent actionEvent){
        new InfoSessieAankondigingController(domeinController,mainScreenController, sessie);
    }
}