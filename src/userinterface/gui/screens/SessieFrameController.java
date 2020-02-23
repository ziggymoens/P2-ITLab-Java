package userinterface.gui.screens;

import com.sun.javafx.binding.StringFormatter;
import domein.DomeinController;
import domein.domeinklassen.Feedback;
import domein.domeinklassen.Sessie;
import domein.interfacesDomein.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import userinterface.gui.components.DateTimePicker;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SessieFrameController extends AnchorPane {
    private final DomeinController domeinController;
    @FXML
    private Label sessieTitel;
    @FXML
    private TextField sessieTitelVeld;
    @FXML
    private Label naamGastspreker;
    @FXML
    private TextField naamGastsprekerVeld;
    @FXML
    private Label start;
    @FXML
    private DateTimePicker startPicker;
    @FXML
    private Label eind;
    @FXML
    private DateTimePicker eindPicker;
    @FXML
    private Label maxPlaatsen;
    @FXML
    private TextField maxPlaatsenVeld;
    @FXML
    private Label verantwoordelijke;
    @FXML
    private TextField verantwoordelijkeVeld;
    @FXML
    private Label media;
    @FXML
    private Button openMedia;
    @FXML
    private Label inschrijving;
    @FXML
    private Button openInschrijving;
    @FXML
    private Label aankondiging;
    @FXML
    private Button openAankodiging;
    @FXML
    private Label feedback;
    @FXML
    private Button openFeedback;
    @FXML
    private ListView<Sessie> sessieList;
    @FXML
    private Button button;

    @FXML
    private Label barcode;

    private ISessie currentSessie;


    public SessieFrameController(DomeinController domeinController) {
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SessieFrame.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        sessieTitel.setText("Titel");
        naamGastspreker.setText("Gastspreker");
        start.setText("Start sessie");
        eind.setText("Einde sessie");
        maxPlaatsen.setText("Maximum plaatsen");
        verantwoordelijke.setText("Verantwoordelijke");
        media.setText("Media");
        inschrijving.setText("Inschrijvingen");
        aankondiging.setText("Aankondigingen");
        feedback.setText("Feedback");

        sessieList.setItems(domeinController.getSessieObservableList());
        sessieList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        button.setOnAction(event -> geefDetails(event));
    }

    @FXML
    private void geefDetails(ActionEvent event) {
        ObservableList<Sessie> observableList = sessieList.getSelectionModel().getSelectedItems();
        vulDetailsIn(observableList);
        sessieList.getSelectionModel().clearSelection();
    }

    private void vulDetailsIn(ObservableList<Sessie> observableList) {
        currentSessie = observableList.get(0);
        sessieTitel.setText("Titel");
        sessieTitelVeld.setText(currentSessie.getTitel());
        naamGastspreker.setText("Gastspreker");
        naamGastsprekerVeld.setText(currentSessie.getNaamGastspreker());
        start.setText("Start sessie");
        startPicker.setDateTimeValue(currentSessie.getStartSessie());
        eind.setText("Einde sessie");
        eindPicker.setDateTimeValue(currentSessie.getEindeSessie());
        maxPlaatsen.setText("Maximum plaatsen");
        maxPlaatsenVeld.setText(String.format("%d", currentSessie.getMaximumAantalPlaatsen()));
        verantwoordelijke.setText("Verantwoordelijke");
        verantwoordelijkeVeld.setText(currentSessie.getVerantwoordelijke().getGebruikersnaam());
        media.setText("Media");
        openMedia.setOnAction(this::schermMedia);
        inschrijving.setText("Inschrijvingen");
        openInschrijving.setOnAction(this::schermInschrijvingen);
        aankondiging.setText("Aankondigingen");
        openAankodiging.setOnAction(this::schermAankondigingen);
        feedback.setText("Feedback");
        openFeedback.setOnAction(this::schermFeedback);
    }

    private void schermFeedback(ActionEvent event) {
        BorderPane borderPane = new BorderPane();
        ListView<IFeedback> feedbackListView = new ListView<>();
        ObservableList<IFeedback> observableList = FXCollections.observableArrayList(currentSessie.getIFeedbackSessie());
        feedbackListView.setItems(observableList);
        borderPane.setCenter(feedbackListView);
        Scene scene = new Scene(borderPane, 600, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Feedback");
        stage.show();
    }

    private void schermAankondigingen(ActionEvent event) {
        BorderPane borderPane = new BorderPane();
        ListView<IAankondiging> aankondigingListView = new ListView<>();
        ObservableList<IAankondiging> observableList = FXCollections.observableArrayList(currentSessie.getIAankondigingenSessie());
        aankondigingListView.setItems(observableList);
        borderPane.setCenter(aankondigingListView);
        Scene scene = new Scene(borderPane, 600, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Aankondigingen");
        stage.show();
    }

    private void schermInschrijvingen(ActionEvent event) {
        BorderPane borderPane = new BorderPane();
        ListView<IInschrijving> inschrijvingListView = new ListView<>();
        ObservableList<IInschrijving> observableList = FXCollections.observableArrayList(currentSessie.getIIngeschrevenGebruikers());
        inschrijvingListView.setItems(observableList);
        borderPane.setCenter(inschrijvingListView);
        Scene scene = new Scene(borderPane, 600, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Inschrijvingen");
        stage.show();
    }

    private void schermMedia(ActionEvent event) {
        BorderPane borderPane = new BorderPane();
        ListView<IMedia> mediaListView = new ListView<>();
        ObservableList<IMedia> observableList = FXCollections.observableArrayList(currentSessie.getIMediaBijSessie());
        mediaListView.setItems(observableList);
        borderPane.setCenter(mediaListView);
        Scene scene = new Scene(borderPane, 600, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Media");
        stage.show();
    }
}
