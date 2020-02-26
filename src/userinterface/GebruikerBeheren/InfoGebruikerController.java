package userinterface.GebruikerBeheren;

import domein.DomeinController;
import domein.domeinklassen.Gebruiker;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import domein.interfacesDomein.IGebruiker;
import domein.interfacesDomein.ISessie;
import domein.interfacesDomein.ISessieKalender;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import userinterface.MAIN.MainScreenController;

import java.io.IOException;

public class InfoGebruikerController extends BorderPane{
    private DomeinController domeinController;

    @FXML
    private TextField txtFieldUser;
    @FXML
    private Button zoekKnop, nieuw, bewerken, verwijderen;
    @FXML
    private ListView<IGebruiker> listView;
    @FXML
    private TextField naamgebruiker, gebruikersnaam, status, type;

    private ObservableList<IGebruiker> gebruikers;

    private IGebruiker gebruiker;


    public InfoGebruikerController(DomeinController domeinController, MainScreenController mainScreenController) {
        this.domeinController = domeinController;
        FXMLLoader loader
                = new FXMLLoader(getClass().getResource("InfoGebruiker.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        listView.setItems(FXCollections.observableArrayList(domeinController.geefIGebruikers()));



        mainScreenController.vulSchermIn(this);
    }


    }


