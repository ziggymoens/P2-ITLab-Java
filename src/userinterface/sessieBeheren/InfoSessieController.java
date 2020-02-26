package userinterface.sessieBeheren;

import domein.DomeinController;
import domein.interfacesDomein.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import userinterface.aankondigingPlaatsen.AankondigingPlaatsenController;
import userinterface.media.NieuweMediaController;

import java.io.IOException;

public class  InfoSessieController extends BorderPane {
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
    private Button infoMedia,nieuwMedia, bewerkenMedia, infoFeedback, infoInschrijving, infoAankondiging, voegAankondigingToe;

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
        listViewMedia.setItems(sessie.getIMediaBijSessie());
        listViewMedia.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listViewFeedback.setItems(sessie.getIFeedbackSessie());
        listViewFeedback.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listViewAankondigingen.setItems(sessie.getIAankondigingenSessie());
        listViewAankondigingen.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listViewInschrijvingen.setItems(sessie.getIIngeschrevenGebruikers());
        listViewInschrijvingen.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        infoFeedback.setOnAction(this::infoFeedback);

        infoMedia.setOnAction(this::infoMedia);
        nieuwMedia.setOnAction(this::nieuwMedia);

        infoInschrijving.setOnAction(this::infoInschrijving);

        infoAankondiging.setOnAction(this::infoAankondiging);

        voegAankondigingToe.setOnAction(this::voegAankondigingToe);

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
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(new Label(media.toString_Compleet()));
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

    private void voegAankondigingToe(ActionEvent actionEvent){
        Scene scene = new Scene(new AankondigingPlaatsenController());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
