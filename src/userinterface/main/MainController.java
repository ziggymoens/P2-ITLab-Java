package userinterface.main;

import domein.controllers.DomeinController;
import domein.interfacesDomein.IGebruiker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class MainController extends AnchorPane{
    //region buttonBar FXML
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
    //endregion FMXL

    @FXML
    private RadioMenuItem taalN, taalE, taalF;
    @FXML
    private ImageView profielFoto;
    @FXML
    private Label gebruikersnaam, laatst;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab sessieTab;
    @FXML
    private BorderPane sessieTabBP;
    @FXML
    private Menu menuAccount;

    private DomeinController domeinController;
    private IGebruiker gebruiker;

    public MainController(DomeinController domeinController)
    {
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        menuAccount.setText(String.format("Ingelogd als: %s", domeinController.geefHuidigeIGebruiker().getNaam()));
        gebruiker = domeinController.geefHuidigeIGebruiker();
        gebruikersnaam.setText(gebruiker.getNaam());
        Image image = new Image("storage/profielfotos/profielfoto.png");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        laatst.setText(domeinController.geefHuidigeIGebruiker().getLaatstIngelogd().format(formatter));
        this.profielFoto.setImage(image);
        btnSessie.setOnAction(this::openSessie);
        btnGebruiker.setOnAction(this::openGebruiker);
        btnSessiekalender.setOnAction(this::openKalender);
        sessieTabBP.setLeft(new SessieController(domeinController));
    }

    private void openKalender(ActionEvent actionEvent) {
        Tab tab = new Tab("Kalender");
        tab.setClosable(true);
        tab.setContent(new SessieKalenderController());
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    private void openGebruiker(ActionEvent actionEvent) {
        Tab tab = new Tab("Gebruiker");
        tab.setClosable(true);
        tab.setContent(new GebruikerController(domeinController));
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    private void openSessie(ActionEvent actionEvent) {
        Tab tab = new Tab("Sessie");
        tab.setClosable(true);
        tab.setContent(new SessieController(domeinController));
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }


    public Tab maakNieuwTab(Node node) {
        Tab tab = new Tab();
        tab.setContent(node);
        tab.setClosable(true);
        tabPane.getTabs().add(tab);
        return tab;
    }


    public void addTab(Tab tab) {
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    public BorderPane geefMainBP() {
        return sessieTabBP;
    }

    public TabPane getTabPane() {
        return this.tabPane;
    }
}
