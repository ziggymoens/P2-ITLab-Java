package userinterface.sessie.lokaal;

import domein.controllers.DomeinController;
import domein.interfacesDomein.ILokaal;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.*;

public class BeherenLokaalController extends AnchorPane {
    @FXML
    private TableView table;
    @FXML
    private TableColumn<ILokaal, String> lokaalCode, stad, gebouw, verdieping, aantalPlaatsen;

    @FXML
    private ComboBox<String> cbStad, cbGebouw, cbCapaciteit;

    @FXML
    private ComboBox<Integer> cbVerdieping;

    @FXML
    private Button btnReset, btnKiezen, btnFilter;

    private DomeinController domeinController;
    private List<ILokaal> filteredList;

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
        zetButtonsActief();
    }

    private void vulTable() {
        table.getColumns().clear();

        lokaalCode.setCellValueFactory(new PropertyValueFactory<>("lokaalCode"));
        stad.setCellValueFactory(new PropertyValueFactory<>("stad"));
        gebouw.setCellValueFactory(new PropertyValueFactory<>("gebouw"));
        verdieping.setCellValueFactory(new PropertyValueFactory<>("verdieping"));
        aantalPlaatsen.setCellValueFactory(new PropertyValueFactory<>("aantalPlaatsen"));

        filteredList = domeinController.geefILokalen();
        table.setItems(FXCollections.observableArrayList(filteredList));
        table.getColumns().addAll(lokaalCode, stad, gebouw,verdieping,aantalPlaatsen);
        if (!table.getItems().isEmpty() || table.getItems() != null) {
            table.getSelectionModel().select(0);
        }
    }

    private void vulTable(List<ILokaal> lokalen) {
        table.getColumns().clear();

        lokaalCode.setCellValueFactory(new PropertyValueFactory<>("lokaalCode"));
        stad.setCellValueFactory(new PropertyValueFactory<>("stad"));
        gebouw.setCellValueFactory(new PropertyValueFactory<>("gebouw"));
        verdieping.setCellValueFactory(new PropertyValueFactory<>("verdieping"));
        aantalPlaatsen.setCellValueFactory(new PropertyValueFactory<>("aantalPlaatsen"));

        table.setItems(FXCollections.observableArrayList(lokalen));
        table.getColumns().addAll(lokaalCode, stad, gebouw,verdieping,aantalPlaatsen);
        if (!table.getItems().isEmpty() || table.getItems() != null) {
            table.getSelectionModel().select(0);
        }
    }

    /*
    Placeholders!!
     */

    private void vulComboBoxes() {
        cbStad.setItems(FXCollections.observableArrayList(domeinController.geefSteden()).sorted());
        //cbStad.getSelectionModel().selectFirst();
        cbGebouw.setItems(FXCollections.observableArrayList(domeinController.geefGebouwen()).sorted());
        //cbGebouw.getSelectionModel().selectFirst();
        cbVerdieping.setItems(FXCollections.observableArrayList(domeinController.geefVerdiepingen()).sorted());
        //cbVerdieping.getSelectionModel().selectFirst();
        //cbCapaciteit.setItems(FXCollections.observableArrayList(domeinController.geefCapaciteiten()).sorted());
        //cbCapaciteit.getSelectionModel().selectFirst();
    }

    private void zetButtonsActief() {
        btnFilter.setOnAction(this::zetFiltersActief);
        btnReset.setOnAction(this::reset);
        btnKiezen.setOnAction(this::kiezen);
    }

    private void reset(ActionEvent actionEvent) {
        vulTable();
        cbStad.getSelectionModel().selectFirst();
        cbGebouw.getSelectionModel().selectFirst();
        cbVerdieping.getSelectionModel().selectFirst();
        cbCapaciteit.getSelectionModel().selectFirst();
    }

    private void zetFiltersActief(ActionEvent actionEvent) {
        String stad = cbStad.getSelectionModel().getSelectedItem();
        String gebouw = cbGebouw.getSelectionModel().getSelectedItem();
        int verdieping = cbVerdieping.getSelectionModel().getSelectedItem();
        String capaciteit = cbCapaciteit.getSelectionModel().getSelectedItem();
        if(stad.matches("-(.)*")){
            stad = null;
        }
        if(gebouw.matches("-(.)*")){
            gebouw = null;
        }
        /*
        if(verdieping.matches("-(.)*")){
            verdieping = null;
        }

         */
        if(capaciteit.matches("-(.)*")){
            capaciteit = null;
        }
        Map<String, String> filters = new HashMap<>();
        filters.put("stad",stad);
        filters.put("gebouw",gebouw);
        //filters.put("verdieping",verdieping);
        filters.put("capaciteit",capaciteit);
        filters.values().removeIf(Objects::isNull);
        if(filters.keySet().isEmpty()){ vulTable();}
        else {vulTable(domeinController.filterLokaal(filters));}
    }

    private void kiezen(ActionEvent actionEvent) {
        //domeinController.pasLokaalAan((ILokaal) table.getSelectionModel().getSelectedItem());
    }

}
