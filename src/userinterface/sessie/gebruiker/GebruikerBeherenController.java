package userinterface.sessie.gebruiker;

import domein.controllers.DomeinController;
import domein.interfacesDomein.IGebruiker;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import userinterface.main.SessieController;

import java.io.IOException;

public class GebruikerBeherenController extends AnchorPane {
    private DomeinController domeinController;
    private SessieController sessieController;

    @FXML
    private TableView<IGebruiker> tableViewGebruiker;

    @FXML
    private TableColumn<IGebruiker, String> TVnaam;

    @FXML
    private TableColumn<IGebruiker, String> TVgebruikersprofiel;

    @FXML
    private TextField txtFieldSearchbar;

    @FXML
    private Button btnKies;

    public GebruikerBeherenController(DomeinController domeinController, SessieController sessieController){
        this.domeinController = domeinController;
        this.sessieController = sessieController;


        FXMLLoader loader = new FXMLLoader(getClass().getResource("BeherenGebruiker.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        vulTable(FXCollections.observableArrayList(domeinController.geefAlleVerantwoordelijken()));
        zoek();

        btnKies.setOnAction(this::kiezen);
    }

    private void kiezen(ActionEvent actionEvent) {
        sessieController.setIGebruiker((IGebruiker) tableViewGebruiker.getSelectionModel().getSelectedItem());
        Stage stage = (Stage) this.getScene().getWindow();
        stage.close();
    }

    private void vulTable(ObservableList observableArrayList) {
        TVnaam.setCellValueFactory(new PropertyValueFactory<>("naam"));
        TVgebruikersprofiel.setCellValueFactory(new PropertyValueFactory<>("gebruikersprofiel"));
        tableViewGebruiker.setItems(observableArrayList);
        tableViewGebruiker.getColumns().addAll(TVnaam, TVgebruikersprofiel);
    }

    private void zoek() {
        ObservableList data = tableViewGebruiker.getItems();
        txtFieldSearchbar.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                tableViewGebruiker.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<IGebruiker> subentries = FXCollections.observableArrayList();

            long count = tableViewGebruiker.getColumns().stream().count();
            for (int i = 0; i < tableViewGebruiker.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + tableViewGebruiker.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(tableViewGebruiker.getItems().get(i));
                        break;
                    }
                }
            }
            tableViewGebruiker.setItems(subentries);
        });
    }
}
