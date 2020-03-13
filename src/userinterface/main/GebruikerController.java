package userinterface.main;

import domein.Gebruiker;
import domein.controllers.DomeinController;
import domein.controllers.HoofdverantwoordelijkeController;
import domein.enums.Gebruikersprofielen;
import domein.enums.Gebruikersstatus;
import domein.interfacesDomein.IGebruiker;
import domein.interfacesDomein.ISessie;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
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
    private Button btnFilterConfirm;

    @FXML
    private ComboBox<Gebruikersprofielen> comboBoxType;

    @FXML
    private ComboBox<Gebruikersstatus> comboBoxStatus;

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
    private TableView<ISessie> tableViewSessiesGebruikers;

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

    public GebruikerController(DomeinController domeinController){
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Gebruiker.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        vulTableGebruikers(FXCollections.observableArrayList(domeinController.geefAlleIGebruikers()));

        btnVerwijderen.setOnAction(this::verwijderen);
        btnFilterConfirm.setOnAction(this::filterStatusEnType);

        comboBoxStatus.setItems(FXCollections.observableArrayList(Gebruikersstatus.values()));
        comboBoxType.setItems(FXCollections.observableArrayList(Gebruikersprofielen.values()));

        zoek();
    }

    private void filterStatusEnType(ActionEvent actionEvent) {
        String status = comboBoxStatus.getValue().toString();
        String type = comboBoxType.getValue().toString();
        if(status == null && !type.isBlank()){
            status = "";
        }
        if(type == null){
            type = "";
        }

        if(!status.isBlank() && !type.isBlank()){
            vulTableGebruikers(FXCollections.observableArrayList(domeinController.geefIGebruikersOpTypeEnStatus(type, status)));
        }else if(!status.isBlank()){
            vulTableGebruikers(FXCollections.observableArrayList(domeinController.geefIGebruikersOpStatus(status)));
        }else if(!type.isBlank()){
            vulTableGebruikers(FXCollections.observableArrayList(domeinController.geefIGebruikersOpType(type)));
        }else{
            vulTableGebruikers(FXCollections.observableArrayList(domeinController.geefAlleIGebruikers()));
        }
    }

    private void zoek(){
        ObservableList data =  tableViewGebruiker.getItems();
        txtFieldSearchBar.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                tableViewGebruiker.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<IGebruiker> subentries = FXCollections.observableArrayList();

            long count = tableViewGebruiker.getColumns().stream().count();
            for (int i = 0; i < tableViewGebruiker.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + tableViewGebruiker.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(tableViewGebruiker.getItems().get(i));
                        break;
                    }
                }
            }
            tableViewGebruiker.setItems(subentries);
        });
    }

    private void verwijderen(ActionEvent actionEvent) {
        Gebruiker geb = (Gebruiker) tableViewGebruiker.getSelectionModel().getSelectedItem();
        domeinController.verwijderGebruiker(geb);
        refreshTable();
    }

    private void vulTableGebruikers(ObservableList<IGebruiker> observableArrayList) {
        clearTableGebruikers();
        TVnaam.setCellValueFactory(new PropertyValueFactory<>("naam"));
        TVgebruikersnaamChamilo.setCellValueFactory(new PropertyValueFactory<>("gebruikersnaam"));
        TVtype.setCellValueFactory(new PropertyValueFactory<>("gebruikersprofiel"));
        TVstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableViewGebruiker.setItems(FXCollections.observableArrayList(observableArrayList));
        tableViewGebruiker.getColumns().addAll(TVnaam, TVgebruikersnaamChamilo, TVtype, TVstatus);

        if(tableViewGebruiker.getSelectionModel().getSelectedItem() != null){
            tableViewGebruiker.getSelectionModel().select(0);
            vulDetails((Gebruiker) tableViewGebruiker.getSelectionModel().getSelectedItem());
        }

        tableViewGebruiker.getSelectionModel().selectedItemProperty().addListener((observableValue, gebruiker, t1) -> {
            vulDetails((Gebruiker) t1);
        });
    }

    private void vulTableSessies(){

    }

    public void vulDetails(Gebruiker geb){
        geb = (Gebruiker) tableViewGebruiker.getSelectionModel().getSelectedItem();
        txtFieldGebruiker.setText(geb.getNaam());
        txtFieldGebruikersnaam.setText(geb.getGebruikersnaam());
        txtFieldStatus.setText(geb.getStatus().toString());
        txtFieldType.setText(geb.getGebruikersprofiel().toString());
    }

    public void clearTableGebruikers(){
        tableViewGebruiker.getColumns().clear();
    }


    public void refreshTable() {
        clearTableGebruikers();
        vulTableGebruikers(FXCollections.observableArrayList(domeinController.geefAlleIGebruikers()));
    }
}

