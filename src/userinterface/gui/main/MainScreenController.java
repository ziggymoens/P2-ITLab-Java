package userinterface.gui.main;

import domein.DomeinController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

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

    private final DomeinController domeinController;

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
    }

    public BorderPane getMainBorderPane() {
        return mainBorderPane;
    }

    public DomeinController getDomeinController() {
        return domeinController;
    }
}
