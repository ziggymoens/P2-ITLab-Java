package userinterface.MAIN;

import domein.DomeinController;
import domein.interfacesDomein.IGebruiker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import userinterface.GebruikerBeheren.InfoGebruikerController;
import userinterface.sessieBeheren.SessieBeherenController;

import java.io.File;
import java.io.IOException;

public class MainScreenController extends BorderPane {
    @FXML
    private MenuItem newSessie, openSessie, deleteSessie,addAankondiging, addFeedback, addInschrijving, addMedia, gebruikerNieuwegebruiker, gebruikerOpenGebruiker, gebruikerVerwijderGebruiker, about, help, gebruikerGegevens, gebruikerInstellingen, gebruikerUitloggen, gebruikerAfsluiten;
    @FXML
    private ImageView profielFoto;
    @FXML
    private Label gebruikersnaam;

    private DomeinController domeinController;
    private IGebruiker gebruiker;

    public MainScreenController(DomeinController domeinController) {
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        gebruiker = domeinController.geefIGebruiker();
        gebruikersnaam.setText(gebruiker.getNaam());
        Image image = new Image("storage/profielfotos/profielfoto.png");
        this.profielFoto.setImage(image);
        openSessie.setOnAction(this::openSessie);
        gebruikerOpenGebruiker.setOnAction(this::gebruikerOpenGebruiker);
    }

    private void openSessie(ActionEvent actionEvent) {
        new SessieBeherenController(domeinController, this);
    }

    private void gebruikerOpenGebruiker(ActionEvent actionEvent){
        new InfoGebruikerController(domeinController, this);
    }

    public void vulSchermIn(Node node){
        this.setCenter(node);
    }
}
