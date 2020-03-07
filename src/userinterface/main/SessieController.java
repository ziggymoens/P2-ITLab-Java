package userinterface.main;

import domein.DomeinController;
import domein.interfacesDomein.ISessie;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SessieController extends AnchorPane {

        //region buttonBar FXML
        @FXML private Pane pButtonBar;

        @FXML private Button btnSessie;
        @FXML private Button btnGebruiker;
        @FXML private Button btnSessiekalender;
        @FXML private Button btnIngelogd;
        //endregion FMXL

        //region sessieTable FXML
        @FXML private ChoiceBox<String> choiceBoxMaand;
        @FXML private ChoiceBox<String> choiceBoxJaar;
        @FXML private Button btnToevoegenSessie;

        @FXML private TextField txtSearchBar;

        @FXML private TableView<ISessie> tableViewSessie;
        //endregion

        //region sessieDetails FXML
        @FXML private Label lblTitelSessie;

        @FXML private TextField txtTitelSessie;
        @FXML private Label lblErrorTitel;

        @FXML private TextField txtStartSessie;
        @FXML private Label lblErrorStartSessje;

        @FXML private TextField txtEindSessie;
        @FXML private Label lblErrorEindeSessie;

        @FXML private TextField txtMaxPlaatsenSessie;
        @FXML private Label lblErrorMaxPlaatsen;

        @FXML private TextField txtGastsprekerSessie;
        @FXML private Label lblErrorGastspreker;

        @FXML private TextField txtVerantwoordelijkeSessie;
        @FXML private Label lblErrorVerantwoordelijke;

        @FXML private TextField txtLokaalSessie;
        @FXML private Label lblErrorLokaal;

        @FXML private CheckBox checkBoxCapaciteitSessie;

        @FXML private Button btnBewerken;
        @FXML private Button btnVerwijderen;

        @FXML private Label lblMessage;
        //endregion

        //region media, feedback, aankondiging en inschrijving FXML
        @FXML private VBox vBoxRechts;

        @FXML private ChoiceBox<String> choiceBoxSessieToebehoren;

        @FXML private TableView tableViewSessieToebehoren;

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
        @FXML private Pane pLokaal;

        @FXML private HBox hBoxOnderaan;

        @FXML private VBox vBoxOnderLinks;

        @FXML private ComboBox<String> choiceBox1Onder;
        @FXML private ComboBox<String> choiceBox2Onder;
        @FXML private ComboBox<String> choiceBox3Onder;
        @FXML private ComboBox<Integer> choiceBox4Onder;

        @FXML private TableView<String> tableViewOnderaan;

        @FXML private Button btnKiezen;
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

        public SessieController(DomeinController domeinController){

        }

    }