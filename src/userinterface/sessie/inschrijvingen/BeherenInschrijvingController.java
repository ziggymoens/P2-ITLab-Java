package userinterface.sessie.inschrijvingen;

import domein.controllers.DomeinController;
import domein.interfacesDomein.IFeedback;
import domein.interfacesDomein.IInschrijving;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import userinterface.sessie.IDetails;
import userinterface.sessie.gebruiker.GebruikerBeherenController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BeherenInschrijvingController extends AnchorPane implements IDetails {
    private DomeinController domeinController;
    private ObservableList<IInschrijving> inschrijving;
    private IInschrijving huidigeInschrijving;

    @FXML
    private AnchorPane ap;

    @FXML
    private TableView<IInschrijving> table;

    @FXML
    private TableColumn<IInschrijving, String> tblGebruiker;

    @FXML
    private TableColumn<IInschrijving, String> tblDatum;

    @FXML
    private TableColumn<IInschrijving, String> tblStatus;

    @FXML
    private TextField txtGebruiker;

    @FXML
    private Label lblErrorGebruiker;

    @FXML
    private DatePicker dpDate;

    @FXML
    private Label lblErrorDate;

    @FXML
    private ComboBox<String> cbAanwezigheid;

    @FXML
    private Label lblErrorAanwezigheid;

    @FXML
    private TextField txtAanwezig;

    @FXML
    private TextField txtAfwezig;

    @FXML
    private Pane pOnderaan;

    @FXML
    private Button btnWijzig;

    @FXML
    private Button btnOpslaan;

    @FXML
    private Button btnverwijder;

    public BeherenInschrijvingController(DomeinController domeinController){
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("beherenInschrijving.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        vulTable();
        btnOpslaan.setDisable(true);
        btnOpslaan.setVisible(false);
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IInschrijving>(){
            @Override
            public void changed(ObservableValue<? extends IInschrijving> observableValue, IInschrijving iInschrijving, IInschrijving t1) {
                if(t1 != null){

                    btnOpslaan.setDisable(true);
                    btnOpslaan.setVisible(false);
                    btnWijzig.setDisable(false);
                    btnWijzig.setVisible(true);
                    huidigeInschrijving = t1;
                    vulDetails();
                }
            }
        });
        btnWijzig.setOnAction(this::wijzigInschrijving);
        btnverwijder.setOnAction(this::verwijderInschrijving);
    }




    private void vulTable() {
        table.getColumns().clear();
        table.setPlaceholder(new Label("Er zijn geen Inschrijvingen voor deze sessie"));

        tblGebruiker.setCellValueFactory(new PropertyValueFactory<>("gebruiker"));
        tblDatum.setCellValueFactory(new PropertyValueFactory<>("inschrijvingsdatum"));
        tblStatus.setCellValueFactory(new PropertyValueFactory<>("statusAanwezigheid"));

        inschrijving = FXCollections.observableArrayList(domeinController.geefAlleInschrijvingenVanHuidigeSessie());
        table.setItems(inschrijving);
        table.getColumns().addAll(tblGebruiker, tblDatum, tblStatus);
        table.getSelectionModel().select(0);
        huidigeInschrijving = table.getSelectionModel().getSelectedItem();
        if(!domeinController.geefAlleInschrijvingenVanHuidigeSessie().isEmpty())
            vulDetails();
    }

    private void vulDetails() {
        txtGebruiker.setText(huidigeInschrijving.getIGebruiker().getNaam());
        dpDate.setValue(huidigeInschrijving.getInschrijvingsdatum().toLocalDate());
        List<String> cbItems = new ArrayList<>();
        cbItems.add("Afwezig");
        cbItems.add("Aanwezig");
        cbAanwezigheid.setItems(FXCollections.observableArrayList(cbItems));
        cbAanwezigheid.setValue(huidigeInschrijving.isStatusAanwezigheid() ? "Aanwezig" : "Afwezig");
    }

    private void wijzigInschrijving(ActionEvent actionEvent) {
        table.getSelectionModel().clearSelection();
        btnWijzig.setVisible(false);
        btnWijzig.setDisable(true);
        btnOpslaan.setVisible(true);
        btnOpslaan.setDisable(false);
        cbAanwezigheid.setDisable(false);
        btnOpslaan.setOnAction(this::updateInschrijving);
    }

    private void updateInschrijving(ActionEvent actionEvent) {
        btnOpslaan.setVisible(false);
        btnOpslaan.setDisable(true);
        btnWijzig.setVisible(true);
        btnWijzig.setDisable(false);
        List<String> veranderingen = new ArrayList<>();
        veranderingen.add(0, dpDate.getValue().toString());
        veranderingen.add(1, cbAanwezigheid.getValue());
        domeinController.updateInschrijving(huidigeInschrijving, veranderingen);
    }

    private void verwijderInschrijving(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Verwijderen");
        alert.setHeaderText(null);
        alert.setContentText("Ben je zeker dat je deze Inschrijving wilt verwijderen?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            domeinController.verwijderInschrijving(table.getSelectionModel().getSelectedItem());
        }
    }

    @Override
    public void update() {
        vulTable();
    }
}
