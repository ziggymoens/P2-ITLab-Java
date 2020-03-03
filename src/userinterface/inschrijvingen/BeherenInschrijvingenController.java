package userinterface.inschrijvingen;

import domein.DomeinController;
import domein.interfacesDomein.IInschrijving;
import javafx.collections.FXCollections;
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

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<IInschrijving, String> naamGebruiker;
    private TableColumn<IInschrijving, String> datumInschrijving;
    private TableColumn<IInschrijving, String> aanwezigheid;
        //hey
    @FXML
    private Button opslaan, verwijder, bewerken;

    public BeherenInschrijvingenController(DomeinController dc) {
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
        naamGebruiker.setCellValueFactory(new PropertyValueFactory<>("gebruiker"));
        datumInschrijving.setCellValueFactory(new PropertyValueFactory<>("inschrijvingsdatum"));
        aanwezigheid.setCellValueFactory(new PropertyValueFactory<>("statusAanwezigheid"));
        tableView.setItems(FXCollections.observableArrayList(domeinController.geefIInschrijvingen()));
        tableView.getColumns().addAll(naamGebruiker, datumInschrijving, aanwezigheid);

    }

}
