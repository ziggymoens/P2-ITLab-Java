package userinterface.main;

import domein.Aankondiging;
import domein.DomeinController;
import domein.interfacesDomein.IAankondiging;
import domein.interfacesDomein.IGebruiker;
import domein.interfacesDomein.ISessie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import userinterface.aankondiging.AankondigingBewerkenController;
import userinterface.aankondiging.AankondigingPlaatsenController;
import userinterface.gebruiker.InfoGebruikerController;
import userinterface.kalender.KalenderController;
import userinterface.kalender.KalenderGegevens;
import userinterface.media.NieuweMediaController;
import userinterface.sessie.SessieTableViewController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainScreenController extends AnchorPane {
    @FXML
    private MenuItem newSessie, openSessie, deleteSessie,addAankondiging, addFeedback, addInschrijving, addMedia, gebruikerNieuwegebruiker, gebruikerOpenGebruiker, gebruikerVerwijderGebruiker, about, help, gebruikerGegevens, gebruikerInstellingen, gebruikerUitloggen, gebruikerAfsluiten, openKalender;
    @FXML
    private RadioMenuItem taalN, taalE, taalF;
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
        addAankondiging.setOnAction(this::openAankondiging);
        mainTabBP.setLeft(new KalenderController(domeinController, this, new KalenderGegevens(LocalDate.now().getMonth().getValue(), LocalDate.now().getYear())));
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
        tabPane.getSelectionModel().select(tab);
    }

    private void nieuweSessie(ActionEvent actionEvent){
        //new SessieBewerkenController(null, domeinController, null);
        //new SessieTableViewController(domeinController, this);

    }

    private void verwijderSessie(ActionEvent actionEvent){
        new SessieTableViewController(domeinController, this);
    }

    private void gebruikerOpenGebruiker(ActionEvent actionEvent){
        Tab tab = new Tab();
        tab.setContent(new InfoGebruikerController(domeinController, this));
        tab.setText("Gebruiker");
        tab.setClosable(true);
        addTab(tab);
    }

    public Tab maakNieuwTab(Node node){
        Tab tab = new Tab();
        tab.setContent(node);
        tab.setClosable(true);
        tabPane.getTabs().add(tab);
        return tab;
    }

    public void kalenderTonen(ActionEvent event){
        Tab tab = new Tab();
        tab.setContent(new KalenderController(domeinController, this));
        tab.setText("Kalender");
        addTab(tab);
    }

    public void openAankondiging(ActionEvent event){
        ISessie sessie = domeinController.geefISessieById("S1920-000002");
        /*Tab tab = new Tab();
        tab.setContent(new AankondigingPlaatsenController(domeinController, this, sessie));
        tab.setText("Nieuwe Aankondiging");
        addTab(tab);
        tabPane.getSelectionModel().select(tab);*/
        IAankondiging aankondiging = sessie.getIAankondigingenSessie().get(0);
        Tab tab = new Tab();
        tab.setContent(new AankondigingBewerkenController(domeinController, this, sessie, aankondiging));
        tab.setText("Bewerken Aankondiging");
        addTab(tab);
        tabPane.getSelectionModel().select(tab);
    }

    public void addTab(Tab tab){
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    public BorderPane geefMainBP(){
        return mainTabBP;
    }

    public TabPane getTabPane (){
        return this.tabPane;
    }
}
