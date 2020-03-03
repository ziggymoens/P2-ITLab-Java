package userinterface.aankondiging;

import domein.DomeinController;
import domein.interfacesDomein.IAankondiging;
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

public class BeherenAankondigingController extends AnchorPane {
    private DomeinController domeinController;
    private ObservableList<IAankondiging>aankondiging;

    @FXML private TableView table;
    @FXML private TableColumn<IAankondiging, String> gebruiker;
    @FXML private TableColumn<IAankondiging, String> publicatiedatum;
    @FXML private TableColumn<IAankondiging, String> inhoud;
    @FXML private TableColumn<IAankondiging, String> automatischeHerinnering;
    @FXML private TableColumn<IAankondiging, String> herinnering;

    @FXML
    private Button opslaan, verwijder, bewerken;

    public BeherenAankondigingController(DomeinController dc) {
        this.domeinController = dc;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BeherenAankondiging.fxml"));
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
        publicatiedatum.setCellValueFactory(new PropertyValueFactory<>("publicatiedatum"));
        inhoud.setCellValueFactory(new PropertyValueFactory<>("inhoud"));
        automatischeHerinnering.setCellValueFactory(new PropertyValueFactory<>("automatischeHerinnering"));
        herinnering.setCellValueFactory(new PropertyValueFactory<>("herinnering"));

        aankondiging = FXCollections.observableArrayList(domeinController.geefAlleAankondigingenVanHuidigeSessie());

        table.setItems(aankondiging);
        table.getColumns().addAll(gebruiker,publicatiedatum,inhoud,automatischeHerinnering,herinnering);
        table.getSelectionModel().select(0);
    }

}
