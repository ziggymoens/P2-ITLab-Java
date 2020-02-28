package userinterface.startUp;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.ITLab;
import userinterface.MAIN.MainScreenController;

import java.io.IOException;

public class StartUpController extends BorderPane {

    private DomeinController domeinController;

    @FXML
    private Button inloggenButton;

    @FXML
    private TextField gebruikersNaam;

    @FXML
    private PasswordField wachtwoord;

    @FXML
    private ImageView logo;

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
        Image image = new Image("storage/images/logoHoGent.png");
        this.logo.setImage(image);
        inloggenButton.setOnAction(this::inloggen);
    }

    private void inloggen(ActionEvent actionEvent) {
        ITLab.primaryStage.close();
        //hey
        domeinController.setHuidigeGebruiker(gebruikersNaam.getText().isBlank()? "758095zm": gebruikersNaam.getText());
        Scene scene = new Scene(new MainScreenController(domeinController));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }
}
