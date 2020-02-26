package userinterface.sessieBeheren;

import domein.DomeinController;
import domein.interfacesDomein.ISessie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.format.DateTimeFormatter;


public class SessieBewerkenController extends BorderPane {
    private ISessie sessie;
    private DomeinController domeinController;

    @FXML
    private TextField naamverantwoordelijke, titel, naamGastspreker, lokaal, start, eind, maxPlaatsen;

    @FXML
    private Button aankondigingen, media, gerbuikers, feedback, toepassen, cancel;

    public SessieBewerkenController(ISessie sessie, DomeinController domeinController) {
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
       this.sessie = sessie;
        naamverantwoordelijke.setEditable(true);
        naamGastspreker.setEditable(true);
        titel.setEditable(true);
        lokaal.setEditable(true);
        start.setEditable(true);
        eind.setEditable(true);
        maxPlaatsen.setEditable(true);
        geefDetails(sessie);
        toepassen.setOnAction(this::pasSessieAan);
        cancel.setOnAction(this::cancel);
        media.setOnAction(this::media);
        aankondigingen.setOnAction(this::aankondigingen);
        gerbuikers.setOnAction(this::gerbuikers);
        feedback.setDisable(true);
        if(sessie.isGeopend()){
            feedback.setDisable(false);
            feedback.setOnAction(this::feedback);
        }

    }

    private void geefDetails(ISessie sessie) {
        naamverantwoordelijke.setText(sessie.getVerantwoordelijke().getNaam());
        titel.setText(sessie.getTitel());
        naamGastspreker.setText(sessie.getNaamGastspreker());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        start.setText(sessie.getStartSessie().format(formatter));
        eind.setText(sessie.getEindeSessie().format(formatter));
        maxPlaatsen.setText(String.valueOf(sessie.getMaximumAantalPlaatsen()));
    }

    private void pasSessieAan(ActionEvent actionEvent){
        domeinController.pasSessieAan();
    }

    private void media(ActionEvent actionEvent) {
    }

    private void aankondigingen(ActionEvent actionEvent) {
    }

    private void gerbuikers(ActionEvent actionEvent) {
    }

    private void feedback(ActionEvent actionEvent) {
    }

    private void cancel(ActionEvent actionEvent) {
    }

}
