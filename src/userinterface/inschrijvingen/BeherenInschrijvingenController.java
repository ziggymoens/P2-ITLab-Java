package userinterface.inschrijvingen;

import domein.DomeinController;
import domein.interfacesDomein.IInschrijving;
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

public class BeherenInschrijvingenController extends AnchorPane {
    private DomeinController domeinController;

    @FXML private TableView tableView;
    @FXML private TableColumn<IInschrijving, String> naam;
    @FXML private TableColumn<IInschrijving, String> datum;
    @FXML private TableColumn<IInschrijving, String> aanwezig;

    @FXML
    private Button opslaan, verwijder, bewerken;

    public BeherenInschrijvingenController(DomeinController dc) {
        this.domeinController = dc;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BeherenInschrijving.fxml"));
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
        tableView.getColumns().clear();

        naam.setCellValueFactory(new PropertyValueFactory<>("gebruiker"));
        datum.setCellValueFactory(new PropertyValueFactory<>("inschrijvingsdatum"));
        aanwezig.setCellValueFactory(new PropertyValueFactory<>("statusAanwezigheid"));

        ObservableList<IInschrijving>inschrijvingen = FXCollections.observableArrayList(domeinController.geefAlleInschrijvingenVanSessie());
        tableView.setItems(inschrijvingen);
        tableView.getColumns().addAll(naam,datum,aanwezig);
        tableView.getSelectionModel().select(0);
    }

}
