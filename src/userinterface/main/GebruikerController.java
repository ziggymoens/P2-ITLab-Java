package userinterface.main;

import domein.controllers.DomeinController;
import domein.gebruiker.Gebruiker;
import domein.interfacesDomein.IGebruiker;
import domein.interfacesDomein.ISessie;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GebruikerController extends AnchorPane {
    private DomeinController domeinController;
    private boolean denyPersist = true;
    private IGebruiker selected;
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
    private ComboBox<String> comboBoxType;

    @FXML
    private ComboBox<String> comboBoxStatus;

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
    private Label lblErrorNaam, lblErrorGebruikersnaam;

    @FXML
    private TextField txtFieldGebruikersnaam;

    @FXML
    private TextField txtFieldType;

    @FXML
    private Label lblErrorTypeGebruiker;

    @FXML
    private TextField txtFieldStatus;

    @FXML
    private TextField txtFieldBarcode;

    @FXML
    private Label lblErrorStatusGebruiker;

    @FXML
    private Button btnWijzigen, btnOpslaan;

    @FXML
    private Button btnVerwijderen, btnAnnuleren;

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

    @FXML
    private Button uploadFoto;
    @FXML
    private ImageView profielfoto;


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
        uploadFoto.setVisible(false);
        uploadFoto.setOnAction(this::uploadNewFoto);

        ObservableList<String> type = FXCollections.observableArrayList("gebruiker", "verantwoordelijke", "hoofdverantwoordelijke");
        ObservableList<String> status = FXCollections.observableArrayList("actief", "niet actief", "geblokkeerd");
        comboBoxType.setItems(type);
        comboBoxStatus.setItems(status);
        zoek();
    }

    private void filter(ActionEvent actionEvent) {
        String comboStatus = comboBoxStatus.getSelectionModel().getSelectedItem();
        String comboType = comboBoxType.getSelectionModel().getSelectedItem();
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

        tableViewGebruiker.getSelectionModel().select(0);
        //Selecteren van eerste gebruiker en details hiervan vullen als er gebruikers er in het systeem aanwezig zijn.
        if(tableViewGebruiker.getSelectionModel().getSelectedItem() != null){
            vulDetails(tableViewGebruiker.getSelectionModel().getSelectedItem());
            vulTableSessies((Gebruiker) tableViewGebruiker.getSelectionModel().getSelectedItem());
        }

        //Listener voor het vullen van de details en sessies van de gebruiker waarop geklikt is.
        tableViewGebruiker.getSelectionModel().selectedItemProperty().addListener((observableValue, gebruiker, t1) -> {
            if(t1 != null) {
                this.selected = t1;
                vulDetails(t1);
                vulTableSessies((Gebruiker) t1);
            }
        });
    }

    private void wijzigGebruiker(ActionEvent actionEvent){
        btnWijzigen.setVisible(false);
        btnVerwijderen.setVisible(false);
        btnOpslaan.setVisible(true);
        btnAnnuleren.setVisible(true);
        txtFieldGebruiker.setEditable(true);
        txtFieldGebruikersnaam.setEditable(true);
        ObservableList<String> type = FXCollections.observableArrayList("gebruiker", "verantwoordelijke", "hoofdverantwoordelijke");
        ObservableList<String> status = FXCollections.observableArrayList("actief", "niet actief", "geblokkeerd");
        comboBoxTypeGebruiker.setItems(type);
        comboBoxTypeGebruiker.getSelectionModel().selectFirst();
        comboBoxStatusGebruiker.setItems(status);
        comboBoxStatusGebruiker.getSelectionModel().selectFirst();
        btnOpslaan.setOnAction(this::updateGebruiker);
        btnAnnuleren.setCancelButton(true);
        btnAnnuleren.setOnAction(this::refreshAll);
    }

    private void uploadNewFoto(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Media Toevoegen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.gif", "*.jpg"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                BufferedImage image = ImageIO.read(file);
                domeinController.profielfotoGebruikerWijzigen(image, selected);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                vulProfielfotoIn(selected);
            }
        }
    }

    public void updateGebruiker(ActionEvent actionEvent){
        domeinController.updateGebruiker(txtFieldGebruiker.getText(), txtFieldGebruikersnaam.getText(),
                comboBoxStatusGebruiker.getSelectionModel().getSelectedItem(),
                comboBoxTypeGebruiker.getSelectionModel().getSelectedItem());
        btnOpslaan.setVisible(false);
        btnWijzigen.setVisible(true);
        uploadFoto.setVisible(false);
        refreshTable();
    }

    private void maakNieuweGebruiker(ActionEvent actionEvent){
        uploadFoto.setVisible(false);
        btnWijzigen.setVisible(false);
        btnAnnuleren.setVisible(true);
        btnAnnuleren.setCancelButton(true);
        btnOpslaan.setVisible(true);

        clearDetails();
        txtFieldGebruiker.setEditable(true);
        txtFieldGebruikersnaam.setEditable(true);
        ObservableList<String> type = FXCollections.observableArrayList("gebruiker", "verantwoordelijke", "hoofdverantwoordelijke");
        ObservableList<String> status = FXCollections.observableArrayList("actief", "niet actief", "geblokkeerd");
        comboBoxTypeGebruiker.setItems(type);
        comboBoxStatusGebruiker.setItems(status);
        btnOpslaan.setOnAction(this::gebruikerAanmaken);
    }

    private void gebruikerAanmaken() {
        uploadFoto.setVisible(true);
        tableViewGebruiker.setDisable(true);
        String naam = txtFieldGebruiker.getText();
        String gebruikersnaam = txtFieldGebruikersnaam.getText();
        String gebruikersprofiel = comboBoxStatusGebruiker.getSelectionModel().getSelectedItem();
        String gebruikerstype = comboBoxTypeGebruiker.getSelectionModel().getSelectedItem();
        opslaanDoorgeven(naam, gebruikersnaam, gebruikersprofiel, gebruikerstype);
    }

    private void gebruikerAanmaken(ActionEvent actionEvent) {
        uploadFoto.setVisible(true);
        tableViewGebruiker.setDisable(true);
        String naam = txtFieldGebruiker.getText();
        String gebruikersnaam = txtFieldGebruikersnaam.getText();
        String gebruikersprofiel = comboBoxStatusGebruiker.getSelectionModel().getSelectedItem();
        String gebruikerstype = comboBoxTypeGebruiker.getSelectionModel().getSelectedItem();
        opslaanDoorgeven(naam, gebruikersnaam, gebruikersprofiel, gebruikerstype);
    }

    public void opslaanDoorgeven(String naam, String gebruikersnaam, String gebruikersprofiel, String gebruikerstype){
        if(!naam.isBlank() && !gebruikersnaam.isBlank()){
            denyPersist = false;
            domeinController.maakNieuweGebruiker(naam, gebruikersnaam, gebruikerstype, gebruikersprofiel);
            lblErrorNaam.setVisible(false);
            lblErrorGebruikersnaam.setVisible(false);
            btnOpslaan.setVisible(false);
            btnWijzigen.setVisible(true);
            btnWijzigen.setVisible(true);
            btnAnnuleren.setVisible(false);
            tableViewGebruiker.setDisable(false);
            refreshAll();
        } else{
            if(txtFieldGebruiker.getText().isBlank()){
                lblErrorNaam.setVisible(true);
            }
            if(txtFieldGebruikersnaam.getText().isBlank()){
                lblErrorGebruikersnaam.setVisible(true);
            }
            while(denyPersist){
                gebruikerAanmaken();
            }
        }
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
        uploadFoto.setVisible(true);
        gebruiker = tableViewGebruiker.getSelectionModel().getSelectedItem();
        txtFieldGebruiker.setText(gebruiker.getNaam());
        txtFieldGebruikersnaam.setText(gebruiker.getGebruikersnaam());
        comboBoxStatusGebruiker.setItems(FXCollections.observableArrayList(gebruiker.getStatus()));
        comboBoxStatusGebruiker.getSelectionModel().selectFirst();
        comboBoxTypeGebruiker.setItems(FXCollections.observableArrayList(gebruiker.getGebruikersprofiel()));
        comboBoxTypeGebruiker.getSelectionModel().selectFirst();
        txtFieldBarcode.setText(String.valueOf(gebruiker.getBarcode()));
        vulProfielfotoIn(gebruiker);
    }

    public void vulProfielfotoIn(IGebruiker gebruiker){
        if (gebruiker.getProfielfoto() != null) {
            Image image = SwingFXUtils.toFXImage(gebruiker.getProfielfoto(), null);
            profielfoto.setImage(image);
        }else{
            Image image = new Image("storage/profielfotos/profielfoto.png");
            profielfoto.setImage(image);
        }
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

    public void refreshAll(){
        refreshTable();
        clearDetails();
        btnAnnuleren.setVisible(false);
        btnOpslaan.setVisible(false);
        btnWijzigen.setVisible(true);
        btnVerwijderen.setVisible(true);
    }

    public void refreshAll(ActionEvent actionEvent){
        refreshTable();
        clearDetails();
        btnAnnuleren.setVisible(false);
        btnOpslaan.setVisible(false);
        btnWijzigen.setVisible(true);
        btnVerwijderen.setVisible(true);
    }
}

