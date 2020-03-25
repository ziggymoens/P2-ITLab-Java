package userinterface.sessie.feedback;

import domein.controllers.DomeinController;
import domein.interfacesDomein.IAankondiging;
import domein.interfacesDomein.IFeedback;
import exceptions.domein.FeedbackException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BeherenFeedbackController extends AnchorPane {
    private DomeinController domeinController;
    private ObservableList<IFeedback> feedback;
    private IFeedback huidigeFeedback;

    @FXML
    private AnchorPane ap;

    @FXML
    private TableView<IFeedback> table;

    @FXML
    private TableColumn<IFeedback, String> tblGebruiker;

    @FXML
    private TableColumn<IFeedback, String> tblPub;

    @FXML
    private TextField txtGebruiker;

    @FXML
    private Label lblErrorGebruiker, lblErrorInhoud;

    @FXML
    private TextField txtPublicatieDatum;

    @FXML
    private TextArea txtInhoud;

    @FXML
    private Pane pOnderaan;

    @FXML
    private Button btnWijzig;

    @FXML
    private Button btnOpslaan;

    @FXML
    private Button btnNieuw;

    @FXML
    private Button btnverwijder;

    public BeherenFeedbackController(DomeinController domeinController){
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BeherenFeedback.fxml"));
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
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IFeedback>(){
            @Override
            public void changed(ObservableValue<? extends IFeedback> observableValue, IFeedback iFeedback, IFeedback t1) {
                if(t1 != null){
                    btnNieuw.setDisable(false);
                    btnOpslaan.setDisable(true);
                    btnOpslaan.setVisible(false);
                    btnWijzig.setDisable(false);
                    btnWijzig.setVisible(true);
                    huidigeFeedback = t1;
                    vulDetails();
                }
            }
        });
        btnNieuw.setOnAction(this::nieuweFeedback);
        btnverwijder.setOnAction(this::verwijderFeedback);
        btnWijzig.setOnAction(this::wijzigFeedback);
    }

    private void wijzigFeedback(ActionEvent actionEvent) {
        table.getSelectionModel().clearSelection();
        btnWijzig.setVisible(false);
        btnWijzig.setDisable(true);
        btnOpslaan.setVisible(true);
        btnOpslaan.setDisable(false);
        btnNieuw.setDisable(true);
        txtInhoud.setEditable(true);
        btnOpslaan.setOnAction(this::updateFeedback);
    }

    private void updateFeedback(ActionEvent actionEvent) {
        btnOpslaan.setVisible(false);
        btnOpslaan.setDisable(true);
        btnWijzig.setVisible(true);
        btnWijzig.setDisable(false);
        btnNieuw.setDisable(false);
        List<String> veranderingen = new ArrayList<>();
        veranderingen.add(0, txtInhoud.getText());
        domeinController.updateFeedback(huidigeFeedback, veranderingen);
        vulTable();
    }

    private void verwijderFeedback(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Verwijderen");
        alert.setHeaderText(null);
        alert.setContentText("Ben je zeker dat je deze Feedback wilt verwijderen?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            domeinController.verwijderFeedback(table.getSelectionModel().getSelectedItem());
        }
        vulTable();
    }

    private void nieuweFeedback(ActionEvent actionEvent) {
        table.getSelectionModel().clearSelection();
        lblErrorInhoud.setVisible(false);
        txtGebruiker.setText(domeinController.geefHuidigeIGebruiker().getNaam());
        txtInhoud.setText("");
        txtPublicatieDatum.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd:MM:yyyy")));
        txtInhoud.setEditable(true);
        btnNieuw.setDisable(true);
        btnWijzig.setVisible(false);
        btnWijzig.setDisable(true);
        btnOpslaan.setVisible(true);
        btnOpslaan.setDisable(false);
        btnOpslaan.setOnAction(this::toevoegenFeedback);
    }

    private void toevoegenFeedback(ActionEvent actionEvent) {
        btnOpslaan.setVisible(false);
        btnOpslaan.setDisable(true);
        btnWijzig.setVisible(true);
        btnWijzig.setDisable(false);
        try{
            domeinController.addFeedbackSessie(domeinController.geefHuidigeISessie().getSessieId(), domeinController.geefHuidigeIGebruiker().getGebruikersnaam(), txtInhoud.getText());
            lblErrorInhoud.setVisible(false);
        }catch(FeedbackException e){
            lblErrorInhoud.setVisible(true);
        }
        btnNieuw.setDisable(false);
        txtInhoud.setEditable(false);
        vulTable();
    }

    public void vulTable(){
        table.setPlaceholder(new Label("Er zijn geen Feedbacks voor deze sessie"));
        table.getColumns().clear();

        tblGebruiker.setCellValueFactory(new PropertyValueFactory<>("gebruiker"));
        tblPub.setCellValueFactory(new PropertyValueFactory<>("date"));

        feedback = FXCollections.observableArrayList(domeinController.geefAlleFeedbackVanHuidigeSessie());
        table.setItems(feedback);
        table.getColumns().addAll(tblGebruiker, tblPub);
        table.getSelectionModel().select(0);
        huidigeFeedback = table.getSelectionModel().getSelectedItem();
        if(!domeinController.geefAlleFeedbackVanHuidigeSessie().isEmpty())
            vulDetails();
    }

    public void vulDetails(){
        txtGebruiker.setText(huidigeFeedback.getIGebruiker().getNaam());
        txtPublicatieDatum.setText(huidigeFeedback.getDate().format( DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        txtInhoud.setText(huidigeFeedback.getTekst());
    }
}
