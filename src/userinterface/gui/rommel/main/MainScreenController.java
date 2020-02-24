package userinterface.gui.rommel.main;

import domein.DomeinController;
import domein.domeinklassen.Sessie;
import domein.interfacesDomein.ISessie;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import userinterface.gui.rommel.details.ListController;

import java.io.IOException;

public class MainScreenController extends BorderPane {
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu menuGebruiker, menuSessie, menuHelp;
    @FXML
    private MenuItem menuGebruikerSettings, menuSessieNew, menuSessieOpen, menuHelpAbout;
    @FXML
    private BorderPane mainBorderPane;
    private ObservableList<ISessie> observableListSessie;

    private final DomeinController domeinController;

    public MainScreenController(DomeinController domeinController) {
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        loader.setRoot(this);
        //loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        HBox centerSessie = new HBox();
        ListController<Sessie> listControllerSessie = new ListController<Sessie>(this);
        this.observableListSessie = domeinController.getSessieObservableList();
        listControllerSessie.setItems(observableListSessie);
        centerSessie.getChildren().add(listControllerSessie);
        mainBorderPane.setCenter(centerSessie);
    }

    public BorderPane getMainBorderPane() {
        return mainBorderPane;
    }

    public DomeinController getDomeinController() {
        return domeinController;
    }
}
