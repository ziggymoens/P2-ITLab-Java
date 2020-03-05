package userinterface.media;

import domein.DomeinController;
import domein.interfacesDomein.IMedia;
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

public class BeherenMediaController extends AnchorPane {
    private DomeinController domeinController;
    private ObservableList<IMedia>media;

    @FXML private TableView table;
    @FXML private TableColumn<IMedia, String> type;
    @FXML private TableColumn<IMedia, String> locatie;

    @FXML
    private Button nieuw, verwijder, bewerken;

    public BeherenMediaController(DomeinController dc) {
        this.domeinController = dc;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BeherenMedia.fxml"));
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

        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        locatie.setCellValueFactory(new PropertyValueFactory<>("locatie"));

        media = FXCollections.observableArrayList(domeinController.geefMediaVanHuidigeSessie());

        table.setItems(media);
        table.getColumns().addAll(type, locatie);
        table.getSelectionModel().select(0);
    }

}
