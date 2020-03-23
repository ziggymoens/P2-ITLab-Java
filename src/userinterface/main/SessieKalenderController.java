package userinterface.main;

import domein.controllers.DomeinController;
import domein.interfacesDomein.IAcademiejaar;
import domein.interfacesDomein.ISessie;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.stream.Collectors;

public class SessieKalenderController extends AnchorPane {
    @FXML
    private TableView <IAcademiejaar>tableAj;
    @FXML
    private TableView <ISessie>tableSessie;
    @FXML
    private TableColumn<IAcademiejaar, String> academiejaarString, startString, eindString, aantalSessies;
    @FXML
    private TableColumn<ISessie,String>titel, datum, stad, aanwezigen;
    @FXML
    private Button btnNieuwAj, btnKiezen;
    @FXML
    private ComboBox cbAj, cbMaand;
    @FXML
    private Label lblErrorAj;

    private DomeinController domeinController;
    private IAcademiejaar huidigAj;
    public SessieKalenderController(DomeinController domeinController) {
        this.domeinController = domeinController;
        this.huidigAj = this.domeinController.huidigAcademiejaar;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SessieKalender.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        vulTabel();
        vulComboxes();
        activeerButtons();
    }

    private void vulTabel() {
        tableAj.getColumns().clear();

        tableAj.getColumns().addAll(academiejaarString, startString, eindString, aantalSessies);

        academiejaarString.setCellValueFactory(new PropertyValueFactory("academiejaarString"));
        startString.setCellValueFactory(new PropertyValueFactory("startString"));
        eindString.setCellValueFactory(new PropertyValueFactory("eindString"));
        aantalSessies.setCellValueFactory(new PropertyValueFactory("aantal"));

        tableAj.setItems(FXCollections.observableArrayList(domeinController.geefAcademiejaren()));

        selectInTable();
    }

    private void selectInTable() {
        tableAj.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IAcademiejaar>() {
            @Override
            public void changed(ObservableValue<? extends IAcademiejaar> observableValue, IAcademiejaar iAcademiejaar, IAcademiejaar t1) {
                huidigAj = domeinController.geefAcademiejaarOpId(t1.getAcademiejaar());
                vulTableSessies();
            }
        });

        if (!tableAj.getItems().isEmpty() || tableAj.getItems() != null) {
            if(domeinController.geefHuidigIAcademiejaar() == null){
                tableAj.getSelectionModel().select(0);
            }else{
                tableAj.getSelectionModel().select(domeinController.geefHuidigIAcademiejaar());
            }
        }
    }

    private void vulTableSessies(){
        tableSessie.getColumns().clear();

        tableSessie.getColumns().addAll(titel, datum, stad, aanwezigen);

        titel.setCellValueFactory(new PropertyValueFactory("titel"));
        datum.setCellValueFactory(new PropertyValueFactory("datumString"));
        stad.setCellValueFactory(new PropertyValueFactory("stad"));
        aanwezigen.setCellValueFactory(new PropertyValueFactory("aantalAanwezigen"));

        tableSessie.setItems(FXCollections.observableArrayList(domeinController.geefISessiesOpAcademiejaar(huidigAj.getAcademiejaar())));
    }

    private void activeerButtons() {
        btnNieuwAj.setOnAction(this::startNieuwAcademiejaar);
    }

    private void startNieuwAcademiejaar(ActionEvent actionEvent) {
        //domeinController.startNieuwAcademiejaar();
    }

    private void vulComboxes() {
        cbAj.setItems(FXCollections.observableArrayList(domeinController.geefAcademiejaren().stream().map(e -> e.getAcademiejaarString()).collect(Collectors.toList())));
        cbAj.setValue(cbAj.getItems().get(0));
        cbMaand.setItems(FXCollections.observableArrayList(domeinController.geefMaanden()));
    }
}
