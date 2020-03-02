package userinterface.GebruikerBeheren;

import domein.DomeinController;
import domein.interfacesDomein.IGebruiker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import userinterface.MAIN.MainScreenController;

import java.io.IOException;

public class InfoGebruikerController extends AnchorPane {

    @FXML
    private TextField txtFieldUser;

    //filters voor status
    @FXML
    private CheckBox statusFilterAll, statusFilterActief, statusFilterNietActief, statusFilterGeblokkeerd;

    //filters voor type
    @FXML
    private CheckBox typeFilterAll, typeFilterGebruiker, typeFilterVerantwoordelijke, typeFilterHoofdverantwoordelijke;

    @FXML
    private TableView<IGebruiker> tableViewGebrukers;

    @FXML
    private Button zoekKnop, maakAanKnop, bewerkKnop, verwijderKnop;

    private DomeinController domeinController;

    public InfoGebruikerController(DomeinController domeinController, MainScreenController mainScreenController) {
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoGebruiker.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        tableViewGebrukers.setItems(domeinController.geefIGebruikers());
    }
}
