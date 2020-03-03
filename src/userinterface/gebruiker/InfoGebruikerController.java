package userinterface.gebruiker;

import domein.DomeinController;
import domein.Gebruiker;
import domein.interfacesDomein.IGebruiker;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import userinterface.main.MainScreenController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        VulTable(FXCollections.observableArrayList(domeinController.geefIGebruikers()));

        typeFilterAll.setOnAction(this::FilterAllTypes);
        typeFilterGebruiker.setOnAction(this::FilterGebruikersType);
        typeFilterVerantwoordelijke.setOnAction(this::FilterVerantwoordelijkeType);
        typeFilterHoofdverantwoordelijke.setOnAction(this::FilterHoofdverantwoordelijkeType);

        statusFilterAll.setOnAction(this::FilterAllStatus);
        statusFilterActief.setOnAction(this::FilterActiefStatus);
        statusFilterNietActief.setOnAction(this::FilterNietActiefStatus);
        statusFilterGeblokkeerd.setOnAction(this::FilterGeblokkeerdStatus);

        zoekKnop.setOnAction(this::zoekKnop);

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

    private void zoekKnop(ActionEvent actionEvent) {
        System.out.println("Wie zoekt, die vindt...");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Gevonden!");
    }

    @FXML
    void FilterActiefStatus(ActionEvent event) {
        VulTable(FXCollections.observableArrayList(domeinController.geefAlleActieveGebruikers()));
    }

    private void VulTable(ObservableList<IGebruiker> observableList) {
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
        if(!statusFilterActief.isSelected() && !statusFilterNietActief.isSelected()){
            VulTable(FXCollections.observableArrayList(domeinController.geefAlleGeblokkeerdeGebruikers()));
        }else if(statusFilterActief.isSelected()){
            tableViewGebruikers.setItems(FXCollections.observableArrayList(domeinController.geefAlleGeblokkeerdeGebruikers()));
            tableViewGebruikers.setItems(FXCollections.observableArrayList(domeinController.geefAlleActieveGebruikers()));
        }
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
        tableViewGebruikers.getColumns().clear();
    }



}

