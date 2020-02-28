package userinterface.sessieBeheren;

import domein.DomeinController;
import domein.interfacesDomein.ISessie;
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
    @FXML
    private Button nieuweSessie, verwijderSessie;
    @FXML
    private TableView tableView;
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

    private ObservableList<ISessie> sessies;

    private MainScreenController mainScreenController;

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
        //listView();
        vulTable();
        nieuweSessie.setOnAction(this :: nieuweSessie);
        verwijderSessie.setOnAction(this::verwijderSessie);
    }

    public void vulTable(){
        titel.setCellValueFactory(new PropertyValueFactory<ISessie, String>("titel"));
        startSessie.setCellValueFactory(new PropertyValueFactory<ISessie, String>("start sessie"));
        eindSessie.setCellValueFactory(new PropertyValueFactory<ISessie, String>("eind sessie"));
        lastColumn.setCellValueFactory(new PropertyValueFactory<ISessie, String>("vrije plaatsen"));

        tableView.getItems().setAll(domeinController.geefObservableListISessiesHuidigeGebruiker());
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

    private void nieuweSessie(ActionEvent actionEvent) {

    }

    private void verwijderSessie(ActionEvent actionEvent) {
    }

    /*private void listView (){
        choiceBoxSessie.setItems(FXCollections.observableArrayList(domeinController.geefAcademiejaren()));
        choiceBoxSessie.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                sessies = FXCollections.observableArrayList(domeinController.geefISessiesAcademiejaar(Integer.valueOf(t1)));
                listView.setItems(sessies);
                listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ISessie>() {
                    @Override
                    public void changed(ObservableValue<? extends ISessie> observableValue, ISessie iSessie, ISessie t1) {
                        domeinController.setHuidigeSessie(t1);
                        geefDetails(t1);
                    }
                });
            }
        });
        choiceBoxSessie.setValue(choiceBoxSessie.getItems().get(0));
    }*/
}
