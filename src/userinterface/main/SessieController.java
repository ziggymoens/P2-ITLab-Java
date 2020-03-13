package userinterface.main;

import domein.controllers.DomeinController;
import domein.controllers.HoofdverantwoordelijkeController;
import domein.interfacesDomein.ISessie;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import userinterface.sessie.lokaal.BeherenLokaalController;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class SessieController extends AnchorPane {
    private DomeinController domeinController;
    private HoofdverantwoordelijkeController hoofdverantwoordelijkeController;

    //region sessieTable FXML
    @FXML
    private ChoiceBox<String> choiceBoxMaand;

    @FXML
    private ChoiceBox<String> choiceBoxJaar;
    @FXML
    private Button btnToevoegenSessie;
    @FXML
    private ChoiceBox<String> choiceBoxZoeken;
    @FXML
    private ChoiceBox<String> choiceBoxFilter;
    @FXML
    private TextField txtSearchBar;

    @FXML
    private TableView<ISessie> tableViewSessie;
    @FXML
    private TableColumn<ISessie, String> titel;
    @FXML
    private TableColumn<ISessie, String> startSessie;
    @FXML
    private TableColumn<ISessie, String> maximumAantalPlaatsen;
    //endregion

    //region sessieDetails FXML
    @FXML
    private VBox vboxSessieDetail;

    @FXML
    private Label lblTitelSessie;

    @FXML
    private TextField txtTitelSessie;
    @FXML
    private Label lblErrorTitel;

    @FXML
    private TextField txtStartSessie;
    @FXML
    private Label lblErrorStartSessje;

    @FXML
    private TextField txtEindSessie;
    @FXML
    private Label lblErrorEindeSessie;

    @FXML
    private TextField txtMaxPlaatsenSessie;
    @FXML
    private Label lblErrorMaxPlaatsen;

    @FXML
    private TextField txtGastsprekerSessie;
    @FXML
    private Label lblErrorGastspreker;

    @FXML
    private TextField txtVerantwoordelijkeSessie;
    @FXML
    private Label lblErrorVerantwoordelijke;

    @FXML
    private TextField txtLokaalSessie;
    @FXML
    private Label lblErrorLokaal;

    @FXML
    private CheckBox checkBoxCapaciteitSessie;

    @FXML
    private Button btnBewerkenSessie;
    @FXML
    private Button btnOpslaanSessie;
    @FXML
    private Button btnVerwijderenSessie;

    @FXML
    private Label lblMessage;
    //endregion

    //region Pane onderaan FXML
    @FXML private Pane pOnderaan;
    //endregion

    //region tijdelijk
    //region media, feedback, aankondiging en inschrijving FXML
    @FXML
    private VBox vBoxRechts;

    @FXML
    private RadioButton rabtnAankondiging, rabtnInschrijving, rabtnFeedback, rabtnMedia;

    @FXML
    private TableView tableViewSessieToebehoren;

    //region media
    @FXML
    private AnchorPane apMedia;

    @FXML
    private ChoiceBox<?> cbmedia;

    @FXML
    private TextField txtMedia;

    @FXML
    private ImageView imgmedia;
    //endregion
    //region feedback
    @FXML
    private AnchorPane apFeedback;
    //endregion
    //region aankondiging
    @FXML
    private AnchorPane apAankondiging;

    @FXML
    private TextField txtGebr;

    @FXML
    private TextField txtPub;

    @FXML
    private ChoiceBox<?> cbAutom;

    @FXML
    private TextArea txfInhoud;
    //endregion
    //region inschrijving
    @FXML
    private AnchorPane apInschrijving;
    //endregion
    //endregion

    //region gerbuiker, lokaal FXML
    //region lokaal
    @FXML
    private Pane pLokaal;

    @FXML
    private HBox hBoxOnderaan;

    @FXML
    private VBox vBoxOnderLinks;

    @FXML
    private ComboBox<String> choiceBox1Onder;
    @FXML
    private ComboBox<String> choiceBox2Onder;
    @FXML
    private ComboBox<String> choiceBox3Onder;
    @FXML
    private ComboBox<Integer> choiceBox4Onder;

    @FXML
    private TableView<String> tableViewOnderaan;

    @FXML
    private Button btnKiezen;
    //endregion
    //region gerbuiker
    @FXML
    private Pane pGebruiker;

    @FXML
    private HBox hBoxOnderaan1;

    @FXML
    private VBox vBoxOnderLinks1;

    @FXML
    private ComboBox<?> choiceBox1Onder1;

    @FXML
    private ComboBox<?> choiceBox2Onder1;

    @FXML
    private ComboBox<?> choiceBoxOnder31;

    @FXML
    private ComboBox<?> choiceBox4Onder1;

    @FXML
    private TableView<?> tableViewOnderaan1;

    @FXML
    private Button btnKiezen1;
    //endregion
    //endregion
    //endregion

    public SessieController(DomeinController domeinController) {
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sessie.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        sessieTable();

        choiceBoxJaar.setItems(FXCollections.observableArrayList(domeinController.geefAcademiejaren()));
        choiceBoxJaar.setValue(((Integer)domeinController.academiejaar).toString());
/*        choiceBoxJaar.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                int aj = Integer.parseInt(t1.substring(0,5));
                String temp = domeinController.geefHuidigeIGebruiker().getGebruikersnaam();
                domeinController.setAcademiejaar(//castStringnrDate);
                domeinController.setHuidigeGebruiker(temp);
            }
        });*/
        choiceBoxMaand.setItems(FXCollections.observableArrayList(domeinController.geefMaanden()));
        choiceBoxMaand.setValue(domeinController.vergelijkMaanden());
        choiceBoxZoeken.setItems(FXCollections.observableArrayList(domeinController.geefFilterOpties()));
        choiceBoxZoeken.setValue(domeinController.geefFilterOpties().get(0));
        choiceBoxZoeken.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.equals("Titel")) {
                    txtSearchBar.setVisible(true);
                    choiceBoxFilter.setVisible(false);
                } else {
                    txtSearchBar.setVisible(false);
                    choiceBoxFilter.setVisible(true);
                }
            }
        });

        tableViewSessie.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ISessie>() {
            @Override
            public void changed(ObservableValue<? extends ISessie> observableValue, ISessie iSessie, ISessie t1) {
                domeinController.setHuidigeISessie(t1);
                checkVeranderingen();
                checkBoxCapaciteitSessie.setDisable(true);
                vulDetails();
            }
        });

        checkBoxCapaciteitSessie.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    txtMaxPlaatsenSessie.setEditable(false);
                    txtMaxPlaatsenSessie.setText(Integer.toString(domeinController.geefHuidigeISessie().getMaximumAantalPlaatsen()));
                } else {
                    txtMaxPlaatsenSessie.setEditable(true);
                }
            }
        });

        btnBewerkenSessie.setOnAction(this::bewerkenSessie);
        btnOpslaanSessie.setOnAction(this::opslaanSessie);
        radioButtons();

        pOnderaan.getChildren().addAll( new BeherenLokaalController(domeinController));
    }


    private void sessieTable() {
        tableViewSessie.getColumns().clear();

        titel.setCellValueFactory(new PropertyValueFactory<>("titel"));
        startSessie.setCellValueFactory(new PropertyValueFactory<>("startSessie"));
        maximumAantalPlaatsen.setCellValueFactory(new PropertyValueFactory<>("maximumAantalPlaatsen"));

        tableViewSessie.setItems(FXCollections.observableArrayList(domeinController.geefISessiesHuidigeKalender()));
        tableViewSessie.getColumns().addAll(titel, startSessie, maximumAantalPlaatsen);
        if (!tableViewSessie.getItems().isEmpty() || tableViewSessie.getItems() != null) {
            tableViewSessie.getSelectionModel().select(0);
            domeinController.setHuidigeISessie(domeinController.geefISessiesHuidigeKalender().get(0));
        }
        vulDetails();
    }

    private void vulDetails() {
        lblTitelSessie.setText(domeinController.geefHuidigeISessie().getTitel());
        txtTitelSessie.setText(domeinController.geefHuidigeISessie().getTitel());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        txtStartSessie.setText(domeinController.geefHuidigeISessie().getStartSessie().format(formatter));
        txtEindSessie.setText(domeinController.geefHuidigeISessie().getStartSessie().format(formatter));
        txtMaxPlaatsenSessie.setText(Integer.toString(domeinController.geefHuidigeISessie().getMaximumAantalPlaatsen()));
        txtGastsprekerSessie.setText(domeinController.geefHuidigeISessie().getNaamGastspreker());
        txtVerantwoordelijkeSessie.setText(domeinController.geefHuidigeISessie().getVerantwoordelijke().getNaam());
        txtLokaalSessie.setText(domeinController.geefHuidigeISessie().getLokaal().getLokaalCode());
    }

    private void bewerkenSessie(ActionEvent actionEvent) {
        btnBewerkenSessie.setVisible(false);
        btnBewerkenSessie.setDisable(true);
        btnOpslaanSessie.setDisable(false);
        btnOpslaanSessie.setVisible(true);
        checkBoxCapaciteitSessie.setDisable(false);
        btnBewerkenSessie.setText("Opslaan");
        txtTitelSessie.setEditable(true);
        txtStartSessie.setEditable(true);
        txtEindSessie.setEditable(true);
        txtMaxPlaatsenSessie.setEditable(true);
        txtGastsprekerSessie.setEditable(true);
        txtVerantwoordelijkeSessie.setEditable(true);
        txtLokaalSessie.onMouseClickedProperty().addListener(new ChangeListener<EventHandler<? super MouseEvent>>() {
            @Override
            public void changed(ObservableValue<? extends EventHandler<? super MouseEvent>> observableValue, EventHandler<? super MouseEvent> eventHandler, EventHandler<? super MouseEvent> t1) {
                vulTableLokalen();
            }
        });
    }

    private void opslaanSessie(ActionEvent actionEvent) {
        btnBewerkenSessie.setDisable(false);
        btnBewerkenSessie.setVisible(true);
        btnOpslaanSessie.setDisable(true);
        btnOpslaanSessie.setVisible(false);
        List<Node> n = vboxSessieDetail.getChildren();
        List<String> s = n.stream().filter(e -> e instanceof TextField).map(e -> ((TextField) e).getText()).collect(Collectors.toList());
        s.stream().forEach(e -> System.out.println(e));
    }

    private void vulTableLokalen(){
    }

    private void checkVeranderingen() {

    }

    private void radioButtons() {

        rabtnAankondiging.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    rabtnFeedback.setSelected(false);
                    rabtnInschrijving.setSelected(false);
                    rabtnMedia.setSelected(false);
                    // Nieuw scherm Aankondiging maken
                }
            }
        });
        rabtnAankondiging.setSelected(true);

        rabtnFeedback.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    rabtnAankondiging.setSelected(false);
                    rabtnInschrijving.setSelected(false);
                    rabtnMedia.setSelected(false);
                    // Nieuw scherm Feedback maken
                }
            }
        });

        rabtnInschrijving.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    rabtnAankondiging.setSelected(false);
                    rabtnFeedback.setSelected(false);
                    rabtnMedia.setSelected(false);
                    // Nieuw scherm Inschrijving maken
                }
            }
        });

        rabtnMedia.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    rabtnAankondiging.setSelected(false);
                    rabtnFeedback.setSelected(false);
                    rabtnInschrijving.setSelected(false);
                    // Nieuw scherm Media maken
                }
            }
        });
    }
}