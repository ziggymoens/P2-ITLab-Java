package userinterface.ongebruikt.gebruiker;

import domein.controllers.DomeinController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import userinterface.ongebruikt.main.MainScreenController;

import java.io.IOException;

public class GebruikerMakenController extends BorderPane {

    @FXML
    private TextField txtFieldName;

    @FXML
    private TextField txtFieldGebruikersnaam;

    @FXML
    private ChoiceBox<?> selectionBoxType;

    @FXML
    private ChoiceBox<?> selectionBoxStatus;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnMaak;

    private DomeinController domeinController;

    public GebruikerMakenController(DomeinController domeinController, MainScreenController mainScreenController) {
        this.domeinController = domeinController;
        FXMLLoader loader
                = new FXMLLoader(getClass().getResource("GebruikerMaken.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Tab tab = mainScreenController.maakNieuwTab(this);
        tab.setText("Gebruiker beheren");
    }

}
