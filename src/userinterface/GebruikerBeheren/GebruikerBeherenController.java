package userinterface.GebruikerBeheren;

import domein.DomeinController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import userinterface.MAIN.MainScreenController;

import java.io.IOException;

public class GebruikerBeherenController extends BorderPane {

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

    public GebruikerBeherenController(DomeinController domeinController, MainScreenController mainScreenController) {
        this.domeinController = domeinController;
        FXMLLoader loader
                = new FXMLLoader(getClass().getResource("GebruikerBeheren.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        mainScreenController.vulSchermIn(this);
    }

}
