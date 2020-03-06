package userinterface.main_final;

import javafx.scene.layout.AnchorPane;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainController extends AnchorPane {

        @FXML
        private ChoiceBox<?> choiceBoxMaand;

        @FXML
        private ChoiceBox<?> choiceBoxJaar;

        @FXML
        private Button toevoegenSessie;

        @FXML
        private TextField txtFieldSearchBar;

        @FXML
        private TableView<?> tableViewSessie;

        @FXML
        private TextField txtFieldTitel;

        @FXML
        private Label lblErrorTitel;

        @FXML
        private TextField txtFieldStartSessie;

        @FXML
        private Label lblErrorStartSessje;

        @FXML
        private TextField txtFieldEindeSessie;

        @FXML
        private Label lblErrorEindeSessie;

        @FXML
        private TextField txtFieldGastspreker;

        @FXML
        private Label lblErrorGastspreker;

        @FXML
        private TextField txtFieldVerantwoordelijke;

        @FXML
        private Label lblErrorVerantwoordelijke;

        @FXML
        private TextField txtFieldLokaal;

        @FXML
        private Label lblErrorLokaal;

        @FXML
        private Label lblErrorMaxPlaatsen;

        @FXML
        private CheckBox checkBoxCapaciteit;

        @FXML
        private Button btnBewerken;

        @FXML
        private Button btnVerwijderen;

        @FXML
        private Label lblMessage;

        @FXML
        private VBox vBoxRechts;

        @FXML
        private ChoiceBox<?> choiceBoxSessieToebehoren;

        @FXML
        private TableView<?> tableViewSessieToebehoren;

        @FXML
        private Pane pLokaal;

        @FXML
        private HBox hBoxOnderaan;

        @FXML
        private VBox vBoxOnderLinks;

        @FXML
        private ComboBox<?> choiceBox1Onder;

        @FXML
        private ComboBox<?> choiceBox2Onder;

        @FXML
        private ComboBox<?> choiceBoxOnder3;

        @FXML
        private ComboBox<?> choiceBox4Onder;

        @FXML
        private TableView<?> tableViewOnderaan;

        @FXML
        private Button btnKiezen;

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

        @FXML
        private AnchorPane apFeedback;

        @FXML
        private AnchorPane apInschrijving;

        @FXML
        private AnchorPane apMedia;

        @FXML
        private ChoiceBox<?> cbmedia;

        @FXML
        private TextField txtMedia;

        @FXML
        private ImageView imgmedia;

    }