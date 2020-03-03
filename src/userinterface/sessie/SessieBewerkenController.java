package userinterface.sessie;

import domein.DomeinController;
import domein.interfacesDomein.ISessie;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import userinterface.aankondiging.BeherenAankondigingController;
import userinterface.feedback.BeherenFeedbackController;
import userinterface.inschrijvingen.BeherenInschrijvingenController;
import userinterface.media.BeherenMediaController;
import userinterface.main.MainScreenController;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.stream.Collectors;


public class SessieBewerkenController extends BorderPane {
    private ISessie sessie;
    private DomeinController domeinController;
    private HashMap<String, String> veranderingenMap;
    private MainScreenController mainScreenController;

    @FXML
    private Label tabelTitel, schermTitel, lblinschrijvingen, lblaankondigingen, lblmedia, lblfeedback;
    @FXML
    private TextField titel, naamGast, maxPlaatsen, start, eind, verantwoordelijke;
    @FXML
    private ChoiceBox<String> lokaal;
    @FXML
    private CheckBox geopend;
    @FXML
    private HBox hboxTable;
    @FXML
    private VBox vboxTable;
    @FXML
    private Button inschrijvingen, aankondigingen, media, feedback, nieuw, bewerken, save, cancel;

    public SessieBewerkenController(ISessie sessie, DomeinController domeinController, MainScreenController msc) {
        this.mainScreenController = msc;
        this.sessie = sessie;
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SessieBewerken.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        if(sessie == null) {schermTitel.setText("Aanmaken");}
        else {geefDetails(sessie);}

        veranderingenMap = new HashMap<String, String>();

        verantwoordelijke.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                kijkenVoorAanpassingen("naamverantwoordelijke", t1);
            }
        });
        verantwoordelijke.setEditable(true);

        naamGast.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                kijkenVoorAanpassingen("naamGastspreker", t1);
            }
        });
        naamGast.setEditable(true);


        titel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                kijkenVoorAanpassingen("titel", t1);
            }
        });
        titel.setEditable(true);

        ObservableList<String> lokaalCodes = FXCollections.observableArrayList(domeinController.geefILokalen().stream().map(k -> k.getLokaalCode()).collect(Collectors.toList()));
        lokaal.setItems(lokaalCodes);
        lokaal.setValue(lokaalCodes.stream().filter(e -> e.equals(this.domeinController.geefHuidigeISessie().getLokaal().getLokaalCode())).findFirst().orElse(null));
        lokaal.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                kijkenVoorAanpassingen("lokaal", t1.toString());
            }
        });

        start.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                kijkenVoorAanpassingen("start", t1);
            }
        });
        start.setEditable(true);

        eind.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                kijkenVoorAanpassingen("eind", t1);
            }
        });
        eind.setEditable(true);

        maxPlaatsen.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                kijkenVoorAanpassingen("maxPlaatsen", t1);
            }
        });
        maxPlaatsen.setEditable(true);

        lblaankondigingen.setText(lblaankondigingen.getText() + " " + domeinController.geefHuidigeISessie().getIAankondigingenSessie().size());
        lblfeedback.setText(lblfeedback.getText() + " " + domeinController.geefHuidigeISessie().getIFeedbackSessie().size());
        lblinschrijvingen.setText(lblinschrijvingen.getText() + " " + domeinController.geefHuidigeISessie().getIIngeschrevenGebruikers().size());
        lblmedia.setText(lblmedia.getText() + " " + domeinController.geefHuidigeISessie().getIMediaBijSessie().size());;

        maakTable("inschrijvingen");

        save.setOnAction(this::save);
        cancel.setOnAction(this::cancel);
        media.setOnAction(this::media);
        aankondigingen.setOnAction(this::aankondigingen);
        inschrijvingen.setOnAction(this::inschrijvingen);

        feedback.setDisable(true);
        if(sessie != null && sessie.isGeopend()){
            geopend.setSelected(true);
            feedback.setDisable(false);
            feedback.setOnAction(this::feedback);
        }

    }

    private void maakTable(String t) {
        if(vboxTable.getChildren().size() != 0) vboxTable.getChildren().remove(0);
        switch(t){
            case "inschrijvingen":
                vboxTable.getChildren().addAll(new BeherenInschrijvingenController(domeinController));
                break;

            case "media":
                vboxTable.getChildren().addAll(new BeherenMediaController(domeinController));
                break;

            case "aankondigingen":
                vboxTable.getChildren().addAll(new BeherenAankondigingController(domeinController));
                break;

            case "feedback":
                vboxTable.getChildren().addAll(new BeherenFeedbackController(domeinController));
                break;

        }


    }

    private void kijkenVoorAanpassingen(String var, String t1) {
        veranderingenMap.put(var,t1);
    }

    private void geefDetails(ISessie sessie) {
        verantwoordelijke.setText(sessie.getVerantwoordelijke().getNaam());
        titel.setText(sessie.getTitel());
        naamGast.setText(sessie.getNaamGastspreker());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        start.setText(sessie.getStartSessie().format(formatter));
        eind.setText(sessie.getEindeSessie().format(formatter));
        maxPlaatsen.setText(String.valueOf(sessie.getMaximumAantalPlaatsen()));
    }

    private void save (ActionEvent actionEvent){
/*        if(sessie.getLokaal().getAantalPlaatsen() - (sessie.getLokaal().getAantalPlaatsen() - sessie.getBeschikbarePlaatsen()) < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "DUS JIJ WILT MIJN KOEKJE STELEN? MAG NIET!!",ButtonType.CLOSE);
            maxPlaatsen.setText(String.valueOf(sessie.getMaximumAantalPlaatsen()));
            veranderingenMap.put("maxPlaatsen", String.valueOf(sessie.getMaximumAantalPlaatsen()));
        }*/
        domeinController.pasSessieAan(veranderingenMap);
        Tab selectedTab = mainScreenController.getTabPane().getTabs().remove(mainScreenController.getTabPane().getSelectionModel().getSelectedIndex());
    }

    private void media(ActionEvent actionEvent) {
        maakTable("media");
        //nog te implementeren
    }

    private void aankondigingen(ActionEvent actionEvent) {
        maakTable("aankondigingen");
        //nog te implementeren
    }

    private void inschrijvingen(ActionEvent actionEvent) {
        maakTable("inschrijvingen");
        //nog te implementeren
    }

    private void feedback(ActionEvent actionEvent) {
        maakTable("feedback");
        //nog te implementeren
    }

    private void cancel(ActionEvent actionEvent) {
        Tab selectedTab = mainScreenController.getTabPane().getTabs().remove(mainScreenController.getTabPane().getSelectionModel().getSelectedIndex());
    }



}
