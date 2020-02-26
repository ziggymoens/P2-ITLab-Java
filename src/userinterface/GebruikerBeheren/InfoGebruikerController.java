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
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import userinterface.MAIN.MainScreenController;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
//TODO zoekbalk
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

        vulSchermOp();

        mainScreenController.vulSchermIn(this);

    }

    public void vulSchermOp(){
        listView();
        zoekKnop.setOnAction(this::zoek);
        bewerken.setOnAction(this::bewerken);
        verwijderen.setOnAction(this::verwijderen);
        nieuw.setOnAction(this::nieuw);
    }

    public void listView (){
        listView.setItems(FXCollections.observableArrayList(domeinController.geefIGebruikers()));
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IGebruiker>() {
                @Override
                public void changed(ObservableValue<? extends IGebruiker> observableValue, IGebruiker gebruiker, IGebruiker t1) {
                    gebruikers = FXCollections.observableArrayList(domeinController.geefIGebruikers());
                    listView.setItems(gebruikers);
                    listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                    geefDetails(t1);
                }
            });
    }

    private void geefDetails(IGebruiker gebruiker) {
        naamgebruiker.setText(gebruiker.getNaam());
        gebruikersnaam.setText(gebruiker.getGebruikersnaam());
        status.setText(gebruiker.getStatus().toString());
        type.setText(gebruiker.getGebruikersprofiel().toString());
    }

    private void verwijderen(ActionEvent event){
        System.out.println("klik");
        IGebruiker selected = listView.getSelectionModel().getSelectedItem();
        System.out.println(selected);
        if(selected != null){
            listView.getSelectionModel().clearSelection();
            domeinController.verwijderGebruiker(selected);
        }
    }

    private void zoek(ActionEvent actionEvent) {
        System.out.println("Zoek");
    }

    private void nieuw(ActionEvent actionEvent) {
        System.out.println("nieuw");
    }

    private void bewerken(ActionEvent actionEvent) {
        System.out.println("bewerken");
    }
}


