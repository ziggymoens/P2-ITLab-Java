package userinterface.gebruiker;

import domein.DomeinController;
import domein.interfacesDomein.IGebruiker;
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
import java.util.List;

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
        initVulTable(FXCollections.observableArrayList(domeinController.geefIGebruikers()));

        typeFilterAll.setOnAction(this::FilterAllTypes);
        typeFilterGebruiker.setOnAction(this::FilterGebruikersType);
        typeFilterVerantwoordelijke.setOnAction(this::FilterVerantwoordelijkeType);
        typeFilterHoofdverantwoordelijke.setOnAction(this::FilterHoofdverantwoordelijkeType);

        statusFilterAll.setOnAction(this::FilterAllStatus);
        statusFilterActief.setOnAction(this::FilterActiefStatus);
        statusFilterNietActief.setOnAction(this::FilterNietActiefStatus);
        statusFilterGeblokkeerd.setOnAction(this::FilterGeblokkeerdStatus);
    }

    public void initVulTable(List<IGebruiker> list){
        clearTable();
        TVnaamGebruiker.setCellValueFactory(new PropertyValueFactory<>("naam"));
        TVgebruikersnaamChamilo.setCellValueFactory(new PropertyValueFactory<>("gebruikersnaam"));
        TVstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        TVtype.setCellValueFactory(new PropertyValueFactory<>("gebruikersprofiel"));
        tableViewGebruikers.setItems(FXCollections.observableArrayList(list));
        tableViewGebruikers.getColumns().addAll(TVnaamGebruiker, TVgebruikersnaamChamilo, TVstatus, TVtype);
    }

    @FXML
    void FilterActiefStatus(ActionEvent event) {
        VulTable(FXCollections.observableArrayList(domeinController.geefAlleActieveGebruikers()));
    }

    private void VulTable(ObservableList<IGebruiker> observableList) {
        clearTable();
        tableViewGebruikers.setItems(observableList);
    }

    @FXML
    void FilterAllTypes(ActionEvent event) {
        VulTable(FXCollections.observableArrayList(domeinController.geefIGebruikers()));
        typeFilterAll.setSelected(true);
        typeFilterHoofdverantwoordelijke.setSelected(true);
        typeFilterVerantwoordelijke.setSelected(true);
        typeFilterGebruiker.setSelected(true);
    }

    @FXML
    void FilterAllStatus(ActionEvent event) {
        VulTable(FXCollections.observableArrayList(domeinController.geefIGebruikers()));
        statusFilterAll.setSelected(true);
        statusFilterActief.setSelected(true);
        statusFilterGeblokkeerd.setSelected(true);
        statusFilterNietActief.setSelected(true);
    }

    @FXML
    void FilterGeblokkeerdStatus(ActionEvent event) {
        clearTable();
        VulTable(FXCollections.observableArrayList(domeinController.geefAlleGeblokkeerdeGebruikers()));
    }

    @FXML
    void FilterGebruikersType(ActionEvent event) {
        clearTable();
        VulTable(FXCollections.observableArrayList(domeinController.geefAlleGewoneGebruikers()));
    }

    @FXML
    void FilterHoofdverantwoordelijkeType(ActionEvent event) {
        clearTable();
        VulTable(FXCollections.observableArrayList(domeinController.geefAlleHoofdverantwoordelijkeGebruikers()));
    }

    @FXML
    void FilterNietActiefStatus(ActionEvent event) {
        clearTable();
        VulTable(FXCollections.observableArrayList(domeinController.geefAlleNietActieveGebruikers()));
    }

    @FXML
    void FilterVerantwoordelijkeType(ActionEvent event) {
        clearTable();
        VulTable(FXCollections.observableArrayList(domeinController.geefAlleVerantwoordelijkeGebruikers()));
    }

    public void clearTable(){
        tableViewGebruikers.getItems().clear();
    }

}

