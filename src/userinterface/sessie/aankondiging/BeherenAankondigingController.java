package userinterface.sessie.aankondiging;

import domein.controllers.DomeinController;
import domein.interfacesDomein.IAankondiging;
import domein.interfacesDomein.ISessie;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class BeherenAankondigingController extends AnchorPane {
    DomeinController domeinController;
    private ObservableList<IAankondiging> aankondiging;
    private IAankondiging huidigeAankondiging;

    @FXML
    private TableView<IAankondiging> table;

    @FXML
    private TableColumn<IAankondiging, String> tbcGebruiker;

    @FXML
    private TableColumn<IAankondiging, String> tbcDatum;

    @FXML
    private TableColumn<IAankondiging, String> tbcAutoHerinnering;

    @FXML
    private TableColumn<IAankondiging, String> tbcHerinnering;

    @FXML
    private TextField txtGebr, txtPub;

    @FXML
    private ChoiceBox<String> cbAutom;

    @FXML
    private TextArea txfInhoud;
    @FXML
    private Button btnBewerkenAankondiging, btnNieuweAankondiging, btnVerwijderAankondiging, btnOpslaanAankondiging;

    public BeherenAankondigingController(DomeinController domeinController) {
        this.domeinController = domeinController;
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
        btnOpslaanAankondiging.setDisable(true);
        btnOpslaanAankondiging.setVisible(false);
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IAankondiging>() {
            @Override
            public void changed(ObservableValue<? extends IAankondiging> observableValue, IAankondiging iAankondiging, IAankondiging t1) {
                huidigeAankondiging = t1;
                vulDetails();
            }
        });
        btnBewerkenAankondiging.setOnAction(this::bewerkenAankondiging);
        btnOpslaanAankondiging.setOnAction(this::opslaanAankondiging);
        btnNieuweAankondiging.setOnAction(this::nieuweAankondiging);
        btnVerwijderAankondiging.setOnAction(this::verwijderAankondiging);
    }




    public void vulTable(){
        table.getColumns().clear();

        tbcGebruiker.setCellValueFactory(new PropertyValueFactory<>("gebruiker"));
        tbcDatum.setCellValueFactory(new PropertyValueFactory<>("publicatiedatum"));
        tbcAutoHerinnering.setCellValueFactory(new PropertyValueFactory<>("automatischeHerinnering"));
        tbcHerinnering.setCellValueFactory(new PropertyValueFactory<>("herinnering"));

        aankondiging = FXCollections.observableArrayList(domeinController.geefAlleAankondigingenVanHuidigeSessie());
        table.setItems(aankondiging);
        table.getColumns().addAll(tbcGebruiker, tbcDatum, tbcAutoHerinnering, tbcHerinnering);
        table.getSelectionModel().select(0);
        huidigeAankondiging = table.getSelectionModel().getSelectedItem();
        vulDetails();
    }

    public void vulDetails(){
        txtGebr.setText(huidigeAankondiging.getIGebruiker().getNaam());
        txtPub.setText(huidigeAankondiging.getPublicatiedatum().format( DateTimeFormatter.ofPattern("dd/MM HH:mm")));
        txfInhoud.setText(huidigeAankondiging.getInhoud());
        cbAutom.setItems(FXCollections.observableArrayList(domeinController.geefHerinneringsTijdstippen()));
        if(huidigeAankondiging.isAutomatischeHerinnering())
            cbAutom.setValue(huidigeAankondiging.getIHerinnering().getDagenVooraf().toString());
    }

    private void bewerkenAankondiging(ActionEvent actionEvent) {
        btnBewerkenAankondiging.setVisible(false);
        btnBewerkenAankondiging.setDisable(true);
        btnOpslaanAankondiging.setVisible(true);
        btnOpslaanAankondiging.setDisable(false);
        txtGebr.setEditable(true);
        txtPub.setEditable(true);
        txfInhoud.setEditable(true);
        cbAutom.setDisable(false);
    }

    private void opslaanAankondiging(ActionEvent actionEvent) {
        btnOpslaanAankondiging.setVisible(false);
        btnOpslaanAankondiging.setDisable(true);
        btnBewerkenAankondiging.setVisible(true);
        btnBewerkenAankondiging.setDisable(false);
        txtGebr.setEditable(false);
        txtPub.setEditable(false);
        txfInhoud.setEditable(false);
        cbAutom.setDisable(true);
        if(btnNieuweAankondiging.isDisable()){
            //Nieuwe aankondiging uit dc aanroepen
            btnNieuweAankondiging.setDisable(false);
        }else{
            //Update methode aanroepen uit dc
        }
    }

    private void nieuweAankondiging(ActionEvent actionEvent) {
        txtGebr.setText(domeinController.geefHuidigeIGebruiker().getNaam());
        txtPub.setText("");
        txfInhoud.setText("");
        txfInhoud.setEditable(true);
        cbAutom.setDisable(false);
        cbAutom.setValue(domeinController.geefHerinneringsTijdstippen().get(0));
        btnNieuweAankondiging.setDisable(true);
        btnBewerkenAankondiging.setVisible(false);
        btnBewerkenAankondiging.setDisable(true);
        btnOpslaanAankondiging.setVisible(true);
        btnOpslaanAankondiging.setDisable(false);
    }

    private void verwijderAankondiging(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Verwijderen");
        alert.setHeaderText(null);
        alert.setContentText("Ben je zeker dat je deze aankondiging wilt verwijderen?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            domeinController.verwijderAankondiging(table.getSelectionModel().getSelectedItem());
        }
    }

}
