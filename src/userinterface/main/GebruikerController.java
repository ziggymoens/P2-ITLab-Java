package userinterface.main;

import domein.gebruiker.Gebruiker;
import domein.controllers.DomeinController;
import domein.controllers.HoofdverantwoordelijkeStrategy;
import domein.enums.Gebruikersprofiel;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class GebruikerController extends AnchorPane {
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
    private ComboBox<Gebruikersprofiel> comboBoxType;

    @FXML
    private ComboBox<Gebruikersstatus> comboBoxStatus;

    @FXML
    private ComboBox<String> comboBoxTypeGebruiker;

    @FXML
    private ComboBox<String> comboBoxStatusGebruiker;

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
    private Button btnWijzigen, btnOpslaan;

    @FXML
    private Button btnVerwijderen;

    @FXML
    private Label lblMessage;

    @FXML
    private VBox vBoxRechts;

    @FXML
    private TableView<ISessie> tableViewSessiesGebruikers;

    @FXML
    private TableColumn<ISessie, String> titel, startSessie;
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
        btnFilterConfirm.setOnAction(this::filter);
        btnWijzigen.setOnAction(this::wijzigGebruiker);
        toevoegenGebruiker.setOnAction(this::maakNieuweGebruiker);

        comboBoxStatus.setItems(FXCollections.observableArrayList(Gebruikersstatus.values()));
        comboBoxType.setItems(FXCollections.observableArrayList(Gebruikersprofiel.values()));

        zoek();
    }

    private void filter(ActionEvent actionEvent) {
        Gebruikersstatus comboStatus = comboBoxStatus.getSelectionModel().getSelectedItem();
        Gebruikersprofiel comboType = comboBoxType.getSelectionModel().getSelectedItem();
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
            vulDetails(tableViewGebruiker.getSelectionModel().getSelectedItem());
            vulTableSessies((Gebruiker) tableViewGebruiker.getSelectionModel().getSelectedItem());
        }

        tableViewGebruiker.getSelectionModel().selectedItemProperty().addListener((observableValue, gebruiker, t1) -> {
            if(t1 != null) {
                vulDetails(t1);
                vulTableSessies((Gebruiker) t1);
            }
        });
    }

    private void wijzigGebruiker(ActionEvent actionEvent){
        btnWijzigen.setVisible(false);
        System.out.println("button wijzigen invisible");
        btnOpslaan.setVisible(true);
        System.out.println("button opslaan visible");
        txtFieldGebruiker.setEditable(true);
        txtFieldGebruikersnaam.setEditable(true);
        ObservableList<String> type = FXCollections.observableArrayList("gebruiker", "verantwoordelijke", "hoofdverantwoordelijke");
        ObservableList<String> status = FXCollections.observableArrayList("actief", "niet actief", "geblokkeerd");
        comboBoxTypeGebruiker.setItems(type);
        comboBoxTypeGebruiker.getSelectionModel().selectFirst();
        comboBoxStatusGebruiker.setItems(status);
        comboBoxStatusGebruiker.getSelectionModel().selectFirst();
        btnOpslaan.setOnAction(this::updateGebruiker);
    }

    public void updateGebruiker(ActionEvent actionEvent){
        domeinController.updateGebruiker(txtFieldGebruiker.getText(), txtFieldGebruikersnaam.getText(),
                comboBoxStatusGebruiker.getSelectionModel().getSelectedItem(),
                comboBoxTypeGebruiker.getSelectionModel().getSelectedItem());
        btnOpslaan.setVisible(false);
        btnWijzigen.setVisible(true);
        refreshTable();
    }

    private void maakNieuweGebruiker(ActionEvent actionEvent){
        btnWijzigen.setVisible(false);
        btnOpslaan.setVisible(true);
        clearDetails();
        txtFieldGebruiker.setEditable(true);
        txtFieldGebruikersnaam.setEditable(true);
        comboBoxTypeGebruiker.setItems(FXCollections.observableArrayList(Arrays.stream(Gebruikersprofiel.values()).map(Enum::toString).collect(Collectors.toList())));
        comboBoxStatusGebruiker.setItems(FXCollections.observableArrayList(Arrays.stream(Gebruikersstatus.values()).map(Enum::toString).collect(Collectors.toList())));
        btnOpslaan.setOnAction(this::gebruikerAanmaken);
    }

    private void gebruikerAanmaken(ActionEvent actionEvent) {
        System.out.println(txtFieldGebruiker.getText());
        System.out.println(txtFieldGebruikersnaam.getText());
        System.out.println(comboBoxStatusGebruiker.getSelectionModel().getSelectedItem());
        domeinController.maakNieuweGebruiker(txtFieldGebruiker.getText(), txtFieldGebruikersnaam.getText(),
                comboBoxTypeGebruiker.getSelectionModel().getSelectedItem(),
                comboBoxStatusGebruiker.getSelectionModel().getSelectedItem());
        btnWijzigen.setVisible(true);
        refreshTable();
        btnOpslaan.setVisible(false);
        btnWijzigen.setVisible(true);
    }

    private void vulTableSessies(Gebruiker gebruiker){
        tableViewSessiesGebruikers.setPlaceholder(new Label("Geen sessies voor gebruiker"));
        tableViewSessiesGebruikers.getColumns().clear();
        titel.setCellValueFactory(new PropertyValueFactory<>("titel"));
        startSessie.setCellValueFactory(new PropertyValueFactory<>("startSessie"));

        tableViewSessiesGebruikers.setItems(FXCollections.observableArrayList(domeinController.geefAlleSessiesKalenderVanGebruiker(gebruiker)));
        tableViewSessiesGebruikers.getColumns().addAll(titel, startSessie);
        if (!tableViewSessiesGebruikers.getItems().isEmpty() || tableViewSessiesGebruikers.getItems() != null) {
            tableViewSessiesGebruikers.getSelectionModel().select(0);
            //domeinController.setHuidigeISessie(domeinController.geefISessiesHuidigeKalender().get(0));
        }
    }

    public void vulDetails(IGebruiker gebruiker){
        gebruiker = tableViewGebruiker.getSelectionModel().getSelectedItem();
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
        txtFieldGebruiker.setText("");
        txtFieldGebruikersnaam.setText("");
    }

    public void refreshTable() {
        clearTableGebruikers();
        vulTableGebruikers(FXCollections.observableArrayList(domeinController.geefAlleIGebruikers()));
    }
}

