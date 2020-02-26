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
    Button toepassen, cancel;

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
 /*        naamverantwoordelijke.setEditable(true);
        titel.setEditable(true);
        start.setEditable(true);
        eind.setEditable(true);
        maxPlaatsen.setEditable(true);
        geefDetails(sessie);
        toepassen.setOnAction(this::pasSessieAan);*/
    }

    private void geefDetails(ISessie sessie) {
        naamverantwoordelijke.setText(sessie.getVerantwoordelijke().getNaam());
        titel.setText(sessie.getTitel());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        start.setText(sessie.getStartSessie().format(formatter));
        eind.setText(sessie.getEindeSessie().format(formatter));
        maxPlaatsen.setText(String.valueOf(sessie.isGeopend()?sessie.getAantalAanwezigen():sessie.getBeschikbarePlaatsen()));
    }

    private void pasSessieAan(ActionEvent actionEvent){
        domeinController.pasSessieAan();
    }

}
