package userinterface.sessie.lokaal;

import domein.controllers.DomeinController;
import domein.interfacesDomein.ILokaal;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class BeherenLokaalController extends AnchorPane {
    @FXML
    private TableView table;
    @FXML
    private TableColumn<ILokaal, String> lokaalCode, stad, gebouw, verdieping, aantalPlaatsen;

    @FXML
    private ComboBox<String> cbStad, cbGebouw, cbVerdieping, cbCapaciteit;

    private DomeinController domeinController;

    public BeherenLokaalController(DomeinController domeinController) {
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BeherenLokaal.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        vulTable();
        vulComboBoxes();
    }

    private void vulTable() {
        table.getColumns().clear();

        lokaalCode.setCellValueFactory(new PropertyValueFactory<>("lokaalCode"));
        stad.setCellValueFactory(new PropertyValueFactory<>("stad"));
        gebouw.setCellValueFactory(new PropertyValueFactory<>("gebouw"));
        verdieping.setCellValueFactory(new PropertyValueFactory<>("verdieping"));
        aantalPlaatsen.setCellValueFactory(new PropertyValueFactory<>("aantalPlaatsen"));

        table.setItems(FXCollections.observableArrayList(domeinController.geefILokalen()));
        table.getColumns().addAll(lokaalCode, stad, gebouw,verdieping,aantalPlaatsen);
        if (!table.getItems().isEmpty() || table.getItems() != null) {
            table.getSelectionModel().select(0);
        }
    }

    private void vulComboBoxes() {
        cbStad.setItems(FXCollections.observableArrayList(domeinController.geefSteden()));
        cbGebouw.setItems(FXCollections.observableArrayList(domeinController.geefGebouwen()));
        cbVerdieping.setItems(FXCollections.observableArrayList(domeinController.geefVerdiepingen()));
        cbCapaciteit.setItems(FXCollections.observableArrayList(domeinController.geefCapaciteiten()));
    }

}
