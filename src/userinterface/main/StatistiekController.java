package userinterface.main;

import domein.Statistiek;
import domein.controllers.DomeinController;
import domein.interfacesDomein.IStatistiek;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.*;

public class StatistiekController extends AnchorPane {

    private IStatistiek stat;

    @FXML
    private TextArea csvSessies;

    @FXML
    private ChoiceBox<Integer> sAantal;

    @FXML
    private ChoiceBox<String> sType;

    @FXML
    private TextArea csvGebruikers;

    @FXML
    private ChoiceBox<Integer> gAantal;

    @FXML
    private ChoiceBox<String> gType;

    @FXML
    private TextArea csvLokalen;

    @FXML
    private ChoiceBox<String> gTopAantal;

    @FXML
    private TextArea csvTopGebruikers;

    @FXML
    private ChoiceBox<Integer> lAantal;

    public StatistiekController(DomeinController domeinController) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Statistiek.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        stat = domeinController.geefStatistiek();
        ObservableList<Integer> is = FXCollections.observableArrayList(1, 2, 3, 5, 10, 15, 20, 25, 50, 100);
        ObservableList<String> t = FXCollections.observableArrayList("aanwezigheid","aankondiging", "feedback", "inschrijving", "media");
        ObservableList<String> p = FXCollections.observableArrayList("gebruiker", "verantwoordelijke", "hoofdverantwoordelijke");
        sAantal.setItems(is);
        sAantal.getSelectionModel().selectedItemProperty().addListener((observableValue, integer, t1) -> {
            vulCsvSessies();
        });
        sAantal.getSelectionModel().select(0);
        gAantal.setItems(is);
        gAantal.getSelectionModel().selectedItemProperty().addListener((observableValue, integer, t1) -> {
            vulCsvGebruikers();
        });
        gAantal.getSelectionModel().select(0);
        lAantal.setItems(is);
        lAantal.getSelectionModel().selectedItemProperty().addListener((observableValue, integer, t1) -> {
            vulCsvLokalen();
        });
        lAantal.getSelectionModel().select(0);
        gTopAantal.setItems(p);
        gTopAantal.getSelectionModel().selectedItemProperty().addListener((observableValue, integer, t1) -> {
            vulCsvTopGebruikers();
        });
        gTopAantal.getSelectionModel().select(0);
        sType.setItems(t);
        sType.getSelectionModel().selectedItemProperty().addListener((observableValue, integer, t1) -> {
            vulCsvSessies();
        });
        sType.getSelectionModel().select(0);
        gType.setItems(t);
        gType.getSelectionModel().selectedItemProperty().addListener((observableValue, integer, t1) -> {
            vulCsvGebruikers();
        });
        gType.getSelectionModel().select(0);
    }

    public void vulCsvSessies(){
        csvSessies.setText(stat.geefTopSessieTabel(sType.getSelectionModel().getSelectedItem(), sAantal.getSelectionModel().getSelectedItem()));
    }
    public void vulCsvGebruikers(){
        csvGebruikers.setText(stat.geefTopGebruikerTabel(gType.getSelectionModel().getSelectedItem(), gAantal.getSelectionModel().getSelectedItem()));
    }
    public void vulCsvLokalen(){
        csvLokalen.setText(stat.overzichtLokalen(lAantal.getSelectionModel().getSelectedItem()));
    }
    public void vulCsvTopGebruikers(){
        csvTopGebruikers.setText(stat.overzichtTopGebruikers(gTopAantal.getSelectionModel().getSelectedItem()));
    }


}
