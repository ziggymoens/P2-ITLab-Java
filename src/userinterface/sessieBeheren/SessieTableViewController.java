package userinterface.sessieBeheren;

import domein.DomeinController;
import domein.interfacesDomein.ISessie;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import userinterface.MAIN.MainScreenController;

import java.io.IOException;

public class SessieTableViewController extends BorderPane {
    private DomeinController domeinController;
    private ObservableList<ISessie> sessies;
    private MainScreenController mainScreenController;

    @FXML
    private Button nieuweSessie, verwijderSessie, bewerkSessie, btnzoek;
    @FXML private TableView table;
    @FXML private TableColumn<ISessie, String> titel;
    @FXML private TableColumn<ISessie, String> startSessie;
    @FXML private TableColumn<ISessie, String> eindSessie;
    @FXML private TableColumn<ISessie, String> lastColumn;
    @FXML
    private ChoiceBox<String> choiceBoxKalenderJaar;
    @FXML
    private CheckBox nietGeopend, geopend, open, aalst, gent;
    @FXML
    private TextField zoeken;

    public SessieTableViewController(DomeinController domeinController, MainScreenController mainScreenController) {
        this.domeinController = domeinController;
        this.mainScreenController = mainScreenController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SessieTableView.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        vulTable();
        observable();
        nieuweSessie.setOnAction(this :: nieuweSessie);
        verwijderSessie.setOnAction(this::verwijderSessie);
        bewerkSessie.setOnAction(this::bewerkSessie);
        btnzoek.setOnAction(this::zoek);

    }

    public void vulTable(){
        table.getColumns().clear();
        titel.setCellValueFactory(new PropertyValueFactory<>("titel"));
        startSessie.setCellValueFactory(new PropertyValueFactory<>("startSessie"));
        eindSessie.setCellValueFactory(new PropertyValueFactory<>("eindeSessie"));
        lastColumn.setCellValueFactory(new PropertyValueFactory<>("maximumAantalPlaatsen"));
        table.setItems(domeinController.geefObservableListISessiesHuidigeGebruiker());
        table.getColumns().addAll(titel, startSessie, eindSessie, lastColumn);
    }

    private void geefDetails(ISessie sessie) {
        /*
        naamverantwoordelijke.setText(sessie.getVerantwoordelijke().getNaam());
        titel.setText(sessie.getTitel());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        start.setText(sessie.getStartSessie().format(formatter));
        eind.setText(sessie.getEindeSessie().format(formatter));
        plaatsen.setText(String.valueOf(sessie.isGeopend()?sessie.getAantalAanwezigen():sessie.getBeschikbarePlaatsen()));
   */
    }

    private void zoek(ActionEvent actionEvent) {
    }

    private void bewerkSessie(ActionEvent actionEvent) {
        Tab tab = new Tab("Bewerk Sessie");
        tab.setClosable(true);
        tab.setContent(new SessieBewerkenController(domeinController.geefHuidigeISessie(), domeinController, this));
        mainScreenController.addTab(tab);
    }

    private void nieuweSessie(ActionEvent actionEvent) {

    }

    private void verwijderSessie(ActionEvent actionEvent) {
    }


    private void observable() {

/*        choiceBoxKalenderJaar.setItems(FXCollections.observableArrayList(domeinController.geefAcademiejaren()));
        choiceBoxKalenderJaar.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                sessies = FXCollections.observableArrayList(domeinController.geefISessiesAcademiejaar(Integer.valueOf(t1)));*/
                table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ISessie>() {
                    @Override
                    public void changed(ObservableValue<? extends ISessie> observableValue, ISessie iSessie, ISessie t1) {
                        domeinController.setHuidigeISessie(t1);
                    }
                });
/*            }
        });*/
        //choiceBoxKalenderJaar.setValue(choiceBoxKalenderJaar.getItems().get(0));

    }
}
