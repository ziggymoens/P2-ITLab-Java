package userinterface.MAIN;

import domein.DomeinController;
import domein.interfacesDomein.IGebruiker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import userinterface.GebruikerBeheren.InfoGebruikerController;
import userinterface.kalender.KalenderController;
import userinterface.media.NieuweMediaController;
import userinterface.sessieBeheren.SessieTableViewController;
import userinterface.sessieBeheren.SessieBewerkenController;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class MainScreenController extends AnchorPane {
    @FXML
    private MenuItem newSessie, openSessie, deleteSessie,addAankondiging, addFeedback, addInschrijving, addMedia, gebruikerNieuwegebruiker, gebruikerOpenGebruiker, gebruikerVerwijderGebruiker, about, help, gebruikerGegevens, gebruikerInstellingen, gebruikerUitloggen, gebruikerAfsluiten, openKalender;
    @FXML
    private ImageView profielFoto;
    @FXML
    private Label gebruikersnaam, laatst;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab mainTab;
    @FXML
    private BorderPane mainTabBP;
    @FXML
    private Menu menuAccount;


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
        menuAccount.setText(String.format("Ingelogd als: %s", domeinController.geefIGebruiker().getNaam()));
        gebruiker = domeinController.geefIGebruiker();
        gebruikersnaam.setText(gebruiker.getNaam());
        Image image = new Image("storage/profielfotos/profielfoto.png");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        laatst.setText(domeinController.geefIGebruiker().getLaatstIngelogd().format(formatter));
        this.profielFoto.setImage(image);
        openSessie.setOnAction(this::openSessie);
        openKalender.setOnAction(this::kalenderTonen);
        gebruikerOpenGebruiker.setOnAction(this::gebruikerOpenGebruiker);
        addMedia.setOnAction(this::addMedia);
        newSessie.setOnAction(this::nieuweSessie);
        deleteSessie.setOnAction(this::verwijderSessie);
        mainTabBP.setRight(new KalenderController(domeinController, mainTabBP));
    }


    private void addMedia(ActionEvent event) {
        Stage stage = new Stage();
        stage.setScene(new Scene(new NieuweMediaController(domeinController)));
        stage.show();
    }

    private void openSessie(ActionEvent actionEvent) {
        Tab tab = new Tab("Sessies");
        tab.setClosable(true);
        tab.setContent(new SessieTableViewController(domeinController, this));
        tabPane.getTabs().add(tab);
    }

    private void nieuweSessie(ActionEvent actionEvent){
        new SessieBewerkenController(domeinController);
        new SessieTableViewController(domeinController, this);

    }

    private void verwijderSessie(ActionEvent actionEvent){
        new SessieTableViewController(domeinController, this);
    }

    private void gebruikerOpenGebruiker(ActionEvent actionEvent){
        new InfoGebruikerController(domeinController, this);
    }

    public void vulSchermIn(Node node){
        mainTabBP.setCenter(node);
    }

    public void kalenderTonen(ActionEvent event){
        mainTabBP.setCenter(new KalenderController(domeinController, mainTabBP));
    }
}
