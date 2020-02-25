package userinterface.MAIN;

import domein.DomeinController;
import domein.interfacesDomein.IGebruiker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

import javax.swing.text.html.ImageView;
import java.io.IOException;

public class MainScreenController extends BorderPane {
    @FXML
    private MenuItem nSessie, nGebruiker, nAankondiging, nFeedback, nInschrijving, nMedia, sessieNSessie, sessieOpenSessie, sessieVerwijderSessie, gebruikerNieuwegebruiker, gebruikerOpenGebruiker, gebruikerVerwijderGebruiker, about, help, gebruikerGegevens, gebruikerInstellingen, gebruikerUitloggen, gebruikerAfsluiten;
    @FXML
    private ImageView imageView;
    @FXML
    private Label gebruikersnaam;

    private DomeinController domeinController;
    private IGebruiker gebruiker;

    private MainScreenController(DomeinController domeinController) {
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

        gebruikersnaam.setText(gebruiker.getNaam());
        Image image = new Image("");
    }
}
