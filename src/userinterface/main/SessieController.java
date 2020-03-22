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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import userinterface.sessie.aankondiging.BeherenAankondigingController;
import userinterface.sessie.gebruiker.BeherenGebruikerController;
import userinterface.sessie.lokaal.BeherenLokaalController;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SessieController extends AnchorPane {
    private DomeinController domeinController;
    private ILokaal tempLokaal;
    private IGebruiker tempGebruiker;
    private ISessie sessie;
    private Boolean bewerkenStatus;

    //region sessieTable FXML
    @FXML
    private ChoiceBox<String> choiceBoxMaand, choiceBoxJaar, choiceBoxZoeken, choiceBoxStad, choiceBoxStatus;
    @FXML
    private Button btnToevoegenSessie;
    @FXML
    private TextField txtSearchBar;

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
    private TextField txtStart;
    @FXML
    private Label lblErrorStart;

    @FXML
    private DatePicker dpEind;
    @FXML
    private TextField txtEind;
    @FXML
    private Label lblErrorEind;

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
    private Button btnBewerkenSessie;
    @FXML
    private Button btnOpslaanSessie;
    @FXML
    private Button btnVerwijderenSessie;

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

        System.out.println(domeinController.geefISessiesHuidigeKalender());
        sessieTable();
        activeerFilters();
        activeerButtons();
        activeerRadioButtons();
    }

    private void sessieTable() {
        table.getColumns().clear();

        titel.setCellValueFactory(new PropertyValueFactory<>("titel"));
        startSessie.setCellValueFactory(new PropertyValueFactory<>("startSessie"));
        maximumAantalPlaatsen.setCellValueFactory(new PropertyValueFactory<>("maximumAantalPlaatsen"));

        table.setItems(FXCollections.observableArrayList(domeinController.geefISessiesHuidigeKalender()));
        table.getColumns().addAll(titel, startSessie, maximumAantalPlaatsen);

        selectInTable();
    }

    private void selectInTable() {
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ISessie>() {
            @Override
            public void changed(ObservableValue<? extends ISessie> observableValue, ISessie iSessie, ISessie t1) {
                if(iSessie != null){sessie = iSessie;}
                if(t1 != null){domeinController.setHuidigeISessie(t1);}
                choiceBoxMaand.setValue(domeinController.vergelijkMaanden());
                //zetVeldenBewerken(false);
                vulDetails();
            }
        });

        if (!table.getItems().isEmpty() || table.getItems() != null) {
            if(domeinController.geefHuidigeISessie() == null){
                table.getSelectionModel().select(0);
            }else{
                table.getSelectionModel().select(domeinController.geefHuidigeISessie());
            }
        }
    }

    private void vulDetails() {
        lblTitelSessie.setText(domeinController.geefHuidigeISessie().getTitel());
        System.out.println("titel");
        txtTitelSessie.setText(domeinController.geefHuidigeISessie().getTitel());
        System.out.println("titel2");
        dpStart.setValue(domeinController.geefHuidigeISessie().getStartDatum());
        System.out.println("startdate");
        System.out.println(domeinController.geefHuidigeISessie().getEindeDatum());
        dpEind.setValue(domeinController.geefHuidigeISessie().getEindeDatum());
        System.out.println("eindedate");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm");
        txtStart.setText(domeinController.geefHuidigeISessie().getStartUur().format(dtf));
        System.out.println("startuur");
        txtEind.setText(domeinController.geefHuidigeISessie().getEindeUur().format(dtf));
        System.out.println("einduur");
        txtMaxPlaatsenSessie.setText(Integer.toString(domeinController.geefHuidigeISessie().getMaximumAantalPlaatsen()));
        System.out.println("maxplaatsen");
        txtGastsprekerSessie.setText(domeinController.geefHuidigeISessie().getNaamGastspreker());
        System.out.println("gastspreker");
        //tempGebruiker = domeinController.geefHuidigeISessie().getVerantwoordelijke();
        System.out.println("tempgebruiker");
        txtVerantwoordelijkeSessie.setText(domeinController.geefHuidigeISessie().getVerantwoordelijke().getNaam());
        System.out.println("verantwoordelijke");
        txtLokaalSessie.setText(domeinController.geefHuidigeISessie().getLokaal().getLokaalCode());
        System.out.println("lokaal");
        txtBeschrijving.setText(domeinController.geefHuidigeISessie().getBeschrijving());
        System.out.println("beschrijving");
        tempLokaal = domeinController.geefHuidigeISessie().getLokaal();

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

    private boolean zetVeldenBewerken (Boolean b){
        btnBewerkenSessie.setDisable(b);
        btnBewerkenSessie.setVisible(!b);
        btnOpslaanSessie.setDisable(!b);
        btnOpslaanSessie.setVisible(b);
        cbMax.setDisable(!b);
        txtTitelSessie.setEditable(b);
        dpStart.setEditable(b);
        txtStart.setEditable(b);
        dpEind.setEditable(b);
        txtEind.setEditable(b);
        txtMaxPlaatsenSessie.setEditable(b);
        txtGastsprekerSessie.setEditable(b);
        txtVerantwoordelijkeSessie.setOnMouseClicked(this::openGebruikerPicker);
        txtBeschrijving.setEditable(b);
        return b;
    }

    private void openGebruikerPicker(MouseEvent mouseEvent) {
        new BeherenGebruikerController(domeinController);
    }


    private void bewerkenSessie(ActionEvent actionEvent) {
        bewerkenStatus = zetVeldenBewerken(true);
        txtLokaalSessie.setOnMouseClicked(e -> toonLokalen());
    }

    private void verwijderenSessie(ActionEvent actionEvent) {
        domeinController.verwijderSessie(this.table.getSelectionModel().getSelectedItem(), sessie);
        update();
    }

    private void opslaanSessie(ActionEvent actionEvent) {
        List<String> veranderingen = new ArrayList<>();
        veranderingen.add(0, tempGebruiker.getGebruikersnaam());
        veranderingen.add(1, txtTitelSessie.getText());
        veranderingen.add(2, dpStart.getValue().toString());
        veranderingen.add(3, txtStart.getText());
        veranderingen.add(4, dpEind.getValue().toString());
        veranderingen.add(5, txtEind.getText());
        veranderingen.add(6, tempLokaal.getLokaalCode());
        veranderingen.add(7, txtGastsprekerSessie.getText());
        veranderingen.add(8, txtMaxPlaatsenSessie.getText());
        Map<String, String> fouten = domeinController.controleerDataSessie(veranderingen);
        if(fouten.isEmpty()){
            domeinController.updateSessie(veranderingen);
            new Alert(Alert.AlertType.INFORMATION,"Sessie " + txtTitelSessie + " opgeslagen",ButtonType.OK);
            bewerkenStatus = zetVeldenBewerken(false);
            update();
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
                    case "start":
                        this.lblErrorStart.setText(entry.getValue());
                        this.lblErrorStart.setVisible(true);
                        break;
                    case "eind":
                        this.lblErrorEind.setText(entry.getValue());
                        this.lblErrorEind.setVisible(true);
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
        lblErrorEind.setVisible(false);
        lblErrorEind.setVisible(true);
    }

    private void nieuweSessie(ActionEvent actionEvent){}

    private void activeerFilters() {
        choiceBoxJaar.setItems(FXCollections.observableArrayList("2019 - 2020"));
        choiceBoxJaar.setValue(choiceBoxJaar.getItems().get(0));
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
                    txtSearchBar.setVisible(true);
                    txtSearchBar.setDisable(false);
                    choiceBoxStad.setVisible(false);
                    choiceBoxStad.setDisable(true);
                    choiceBoxStad.setVisible(false);
                    choiceBoxStad.setDisable(true);
                } else if(t1.equals("Stad")){
                    txtSearchBar.setVisible(false);
                    txtSearchBar.setDisable(true);
                    choiceBoxStad.setVisible(true);
                    choiceBoxStad.setDisable(false);
                    choiceBoxStad.setVisible(false);
                    choiceBoxStad.setDisable(true);
                } else if(t1.equals("Status")){
                    txtSearchBar.setVisible(false);
                    txtSearchBar.setDisable(true);
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
    }

    private void toonLokalen(){
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
                    apRechts.getChildren().addAll( new BeherenAankondigingController(domeinController));
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

    public void update() {
        sessieTable();
    }

    public void setIGerbuiker(IGebruiker gebruiker){
        this.tempGebruiker = gebruiker;
        txtVerantwoordelijkeSessie.setText(gebruiker.getNaam());
    }

    public void setLokaal (ILokaal lokaal){
        this.tempLokaal = lokaal;
        txtMaxPlaatsenSessie.setText(((Integer)tempLokaal.getAantalPlaatsen()).toString());
        txtLokaalSessie.setText(lokaal.getLokaalCode());
    }
}