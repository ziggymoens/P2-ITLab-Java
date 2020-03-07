package userinterface.ongebruikt.sessie;

import domein.DomeinController;
import domein.interfacesDomein.ISessie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import userinterface.ongebruikt.aankondiging.BeherenAankondigingController;
import userinterface.ongebruikt.feedback.BeherenFeedbackController;
import userinterface.ongebruikt.inschrijvingen.BeherenInschrijvingenController;
import userinterface.ongebruikt.main.MainScreenController;
import userinterface.ongebruikt.media.BeherenMediaController;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


public class SessieAanmakenController extends BorderPane {
    private ISessie sessie;
    private DomeinController domeinController;
    private List<String> nieuweSessie;
    private MainScreenController mainScreenController;

    @FXML
    private Label tabelTitel;
    @FXML
    private Label lblinschrijvingen, lblaankondigingen, lblmedia, lblfeedback;
    @FXML
    private Label foutTitel, foutGast, foutMax, foutLokaal, foutStart, foutEind, foutVerantwoordelijke;
    @FXML
    private TextField titel, naamGast, maxPlaatsen, start, eind, verantwoordelijke;
    @FXML
    private ChoiceBox<String> lokaal;
    @FXML
    private CheckBox geopend;
    @FXML
    private VBox vboxTable;
    @FXML
    private Button inschrijvingen, aankondigingen, media, feedback, nieuw, bewerken, save, cancel;

    public SessieAanmakenController(DomeinController domeinController, MainScreenController msc) {
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

        verantwoordelijke.setEditable(true);
        naamGast.setEditable(true);
        titel.setEditable(true);
        start.setEditable(true);
        eind.setEditable(true);
        maxPlaatsen.setEditable(true);

        ObservableList<String> lokaalCodes = FXCollections.observableArrayList(domeinController.geefILokalen().stream().map(k -> k.getLokaalCode()).collect(Collectors.toList()));
        lokaal.setItems(lokaalCodes);
        lokaal.setValue(lokaalCodes.stream().filter(e -> e.equals("IT-Lab")).findFirst().orElse(lokaalCodes.get(0)));

        lblaankondigingen.setText(lblaankondigingen.getText() + " " + domeinController.geefHuidigeISessie().getIAankondigingenSessie().size());
        lblfeedback.setText(lblfeedback.getText() + " " + domeinController.geefHuidigeISessie().getIFeedbackSessie().size());
        lblinschrijvingen.setText(lblinschrijvingen.getText() + " " + domeinController.geefHuidigeISessie().getIIngeschrevenGebruikers().size());
        lblmedia.setText(lblmedia.getText() + " " + domeinController.geefHuidigeISessie().getIMediaBijSessie().size());

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
                vboxTable.getChildren().addAll(new BeherenAankondigingController(domeinController, mainScreenController));
                break;

            case "feedback":
                vboxTable.getChildren().addAll(new BeherenFeedbackController(domeinController));
                break;

        }
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
        if(verantwoordelijke.getText().isBlank()){
            foutVerantwoordelijke.setText("Verkeerde input");
        }
        if(naamGast.getText().isBlank()){
            foutGast.setText("Verkeerde input");
        }
        if(titel.getText().isBlank()){
            foutTitel.setText("Verkeerde input");
        }
        if(start.getText().isBlank()){
            foutStart.setText("Verkeerde input");
        }
        if(eind.getText().isBlank()){
            foutEind.setText("Verkeerde input");
        }
        if(maxPlaatsen.getText().isBlank()){
            foutMax.setText("Verkeerde input");
        } else{
        nieuweSessie.add(verantwoordelijke.getText());
        nieuweSessie.add(naamGast.getText());
        nieuweSessie.add(titel.getText());
        nieuweSessie.add(start.getText());
        nieuweSessie.add(eind.getText());
        nieuweSessie.add(maxPlaatsen.getText());
        domeinController.maakSessieAan(nieuweSessie);
        mainScreenController.getTabPane().getTabs().remove(mainScreenController.getTabPane().getSelectionModel().getSelectedIndex());
        }

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
        mainScreenController.getTabPane().getTabs().remove(mainScreenController.getTabPane().getSelectionModel().getSelectedIndex());
    }



}
