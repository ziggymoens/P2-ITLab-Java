package userinterface.aankondigingPlaatsen;

import domein.DomeinController;
import domein.interfacesDomein.ISessie;
import domein.interfacesDomein.ISessieKalender;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import userinterface.MAIN.MainScreenController;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class InfoSessieAankondigingController extends BorderPane {
    private ISessie sessie;
    private DomeinController domeinController;
    @FXML
    private TextField naamverantwoordelijke, titel, gastspreker, lokaal, start, eind, maxPlaatsen;

    @FXML
    private Button voegAankondigingToe;

    @FXML
    private TextArea tekstAankondiging;

    @FXML
    private ChoiceBox<ISessieKalender> choiceBoxSessie;

    @FXML
    private ListView<ISessie> listView;

    private ObservableList<ISessie> sessies;

    public InfoSessieAankondigingController(DomeinController domeinController, MainScreenController mainScreenController, ISessie sessie){
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoSessieAankondiging.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        this.sessie = sessie;
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
                        geefDetails(t1);
                    }
                });
            }
        });
        tekstAankondiging.setPromptText("Schrijf hier je aankondiging");
        choiceBoxSessie.setValue(choiceBoxSessie.getItems().get(0));
        listView.getSelectionModel().select(sessies.indexOf(sessie));
        mainScreenController.vulSchermIn(this);
    }

    private void geefDetails(ISessie sessie) {
        naamverantwoordelijke.setText(sessie.getVerantwoordelijke().getNaam());
        titel.setText(sessie.getTitel());
        gastspreker.setText(sessie.getNaamGastspreker());
        lokaal.setText(sessie.getLokaal().getLokaalCode());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        start.setText(sessie.getStartSessie().format(formatter));
        eind.setText(sessie.getEindeSessie().format(formatter));
        maxPlaatsen.setText("" + sessie.getMaximumAantalPlaatsen());
    }
}
