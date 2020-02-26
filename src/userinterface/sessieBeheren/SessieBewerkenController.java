package userinterface.sessieBeheren;

import domein.DomeinController;
import domein.interfacesDomein.ISessie;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


public class SessieBewerkenController extends BorderPane {
    private ISessie sessie;
    private DomeinController domeinController;
    private HashMap<String, String> veranderingenMap;
    //SessieBeherenController sessieBeherenController;

    @FXML
    private Label aanmakenBewerken;

    @FXML
    private TextField naamverantwoordelijke, titel, naamGastspreker, start, eind, maxPlaatsen;

    @FXML
    private ChoiceBox lokaal;

    @FXML
    private Button aankondigingen, media, gebruikers, feedback, toepassen, cancel;

    public SessieBewerkenController(ISessie sessie, DomeinController domeinController/*, SessieBeherenController sbc*/) {
        this.sessie = sessie;

        this.domeinController = domeinController;
        //this.sessieBeherenController = sbc;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SessieBewerken.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        if(sessie == null) {aanmakenBewerken.setText("Aanmaken");}
        else {geefDetails(sessie);}

        veranderingenMap = new HashMap<String, String>();
        naamverantwoordelijke.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                kijkenVoorAanpassingen("naamverantwoordelijke", t1);
            }
        });
        naamverantwoordelijke.setEditable(true);

        naamGastspreker.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                kijkenVoorAanpassingen("naamGastspreker", t1);
            }
        });
        naamGastspreker.setEditable(true);


        titel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                kijkenVoorAanpassingen("titel", t1);
            }
        });
        titel.setEditable(true);


//        lokaal.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                kijkenVoorAanpassingen("lokaal", t1);
//            }
//        });
//        lokaal.setEditable(true);

        lokaal.setItems(FXCollections.observableArrayList(domeinController.getLokalen()));
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
        toepassen.setOnAction(this::toepassen);
        cancel.setOnAction(this::cancel);
        media.setOnAction(this::media);
        aankondigingen.setOnAction(this::aankondigingen);
        gebruikers.setOnAction(this::gerbuikers);
        feedback.setDisable(true);
        if(sessie != null && sessie.isGeopend()){
            feedback.setDisable(false);
            feedback.setOnAction(this::feedback);
        }

    }

    public SessieBewerkenController(DomeinController domeinController/*, SessieBeherenController sbc*/) {
        this(null, domeinController/*, sbc*/);

    }

    private void kijkenVoorAanpassingen(String var, String t1) {
        veranderingenMap.put(var,t1);
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

    private void toepassen (ActionEvent actionEvent){

        if(sessie.getLokaal().getAantalPlaatsen() - (sessie.getLokaal().getAantalPlaatsen() - sessie.getBeschikbarePlaatsen()) < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "DUS JIJ WILT MIJN KOEKJE STELEN? MAG NIET!!",ButtonType.CLOSE);
            maxPlaatsen.setText(String.valueOf(sessie.getMaximumAantalPlaatsen()));
            veranderingenMap.put("maxPlaatsen", String.valueOf(sessie.getMaximumAantalPlaatsen()));
        }
        domeinController.pasSessieAan(sessie, veranderingenMap);
        Stage stage = (Stage) this.getScene().getWindow();
        stage.close();
        //sessieBeherenController.vulSchermOp();
    }

    private void media(ActionEvent actionEvent) {
        //nog te implementeren
    }

    private void aankondigingen(ActionEvent actionEvent) {
        //nog te implementeren
    }

    private void gerbuikers(ActionEvent actionEvent) {
        //nog te implementeren
    }

    private void feedback(ActionEvent actionEvent) {
        //nog te implementeren
    }

    private void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) this.getScene().getWindow();
        stage.close();
    }



}
