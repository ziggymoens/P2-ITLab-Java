package userinterface.sessie.gebruiker;

import domein.controllers.DomeinController;
import domein.interfacesDomein.IGebruiker;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import userinterface.main.SessieController;

import java.io.IOException;

public class BeherenGebruikerController extends AnchorPane {
    private DomeinController domeinController;
    private SessieController sessieController;
    @FXML
    private HBox hBoxOnderaan1;

    @FXML
    private VBox vBoxOnderLinks1;

    @FXML
    private TextField txtFieldSearchbar;

    @FXML
    private TableView<IGebruiker> tableViewGebruiker;

    @FXML
    private TableColumn<IGebruiker, String> TVnaam, TVtype, TVstatus;

    @FXML
    private Button btnKiezen;

    public BeherenGebruikerController(DomeinController domeinController, SessieController sessieController){
        this.domeinController = domeinController;
        this.sessieController = sessieController;
        try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BeherenGebruiker.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        vulTableGebruikers(FXCollections.observableArrayList(domeinController.geefAlleVerantwoordelijken()));
        zoek();
    }

    private void vulTableGebruikers(ObservableList<IGebruiker> observableArrayList) {
        System.out.println(domeinController.geefAlleVerantwoordelijken());
        TVnaam.setCellValueFactory(new PropertyValueFactory<>("naam"));
        TVtype.setCellValueFactory(new PropertyValueFactory<>("type"));
        TVstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableViewGebruiker.setItems(FXCollections.observableArrayList(observableArrayList));
        tableViewGebruiker.getColumns().addAll(TVnaam, TVtype, TVstatus);

        if(tableViewGebruiker.getSelectionModel().getSelectedItem() != null){
            tableViewGebruiker.getSelectionModel().select(0);
        }
    }

    private void zoek(){
        ObservableList data =  tableViewGebruiker.getItems();
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

    public void clearTableGebruikers(){
        tableViewGebruiker.getColumns().clear();
    }

    private void kiezen(ActionEvent actionEvent) {
        sessieController.setIGerbuiker((IGebruiker) tableViewGebruiker.getSelectionModel().getSelectedItem());
        Stage stage = (Stage) this.getScene().getWindow();
        stage.close();
    }
}
