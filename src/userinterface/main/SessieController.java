package userinterface.main;

import domein.controllers.DomeinController;
import domein.interfacesDomein.IGebruiker;
import domein.interfacesDomein.ILokaal;
import domein.interfacesDomein.ISessie;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import userinterface.sessie.IDetails;
import userinterface.sessie.aankondiging.BeherenAankondigingController;
import userinterface.sessie.feedback.BeherenFeedbackController;
import userinterface.sessie.gebruiker.GebruikerBeherenController;
import userinterface.sessie.inschrijvingen.BeherenInschrijvingController;
import userinterface.sessie.lokaal.BeherenLokaalController;
import userinterface.sessie.media.BeherenMediaController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class SessieController extends AnchorPane implements IObserver {
    private DomeinController domeinController;
    private ILokaal tempLokaal;
    private IGebruiker tempGebruiker;
    private ISessie sessie;
    private Button temp;
    private List<IDetails> detailPanels;

    @FXML
    private AnchorPane apSessie, apSessieDetail;

    //region sessieTable FXML
    @FXML
    private ChoiceBox<String> choiceBoxMaand, choiceBoxJaar, choiceBoxZoeken, choiceBoxStad, choiceBoxStatus;
    @FXML
    private TextField txtSearchbar;

    @FXML
    private TableView<ISessie> table;
    @FXML
    private TableColumn<ISessie, String> titel;
    @FXML
    private TableColumn<ISessie, String> startSessie;
    @FXML
    private TableColumn<ISessie, String> maximumAantalPlaatsen;
    //endregion

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
    private TextField txtStart, txtEind;
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
    private CheckBox cbZichtbaar, cbNietZichtbaar;

    @FXML
    private Button btnBewerkenSessie, btnOpslaanSessie, btnOpenSessie, btnSluitSessie, btnVerwijderenSessie, btnToevoegenSessie, btnAnnuleer;

    @FXML
    private TextArea txtBeschrijving;
    //endregion

    //region Pane media, feedback, aankondiging en inschrijving FXML
    @FXML
    private RadioButton rabtnAankondiging, rabtnInschrijving, rabtnFeedback, rabtnMedia;

    @FXML
    private AnchorPane apRechts;
    //endregion

    public SessieController(DomeinController domeinController) {
        detailPanels = new ArrayList<>();
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
        activeerFilters();
        activeerButtons();
        activeerRadioButtons();
    }

    private void sessieTable() {
        table.getColumns().clear();

        titel.setCellValueFactory(new PropertyValueFactory<>("titel"));
        startSessie.setCellValueFactory(new PropertyValueFactory<>("datumString"));
        maximumAantalPlaatsen.setCellValueFactory(new PropertyValueFactory<>("maximumAantalPlaatsen"));

        table.setItems(FXCollections.observableArrayList(domeinController.geefNietGeopendeISessiesHuidigeKalender()));
        table.getColumns().addAll(titel, startSessie, maximumAantalPlaatsen);

        selectInTable();
        zoek();

    }

    private void selectInTable() {
        SessieController sc = this;
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ISessie>() {
            @Override
            public void changed(ObservableValue<? extends ISessie> observableValue, ISessie iSessie, ISessie t1) {
                if (iSessie != null) {
                    sessie = iSessie;
                }
                if (t1 != null) {
                    domeinController.setHuidigeISessie(t1);
                    domeinController.addObserver(sc);
                }
                choiceBoxMaand.setValue(domeinController.vergelijkMaanden());
                zetVeldenBewerken(false);
                vulDetails();
                verbergErrors();
            }
        });

        if (!table.getItems().isEmpty() || table.getItems() != null) {
            if (domeinController.geefHuidigeISessie() == null) {
                table.getSelectionModel().select(0);
            } else {
                table.getSelectionModel().select(domeinController.geefHuidigeISessie());
            }
        }
    }

    private void vulDetails() {
        lblTitelSessie.setText(domeinController.geefHuidigeISessie().getTitel());
        txtTitelSessie.setText(domeinController.geefHuidigeISessie().getTitel());
        dpStart.setValue(domeinController.geefHuidigeISessie().getDatum());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm");
        txtStart.setText(domeinController.geefHuidigeISessie().getStartUur().format(dtf));
        txtEind.setText(domeinController.geefHuidigeISessie().getEindeUur().format(dtf));
        txtGastsprekerSessie.setText(domeinController.geefHuidigeISessie().getNaamGastspreker());
        txtMaxPlaatsenSessie.setText(Integer.toString(domeinController.geefHuidigeISessie().getMaximumAantalPlaatsen()));
        tempGebruiker = domeinController.geefHuidigeISessie().getVerantwoordelijke();
        txtVerantwoordelijkeSessie.setText(domeinController.geefHuidigeISessie().getVerantwoordelijke().getNaam());
        txtLokaalSessie.setText(domeinController.geefHuidigeISessie().getLokaal().getLokaalCode());
        txtBeschrijving.setText(domeinController.geefHuidigeISessie().getBeschrijving());
        tempLokaal = domeinController.geefHuidigeISessie().getLokaal();
        setButtonsStandaard();
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
                } else {
                    txtMaxPlaatsenSessie.setEditable(true);
                }
            }
        });
        cbMax.setSelected(true);
        txtMaxPlaatsenSessie.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.BACK_SPACE)) {
                cbMax.setSelected(false);
            }
        });
        txtMaxPlaatsenSessie.textProperty().addListener((observableValue, s, t1) -> {
            if(txtMaxPlaatsenSessie != null) {
                if (!t1.isBlank() && !t1.isEmpty() && controleerMaxLokaal(t1)) {
                    cbMax.setSelected(true);
                }
            }
        });
        updateDetailsPanels();
    }


    private boolean controleerMaxLokaal(String s) {
        return domeinController.controleerMaxCapaciteitLokaal(Integer.parseInt(s), tempLokaal);
    }

    private void zetVeldenBewerken(Boolean b) {
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
    }

    private void bewerkenSessie(ActionEvent actionEvent) {
        zetVeldenBewerken(true);
        setButtonsBewerken();
        temp = (Button) actionEvent.getSource();
        table.setDisable(true);
        txtLokaalSessie.setOnMouseClicked(e -> toonLokalen());
        txtVerantwoordelijkeSessie.setOnMouseClicked(e -> toonGebruikers());
    }

    private void setButtonsStandaard() {
        if (domeinController.isZichtbaar()) {
            btnBewerkenSessie.setVisible(true);
            btnBewerkenSessie.setDisable(false);
            btnOpslaanSessie.setVisible(false);
            btnOpslaanSessie.setDisable(true);
            btnAnnuleer.setVisible(false);
            btnAnnuleer.setDisable(true);
            btnVerwijderenSessie.setDisable(false);
            btnVerwijderenSessie.setVisible(true);
            btnVerwijderenSessie.setDisable(true);
            if (domeinController.isSessieGesloten()) {
                btnBewerkenSessie.setDisable(true);
                btnVerwijderenSessie.setVisible(false);
                btnSluitSessie.setVisible(true);
                btnSluitSessie.setDisable(true);
                btnOpenSessie.setVisible(false);
                btnOpenSessie.setDisable(true);
            } else if (domeinController.isSessieOpen()) {
                btnBewerkenSessie.setDisable(true);
                btnVerwijderenSessie.setVisible(false);
                btnBewerkenSessie.setDisable(true);
                btnSluitSessie.setVisible(true);
                btnSluitSessie.setDisable(false);
                btnOpenSessie.setVisible(false);
                btnOpenSessie.setDisable(true);
            } else {
                btnBewerkenSessie.setVisible(true);
                btnBewerkenSessie.setDisable(false);
                btnSluitSessie.setVisible(false);
                btnSluitSessie.setDisable(true);
                btnOpenSessie.setVisible(true);
                btnOpenSessie.setDisable(false);
            }
        } else {
            btnSluitSessie.setVisible(false);
            btnSluitSessie.setDisable(true);
            btnOpenSessie.setVisible(false);
            btnOpenSessie.setDisable(true);
            btnBewerkenSessie.setVisible(true);
            btnBewerkenSessie.setDisable(false);
            btnOpslaanSessie.setVisible(false);
            btnOpslaanSessie.setDisable(true);
            btnAnnuleer.setVisible(false);
            btnAnnuleer.setDisable(true);
            btnVerwijderenSessie.setDisable(false);
            btnVerwijderenSessie.setVisible(true);
        }
    }

    private void setButtonsBewerken() {
        btnSluitSessie.setVisible(false);
        btnSluitSessie.setDisable(true);
        btnOpenSessie.setVisible(false);
        btnOpenSessie.setDisable(true);
        btnBewerkenSessie.setVisible(false);
        btnBewerkenSessie.setDisable(true);
        btnOpslaanSessie.setVisible(true);
        btnOpslaanSessie.setDisable(false);
        btnAnnuleer.setVisible(true);
        btnAnnuleer.setDisable(false);
        btnVerwijderenSessie.setDisable(true);
        btnVerwijderenSessie.setVisible(false);
    }

    private void verwijderenSessie(ActionEvent actionEvent) {
        String fout = domeinController.verwijderSessie(this.table.getSelectionModel().getSelectedItem(), sessie);
        if(fout.isBlank()){update();}
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Sessie kan niet meer worden verwijderd", ButtonType.OK);
            alert.setHeaderText("Sessie werd niet verwijderd!");
            alert.showAndWait();
        }
    }

    private void nieuweSessie(ActionEvent actionEvent) {
        resetTextvelden();
        verbergErrors();
        bewerkenSessie(actionEvent);
        tempLokaal = null;
        tempGebruiker = null;
    }

    private void opslaanSessie(ActionEvent actionEvent) {
        verbergErrors();
        List<String> veranderingen = new ArrayList<>();
        Map<String, String> fouten = new HashMap<>();
        try {
            veranderingen.add(0, tempGebruiker.getGebruikersnaam());
        } catch (NullPointerException np) {
            veranderingen.add(0, null);
        }
        veranderingen.add(1, txtTitelSessie.getText());
        veranderingen.add(2, dpStart.getValue().toString());
        zetTijdVelden();
        veranderingen.add(3, txtStart.getText());
        veranderingen.add(4, txtEind.getText());
        try {
            veranderingen.add(5, tempLokaal.getLokaalCode());
        } catch (NullPointerException np) {
            veranderingen.add(5, null);
        }
        veranderingen.add(6, txtGastsprekerSessie.getText());
        veranderingen.add(7, txtMaxPlaatsenSessie.getText());
        veranderingen.add(8, txtBeschrijving.getText());
        veranderingen.add(9, ((Boolean) cbZichtbaar.isSelected()).toString());

        if(temp.getId().equals("btnToevoegenSessie")){
            fouten.putAll(domeinController.errorVelden(veranderingen, true));
            //update();
        } else {
            fouten.putAll(domeinController.errorVelden(veranderingen, false));
        }
        if (fouten.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sessie met naam " + txtTitelSessie.getText() + " werd opgeslagen!", ButtonType.OK);
            alert.setHeaderText("Sessie opgeslagen");
            alert.showAndWait();
            zetVeldenBewerken(false);
            setButtonsStandaard();
            verbergErrors();
            table.setDisable(false);
            update();
        } else {
            for (Map.Entry<String, String> entry : fouten.entrySet()) {
                switch (entry.getKey()) {
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
                    case "start":
                    case "eind":
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
        }
/*        List<Node> n = vboxSessieDetail.getChildren();
        List<String> s = n.stream().filter(e -> e instanceof TextField).map(e -> ((TextField) e).getText()).collect(Collectors.toList());
        s.stream().forEach(e -> System.out.println(e));*/
    }

    private void zetTijdVelden(){
        if (txtStart.getText().matches("(\\d){1,2}:\\d")) {
            String txt = txtStart.getText();
            txt += "0";
            txtStart.setText(txt);
        } else if (txtStart.getText().matches("(\\d){1,2}:")) {
            String txt = txtStart.getText();
            txt += "00";
            txtStart.setText(txt);
        } else if (txtStart.getText().matches("(\\d){1,2}")) {
            String txt = txtStart.getText();
            txt += ":00";
            txtStart.setText(txt);
        }
        if (txtEind.getText().matches("(\\d){1,2}:\\d")) {
            String txt = txtEind.getText();
            txt += "0";
            txtEind.setText(txt);
        } else if (txtEind.getText().matches("(\\d){1,2}:")) {
            String txt = txtEind.getText();
            txt += "00";
            txtEind.setText(txt);
        } else if (txtEind.getText().matches("(\\d){1,2}")) {
            String txt = txtEind.getText();
            txt += ":00";
            txtEind.setText(txt);
        }
    }

    private void verbergErrors() {
        lblErrorMaxPlaatsen.setVisible(false);
        lblErrorLokaal.setVisible(false);
        lblErrorGastspreker.setVisible(false);
        lblErrorTitel.setVisible(false);
        lblErrorVerantwoordelijke.setVisible(false);
        lblErrorDatum.setVisible(false);
        lblErrorUur.setVisible(false);
    }

    private void resetTextvelden() {
        lblTitelSessie.setText("NIEUWE SESSIE");
        txtTitelSessie.clear();
        dpStart.setValue(LocalDate.now());
        txtStart.clear();
        txtEind.clear();
        txtVerantwoordelijkeSessie.clear();
        txtGastsprekerSessie.clear();
        txtLokaalSessie.clear();
        txtMaxPlaatsenSessie.clear();
        txtBeschrijving.clear();
        cbNietZichtbaar.setSelected(true);
    }

    private void activeerFilters() {
        choiceBoxJaar.setItems(FXCollections.observableArrayList(domeinController.geefAcademiejaren().stream().map(e -> e.getAcademiejaarString()).collect(Collectors.toList())));
        //choiceBoxJaar.setValue(choiceBoxJaar.getItems().get());
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
        choiceBoxMaand.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                //
            }
        });
        choiceBoxZoeken.setItems(FXCollections.observableArrayList(domeinController.geefFilterOpties()));
        choiceBoxZoeken.setValue(domeinController.geefFilterOpties().get(0));
        choiceBoxZoeken.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.equals("Titel")) {
                    txtSearchbar.setVisible(true);
                    txtSearchbar.setDisable(false);
                    choiceBoxStad.setVisible(false);
                    choiceBoxStad.setDisable(true);
                    choiceBoxStad.setVisible(false);
                    choiceBoxStad.setDisable(true);
                } else if (t1.equals("Stad")) {
                    txtSearchbar.setVisible(false);
                    txtSearchbar.setDisable(true);
                    choiceBoxStad.setVisible(true);
                    choiceBoxStad.setDisable(false);
                    choiceBoxStad.setVisible(false);
                    choiceBoxStad.setDisable(true);
                } else if (t1.equals("Status")) {
                    txtSearchbar.setVisible(false);
                    txtSearchbar.setDisable(true);
                    choiceBoxStad.setVisible(false);
                    choiceBoxStad.setDisable(true);
                    choiceBoxStad.setVisible(true);
                    choiceBoxStad.setDisable(false);
                }
            }
        });
    }

    private void activeerButtons() {
        btnBewerkenSessie.setOnAction(this::bewerkenSessie);
        btnOpslaanSessie.setOnAction(this::opslaanSessie);
        btnVerwijderenSessie.setOnAction(this::verwijderenSessie);
        btnToevoegenSessie.setOnAction(this::nieuweSessie);
        btnSluitSessie.setOnAction(this::sluitSessie);
        btnOpenSessie.setOnAction(this::openSessie);
        btnAnnuleer.setOnAction(this::annuleer);
    }

    private void annuleer(ActionEvent event) {
        setButtonsStandaard();
        zetVeldenBewerken(false);
        table.setDisable(false);
        vulDetails();
    }

    private void openSessie(ActionEvent actionEvent) {
        domeinController.veranderState("open");
    }

    private void sluitSessie(ActionEvent actionEvent) {
        domeinController.veranderState("gesloten");
    }

    private void toonGebruikers() {
        Scene scene = new Scene(new GebruikerBeherenController(domeinController, this));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void toonLokalen() {
        Scene scene = new Scene(new BeherenLokaalController(domeinController, this));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void activeerRadioButtons() {

        rabtnAankondiging.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    rabtnFeedback.setSelected(false);
                    rabtnInschrijving.setSelected(false);
                    rabtnMedia.setSelected(false);
                    BeherenAankondigingController d = new BeherenAankondigingController(domeinController);
                    detailPanels.add(d);
                    apRechts.getChildren().addAll(d);
                } else
                    apRechts.getChildren().remove(0);
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
                    BeherenFeedbackController d = new BeherenFeedbackController(domeinController);
                    detailPanels.add(d);
                    apRechts.getChildren().addAll(d);
                } else
                    apRechts.getChildren().remove(0);
            }
        });

        rabtnInschrijving.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    rabtnAankondiging.setSelected(false);
                    rabtnFeedback.setSelected(false);
                    rabtnMedia.setSelected(false);
                    BeherenInschrijvingController d = new BeherenInschrijvingController(domeinController);
                    detailPanels.add(d);
                    apRechts.getChildren().addAll(d);
                } else
                    apRechts.getChildren().remove(0);
            }
        });

        rabtnMedia.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    rabtnAankondiging.setSelected(false);
                    rabtnFeedback.setSelected(false);
                    rabtnInschrijving.setSelected(false);
                    BeherenMediaController d = new BeherenMediaController(domeinController);
                    detailPanels.add(d);
                    apRechts.getChildren().addAll(d);
                } else
                    apRechts.getChildren().remove(0);
            }
        });
    }

    private void updateDetailsPanels() {
        for(IDetails d : detailPanels){
            d.update();
        }
    }

    @Override
    public void update() {
        sessieTable();
    }

    public void setIGebruiker(IGebruiker gebruiker) {
        this.tempGebruiker = gebruiker;
        txtVerantwoordelijkeSessie.setText(gebruiker.getNaam());
    }

    public void setLokaal(ILokaal lokaal) {
        this.tempLokaal = lokaal;
        txtMaxPlaatsenSessie.setText(((Integer) tempLokaal.getAantalPlaatsen()).toString());
        txtLokaalSessie.setText(lokaal.getLokaalCode());
    }

    private void zoek(){
        ObservableList data =  table.getItems();
        txtSearchbar.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                table.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<ISessie> subentries = FXCollections.observableArrayList();

            long count = table.getColumns().stream().count();
            for (int i = 0; i < table.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + table.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(table.getItems().get(i));
                        break;
                    }
                }
            }
            table.setItems(subentries);
        });
    }
}