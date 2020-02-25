package userinterface.sessieBeheren;

import domein.DomeinController;
import domein.interfacesDomein.ISessie;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class SessieBeherenController extends BorderPane {
    private DomeinController domeinController;
    @FXML
    private TextField naamverantwoordelijke, titel, start, eind, plaatsen;
    @FXML
    private Button meer, bewerken, verwijderen, nieuw;
    @FXML
    private ListView<ISessie> listView;

    private ObservableList<ISessie> sessies;

    private ISessie sessie;

    public SessieBeherenController(DomeinController domeinController) {
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SessieBeheren.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        sessies = domeinController.getSessieObservableList();
        listView.setItems(sessies);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ISessie>() {

            @Override
            public void changed(ObservableValue<? extends ISessie> observableValue, ISessie iSessie, ISessie t1) {
                sessie = t1;
                geefDetails(t1);
            }
        });
        meer.setOnAction(this::meer);
        bewerken.setOnAction(this::bewerken);
        verwijderen.setOnAction(this::verwijderen);
        nieuw.setOnAction(this::nieuw);
    }

    private void geefDetails(ISessie sessie) {
        naamverantwoordelijke.setText(sessie.getVerantwoordelijke().getNaam());
        titel.setText(sessie.getTitel());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        start.setText(sessie.getStartSessie().format(formatter));
        eind.setText(sessie.getEindeSessie().format(formatter));
        plaatsen.setText(String.valueOf(sessie.isGeopend()?sessie.getAantalAanwezigen():sessie.getBeschikbarePlaatsen()));
    }

    private void nieuw(ActionEvent actionEvent) {

    }

    private void verwijderen(ActionEvent actionEvent) {

    }

    private void bewerken(ActionEvent actionEvent) {


    }

    private void meer(ActionEvent actionEvent) {
        Scene scene = new Scene(new InfoSessieController(sessie));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}