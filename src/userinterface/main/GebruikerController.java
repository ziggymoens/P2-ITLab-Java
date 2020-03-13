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
import org.mockito.internal.matchers.Null;

import java.io.IOException;

public class GebruikerController extends AnchorPane {
    private HoofdverantwoordelijkeController hoofdverantwoordelijkeController;
    private DomeinController domeinController;
    private boolean comboBoxTypeChanged, comboBoxStatusChanged;

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
    private ComboBox<Gebruikersprofielen> comboBoxTypeGebruiker;

    @FXML
    private ComboBox<Gebruikersstatus> comboBoxStatusGebruiker;

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
    private TableColumn<ISessie, String> titel, startSessie, aantalVrijePlaatsen, aantalInschrijvingen;
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
        //vulTableSessies();

        btnVerwijderen.setOnAction(this::verwijderen);
        btnFilterConfirm.setOnAction(this::filter);
        btnWijzigen.setOnAction(this::wijzigGebruiker);
        toevoegenGebruiker.setOnAction(this::maakNieuweGebruiker);

        comboBoxStatus.setItems(FXCollections.observableArrayList(Gebruikersstatus.values()));
        comboBoxType.setItems(FXCollections.observableArrayList(Gebruikersprofielen.values()));

        zoek();
    }

    private void filter(ActionEvent actionEvent) {
        Gebruikersstatus comboStatus = comboBoxStatus.getSelectionModel().getSelectedItem();
        Gebruikersprofielen comboType = comboBoxType.getSelectionModel().getSelectedItem();
        if(comboStatus == null && comboType == null){
            vulTableGebruikers(FXCollections.observableArrayList(domeinController.geefAlleIGebruikers()));
        }else if(comboType == null && comboStatus != null){
            vulTableGebruikers(FXCollections.observableArrayList(domeinController.geefIGebruikersOpStatus(comboStatus.toString())));
        }else if(comboStatus == null && comboType != null){
            vulTableGebruikers(FXCollections.observableArrayList(domeinController.geefIGebruikersOpType(comboType.toString())));
        }else{
            vulTableGebruikers(FXCollections.observableArrayList(domeinController.geefIGebruikersOpTypeEnStatus(comboType.toString(), comboStatus.toString())));
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

    private void wijzigGebruiker(ActionEvent actionEvent){
        txtFieldGebruiker.setEditable(true);
        txtFieldGebruikersnaam.setEditable(true);
        comboBoxTypeGebruiker.setItems(FXCollections.observableArrayList(Gebruikersprofielen.values()));
        comboBoxStatusGebruiker.setItems(FXCollections.observableArrayList(Gebruikersstatus.values()));
        btnWijzigen.setText("Opslaan");

        //btnWijzigen.setOnAction(this::UpdateGebruiker);
    }

    private void UpdateGebruiker(ActionEvent actionEvent) { //UPDATE?
        domeinController.verwijderGebruiker(tableViewGebruiker.getSelectionModel().getSelectedItem());
        domeinController.maakNieuweGebruiker(txtFieldGebruiker.getText(), txtFieldGebruikersnaam.getText(), comboBoxTypeGebruiker.getSelectionModel().getSelectedItem(), comboBoxStatusGebruiker.getSelectionModel().getSelectedItem());
        vulTableGebruikers(FXCollections.observableArrayList(domeinController.geefAlleIGebruikers()));
    }

    private void maakNieuweGebruiker(ActionEvent actionEvent){
        btnWijzigen.setText("Opslaan");
        clearDetails();
        txtFieldGebruiker.setEditable(true);
        txtFieldGebruikersnaam.setEditable(true);
        comboBoxTypeGebruiker.setItems(FXCollections.observableArrayList(Gebruikersprofielen.values()));
        comboBoxStatusGebruiker.setItems(FXCollections.observableArrayList(Gebruikersstatus.values()));
        domeinController.maakNieuweGebruiker(txtFieldGebruiker.getText(), txtFieldGebruikersnaam.getText(), comboBoxTypeGebruiker.getSelectionModel().getSelectedItem(), comboBoxStatusGebruiker.getSelectionModel().getSelectedItem());
        refreshTable();
    }

    private void vulTableSessies(){
        tableViewSessiesGebruikers.getColumns().clear();

        titel.setCellValueFactory(new PropertyValueFactory<>("Titel"));
        startSessie.setCellValueFactory(new PropertyValueFactory<>("Start sessie"));
        aantalInschrijvingen.setCellValueFactory(new PropertyValueFactory<>("Aantal inschrijvingen"));
        aantalVrijePlaatsen.setCellValueFactory(new PropertyValueFactory<>("Aantal vrije plaatsen"));

        tableViewSessiesGebruikers.setItems(FXCollections.observableArrayList(domeinController.geefISessiesHuidigeKalender()));
        tableViewSessiesGebruikers.getColumns().addAll(titel, startSessie, aantalInschrijvingen, aantalVrijePlaatsen);
        if (!tableViewSessiesGebruikers.getItems().isEmpty() || tableViewSessiesGebruikers.getItems() != null) {
            tableViewSessiesGebruikers.getSelectionModel().select(0);
            domeinController.setHuidigeISessie(domeinController.geefISessiesHuidigeKalender().get(0));
        }
    }

    public void vulDetails(Gebruiker gebruiker){
        gebruiker = (Gebruiker) tableViewGebruiker.getSelectionModel().getSelectedItem();
        txtFieldGebruiker.setText(gebruiker.getNaam());
        txtFieldGebruikersnaam.setText(gebruiker.getGebruikersnaam());
        comboBoxStatusGebruiker.setItems(FXCollections.observableArrayList(gebruiker.getStatus()));
        comboBoxStatusGebruiker.getSelectionModel().selectFirst();
        comboBoxTypeGebruiker.setItems(FXCollections.observableArrayList(gebruiker.getGebruikersprofiel()));
        comboBoxTypeGebruiker.getSelectionModel().selectFirst();
    }

    public void clearTableGebruikers(){
        tableViewGebruiker.getColumns().clear();
    }

    public void clearDetails(){
        txtFieldGebruiker.clear();
        txtFieldGebruikersnaam.clear();
        txtFieldStatus.clear();
        txtFieldType.clear();
    }

    public void refreshTable() {
        clearTableGebruikers();
        vulTableGebruikers(FXCollections.observableArrayList(domeinController.geefAlleIGebruikers()));
    }
}

