package userinterface.sessie.lokaal;

import domein.controllers.DomeinController;
import domein.interfacesDomein.ILokaal;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import userinterface.main.SessieController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class BeherenLokaalController extends AnchorPane {
    @FXML
    private TableView table;
    @FXML
    private TableColumn<ILokaal, String> lokaalCode, stad, gebouw, verdieping, aantalPlaatsen;

    @FXML
    private ComboBox<String> cbStad, cbGebouw, cbVerdieping;

    @FXML
    private TextField txtMin, txtMax;

    @FXML
    private Button btnReset, btnKiezen, btnFilter;

    private DomeinController domeinController;
    private SessieController sessieController;
    private List<ILokaal> filteredList;

    public BeherenLokaalController(DomeinController domeinController, SessieController sessieController) {
        this.domeinController = domeinController;
        this.sessieController = sessieController;
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
        cbStad.getSelectionModel().selectFirst();
        cbGebouw.setItems(FXCollections.observableArrayList(domeinController.geefGebouwen()).sorted());
        cbGebouw.getSelectionModel().selectFirst();
        cbVerdieping.setItems(FXCollections.observableArrayList(domeinController.geefVerdiepingen().stream().map(e -> e.toString()).collect(Collectors.toList())).sorted());
        cbVerdieping.getSelectionModel().selectFirst();
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
        txtMin.clear();
        txtMax.clear();
    }

    private void zetFiltersActief(ActionEvent actionEvent) {
        String stad = cbStad.getSelectionModel().getSelectedItem();
        String gebouw = cbGebouw.getSelectionModel().getSelectedItem();
        String verdieping = cbVerdieping.getSelectionModel().getSelectedItem();
        String minCapaciteit = txtMin.getText();
        String maxCapaciteit = txtMax.getText();
        if(stad.matches("-(.)*")){
            stad = "";
        }
        if(gebouw.matches("-(.)*")){
            gebouw = "";
        }
        if(verdieping.matches("-(.)*")){
            verdieping = "";
        }
        Map<String, String> filters = new HashMap<>();
        filters.put("stad",stad);
        filters.put("gebouw",gebouw);
        filters.put("verdieping",verdieping);
        filters.put("minCapaciteit",minCapaciteit);
        filters.put("maxCapaciteit",maxCapaciteit);
        filters.values().removeIf(String::isBlank);
        filters.values().removeIf(String::isEmpty);
        filters.values().removeIf(Objects::isNull);
        if(filters.keySet().isEmpty()){ vulTable();}
        else {vulTable(domeinController.filterLokaal(filters));}
    }

    private void kiezen(ActionEvent actionEvent) {
        sessieController.setLokaal((ILokaal) table.getSelectionModel().getSelectedItem());
        Stage stage = (Stage) this.getScene().getWindow();
        stage.close();
    }

}