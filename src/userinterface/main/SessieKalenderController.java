package userinterface.main;

import domein.controllers.DomeinController;
import domein.interfacesDomein.IAcademiejaar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SessieKalenderController extends AnchorPane {
    @FXML
    private TableView table;
    @FXML
    private TableColumn<IAcademiejaar, String> aj, start, eind, aantalSessies;
    @FXML
    private Button btnNieuwAj, btnKiezen;
    @FXML
    private ComboBox cbAj, cbMaand;
    @FXML
    private Label lblErrorAj;

    private DomeinController domeinController;
    public SessieKalenderController(DomeinController domeinController) {
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SessieKalender.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        //vulTabel();
        //vulComboxes();
        //activeerButtons();
    }

    private void vulTabel() {
        table.getColumns().clear();

        table.getColumns().addAll(aj, start, eind, aantalSessies);

        aj.setCellValueFactory(new PropertyValueFactory("aj"));
        start.setCellValueFactory(new PropertyValueFactory("start"));
        eind.setCellValueFactory(new PropertyValueFactory("eind"));
        aantalSessies.setCellValueFactory(new PropertyValueFactory("aantal"));

        //selectInTable();
    }

    private void selectInTable() {
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String string, String t1) {
                //cbAj.set
            }
        });
    }

    private void activeerButtons() {
    }

    private void vulComboxes() {
        cbAj.setItems(FXCollections.observableArrayList("2019 - 2020"));
        cbAj.setValue(cbAj.getItems().get(0));
        cbMaand.setItems(FXCollections.observableArrayList(domeinController.geefMaanden()));
    }
}
