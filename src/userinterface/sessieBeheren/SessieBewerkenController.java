package userinterface.sessieBeheren;

import domein.DomeinController;
import domein.interfacesDomein.IInschrijving;
import domein.interfacesDomein.ILokaal;
import domein.interfacesDomein.ISessie;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import userinterface.MAIN.OverzichtController;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


public class SessieBewerkenController extends BorderPane {
    private ISessie sessie;
    private DomeinController domeinController;
    private SessieTableViewController sessieTableViewController;
    private HashMap<String, String> veranderingenMap;

    @FXML
    private Label tabelTitel, schermTitel, lblinschrijvingen, lblaankondigingen, lblmedia, lblfeedback;
    @FXML
    private TextField titel, naamGast, maxPlaatsen, start, eind, verantwoordelijke;
    @FXML
    private ChoiceBox<ILokaal> lokaal;
    @FXML
    private CheckBox geopend;
    @FXML
    private VBox vboxTable;
    @FXML
    private Button inschrijvingen, aankondigingen, media, feedback, nieuw, bewerken, save, cancel;

    public SessieBewerkenController(ISessie sessie, DomeinController domeinController, SessieTableViewController stvc) {
        this.sessie = sessie;
        this.domeinController = domeinController;
        this.sessieTableViewController = stvc;
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

        lokaal.setItems(FXCollections.observableArrayList(domeinController.geefILokalen()));
        lokaal.setValue(domeinController.geefILokalen().stream().filter(lokaal -> lokaal.getLokaalCode().equals(this.domeinController.geefHuidigeISessie().getLokaal().getLokaalCode())).findFirst().orElse(null));
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

        vboxTable.getChildren().addAll(new OverzichtController<IInschrijving>());
        vboxTable.getChildren().get(0).minWidth(100);
        vboxTable.getChildren().get(0).maxWidth(100);
        vboxTable.getChildren().get(0).maxHeight(500);




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

    private void vulTable(String t) {
        /*table.getColumns().clear();
        switch(t){
            case "inschrijvingen":
                System.out.println(domeinController.geefHuidigeISessie());
                System.out.println(domeinController.geefHuidigeISessie().getIIngeschrevenGebruikers());
                TableColumn<Gebruiker, String> gebruiker = new TableColumn<>("Naam gebruiker");
                TableColumn<Inschrijving, String> datum = new TableColumn<>("Datum inschrijving");
                TableColumn<Inschrijving, String> statusAanwezigheid = new TableColumn<>("Status aanwezigheid");

                gebruiker.setCellValueFactory(new PropertyValueFactory<>("naam"));
                datum.setCellValueFactory(new PropertyValueFactory<>("inschrijvingsdatum"));
                statusAanwezigheid.setCellValueFactory(new PropertyValueFactory<>("statusAanwezigheid"));

                table = new TableView<>();
                table.setItems(domeinController.geefHuidigeISessie().getIIngeschrevenGebruikers());
                table.getColumns().addAll(gebruiker, datum, statusAanwezigheid);

                break;

            case "media":
                TableColumn<Media, MediaTypes> mediatype = new TableColumn<>("Type media");
                TableColumn<Media, String> locatie = new TableColumn<>("Bestandslocatie");
                mediatype.setCellValueFactory(new PropertyValueFactory<>("type"));
                locatie.setCellValueFactory(new PropertyValueFactory<>("locatie"));
                table = new TableView<IMedia>();
                table.setItems(domeinController.geefObservableListIMedia());
                table.getColumns().addAll(mediatype, locatie);
                break;

            case "aankondigingen":
                TableColumn<IAankondiging, LocalDateTime> publicatiedatum = new TableColumn<>("Publicatie datum");
                publicatiedatum.setCellValueFactory(new PropertyValueFactory<>("publicatiedatum"));
                TableColumn<IAankondiging, String> inhoud = new TableColumn<>("Inhoud");
                inhoud.setCellValueFactory(new PropertyValueFactory<>("inhoud"));
                TableColumn<IAankondiging, Boolean> automatischeHerinnering = new TableColumn<>("Automatische herinnering");
                automatischeHerinnering.setCellValueFactory(new PropertyValueFactory<>("automatischeHerinnering"));
                TableColumn<IAankondiging, Integer> dagenVooraf = new TableColumn<>("Aantal dagen vooraf");
                dagenVooraf.setCellValueFactory(new PropertyValueFactory<>("dagenVooraf"));
                table = new TableView<IAankondiging>();
                table.setItems(domeinController.geefHuidigeISessie().getIAankondigingenSessie());
                table.getColumns().addAll(publicatiedatum, inhoud, automatischeHerinnering);
                break;

            case "feedback":
                TableColumn<IFeedback, IGebruiker> gebr = new TableColumn<>("Naam gebruiker");
                gebr.setCellValueFactory(new PropertyValueFactory<>("gebruiker.getNaam()"));
                TableColumn<IFeedback, String> tekst = new TableColumn<>("Inhoud");
                tekst.setCellValueFactory(new PropertyValueFactory<>("tekst"));
                table = new TableView<IFeedback>();
                table.setItems(domeinController.geefHuidigeISessie().getIFeedbackSessie());
                table.getColumns().addAll(gebr, tekst);
                break;

        }*/


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
        Stage stage = (Stage) this.getScene().getWindow();
        stage.close();
    }

    private void media(ActionEvent actionEvent) {
        vulTable("media");
        //nog te implementeren
    }

    private void aankondigingen(ActionEvent actionEvent) {
        vulTable("aankondigingen");
        //nog te implementeren
    }

    private void inschrijvingen(ActionEvent actionEvent) {
        vulTable("inschrijvingen");
        //nog te implementeren
    }

    private void feedback(ActionEvent actionEvent) {
        vulTable("feedback");
        //nog te implementeren
    }

    private void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) this.getScene().getWindow();
        stage.close();
    }



}
