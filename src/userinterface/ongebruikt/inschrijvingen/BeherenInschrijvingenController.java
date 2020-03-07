package userinterface.ongebruikt.inschrijvingen;

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
    private ObservableList<IInschrijving>inschrijvingen;

    @FXML private TableView table;
    @FXML private TableColumn<IInschrijving, String> gebruiker;
    @FXML private TableColumn<IInschrijving, String> inschrijvingsdatum;
    @FXML private TableColumn<IInschrijving, String> statusAanwezigheid;

    @FXML
    private Button nieuw, verwijder, bewerken;

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
        table.getColumns().clear();

        gebruiker.setCellValueFactory(new PropertyValueFactory<>("gebruiker"));
        inschrijvingsdatum.setCellValueFactory(new PropertyValueFactory<>("inschrijvingsdatum"));
        statusAanwezigheid.setCellValueFactory(new PropertyValueFactory<>("statusAanwezigheid"));

        inschrijvingen = FXCollections.observableArrayList(domeinController.geefAlleInschrijvingenVanHuidigeSessie());

        table.setItems(inschrijvingen);
        table.getColumns().addAll(gebruiker,inschrijvingsdatum,statusAanwezigheid);
        table.getSelectionModel().select(0);
    }

}
