package userinterface.gebruiker;

import domein.DomeinController;
import domein.enums.Gebruikersprofielen;
import domein.interfacesDomein.IGebruiker;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import userinterface.main.MainScreenController;
import java.io.IOException;
import java.util.Optional;

public class InfoGebruikerController extends AnchorPane {

    @FXML
    private TextField txtFieldUser;

    //filters voor status
    @FXML
    private CheckBox statusFilterAll, statusFilterActief, statusFilterNietActief, statusFilterGeblokkeerd;

    //filters voor type
    @FXML
    private CheckBox typeFilterAll, typeFilterGebruiker, typeFilterVerantwoordelijke, typeFilterHoofdverantwoordelijke;

    @FXML
    private TableView<IGebruiker> tableViewGebruikers;

    @FXML
    private TableColumn<IGebruiker, String> TVnaamGebruiker, TVgebruikersnaamChamilo, TVstatus, TVtype;

    @FXML
    private Button zoekKnop, maakAanKnop, bewerkKnop, verwijderKnop;

    private DomeinController domeinController;

    public InfoGebruikerController(DomeinController domeinController, MainScreenController mainScreenController) {
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoGebruiker.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        //tableViewGebruikers = new TableView<>();

        vulTable(FXCollections.observableArrayList(domeinController.geefIGebruikers()));

        typeFilterAll.setOnAction(this::filterAllTypes);
        typeFilterGebruiker.setOnAction(this::filterGebruikersType);
        typeFilterVerantwoordelijke.setOnAction(this::filterVerantwoordelijkeType);
        typeFilterHoofdverantwoordelijke.setOnAction(this::filterHoofdverantwoordelijkeType);

        statusFilterAll.setOnAction(this::filterAllStatus);
        statusFilterActief.setOnAction(this::filterActiefStatus);
        statusFilterNietActief.setOnAction(this::filterNietActiefStatus);
        statusFilterGeblokkeerd.setOnAction(this::filterGeblokkeerdStatus);

        zoekKnop.setOnAction(this::zoekKnop);
        verwijderKnop.setOnAction(this::bevestigVerwijderGebruiker);

        ObservableList data =  tableViewGebruikers.getItems();
            txtFieldUser.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                tableViewGebruikers.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<IGebruiker> subentries = FXCollections.observableArrayList();

            long count = tableViewGebruikers.getColumns().stream().count();
            for (int i = 0; i < tableViewGebruikers.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + tableViewGebruikers.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(tableViewGebruikers.getItems().get(i));
                        break;
                    }
                }
            }
            tableViewGebruikers.setItems(subentries);
        });
    }

    private void bevestigVerwijderGebruiker(ActionEvent event) {
        if(tableViewGebruikers.getSelectionModel().getSelectedItem().getGebruikersprofiel().toString().equalsIgnoreCase(Gebruikersprofielen.HOOFDVERANTWOORDELIJKE.toString())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("FOUT BIJ VERWIJDEREN");
            alert.setHeaderText("HOOFDVERANTWOORDELIJKE KAN NIET VERWIJDERD WORDEN");
            alert.setContentText("Het is niet mogelijk om de hoofdgebruiker te verwijderen. \nIndien u dit wenst, doe dit via de Database");
            Optional<ButtonType> antwoord = alert.showAndWait();
     }else {
            Alert alertConf = new Alert(Alert.AlertType.CONFIRMATION);
            alertConf.setTitle("Bevestig verwijderen van gebruiker:");
            alertConf.setHeaderText(tableViewGebruikers.getSelectionModel().getSelectedItem().toString());
            alertConf.setContentText("close");
            Optional<ButtonType> antwoord = alertConf.showAndWait();
            if (antwoord.get() == ButtonType.CANCEL) {
                event.consume();
            } else {
                verwijderGebruiker();
            }
        }
    }

    public void verwijderGebruiker() {
        IGebruiker gebruiker = tableViewGebruikers.getSelectionModel().getSelectedItem();
        domeinController.verwijderGebruiker(gebruiker);
        refreshTable();
    }

    private void zoekKnop(ActionEvent actionEvent) {
        System.out.println("Wie zoekt, die vindt...");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Gevonden!");
    }

    @FXML
    void filterActiefStatus(ActionEvent event) {
        vulTable(FXCollections.observableArrayList(domeinController.geefAlleActieveGebruikers()));
    }

    private void vulTable(ObservableList<IGebruiker> observableList) {
        tableViewGebruikers.getColumns().clear();
        TVnaamGebruiker.setCellValueFactory(new PropertyValueFactory<>("naam"));
        TVgebruikersnaamChamilo.setCellValueFactory(new PropertyValueFactory<>("gebruikersnaam"));
        TVstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        TVtype.setCellValueFactory(new PropertyValueFactory<>("gebruikersprofiel"));
        tableViewGebruikers.setItems(FXCollections.observableArrayList(observableList));
        tableViewGebruikers.getColumns().addAll(TVnaamGebruiker, TVgebruikersnaamChamilo, TVstatus, TVtype);
        if(tableViewGebruikers.getSelectionModel() != null)
            tableViewGebruikers.getSelectionModel().select(0);
    }

    @FXML
    void filterAllTypes(ActionEvent event) {
        vulTable(FXCollections.observableArrayList(domeinController.geefIGebruikers()));
        typeFilterAll.setSelected(true);
        typeFilterHoofdverantwoordelijke.setSelected(true);
        typeFilterVerantwoordelijke.setSelected(true);
        typeFilterGebruiker.setSelected(true);
    }

    @FXML
    void filterAllStatus(ActionEvent event) {
        vulTable(FXCollections.observableArrayList(domeinController.geefIGebruikers()));
        statusFilterAll.setSelected(true);
        statusFilterActief.setSelected(true);
        statusFilterGeblokkeerd.setSelected(true);
        statusFilterNietActief.setSelected(true);
    }

    @FXML
    void filterGeblokkeerdStatus(ActionEvent event) {
        clearTable();
        if(!statusFilterActief.isSelected() && !statusFilterNietActief.isSelected()){
            vulTable(FXCollections.observableArrayList(domeinController.geefAlleGeblokkeerdeGebruikers()));
        }else if(statusFilterActief.isSelected()){
            tableViewGebruikers.setItems(FXCollections.observableArrayList(domeinController.geefAlleGeblokkeerdeGebruikers()));
            tableViewGebruikers.setItems(FXCollections.observableArrayList(domeinController.geefAlleActieveGebruikers()));
        }
    }

    @FXML
    void filterGebruikersType(ActionEvent event) {
        clearTable();
        vulTable(FXCollections.observableArrayList(domeinController.geefAlleGewoneGebruikers()));
    }

    @FXML
    void filterHoofdverantwoordelijkeType(ActionEvent event) {
        clearTable();
        vulTable(FXCollections.observableArrayList(domeinController.geefAlleHoofdverantwoordelijkeGebruikers()));
    }

    @FXML
    void filterNietActiefStatus(ActionEvent event) {
        clearTable();
        vulTable(FXCollections.observableArrayList(domeinController.geefAlleNietActieveGebruikers()));
    }

    @FXML
    void filterVerantwoordelijkeType(ActionEvent event) {
        clearTable();
        vulTable(FXCollections.observableArrayList(domeinController.geefAlleVerantwoordelijkeGebruikers()));
    }

    public void clearTable(){
        tableViewGebruikers.getColumns().clear();
    }

    public void refreshTable() {
        clearTable();
        vulTable(FXCollections.observableArrayList(domeinController.geefIGebruikers()));
    }



}

