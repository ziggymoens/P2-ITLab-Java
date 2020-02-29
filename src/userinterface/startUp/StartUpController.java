package userinterface.startUp;

import domein.DomeinController;
import domein.PasswordUtils;
import domein.SessieKalender;
import domein.interfacesDomein.IGebruiker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.ITLab;
import userinterface.MAIN.MainScreenController;

import java.io.IOException;

public class StartUpController extends AnchorPane {

    private DomeinController domeinController;

    @FXML
    private Button inloggenButton;

    @FXML
    private TextField gebruikersNaam;

    @FXML
    private PasswordField wachtwoord;

    @FXML
    private ImageView logo;

    @FXML
    private Label error;

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
        gebruikersNaam.setText("itlab");
        inloggenButton.setOnAction(this::inloggen);
    }

    private void inloggen(ActionEvent actionEvent) {
        String gebruikersnaam = gebruikersNaam.getText();
        String ww = wachtwoord.getText();

        IGebruiker gebruiker = domeinController.geefIGebruikers().stream().filter(g -> g.getGebruikersnaam().equals(gebruikersnaam)).findFirst().orElse(null);

        if (gebruiker == null || !PasswordUtils.verifyUserPassword(ww, gebruiker.getWachtwoord())){
            error.setText("Gebruikersnaam en/of wachtwoord zijn ongeldig");
            wachtwoord.setText("");
        } else {
            ITLab.primaryStage.close();
            domeinController.setHuidigeGebruiker(gebruiker.getGebruikersnaam());
            Scene scene = new Scene(new MainScreenController(domeinController));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
    }
}
