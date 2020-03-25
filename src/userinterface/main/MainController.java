package userinterface.main;

import domein.controllers.DomeinController;
import domein.interfacesDomein.IGebruiker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

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
    @FXML
    private Button gebruiker;
    //endregion FMXL


    @FXML
    private TabPane tabPane;
    @FXML
    private Tab sessieTab;
    @FXML
    private BorderPane sessieTabBP;

    private DomeinController domeinController;
    private IGebruiker iGebruiker;

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
        iGebruiker = domeinController.geefHuidigeIGebruiker();
        gebruiker.setText(iGebruiker.getNaam());
        openSessie();
        openGebruiker();
        openKalender();
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(0);
        gebruiker.setOnAction(this::huidigeGebruikerDetails);
    }

    private void huidigeGebruikerDetails(ActionEvent event) {
        Scene scene = new Scene(new HuidigeGebruikerController(domeinController.geefHuidigeIGebruiker()));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void openKalender() {
        Tab tab = new Tab("Kalender");
        tab.setClosable(false);
        tab.setContent(new SessieKalenderController(domeinController));
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    private void openGebruiker() {
        Tab tab = new Tab("Gebruiker");
        tab.setClosable(false);
        tab.setContent(new GebruikerController(domeinController));
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    private void openSessie() {
        Tab tab = new Tab("Sessie");
        tab.setClosable(false);
        tab.setContent(new SessieController(domeinController));
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }


    public Tab maakNieuwTab(Node node) {
        Tab tab = new Tab();
        tab.setContent(node);
        tab.setClosable(false);
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
