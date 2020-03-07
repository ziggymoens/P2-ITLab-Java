package userinterface.ongebruikt.feedback;

import domein.controllers.DomeinController;
import domein.interfacesDomein.IFeedback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class BeherenFeedbackController extends AnchorPane {
    private DomeinController domeinController;
    private ObservableList<IFeedback>feedback;

    @FXML private TableView table;
    @FXML private TableColumn<IFeedback, String> gebruiker;
    @FXML private TableColumn<IFeedback, String> tekst;

    @FXML
    private Button nieuw, verwijder, bewerken;

    public BeherenFeedbackController (DomeinController dc) {
        this.domeinController = dc;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BeherenFeedback.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        vulTable();
    }

    private void vulTable(){
        table.getColumns().clear();

        gebruiker.setCellValueFactory(new PropertyValueFactory<>("gebruiker"));
        tekst.setCellValueFactory(new PropertyValueFactory<>("tekst"));

        feedback = FXCollections.observableArrayList(domeinController.geefAlleFeedbackVanHuidigeSessie());

        table.setItems(feedback);
        table.getColumns().addAll(gebruiker, tekst);
        table.getSelectionModel().select(0);
    }

}

