package userinterface.GebruikerBeheren;

import domein.DomeinController;
import domein.interfacesDomein.IGebruiker;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import userinterface.MAIN.MainScreenController;

import java.io.IOException;

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
        vulTable(domeinController.geefIGebruikers());

        typeFilterAll.setOnAction(this::FilterAllTypes);
        typeFilterGebruiker.setOnAction(this::FilterGebruikersType);
        typeFilterVerantwoordelijke.setOnAction(this::FilterVerantwoordelijkeType);
        typeFilterHoofdverantwoordelijke.setOnAction(this::FilterHoofdverantwoordelijkeType);

        statusFilterAll.setOnAction(this::FilterAllStatus);
        statusFilterActief.setOnAction(this::FilterActiefStatus);
        statusFilterNietActief.setOnAction(this::FilterNietActiefStatus);
        statusFilterGeblokkeerd.setOnAction(this::FilterGeblokkeerdStatus);
    }

    public void vulTable(ObservableList<IGebruiker> observableList){
        clearTable();
        TVnaamGebruiker.setCellValueFactory(new PropertyValueFactory<>("naam"));
        TVgebruikersnaamChamilo.setCellValueFactory(new PropertyValueFactory<>("gebruikersnaam"));
        TVstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        TVtype.setCellValueFactory(new PropertyValueFactory<>("gebruikersprofiel"));
        tableViewGebruikers.setItems(observableList);
        tableViewGebruikers.getColumns().addAll(TVnaamGebruiker, TVgebruikersnaamChamilo, TVstatus, TVtype);
    }

    @FXML
    void FilterActiefStatus(ActionEvent event) {
        clearTable();
        vulTable(domeinController.geefAlleActieveGebruikers());
    }

    @FXML
    void FilterAllTypes(ActionEvent event) {
        clearTable();
        vulTable(domeinController.geefIGebruikers());
        typeFilterHoofdverantwoordelijke.setSelected(true);
        typeFilterVerantwoordelijke.setSelected(true);
        typeFilterGebruiker.setSelected(true);
    }

    @FXML
    void FilterAllStatus(ActionEvent event) {
        clearTable();
        vulTable(domeinController.geefIGebruikers());
        statusFilterAll.setSelected(true);
        statusFilterActief.setSelected(true);
        statusFilterGeblokkeerd.setSelected(true);
        statusFilterNietActief.setSelected(true);
    }

    @FXML
    void FilterGeblokkeerdStatus(ActionEvent event) {
        clearTable();
        vulTable(domeinController.geefAlleGeblokkeerdeGebruikers());
    }

    @FXML
    void FilterGebruikersType(ActionEvent event) {
        clearTable();
        vulTable(domeinController.geefAlleGewoneGebruikers());
    }

    @FXML
    void FilterHoofdverantwoordelijkeType(ActionEvent event) {
        clearTable();
        vulTable(domeinController.geefAlleHoofdverantwoordelijkeGebruikers());
    }

    @FXML
    void FilterNietActiefStatus(ActionEvent event) {
        clearTable();
        vulTable(domeinController.geefAlleNietActieveGebruikers());
    }

    @FXML
    void FilterVerantwoordelijkeType(ActionEvent event) {
        clearTable();
        vulTable(domeinController.geefAlleVerantwoordelijkeGebruikers());
    }

    public void clearTable(){
        tableViewGebruikers.getItems().clear();
    }

}

