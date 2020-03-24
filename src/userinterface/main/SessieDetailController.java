package userinterface.main;

import domein.controllers.DomeinController;
import domein.interfacesDomein.IGebruiker;
import domein.interfacesDomein.ILokaal;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import userinterface.sessie.gebruiker.GebruikerBeherenController;
import userinterface.sessie.lokaal.BeherenLokaalController;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SessieDetailController extends AnchorPane {
    //region sessieDetails FXML
    @FXML
    private Label lblTitelSessie;

    @FXML
    private TextField txtTitelSessie;
    @FXML
    private Label lblErrorTitel;

    @FXML
    private DatePicker dpStart;
    @FXML
    private Label lblErrorDatum;

    @FXML
    private TextField txtStart;
    @FXML
    private TextField txtEind;
    @FXML
    private Label lblErrorUur;

    @FXML
    private TextField txtVerantwoordelijkeSessie;
    @FXML
    private Label lblErrorVerantwoordelijke;

    @FXML
    private TextField txtGastsprekerSessie;
    @FXML
    private Label lblErrorGastspreker;


    @FXML
    private TextField txtLokaalSessie;
    @FXML
    private Label lblErrorLokaal;

    @FXML
    private TextField txtMaxPlaatsenSessie;
    @FXML
    private Label lblErrorMaxPlaatsen;
    @FXML
    private CheckBox cbMax;

    @FXML
    private CheckBox cbOpenSessie;

    @FXML
    private CheckBox cbZichtbaar;
    @FXML
    private CheckBox cbNietZichtbaar;

    @FXML
    private Button btnBewerkenSessie;
    @FXML
    private Button btnOpslaanSessie;
    @FXML
    private Button btnVerwijderenSessie;

    @FXML
    private TextArea txtBeschrijving;
    //endregion

    private DomeinController domeinController;
    private SessieController sessieController;
    private IGebruiker tempGebruiker;
    private ILokaal tempLokaal;
    private Boolean bewerkenStatus;

    public SessieDetailController(DomeinController domeinController, SessieController sessieController) {
        this.domeinController = domeinController;
        this.sessieController = sessieController;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SessieDetail.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        activeerButtons();
    }

    public void vulDetails() {
        lblTitelSessie.setText(domeinController.geefHuidigeISessie().getTitel());
        txtTitelSessie.setText(domeinController.geefHuidigeISessie().getTitel());
        dpStart.setValue(domeinController.geefHuidigeISessie().getDatum());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm");
        txtStart.setText(domeinController.geefHuidigeISessie().getStartUur().format(dtf));
        txtEind.setText(domeinController.geefHuidigeISessie().getEindeUur().format(dtf));
        txtMaxPlaatsenSessie.setText(Integer.toString(domeinController.geefHuidigeISessie().getMaximumAantalPlaatsen()));
        txtGastsprekerSessie.setText(domeinController.geefHuidigeISessie().getNaamGastspreker());
        tempGebruiker = domeinController.geefHuidigeISessie().getVerantwoordelijke();
        txtVerantwoordelijkeSessie.setText(domeinController.geefHuidigeISessie().getVerantwoordelijke().getNaam());
        txtLokaalSessie.setText(domeinController.geefHuidigeISessie().getLokaal().getLokaalCode());
        txtBeschrijving.setText(domeinController.geefHuidigeISessie().getBeschrijving());
        tempLokaal = domeinController.geefHuidigeISessie().getLokaal();
        cbOpenSessie.setSelected(domeinController.isSessieOpen());
        if(domeinController.isSessieGesloten()){
            cbOpenSessie.setDisable(true);
        }
        cbZichtbaar.setSelected(domeinController.isZichtbaar());
        cbNietZichtbaar.setSelected(!domeinController.isZichtbaar());
        cbZichtbaar.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                cbZichtbaar.setSelected(t1);
                cbNietZichtbaar.setSelected(!t1);
            }
        });
        cbNietZichtbaar.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                cbZichtbaar.setSelected(!t1);
                cbNietZichtbaar.setSelected(t1);
            }
        });
        cbMax.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    txtMaxPlaatsenSessie.setText(Integer.toString(tempLokaal.getAantalPlaatsen()));
                    txtMaxPlaatsenSessie.setEditable(false);
                } else{
                    txtMaxPlaatsenSessie.setEditable(true);
                }
            }
        });
        cbMax.setSelected(true);
        txtMaxPlaatsenSessie.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.BACK_SPACE) && bewerkenStatus){
                    cbMax.setSelected(false);
                }
            }
        });

        txtMaxPlaatsenSessie.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(controleerMaxLokaal(t1)){
                    cbMax.setSelected(true);
                }
            }
        });
    }

    private boolean controleerMaxLokaal(String s){
        return domeinController.controleerMaxCapaciteitLokaal(Integer.parseInt(s), tempLokaal);
    }

    public boolean zetVeldenBewerken (Boolean b){
        btnBewerkenSessie.setDisable(b);
        btnBewerkenSessie.setVisible(!b);
        btnOpslaanSessie.setDisable(!b);
        btnOpslaanSessie.setVisible(b);
        txtTitelSessie.setEditable(b);
        dpStart.setEditable(b);
        dpStart.setDisable(!b);
        txtStart.setEditable(b);
        txtEind.setEditable(b);
        txtMaxPlaatsenSessie.setEditable(b);
        txtGastsprekerSessie.setEditable(b);
        txtBeschrijving.setEditable(b);
        cbMax.setDisable(!b);
        cbNietZichtbaar.setDisable(!b);
        cbZichtbaar.setDisable(!b);
        cbOpenSessie.setDisable(!b);
        return b;
    }


    private void bewerkenSessie(ActionEvent actionEvent) {
        bewerkenStatus = zetVeldenBewerken(true);
        txtLokaalSessie.setOnMouseClicked(e -> toonLokalen());
        txtVerantwoordelijkeSessie.setOnMouseClicked(e -> toonGebruikers());
    }

    private void verwijderenSessie(ActionEvent actionEvent) {
        //sessieController.verwijder();
    }

    private void opslaanSessie(ActionEvent actionEvent) {
        List<String> veranderingen = new ArrayList<>();
        veranderingen.add(0, tempGebruiker.getGebruikersnaam());
        veranderingen.add(1, txtTitelSessie.getText());
        veranderingen.add(2, dpStart.getValue().toString());
        veranderingen.add(3, txtStart.getText());
        veranderingen.add(4, txtEind.getText());
        veranderingen.add(5, tempLokaal.getLokaalCode());
        veranderingen.add(6, txtGastsprekerSessie.getText());
        veranderingen.add(7, txtMaxPlaatsenSessie.getText());
        Map<String, String> fouten = domeinController.controleerDataSessie(veranderingen);
        if(fouten.isEmpty()){
            domeinController.updateSessie(veranderingen);
            new Alert(Alert.AlertType.INFORMATION,"Sessie " + txtTitelSessie + " opgeslagen",ButtonType.OK);
            bewerkenStatus = zetVeldenBewerken(false);
            sessieController.update();
        } else {
            String fout = "";
            for(Map.Entry<String, String> entry : fouten.entrySet()){
                switch(entry.getKey()){
                    case "verantwoordelijke":
                        this.lblErrorVerantwoordelijke.setText(entry.getValue());
                        this.lblErrorVerantwoordelijke.setVisible(true);
                        break;
                    case "titel":
                        this.lblErrorTitel.setText(entry.getValue());
                        this.lblErrorTitel.setVisible(true);
                        break;
                    case "datum":
                        this.lblErrorDatum.setText(entry.getValue());
                        this.lblErrorDatum.setVisible(true);
                        break;
                    case "uur":
                        this.lblErrorUur.setText(entry.getValue());
                        this.lblErrorUur.setVisible(true);
                        break;
                    case "gastspreker":
                        this.lblErrorGastspreker.setText(entry.getValue());
                        this.lblErrorGastspreker.setVisible(true);
                        break;
                    case "maxPlaatsen":
                        this.lblErrorMaxPlaatsen.setText(entry.getValue());
                        this.lblErrorMaxPlaatsen.setVisible(true);
                        break;
                    case "lokaal":
                        this.lblErrorLokaal.setText(entry.getValue());
                        this.lblErrorLokaal.setVisible(true);
                        break;
                }
            }
            verbergErrors();
        }
/*        List<Node> n = vboxSessieDetail.getChildren();
        List<String> s = n.stream().filter(e -> e instanceof TextField).map(e -> ((TextField) e).getText()).collect(Collectors.toList());
        s.stream().forEach(e -> System.out.println(e));*/
    }

    private void verbergErrors () {
        lblErrorMaxPlaatsen.setVisible(false);
        lblErrorLokaal.setVisible(false);
        lblErrorGastspreker.setVisible(false);
        lblErrorTitel.setVisible(false);
        lblErrorVerantwoordelijke.setVisible(false);
        lblErrorDatum.setVisible(false);
        lblErrorUur.setVisible(true);
    }

    private void nieuweSessie(ActionEvent actionEvent){}

    private void activeerButtons() {
        btnBewerkenSessie.setOnAction(this::bewerkenSessie);
        btnOpslaanSessie.setOnAction(this::opslaanSessie);
        btnVerwijderenSessie.setOnAction(this::verwijderenSessie);
    }

    private void toonGebruikers(){
        Scene scene = new Scene(new GebruikerBeherenController(domeinController, sessieController));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void toonLokalen(){
        Scene scene = new Scene(new BeherenLokaalController(domeinController, sessieController));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void setIGebruiker(IGebruiker gebruiker){
        this.tempGebruiker = gebruiker;
        txtVerantwoordelijkeSessie.setText(gebruiker.getNaam());
    }

    public void setLokaal (ILokaal lokaal){
        this.tempLokaal = lokaal;
        txtMaxPlaatsenSessie.setText(((Integer)lokaal.getAantalPlaatsen()).toString());
        txtLokaalSessie.setText(lokaal.getLokaalCode());
    }
}