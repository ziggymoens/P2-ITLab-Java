package userinterface.sessieBeheren;

import domein.DomeinController;
import domein.interfacesDomein.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import userinterface.aankondigingPlaatsen.AankondigingPlaatsenController;
import userinterface.media.NieuweMediaController;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class  InfoSessieController extends AnchorPane {
    private ISessie sessie;
    private DomeinController domeinController;

    @FXML
    private ListView<IMedia> listViewMedia;
    @FXML
    private ListView<IFeedback> listViewFeedback;
    @FXML
    private ListView<IInschrijving> listViewInschrijvingen;
    @FXML
    private ListView<IAankondiging> listViewAankondigingen;
    @FXML
    private Button infoMedia,nieuwMedia, bewerkenMedia, infoFeedback,nieuwFeedback,bewerkenFeedback, infoInschrijving, nieuwInschrijving,bewerkenInschrijving, infoAankondiging, voegAankondigingToe, bewerkenAankondiging;
    @FXML
    private TextField sessieId, naamGast, maxPlaatsen, lokaal, start, eind, verantwoordelijke, titel;
    @FXML
    private CheckBox geopend;

    public InfoSessieController(ISessie sessie, DomeinController domeinController) {
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoSessie.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        this.sessie = sessie;
        listViewMedia.setItems(FXCollections.observableArrayList(sessie.getIMediaBijSessie()));
        listViewMedia.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listViewFeedback.setItems(FXCollections.observableArrayList(sessie.getIFeedbackSessie()));
        listViewFeedback.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listViewAankondigingen.setItems(FXCollections.observableArrayList(sessie.getIAankondigingenSessie()));
        listViewAankondigingen.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listViewInschrijvingen.setItems(FXCollections.observableArrayList(sessie.getIIngeschrevenGebruikers()));
        listViewInschrijvingen.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        infoFeedback.setOnAction(this::infoFeedback);

        infoMedia.setOnAction(this::infoMedia);
        nieuwMedia.setOnAction(this::nieuwMedia);

        infoInschrijving.setOnAction(this::infoInschrijving);

        infoAankondiging.setOnAction(this::infoAankondiging);

        //voegAankondigingToe.setOnAction(this::voegAankondigingToe);

        invullenGegevens();

    }

    private void invullenGegevens() {
        sessieId.setText(sessie.getSessieId());
        titel.setText(sessie.getTitel());
        naamGast.setText(sessie.getNaamGastspreker());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        start.setText(sessie.getStartSessie().format(formatter));
        eind.setText(sessie.getEindeSessie().format(formatter));
        maxPlaatsen.setText(String.valueOf(sessie.getMaximumAantalPlaatsen()));
        lokaal.setText(sessie.getLokaal().getLokaalCode());
        verantwoordelijke.setText(sessie.getVerantwoordelijke().getNaam());
        geopend.setSelected(sessie.isGeopend());
    }

    private void nieuwMedia(ActionEvent event) {
        Scene scene = new Scene(new NieuweMediaController(domeinController, sessie));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private void infoAankondiging(ActionEvent actionEvent) {
        IAankondiging aankondiging = listViewAankondigingen.getSelectionModel().getSelectedItem();
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(new Label(aankondiging.toString_Compleet()));
        borderPane.setMinHeight(100);
        borderPane.setMinWidth(200);
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private void infoInschrijving(ActionEvent actionEvent) {
        IInschrijving inschrijving = listViewInschrijvingen.getSelectionModel().getSelectedItem();
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(new Label(inschrijving.toString_Compleet()));
        borderPane.setMinHeight(100);
        borderPane.setMinWidth(200);
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private void infoMedia(ActionEvent actionEvent) {
        IMedia media = listViewMedia.getSelectionModel().getSelectedItem();
        VBox vBox = new VBox(new Label(media.toString_Compleet()));
        WebView webview = null;
        ImageView imageView = null;
        if (media.getTypeString().equals("URL")) {
            /*MediaPlayer oracleVid = new MediaPlayer(
                    new Media("http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv")
            );
           vBox.getChildren().add(new MediaView(oracleVid));

             */
            /*oracleVid.play();
            webview = new WebView();
            webview.getEngine().load(
                    "https://www.youtube.com/embed/q7HkIwbj3CM"
            );
            webview.setPrefSize(640, 390);
            vBox.getChildren().add(webview);

             */
        }
        if(media.getTypeString().equals("FOTO")){
            imageView = new ImageView();
            Image image = new Image("storage/profielfotos/profielfoto.png");
            imageView.setImage(image);
            vBox.getChildren().add(imageView);
        }
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vBox);
        borderPane.setMinHeight(100);
        borderPane.setMinWidth(200);
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private void infoFeedback(ActionEvent actionEvent) {
        IFeedback feedback = listViewFeedback.getSelectionModel().getSelectedItem();
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(new Label(feedback.toString_Compleet()));
        borderPane.setMinHeight(100);
        borderPane.setMinWidth(200);
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    /*private void voegAankondigingToe(ActionEvent actionEvent){
        AankondigingPlaatsenController aankondigingPlaatsenController = new AankondigingPlaatsenController(domeinController, sessie);
        Scene scene = new Scene(aankondigingPlaatsenController);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }*/
}
