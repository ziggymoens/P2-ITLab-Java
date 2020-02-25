package userinterface.startUp;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.ITLab;
import userinterface.sessieBeheren.SessieBeherenController;

import java.io.IOException;

public class StartUpController extends BorderPane {

    private DomeinController domeinController;

    @FXML
    private Button inloggenButton, aanmeldenButton;

    public StartUpController(DomeinController domeinController) {
        this.domeinController = domeinController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartUp.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        inloggenButton.setOnAction(this::inloggen);
        aanmeldenButton.setOnAction(this::aanmelden);
    }

    private void inloggen(ActionEvent actionEvent) {
        ITLab.primaryStage.close();
        Scene scene = new Scene(new SessieBeherenController(domeinController));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    private void aanmelden(ActionEvent actionEvent) {

    }
}
