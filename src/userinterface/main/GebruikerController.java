package userinterface.main;

import domein.SessieKalender;
import domein.controllers.DomeinController;
import domein.controllers.HoofdverantwoordelijkeController;
import domein.interfacesDomein.IGebruiker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class GebruikerController extends AnchorPane {
    private HoofdverantwoordelijkeController hoofdverantwoordelijkeController;
    private DomeinController domeinController;

    @FXML
    private Pane pButtonBar;

    @FXML
    private Button btnSessie;

    @FXML
    private Button btnGebruiker;

    @FXML
    private Button btnSessiekalender;

    @FXML
    private Button btnIngelogd;

    @FXML
    private ComboBox<?> comboBoxType;

    @FXML
    private ComboBox<?> comboBoxStatus;

    @FXML
    private Button toevoegenGebruiker;

    @FXML
    private TextField txtFieldSearchBar;

    @FXML
    private TableView<IGebruiker> tableViewGebruiker;

    @FXML
    private TableColumn<IGebruiker, String> TVnaam, TVgebruikersnaamChamilo, TVtype, TVstatus;

    @FXML
    private TextField txtFieldGebruiker;

    @FXML
    private Label lblErrorNaam;

    @FXML
    private TextField txtFieldGebruikersnaam;

    @FXML
    private TextField txtFieldType;

    @FXML
    private Label lblErrorTypeGebruiker;

    @FXML
    private TextField txtFieldStatus;

    @FXML
    private Label lblErrorStatusGebruiker;

    @FXML
    private Button btnWijzigen;

    @FXML
    private Button btnVerwijderen;

    @FXML
    private Label lblMessage;

    @FXML
    private VBox vBoxRechts;

    @FXML
    private TableView<?> tableViewSessiesGebruikers;

    @FXML
    private HBox hBoxOnderaan;

    @FXML
    private VBox vBoxOnderLinks;

    @FXML
    private ComboBox<?> choiceBox1Onder;

    @FXML
    private ComboBox<?> choiceBox2Onder;

    @FXML
    private ComboBox<?> choiceBoxOnder3;

    @FXML
    private ComboBox<?> choiceBox4Onder;

    @FXML
    private TableView<?> tableViewOnderaan;

    @FXML
    private Button btnKiezen;

    public GebruikerController(HoofdverantwoordelijkeController hoofdverantwoordelijkeController){
        this.hoofdverantwoordelijkeController = hoofdverantwoordelijkeController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Gebruiker.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
//        System.out.println(hoofdverantwoordelijkeController.geefIGebruikers());
        //vulTable(FXCollections.observableArrayList(hoofdverantwoordelijkeController.geefIGebruikers()));
    }

    private void vulTable(ObservableList<IGebruiker> observableArrayList) {
        tableViewGebruiker.getColumns().clear();
        TVnaam.setCellValueFactory(new PropertyValueFactory<>("naam"));
        TVgebruikersnaamChamilo.setCellValueFactory(new PropertyValueFactory<>("gebruikersnaam"));
        TVtype.setCellValueFactory(new PropertyValueFactory<>("gebruikersprofiel"));
        TVstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableViewGebruiker.setItems(FXCollections.observableArrayList(observableArrayList));
        tableViewGebruiker.getColumns().addAll(TVnaam, TVgebruikersnaamChamilo, TVtype, TVstatus);
        if(tableViewGebruiker.getSelectionModel() != null){
            tableViewGebruiker.getSelectionModel().select(0);
        }
    }
}
